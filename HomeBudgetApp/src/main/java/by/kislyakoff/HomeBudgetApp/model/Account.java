package by.kislyakoff.HomeBudgetApp.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import by.kislyakoff.HomeBudgetApp.model.dict.CurrencyName;

@Entity
@Table(name = "account")
public class Account {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name") 
	@NotBlank(message = "Please specify the name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", referencedColumnName = "id")
	private Person person;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_id", referencedColumnName = "id")
	private Currency currency;
	
	@Column(name = "currency_code")
	private CurrencyName currencyCode;
	
	@Column(name = "active")
	private Boolean active;
	
	@Column(name = "include_in_total")
	private Boolean includeInTotal;
	
	@Column(name = "balance")
	private BigDecimal balance;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
}
