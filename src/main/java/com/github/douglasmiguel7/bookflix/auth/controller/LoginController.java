package com.github.douglasmiguel7.bookflix.auth.controller;

import com.github.douglasmiguel7.bookflix.shared.controller.WebAppController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LoginController extends WebAppController {

	@GetMapping("/login")
	public String login(Principal principal) {

		if (principal != null) {
			return "redirect:/app";
		}

		return "auth/login";
	}

}
