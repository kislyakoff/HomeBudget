package by.kislyakoff.HomeBudgetApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.kislyakoff.HomeBudgetApp.model.Currency;
import by.kislyakoff.HomeBudgetApp.model.dict.CurrencyName;

@Repository
public interface CurrenciesRepository extends JpaRepository<Currency, Integer> {

	Currency findByName(CurrencyName name);
}
