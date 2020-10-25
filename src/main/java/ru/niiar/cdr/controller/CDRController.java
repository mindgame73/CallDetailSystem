package ru.niiar.cdr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.niiar.cdr.dao.CallDetailRecordRepository;
import ru.niiar.cdr.dao.DivisionRepository;
import ru.niiar.cdr.dao.SubscriberRepository;
import ru.niiar.cdr.model.CallDetailRecord;

@Controller
public class CDRController {
    @Autowired
    private CallDetailRecordRepository callDetailRecordRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private DivisionRepository divisionRepository;

    @RequestMapping(value = "/calldetails", method = RequestMethod.GET)
    public String getCallDetailRecords(@RequestParam(required = false) Integer id,
                                       @RequestParam(required = false) String search,
                                       @RequestParam(required = false) Integer divId,
                                       Model model) {
        CallDetailRecord cdr = new CallDetailRecord();
        if (divId != null){
            model.addAttribute("cdrs", callDetailRecordRepository
                    .findAllBySubscriberADivisionOrderByStartTime(divisionRepository.findById(divId).get()));
            return "calldetails";
        }
        else if (id != null) {
            model.addAttribute("cdrs", callDetailRecordRepository.findAllBySubscriberA(subscriberRepository.findById(id).get()));
            return "calldetails";
        } else {
            if (search == null) {
                model.addAttribute("cdrs", callDetailRecordRepository.findAllByOrderByStartTimeDesc());
                return "calldetails";
            } else {
                if (!search.equals("")) {
                    try {
                        long numB = Long.parseLong(search);
                        long numA = 8423500000L + Long.parseLong(search);
                        model.addAttribute("cdrs", callDetailRecordRepository.findAllByNumberAOrNumberB(numA, numB));
                        return "calldetails";
                    } catch (NumberFormatException ex) {
                        model.addAttribute("cdrs", callDetailRecordRepository.findAllBySubscriberAFullNameContains(search));
                        return "calldetails";
                    }
                } else {
                    model.addAttribute("cdrs", callDetailRecordRepository.findAllByOrderByStartTimeDesc());
                    return "calldetails";
                }
            }
        }
    }

}
