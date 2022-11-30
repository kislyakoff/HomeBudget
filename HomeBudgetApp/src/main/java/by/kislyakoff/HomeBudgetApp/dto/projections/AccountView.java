package by.kislyakoff.HomeBudgetApp.dto.projections;

import java.math.BigDecimal;

import by.kislyakoff.HomeBudgetApp.model.dict.CurrencyName;

public interface AccountView {
	
	Integer getId();
	
	String getName();
	
	CurrencyName getCurrencyCode();
	
	BigDecimal getBalance();

}
