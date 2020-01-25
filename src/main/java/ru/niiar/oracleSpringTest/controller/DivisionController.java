package ru.niiar.oracleSpringTest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.niiar.oracleSpringTest.dao.CallDetailRecordRepository;
import ru.niiar.oracleSpringTest.dao.DivisionRepository;
import ru.niiar.oracleSpringTest.dto.DivisionCountDto;
import ru.niiar.oracleSpringTest.model.CallDetailRecord;
import ru.niiar.oracleSpringTest.model.Division;
import ru.niiar.oracleSpringTest.service.QueryDslService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DivisionController {
    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    QueryDslService queryDslService;

    @Autowired
    CallDetailRecordRepository callDetailRecordRepository;

    @ResponseBody
    @RequestMapping(value="/divisions/ajax", method = RequestMethod.GET)
        public List<Division> getJSONDivisions(){
            return divisionRepository.findAllByOrderByDivisionNameAsc();
        }


    @RequestMapping(value = "/divisions", method = RequestMethod.GET)
    public String getDivisions(@RequestParam(required = false) String search,  Model model) {
        if (search == null){
            model.addAttribute("divisions", divisionRepository.findAllByOrderByDivisionNameAsc());
        }
        else
        {
            if (!search.equals("")){
                model.addAttribute("divisions", divisionRepository.findByDivisionNameContainingIgnoreCase(search));
            }
            else
            {
                model.addAttribute("divisions", divisionRepository.findAllByOrderByDivisionNameAsc());
            }
        }
        return "divisions";
    }

    @RequestMapping(value = "/divisions/charts", method = RequestMethod.GET)
    public String getChartView(@RequestParam(required = false) Integer id,
                               @RequestParam(required = false) String monthId,
                               @RequestParam(required = false) String year,
                               Model model){
        if (id == null || !divisionRepository.findById(id).isPresent()) {
            if (monthId == null && year == null)
                model.addAttribute("chartTitle","Анализ всех подразделений (Текущий месяц)");
            else
                model.addAttribute("chartTitle",String.format("Анализ всех подразделений (%s - %s)",monthId,year));

            List<DivisionCountDto> divisionCount = new ArrayList<>();
            for (Object[] countCall : queryDslService.getDivisionCountCalls(monthId,year)) {
                divisionCount.add(new DivisionCountDto((String) countCall[0], ((BigDecimal) countCall[1]).intValue()));
            }
            model.addAttribute("divisionCount", divisionCount);
            return "divisionchart";
        }
        else
        {
            Division selected = divisionRepository.findById(id).get();

            if (monthId == null && year == null)
                model.addAttribute("chartTitle",String.format("Анализ подразделения - %s (Текущий месяц)",selected.getDivisionName()));
            else
                model.addAttribute("chartTitle",String.format("Анализ подразделения - %s (%s - %s)",selected.getDivisionName(),monthId,year));

            List<DivisionCountDto> divisionCount = new ArrayList<>();
            for (Object[] countCall : queryDslService.getPeopleDivisionCountCalls(id, monthId, year)) {
                divisionCount.add(new DivisionCountDto((String) countCall[0], ((BigDecimal) countCall[1]).intValue()));
            }

            List<CallDetailRecord> cdrs = callDetailRecordRepository
                    .findAllBySubscriberDivision(selected);

            if (monthId != null && year != null) {
                String yearMonth = year.toString().concat("-").concat(monthId.toString());
                cdrs.removeIf(cdr -> !cdr.getStartTime().substring(0, 7).equals(yearMonth));
                if (cdrs.size() != 0)
                    model.addAttribute("showTable", true);
            }

            model.addAttribute("divisionCount", divisionCount);
            model.addAttribute("idParam", id);
            model.addAttribute("cdrs", cdrs);

            return "divisionchart";
        }
    }

}
