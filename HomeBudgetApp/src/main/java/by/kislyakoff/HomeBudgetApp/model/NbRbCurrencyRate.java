package by.kislyakoff.HomeBudgetApp.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NbRbCurrencyRate {

	private final int id;

	private final LocalDateTime Date;
	private final String Cur_Abbreviation;
	private final int Cur_Scale;
	private final String Cur_Name;
	private final Double Cur_OfficialRate;

	@JsonCreator
	public NbRbCurrencyRate(@JsonProperty("Cur_ID") int id, @JsonProperty("Date") LocalDateTime date,
			@JsonProperty("Cur_Abbreviation") String cur_Abbreviation, @JsonProperty("Cur_Scale") int cur_Scale,
			@JsonProperty("Cur_Name") String cur_Name, @JsonProperty("Cur_OfficialRate") Double cur_OfficialRate) {
		this.id = id;
		Date = date;
		Cur_Abbreviation = cur_Abbreviation;
		Cur_Scale = cur_Scale;
		Cur_Name = cur_Name;
		Cur_OfficialRate = cur_OfficialRate;
	}

	public int getId() {
		return id;
	}

	public LocalDateTime getDate() {
		return Date;
	}

	public String getCur_Abbreviation() {
		return Cur_Abbreviation;
	}

	public int getCur_Scale() {
		return Cur_Scale;
	}

	public String getCur_Name() {
		return Cur_Name;
	}

	public Double getCur_OfficialRate() {
		return Cur_OfficialRate;
	}

}
