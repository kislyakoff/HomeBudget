package by.kislyakoff.HomeBudgetApp.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import by.kislyakoff.HomeBudgetApp.dto.AccountDTO;
import by.kislyakoff.HomeBudgetApp.dto.projections.AccountView;
import by.kislyakoff.HomeBudgetApp.model.Account;
import by.kislyakoff.HomeBudgetApp.model.Person;
import by.kislyakoff.HomeBudgetApp.service.AccountsService;
import by.kislyakoff.HomeBudgetApp.util.validators.AccountValidator;

@Controller
@RequestMapping("/homebudget/accounts")
public class AccountsController {
	
	private final AccountsService accountsService;
	private final ModelMapper modelMapper;
	private final AccountValidator accountValidator;
	
	@Autowired
	public AccountsController(AccountsService accountsService, ModelMapper modelMapper, AccountValidator accountValidator) {
		this.accountsService = accountsService;
		this.modelMapper = modelMapper;
		this.accountValidator = accountValidator;
	}



	@GetMapping
	public String accountsList(@AuthenticationPrincipal(expression = "person") Person person,
			@RequestParam(name = "show", required = false) boolean showClosed , Model model) {
		
		model.addAttribute("show", showClosed);
		model.addAttribute("accounts", showClosed ? accountsService.accountsList(person.getId())
																		.stream()
																		.map(this::convertToAccountDTO)
																		.collect(Collectors.toList()) :
													accountsService.accountsListActive(person.getId())
																		.stream()
																		.map(this::convertToAccountDTO)
																		.collect(Collectors.toList()));
	
	
	return "layouts/accounts";
	}
	
	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<HttpStatus> create(@RequestBody AccountDTO accountDTO, BindingResult bindingResult,
								@AuthenticationPrincipal(expression = "person") Person person) {
		Account account = convertToAccount(accountDTO);
		
		accountValidator.validate(account, bindingResult);
		
		if (bindingResult.hasErrors())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		accountsService.create(account, person);
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@PatchMapping("edit/{id}")
	@ResponseBody
	public ResponseEntity<HttpStatus> edit(@PathVariable Integer id, @RequestBody AccountDTO accountDTO, 
			BindingResult bindingResult, @AuthenticationPrincipal(expression = "person") Person person) {
		Account account = convertToAccount(accountDTO);
		
		accountValidator.validate(account, bindingResult);
		
		if (bindingResult.hasErrors())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				
		accountsService.update(id, account, person);		
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@DeleteMapping("/erase/{id}")
	@ResponseBody
	public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {
		
		accountsService.delete(id);
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	

	@PatchMapping("/{include}/{id}")
	@ResponseBody
	public ResponseEntity<HttpStatus> includeInTotal(@PathVariable Integer id,
												@PathVariable String include) {
		Boolean toInclude = include.equals("include") ? true : false;
		accountsService.include(toInclude, id);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@PatchMapping("/to{open}/{id}")
	@ResponseBody
	public ResponseEntity<HttpStatus> activate(@PathVariable Integer id,
												@PathVariable String open) {
		Boolean toOpen = open.equals("open") ? true : false;
		accountsService.open(toOpen, id);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@GetMapping("/check/{id}")
	@ResponseBody
	public boolean checkIsEmpty(@PathVariable Integer id) {
		
		
		return accountsService.isEmpty(id);
	}
	
	@GetMapping("/list")
	@ResponseBody
	public List<AccountView> accountsListForTransaction(@AuthenticationPrincipal(expression = "id") Integer id) {
		
		return accountsService.accountsListActiveForTransaction(id);
	}
	
	private Account convertToAccount(AccountDTO accountDTO) {
		return modelMapper.map(accountDTO, Account.class);
	}
	
	private AccountDTO convertToAccountDTO(Account account) {
		return modelMapper.map(account, AccountDTO.class);
	}

}
