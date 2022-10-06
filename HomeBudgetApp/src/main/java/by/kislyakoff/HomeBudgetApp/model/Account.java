package by.kislyakoff.HomeBudgetApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import by.kislyakoff.HomeBudgetApp.model.dict.CurrencyName;

@Entity
@Table(name = "account")
public class Account {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", referencedColumnName = "id")
	private Person person;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_id", referencedColumnName = "id")
	private Currency currency;
	
	@Column(name = "currency_name")
	@Enumerated(EnumType.ORDINAL)
	private CurrencyName currencyName;
	
	@Column(name = "active")
	private Boolean active;
	
	@Column(name = "includeInTotal")
	private Boolean includeInTotal;

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

	public CurrencyName getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(CurrencyName currencyName) {
		this.currencyName = currencyName;
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
