package by.kislyakoff.HomeBudgetApp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import by.kislyakoff.HomeBudgetApp.model.Person;
import by.kislyakoff.HomeBudgetApp.service.AdminService;
import by.kislyakoff.HomeBudgetApp.util.validators.PersonValidator;

@Controller
@RequestMapping("/homebudget/users")
public class AdminController {
	
	private final AdminService adminService;
	private final PersonValidator personValidator;

	@Autowired
	public AdminController(AdminService adminService, PersonValidator personValidator) {
		this.adminService = adminService;
		this.personValidator = personValidator;
	}
	
	@GetMapping
	public String usersList(Model model) {
		model.addAttribute("people", adminService.usersList());
		
		return "layouts/users";
	}
	
	@GetMapping("/create")
	public String newPerson(@ModelAttribute("person") Person person) {
		
		return "layouts/create";
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
		
		personValidator.validate(person, bindingResult);
		
		if (bindingResult.hasErrors())
			return "layouts/create";
		
		adminService.create(person);
		
		return "redirect:/homebudget/users";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		model.addAttribute("person", adminService.findById(id));
		return "layouts/edit";
	}
	
	@PatchMapping("/edit/{id}")
	public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
							@PathVariable Integer id) {
		
		personValidator.validate(person, bindingResult);
		
		if (bindingResult.hasErrors())
			
			return "layouts/edit";
		
		adminService.update(id, person);
		
		return "redirect:/homebudget/users";
	}
	
	@PatchMapping("/{activate}/{id}")
	@ResponseBody
	public ResponseEntity<HttpStatus> activate(@PathVariable Integer id,
												@PathVariable String activate) {
		Boolean toActivate = activate.equals("activate") ? true : false;
		adminService.activate(toActivate, id);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@DeleteMapping("/erase/{id}")
	@ResponseBody
	public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {
		
		adminService.delete(id);
		
		return ResponseEntity.ok(HttpStatus.OK);
	}

}
