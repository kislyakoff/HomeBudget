package by.kislyakoff.HomeBudgetApp.dto.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;

import by.kislyakoff.HomeBudgetApp.model.dict.TransactionType;

public interface TransactionProjection {
	
	Long getId();
	
	@Value("#{@transactionTypeConverter.convertToEntityAttribute(target.type)}")
	TransactionType getType();
	
	LocalDate getDate();
	
	@Value("#{new by.kislyakoff.HomeBudgetApp.dto.projections.Acc1Projection(target.acc1Name, @currencyNameConverter.convertToEntityAttribute(target.acc1CurrencyCode), target.acc1Balance)}")
	Acc1Projection getAcc1();
	
	@Value("#{target.acc2Name != null ? new by.kislyakoff.HomeBudgetApp.dto.projections.Acc1Projection(target.acc2Name, @currencyNameConverter.convertToEntityAttribute(target.acc1CurrencyCode), target.acc2Balance) : null}")
	Acc1Projection getAcc2();
	
	BigDecimal getAmount();
	
	@Value("#{target.partnerName != null ? new by.kislyakoff.HomeBudgetApp.dto.projections.PartnerProjection(target.partnerName) : null}")
	PartnerProjection getPartner();
	
	String getComment();
	
	@Value("#{target.categoryName != null ? new by.kislyakoff.HomeBudgetApp.dto.projections.CategoryProjection(target.categoryName) : null}")
	CategoryProjection getCategory();
	
}
