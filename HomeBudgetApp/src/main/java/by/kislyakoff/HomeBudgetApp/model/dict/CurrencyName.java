package by.kislyakoff.HomeBudgetApp.model.dict;

public enum CurrencyName {
	
	BYN(933), USD(840), EUR(978), RUB(643);

	private Integer code;
	
	CurrencyName(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}
}
