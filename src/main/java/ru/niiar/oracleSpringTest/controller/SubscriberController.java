package ru.niiar.oracleSpringTest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.niiar.oracleSpringTest.dao.DivisionRepository;
import ru.niiar.oracleSpringTest.dao.SubscriberRepository;
import ru.niiar.oracleSpringTest.model.Division;
import ru.niiar.oracleSpringTest.model.Subscriber;
import ru.niiar.oracleSpringTest.service.QueryDslService;

import java.util.*;

@Controller
public class SubscriberController {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private QueryDslService dslService;


    @RequestMapping(value = "/subscribers", method = RequestMethod.GET)
    public String getSubscribers(@RequestParam(required = false) String search,
                                 @RequestParam(required = false) Integer divId, Model model) {
        model.addAttribute("filterSub", new Subscriber());
        model.addAttribute("divisions", divisionRepository.findAllByOrderByDivisionNameAsc());
        if (divId != null){
            model.addAttribute("subscribers",subscriberRepository.findAllByDivision(divisionRepository.findById(divId).get()));
        }
        else if (search == null)
            model.addAttribute("subscribers", subscriberRepository.findAllByOrderByInternalNumAsc());
        else
        {
            if (!search.equals("")){
                try{
                    int int_num = Integer.parseInt(search);
                    long ext_num = 8423500000L +  Long.parseLong(search);
                    model.addAttribute("subscribers", subscriberRepository.findByInternalNumOrExternalNum(int_num,ext_num));
                    return "subscribers";
                }
                catch (NumberFormatException ex){
                    model.addAttribute("subscribers", subscriberRepository.findByFullNameContains(search));
                    return "subscribers";
                }
            }
            else{
                model.addAttribute("subscribers", subscriberRepository.findAllByOrderByInternalNumAsc());
            }
        }
        return "subscribers";
    }


    @RequestMapping(value = "/subscribers", method = RequestMethod.POST)
    public String getSubscriberFilter(@ModelAttribute("filterSub") Subscriber filterSub, Model model,
                                      BindingResult result, Errors errors){
        // необходимо реализовать валидатор для полей internulNum и externalNum для проверки numbericValues и Length
        List<Subscriber> subs = dslService.getSubscriberByFilter(filterSub);
        Set<Division> divisions = new TreeSet<>(Comparator.comparing(Division::getDivisionName));
        for (Subscriber sub : subs) {
            if (sub.getDivision() != null)
                divisions.add(sub.getDivision());
        }

        model.addAttribute("subscribers", subs);
        model.addAttribute("divisions", divisions );
        model.addAttribute("showFilter",true);
        return "subscribers";
    }

    @ResponseBody
    @RequestMapping(value = "/subscribers/edit", method = RequestMethod.GET)
    public Subscriber getEditSub(@RequestParam Integer id, Model model){
        Subscriber subscriber = subscriberRepository.findById(id).get();
        return subscriber;
    }


    @ResponseBody
    @RequestMapping(value="/subscribers/update", method = RequestMethod.POST)
        public Subscriber updateSubscriber(@RequestBody Subscriber subscriber){
        if (subscriber.getDivision().getDivision_id() == 0){
            subscriber.setDivision(null);
            subscriberRepository.save(subscriber);
        }
        else
        {
            subscriberRepository.save(subscriber);
        }
        return subscriber;
    }

    @ResponseBody
    @RequestMapping(value="/subscribers/create", method = RequestMethod.POST)
    public Subscriber createSubscriber(@RequestBody Subscriber subscriber){
        if (subscriber.getDivision().getDivision_id() == 0){
            subscriber.setDivision(null);
            subscriberRepository.save(subscriber);
        }
        else
        {
            subscriberRepository.save(subscriber);
        }
        return subscriber;
    }

    @ResponseBody
    @RequestMapping(value = "/subscribers/delete", method = RequestMethod.GET)
    public Subscriber getDeleteSub(@RequestParam Integer id, Model model){
        Subscriber subscriber = subscriberRepository.findById(id).get();
        return subscriber;
    }

    @ResponseBody
    @RequestMapping(value="/subscribers/delete", method = RequestMethod.POST)
    public Subscriber deleteSubscriber(@RequestBody Subscriber subscriber){
        subscriberRepository.deleteById(subscriber.getSub_id());
        return subscriber;
    }
}
