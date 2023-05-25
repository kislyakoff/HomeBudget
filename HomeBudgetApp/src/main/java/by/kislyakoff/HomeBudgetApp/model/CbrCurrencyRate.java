package by.kislyakoff.HomeBudgetApp.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public final class CbrCurrencyRate {

	private final String numCode;
	private final String charCode;
	private final Integer nominal;
	private final String name;
	private final Double value;

	@JsonCreator
	public CbrCurrencyRate(String numCode, String charCode, Integer nominal, String name, Double value) {
		this.numCode = numCode;
		this.charCode = charCode;
		this.nominal = nominal;
		this.name = name;
		this.value = value;
	}

	public String getNumCode() {
		return numCode;
	}

	public String getCharCode() {
		return charCode;
	}

	public Integer getNominal() {
		return nominal;
	}

	public String getName() {
		return name;
	}

	public Double getValue() {
		return value;
	}
	
	

}
