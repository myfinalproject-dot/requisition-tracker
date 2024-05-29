package pt.dt.requisitiontracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pt.dt.requisitiontracker.model.User;
import pt.dt.requisitiontracker.service.UserService;

import javax.validation.Valid;

@Controller
public class RegisterController {

    private UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "forms/register";
    }
    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "forms/register";
        }

        boolean emailExists = userService.isUserEmailPresent(user.getEmail());
        boolean nameExists = userService.isUserNamePresent(user.getName());

        if (emailExists && nameExists) {
            model.addAttribute("exist", true);
            model.addAttribute("name_exist", true);
            model.addAttribute("user", user);
            return "forms/register";
        } else if (emailExists) {
            model.addAttribute("exist", true);
            model.addAttribute("user", user);
            return "forms/register";
        } else if (nameExists) {
            model.addAttribute("name_exist", true);
            model.addAttribute("user", user);
            return "forms/register";
        }

        userService.createUser(user);
        return "views/success";
    }

    /*@PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "forms/register";
        }

        if (userService.isUserEmailPresent(user.getEmail()) && userService.isUserNamePresent(user.getName())) {
            model.addAttribute("exist", true);
            model.addAttribute("name_exist", true);
            return "register";
        }
        userService.createUser(user);
        return "views/success";
    }*/

}
