package by.kislyakoff.HomeBudgetApp.model;

import java.util.List;

public final class CachedCbrCurrencyRates {

	private final List<CbrCurrencyRate> currencyRates;

	public CachedCbrCurrencyRates(List<CbrCurrencyRate> currencyRates) {
		this.currencyRates = currencyRates;
	}

	public List<CbrCurrencyRate> getCurrencyRates() {
		return currencyRates;
	}
}
