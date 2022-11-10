package by.kislyakoff.HomeBudgetApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.kislyakoff.HomeBudgetApp.model.Account;
import by.kislyakoff.HomeBudgetApp.model.dict.CurrencyName;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Integer> {
	
	List<Account> findByPersonIdOrderByNameAsc(Integer id);
	List<Account> findByPersonIdAndActiveTrueOrderByNameAsc(Integer id);
	boolean existsByNameAndCurrencyCode(String name, CurrencyName currency);
}
