package ru.niiar.cdr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.niiar.cdr.dto.UserDto;
import ru.niiar.cdr.exception.UserAlreadyExistException;
import ru.niiar.cdr.service.UserServiceImpl;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String getRegistrationPage(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("user") @Valid UserDto accountDto,
                                 BindingResult result, Errors errors) {
        ModelAndView modelAndView = new ModelAndView("registration", "user", accountDto);
        if (!accountDto.getPassword().equals(accountDto.getMatchingPassword()))
            modelAndView.addObject("passMatch", "Пароли не совпадают");

            if (result.hasErrors())
                return modelAndView;
            else {
                try {
                    userService.registerUser(accountDto);
                } catch (UserAlreadyExistException e) {
                    modelAndView.addObject("userExistMessage", "Ошибка. Такой пользователь уже существует!");
                    return modelAndView;
                }
            }
            return new ModelAndView("success","welcomemsg",
                    "Поздравляю вас! Вы успешно зарегистрировались в системе. Ожидайте одобрения вашей учетной записи администратором системы.");
    }

}
