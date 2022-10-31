package by.kislyakoff.HomeBudgetApp.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import by.kislyakoff.HomeBudgetApp.model.Partner;
import by.kislyakoff.HomeBudgetApp.service.PartnersService;
import by.kislyakoff.HomeBudgetApp.util.helpers.PaginationHelper;
import by.kislyakoff.HomeBudgetApp.util.validators.PartnerValidator;

@Controller
@RequestMapping("/homebudget/partners")
public class PartnersController {
	
	private final PartnersService partnersService;
	private final PartnerValidator partnerValidator;
	
	@Autowired
	public PartnersController(PartnersService partnersService, PartnerValidator partnerValidator) {
		this.partnersService = partnersService;
		this.partnerValidator = partnerValidator;
	}

	@GetMapping({"", "/{page}"})
	public String partnersList(@PathVariable Optional<Integer> page, 
						@RequestParam(name = "seek", required = false) String seek, Model model) {
		
		Integer _page = 0;
		
		if (page.isPresent()) {
			_page = page.get() - 1;
		}
		Page<Partner> list = partnersService.partnersListPaginated(seek, _page, PartnersService.DEFAULT_PARTNERS_PER_PAGE,
				PartnersService.Order.NAME_ASC);
		
		
		model.addAttribute("partners", list.getContent());
		model.addAttribute("page", _page + 1);
		model.addAttribute("pagination", PaginationHelper.generate(_page + 1, list.getTotalPages()));
		model.addAttribute("seek", seek);
		return "layouts/partners";
	}
	
	@GetMapping("/create")
	public String newPartner(@ModelAttribute("partner") Partner partner) {
		return "layouts/create_partner";
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute("partner") @Valid Partner partner, BindingResult bindingResult) {
		
		partnerValidator.validate(partner, bindingResult);
		
		if (bindingResult.hasErrors())
			return "layouts/create_partner";
		
		partnersService.create(partner);
		
		return "redirect:/homebudget/partners";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		model.addAttribute("partner", partnersService.findById(id));
		return "layouts/edit_partner";
	}
	
	@PatchMapping("/edit/{id}")
	public String update(@ModelAttribute("partner") @Valid Partner partner, BindingResult bindingResult,
							@PathVariable Integer id) {
		
		partnerValidator.validate(partner, bindingResult);
		
		if (bindingResult.hasErrors())
			
			return "layouts/edit_partner";
		
		partnersService.update(id, partner);
		
		return "redirect:/homebudget/partners";
	}
	
	@DeleteMapping("/erase/{id}")
	@ResponseBody
	public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {
		
		partnersService.delete(id);
		
		return ResponseEntity.ok(HttpStatus.OK);
	}

}
