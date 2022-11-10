package by.kislyakoff.HomeBudgetApp.dto;

import by.kislyakoff.HomeBudgetApp.model.dict.CurrencyName;

public class AccountDTO {
	
	private String id;
	
	private String name;
	
	private String description;
	
	private CurrencyName currencyCode;
	
	private Boolean active;
	
	private Boolean includeInTotal;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public CurrencyName getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(CurrencyName currencyCode) {
		this.currencyCode = currencyCode;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Boolean getIncludeInTotal() {
		return includeInTotal;
	}
	public void setIncludeInTotal(Boolean includeInTotal) {
		this.includeInTotal = includeInTotal;
	}
	
	

}
