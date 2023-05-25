package by.kislyakoff.HomeBudgetApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import by.kislyakoff.HomeBudgetApp.dto.projections.AccountView;
import by.kislyakoff.HomeBudgetApp.dto.projections.BalanceByCurrencyView;
import by.kislyakoff.HomeBudgetApp.model.Account;
import by.kislyakoff.HomeBudgetApp.model.dict.CurrencyName;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Integer> {
	
	List<Account> findByPersonIdOrderByNameAsc(int id);
	List<Account> findByPersonIdAndActiveTrueOrderByNameAsc(int id);
	boolean existsByNameAndCurrencyCodeAndPersonId(String name, CurrencyName currency, int id);
	List<AccountView> findByPersonIdAndActiveTrue(int id);
	boolean existsByPersonId(int id);
	boolean existsByNameAndActiveTrue(String name);
	Optional<Account> findByIdAndPersonId(int id, int ownerId);
	
	@Query("SELECT a.currencyCode AS currencyCode, SUM(a.balance) AS balanceSum "
			+ "FROM Account a WHERE a.person.id= :personId AND a.includeInTotal = TRUE GROUP BY a.currencyCode")
	List<BalanceByCurrencyView> getBalanceByPersonId(@Param("personId")int personId);
}
