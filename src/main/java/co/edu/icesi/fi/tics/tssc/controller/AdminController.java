package co.edu.icesi.fi.tics.tssc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}
}
