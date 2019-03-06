package com.github.douglasmiguel7.biblioteca.shared.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RedirectController {

	@RequestMapping
	public String redirect() {
		return "redirect:/app";
	}

}
