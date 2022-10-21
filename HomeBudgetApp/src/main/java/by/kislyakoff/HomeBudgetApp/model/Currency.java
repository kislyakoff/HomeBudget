package by.kislyakoff.HomeBudgetApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import by.kislyakoff.HomeBudgetApp.model.dict.CurrencyName;

@Entity
@Table(name = "currency")
public class Currency {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "Please specify currency")
	@Column(name = "currency_code")
	private CurrencyName name;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CurrencyName getName() {
		return name;
	}

	public void setName(CurrencyName name) {
		this.name = name;
	}

}
