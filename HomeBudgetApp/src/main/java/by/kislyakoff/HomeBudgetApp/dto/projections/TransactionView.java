package by.kislyakoff.HomeBudgetApp.dto.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;

import by.kislyakoff.HomeBudgetApp.model.dict.CurrencyName;
import by.kislyakoff.HomeBudgetApp.model.dict.TransactionType;

public interface TransactionView {
	
	Long getId();
	
//	@Value("#{@transactionTypeConverter.convertToEntityAttribute(target.type)}")
	TransactionType getType();
	
	LocalDate getDate();
	
	AccountProjection getAcc1();
	
	AccountProjection getAcc2();
	
	interface AccountProjection {
		String getName();
		CurrencyName getCurrencyCode();
		BigDecimal getBalance();
	}
	
	BigDecimal getAmount();
	
	@Value("#{target.partner.name}")
	String getTransactionsPartnerName();
	
	PartnerView getPartner();
	
	interface PartnerView {
		
		String getName();
	}
	
	String getComment();
	
	CategoryView getCategory();
	
	interface CategoryView {
//		@Value("#{target.type}")
		String getName();
	}
}
