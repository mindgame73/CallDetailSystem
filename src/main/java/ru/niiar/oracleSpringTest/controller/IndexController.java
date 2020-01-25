package ru.niiar.oracleSpringTest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.niiar.oracleSpringTest.dao.RosDetalizationRepository;
import ru.niiar.oracleSpringTest.utils.RosDetalizationWrapper;


@Controller
@RequestMapping(path={"/","index"})
public class IndexController {

    @Autowired
    RosDetalizationRepository rosDetalizationRepository;

    @GetMapping("")
    public String getHomePage() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(){
        return "login";
    }

    @RequestMapping(value = "/charts", method = RequestMethod.GET)
    public String getHomeChartPage(){
        return "charts";
    }

    @GetMapping("/logout")
    public String getLogoutPage(){
        return "logout";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model) {
            String message ="У вас нет прав для доступа к этой странице!";
            model.addAttribute("message", message);
        return "403";
        }

    }
