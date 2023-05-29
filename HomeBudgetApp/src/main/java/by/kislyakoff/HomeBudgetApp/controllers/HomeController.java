package by.kislyakoff.HomeBudgetApp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import by.kislyakoff.HomeBudgetApp.dto.projections.BalanceByCurrencyView;
import by.kislyakoff.HomeBudgetApp.service.AccountsService;

@Controller
@RequestMapping("/homebudget")
public class HomeController {

	private final AccountsService accountsService;

	@Autowired
	public HomeController(AccountsService accountsService) {
		this.accountsService = accountsService;
	}

	@GetMapping
	public String home() {
		return "layouts/home";
	}

	@GetMapping("/balances")
	@ResponseBody
	public List<BalanceByCurrencyView> getBalance(@AuthenticationPrincipal(expression = "id") Integer ownerId) {
		return accountsService.getBalance(ownerId);
	}

}
