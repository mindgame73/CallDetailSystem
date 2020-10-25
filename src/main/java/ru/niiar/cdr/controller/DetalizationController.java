package ru.niiar.cdr.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import ru.niiar.cdr.dao.DivisionRepository;
import ru.niiar.cdr.dao.RosDetalizationRepository;
import ru.niiar.cdr.dao.SubscriberRepository;
import ru.niiar.cdr.dto.DivisionCountDto;
import ru.niiar.cdr.model.Division;
import ru.niiar.cdr.model.RosDetalization;
import ru.niiar.cdr.model.Subscriber;
import ru.niiar.cdr.service.QueryDslService;
import ru.niiar.cdr.utils.RosDetalizationWrapper;

import javax.persistence.NonUniqueResultException;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class DetalizationController {

    private static  List<RosDetalization> rosDetalizations;

    @Autowired
    private QueryDslService queryDslService;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private RosDetalizationRepository rosDetalizationRepository;

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String getView(){
        return "upload";
    }

    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public String getImportDataView(@RequestParam(required = false) String action, Model model){
        if (action == null){
            model.addAttribute("rosDetalizations", rosDetalizations);
        }
        else if (action.equals("import")){
            if (rosDetalizationRepository.findByDateTimeIsContaining(rosDetalizations.get(1).getDateTime()) == null){
               rosDetalizationRepository.saveAll(rosDetalizations); // требуется доработать проверку на существующие записи
            }
            else
            {
                model.addAttribute("error", "Данные уже были импортированы в систему");
                return "import";
            }
            rosDetalizations = null;
            model.addAttribute("welcomemsg","Данные были успешно занесены в систему.");
            return "success";
        }
        return "import";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    RedirectView handleFileUpload(@RequestParam("file") MultipartFile file){
        if (!file.isEmpty() && file.getOriginalFilename().endsWith(".csv")){
            try {
                File myFile = new File(file.getOriginalFilename());
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(myFile));
                stream.write(bytes);
                stream.close();
                rosDetalizations = new ArrayList<>();
                try (BufferedReader br = new BufferedReader(new FileReader(myFile))){
                    String line;
                    boolean isReadingBlock = false;
                    while((line = br.readLine()) != null){
                        if (line.contains("междугородных")) isReadingBlock = true;
                        if (line.contains("местных")) isReadingBlock = false;
                        if(isReadingBlock){
                            try {
                                      rosDetalizations.add(parseDetalization(line));
                            }
                            catch (NumberFormatException ex){
                                System.out.println("Ошибка конвертации " + line);
                            }
                            catch (IncorrectResultSizeDataAccessException ex){
                                System.out.println("Дубликат абонента " + line);
                            }
                            catch (ArrayIndexOutOfBoundsException ex){
                                System.out.println("Index 0 out of bounds for length 0 " + line);
                            }
                        }
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return new RedirectView("/import");
    }

    @RequestMapping(value = "/roschart", method = RequestMethod.GET)
    public String getChartView(@RequestParam(required = false) Integer id,
                               @RequestParam(required = false) String monthId,
                               @RequestParam(required = false) String year,
                               Model model){
        if (monthId == null)
            monthId = String.valueOf(LocalDate.now().getMonth().getValue());
        if (year == null)
            year = String.valueOf(LocalDate.now().getYear());

        int all_sum = 0;
        if (id == null) {
            List<DivisionCountDto> divisionCount = new ArrayList<>();
            for (Object[] countCall : queryDslService.getRosDetalizationCountCalls(monthId,year)) {
                all_sum+=((BigDecimal) countCall[1]).intValue();
                divisionCount.add(new DivisionCountDto((String) countCall[0], ((BigDecimal) countCall[1]).intValue()));
            }
            model.addAttribute("chartTitle",String.format("График затрат подразделений (%s - %s) | Общая сумма затрат составляет %s₽",monthId,year, all_sum));
            model.addAttribute("divisionCount", divisionCount);
            return "roschart";
        }
        else
        {
            Division selected = divisionRepository.findById(id).get();
            List<DivisionCountDto> divisionCount = new ArrayList<>();
            for (Object[] countCall : queryDslService.getPeopleRosDetalizationCountCalls(id, monthId, year)) {
                all_sum+=((BigDecimal) countCall[1]).intValue();
                divisionCount.add(new DivisionCountDto((String) countCall[0], ((BigDecimal) countCall[1]).intValue()));
            }

            model.addAttribute("chartTitle",String.format("Анализ затрат подразделения - %s (%s - %s) | Общая сумма затрат составляет %s₽",
                    selected.getDivisionName(),monthId,year,all_sum));

            List<RosDetalization> rosDetalizations = rosDetalizationRepository
                    .findAllBySubscriberDivision(selected);

                String yearMonth = year.concat("-").concat(monthId);
                rosDetalizations.removeIf(ros -> !ros.getDateTime().substring(0, 7).equals(yearMonth));


            model.addAttribute("showTable", true);
            model.addAttribute("divisionCount", divisionCount);
            model.addAttribute("idParam", id);
            model.addAttribute("rosDetalizations", rosDetalizations);
            return "roschart";
        }
    }

    @RequestMapping(value="/roschart/savexls", method = RequestMethod.GET)
        public RedirectView saveToXls(@RequestParam Integer id){
            Division division = divisionRepository.findById(id).get();
            RosDetalizationWrapper wrapper =
                    new RosDetalizationWrapper(rosDetalizationRepository.findAllBySubscriberDivision(division),
                            String.format("Детализация за %s", division.getDivisionName()));
            wrapper.saveToXLS();
            return new RedirectView("/download/xls/roschart.xls");
    }


    @RequestMapping(value = "/routechart", method = RequestMethod.GET)
    public String getRouteChartView(@RequestParam(required = false) String route,
                               @RequestParam(required = false) String monthId,
                               @RequestParam(required = false) String year,
                               Model model){
        if (route == null) {
            List<DivisionCountDto> divisionCount = new ArrayList<>();
            for (Object[] countCall : queryDslService.getAllRouteCount(monthId,year)) {
                divisionCount.add(new DivisionCountDto((String) countCall[0], ((BigDecimal) countCall[1]).intValue()));
            }
            if (monthId == null && year == null)
                model.addAttribute("chartTitle", "Диаграмма затрат по направлениям (Текущий месяц)");
            else
                model.addAttribute("chartTitle",String.format("Диаграмма затрат по направлениям (%s - %s)",monthId,year));
            model.addAttribute("divisionCount", divisionCount);
            return "routechart";
        }
        else
        {
            List<DivisionCountDto> divisionCount = new ArrayList<>();
            for (Object[] countCall : queryDslService.getCountByRouteName(route, monthId, year)) {
                divisionCount.add(new DivisionCountDto((String) countCall[0], ((BigDecimal) countCall[1]).intValue()));
            }

            if (monthId == null && year == null)
                model.addAttribute("chartTitle",String.format("Диаграмма затрат направления - %s (Текущий месяц)",
                        route));
            else
                model.addAttribute("chartTitle",String.format("Диаграмма затрат направления - %s (%s - %s)",
                        route,monthId,year));


            model.addAttribute("divisionCount", divisionCount);
            model.addAttribute("hidetable", false);
            return "routechart";
        }
    }

    private RosDetalization parseDetalization(String line)  throws NumberFormatException, NonUniqueResultException{
            String[] lineArray = line.split(";");

            RosDetalization rosDetalization = new RosDetalization();
            rosDetalization.setNumberA(Long.parseLong(lineArray[0]));
            rosDetalization.setDateTime(lineArray[1] + ":00");
            rosDetalization.setRoute(lineArray[3]);
            rosDetalization.setNumberB(Long.parseLong(lineArray[4]));
            rosDetalization.setDuration(Integer.parseInt(lineArray[5]));
            rosDetalization.setCost(Double.parseDouble(lineArray[6].replace(",",".")));
                Optional<Subscriber> sub = subscriberRepository.findByExternalNum(rosDetalization.getNumberA());
                sub.ifPresent(rosDetalization::setSubscriber);
            return rosDetalization;
    }
}
