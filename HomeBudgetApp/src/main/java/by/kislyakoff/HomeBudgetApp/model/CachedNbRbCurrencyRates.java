package by.kislyakoff.HomeBudgetApp.model;

import java.util.List;

public class CachedNbRbCurrencyRates {

	private final List<NbRbCurrencyRate> currencyRates;

	public CachedNbRbCurrencyRates(List<NbRbCurrencyRate> currencyRates) {
		this.currencyRates = currencyRates;
	}

	public List<NbRbCurrencyRate> getCurrencyRates() {
		return currencyRates;
	}

}
