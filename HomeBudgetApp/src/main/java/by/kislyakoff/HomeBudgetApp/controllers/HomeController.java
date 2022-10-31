package by.kislyakoff.HomeBudgetApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/homebudget")
public class HomeController {
	
	@GetMapping
	public String home() {
		return "layouts/home";
	}

}
