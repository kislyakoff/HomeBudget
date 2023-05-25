package by.kislyakoff.HomeBudgetApp.dto.projections;

import java.math.BigDecimal;

import by.kislyakoff.HomeBudgetApp.model.dict.CurrencyName;

public interface BalanceByCurrencyView {
	
	CurrencyName getCurrencyCode();
	
	BigDecimal getBalanceSum();

}
