package by.kislyakoff.HomeBudgetApp.dto;

public class NbRbCurrencyRateDTO {

	private String Cur_Abbreviation;
	private Double Cur_OfficialRate;

	public String getCur_Abbreviation() {
		return Cur_Abbreviation;
	}

	public void setCur_Abbreviation(String cur_Abbreviation) {
		Cur_Abbreviation = cur_Abbreviation;
	}

	public Double getCur_OfficialRate() {
		return Cur_OfficialRate;
	}

	public void setCur_OfficialRate(Double cur_OfficialRate) {
		Cur_OfficialRate = cur_OfficialRate;
	}

}
