package ru.niiar.oracleSpringTest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.niiar.oracleSpringTest.dao.RoleRepository;
import ru.niiar.oracleSpringTest.dao.UserRepository;
import ru.niiar.oracleSpringTest.model.Role;
import ru.niiar.oracleSpringTest.model.User;

import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getAdminPage(Model model){
        model.addAttribute("users", userRepository.findAllByOrderByEnabledDesc());
        return "admin";
    }

    @ResponseBody
    @RequestMapping(value = "/admin/user", method = RequestMethod.GET)
    public User getUser(@RequestParam Integer id){
        return userRepository.findById(id).get();
    }

    @ResponseBody
    @RequestMapping(value = "/admin/user", method = RequestMethod.POST)
    public User deleteUser(@RequestBody User user){
        Optional<User> selected = userRepository.findById(user.getUser_id());
        if (selected.isPresent()){
            userRepository.delete(selected.get());
            return selected.get();
        }
        else
        {
            return null;
        }
    }

    @RequestMapping(value = "/admin/setrole", method = RequestMethod.GET)
    public RedirectView setRoles(@RequestParam Integer userId,
                                 @RequestParam String roleName,
                                 @RequestParam boolean flag){
        Optional<User> user = userRepository.findById(userId);
        Role role = roleRepository.findRoleByRoleName(roleName);
        if (flag){
            user.get().getRoles().add(role);
            userRepository.save(user.get());
        }
        else
        {
            user.get().getRoles().remove(role);
            userRepository.save(user.get());
        }

        return new RedirectView("/admin");
    }

    @RequestMapping(value="/admin/setenabled", method = RequestMethod.GET)
        public RedirectView setEnabled(@RequestParam Integer userId,
                                       @RequestParam boolean flag){

        Optional<User> user = userRepository.findById(userId);
        if (flag){
            user.get().setEnabled(true);
            userRepository.save(user.get());
        }
        else
        {
            user.get().setEnabled(false);
            userRepository.save(user.get());
        }
        return new RedirectView("/admin");
    }

}
