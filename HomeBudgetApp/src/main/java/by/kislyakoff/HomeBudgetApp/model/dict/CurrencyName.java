package by.kislyakoff.HomeBudgetApp.model.dict;

public enum CurrencyName {
	
	BYN(933), USD(840), EUR(978), RUB(643);

	private int code;
	
	CurrencyName(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
