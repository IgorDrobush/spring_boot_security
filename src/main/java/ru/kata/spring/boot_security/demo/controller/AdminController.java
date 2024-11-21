package ru.kata.spring.boot_security.demo.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private Set<Role> roles;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin/all_users")
    public String getAllUsers(ModelMap model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "all_users";
    }

    @GetMapping(value = "/admin/save_user_form")
    public String saveUserForm(ModelMap model) {
        roles = roleService.getAllRoles();
        model.addAttribute("user", new User());
        model.addAttribute("roles", roles);
        return "save_user_form";
    }

    @PostMapping(value = "/admin/save")
    public String saveUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            ModelMap model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roles);
            return "save_user_form";
        }

        try {
            userService.saveUser(user);
        } catch (RuntimeException e) {
            bindingResult.rejectValue(
                    "username",
                    "username.exists",
                    "Пользователь с таким именем уже существует"
            );
            model.addAttribute("roles", roles);
            return "save_user_form";
        }

        return "redirect:/admin/all_users";
    }

    @GetMapping(value = "/admin/delete")
    public String deleteUser(@RequestParam(value = "id", required = false) Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/all_users";
    }

    @GetMapping(value = "/admin/update_user_form")
    public String updateUserForm(
            @RequestParam(value = "id", required = false) Long id,
            ModelMap model
    ) {
        User user = userService.findUserById(id);
        roles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "update_user_form";
    }

    @PostMapping(value = "/admin/update")
    public String updateUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            ModelMap model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roles);
            return "update_user_form";
        }

        userService.updateUser(user);
        return "redirect:/admin/all_users";
    }
}
