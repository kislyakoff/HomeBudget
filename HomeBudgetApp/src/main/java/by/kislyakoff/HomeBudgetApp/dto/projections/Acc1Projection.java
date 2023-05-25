package by.kislyakoff.HomeBudgetApp.dto.projections;

import java.math.BigDecimal;

import by.kislyakoff.HomeBudgetApp.model.dict.CurrencyName;

public class Acc1Projection {
	
	private String name;
	
	private CurrencyName currencyCode;
	
	private BigDecimal balance;
	
	public Acc1Projection(String name, CurrencyName currencyCode, BigDecimal balance) {
		this.name = name;
		this.currencyCode = currencyCode;
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CurrencyName getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(CurrencyName currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Acc1Projection [name=" + name + ", currencyCode=" + currencyCode + ", balance=" + balance + "]";
	}
	
	
	
	
	
	
}
