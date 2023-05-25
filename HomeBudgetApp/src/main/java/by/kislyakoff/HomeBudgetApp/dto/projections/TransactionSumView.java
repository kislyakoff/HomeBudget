package by.kislyakoff.HomeBudgetApp.dto.projections;

import java.math.BigDecimal;

public interface TransactionSumView {
	
	// use String but not TransactionType enum because AttributeConverter doesn't work 
	// with native query for interface projections and this "hack" works
	String getType();
	
	BigDecimal getAmount();

}
