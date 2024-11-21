package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;

@Controller
public class UserController {

	@GetMapping("/user")
	public String getProfile(@AuthenticationPrincipal User user, Model model) {
		System.out.println("Сработал getProfile, User: " + user);
		model.addAttribute("user", user);
		return "user";
	}
}