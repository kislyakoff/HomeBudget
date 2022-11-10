package by.kislyakoff.HomeBudgetApp.util.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import by.kislyakoff.HomeBudgetApp.model.Account;
import by.kislyakoff.HomeBudgetApp.service.AccountsService;

@Component
public class AccountValidator implements Validator {
	
	private final AccountsService accountsService;
	
	

	public AccountValidator(AccountsService accountsService) {
		this.accountsService = accountsService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Account.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Account account = (Account) target;
		
		boolean isExists = accountsService.isExists(account.getName(), account.getCurrencyCode());
		
		// validate for a new account
		if (account.getId() == null && isExists)
			errors.reject(null, "Счет с таким именем и валютой уже существует");
		
		// validate when update account with existed name&currency and ignore error with own name&currency
		
		else if (account.getId() != null) {
			Account accToCheck = accountsService.findById(account.getId());
			boolean isEquals = account.getName().equals(accToCheck.getName()) && 
							account.getCurrencyCode() == accToCheck.getCurrencyCode();
			if (isExists && !isEquals)
				errors.reject(null, "Счет с таким именем и валютой уже существует");
		}
			
			
	}

}
