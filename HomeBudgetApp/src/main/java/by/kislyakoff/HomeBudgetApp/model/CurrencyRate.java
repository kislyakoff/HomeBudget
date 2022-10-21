package by.kislyakoff.HomeBudgetApp.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "currency_rate")
public class CurrencyRate {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "rate_date")
	private LocalDateTime date;
	
	@ManyToOne
	@JoinColumn(name = "currency_id", referencedColumnName = "id")
	private Currency currency;
	
	@Column(name = "rate")
	private BigDecimal rate;
	
	@Column(name = "rate_scale")
	private Integer rateScale;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Integer getRateScale() {
		return rateScale;
	}

	public void setRateScale(Integer rateScale) {
		this.rateScale = rateScale;
	}
	
	

}
