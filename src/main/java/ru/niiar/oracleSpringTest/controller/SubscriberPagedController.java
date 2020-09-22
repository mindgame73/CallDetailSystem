package ru.niiar.oracleSpringTest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.niiar.oracleSpringTest.model.Subscriber;
import ru.niiar.oracleSpringTest.service.SubscriberService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class SubscriberPagedController {

    @Autowired
    private SubscriberService subscriberService;

    @RequestMapping(value = "/subscribers-paged", method = RequestMethod.GET)
    public String subToList(Model model,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size,
                            @PageableDefault(value = 100) Pageable pageable) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(pageable.getPageSize());

        Page<Subscriber> subPage = subscriberService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("subPage", subPage);

        int totalPages = subPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "subList.html";
    }

}
