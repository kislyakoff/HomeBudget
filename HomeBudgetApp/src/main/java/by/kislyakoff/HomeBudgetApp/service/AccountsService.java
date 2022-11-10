package by.kislyakoff.HomeBudgetApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.kislyakoff.HomeBudgetApp.model.Account;
import by.kislyakoff.HomeBudgetApp.model.Person;
import by.kislyakoff.HomeBudgetApp.model.dict.CurrencyName;
import by.kislyakoff.HomeBudgetApp.repository.AccountsRepository;
import by.kislyakoff.HomeBudgetApp.repository.CurrenciesRepository;
import by.kislyakoff.HomeBudgetApp.repository.TransactionsRepository;

@Service
@Transactional(readOnly = true)
public class AccountsService {
	
	private final AccountsRepository accountsRepository;
	private final TransactionsRepository transactionsRepository;
	private final CurrenciesRepository currenciesRepository;

	@Autowired
	public AccountsService(AccountsRepository accountsRepository, TransactionsRepository transactionsRepository, CurrenciesRepository currenciesRepository) {
		this.accountsRepository = accountsRepository;
		this.transactionsRepository = transactionsRepository;
		this.currenciesRepository = currenciesRepository;
	}
	
	public List<Account> accountsList(Integer id) {
		return accountsRepository.findByPersonIdOrderByNameAsc(id);
	}
	
	public List<Account> accountsListActive(Integer id) {
		return accountsRepository.findByPersonIdAndActiveTrueOrderByNameAsc(id);
	}

	@Transactional
	public void include(Boolean toInclude, Integer id) {
		
		Optional<Account> accToInclude = accountsRepository.findById(id);

		if(accToInclude.isPresent())
			accToInclude.get().setIncludeInTotal(toInclude);
	}
	
	public boolean isEmpty(int id) {
		return !transactionsRepository.existsByAcc1IdOrAcc2Id(id, id);
	}

	@Transactional
	public void create(Account account, Person person) {
		
		account.setPerson(person);
		account.setCurrency(currenciesRepository.findByName(account.getCurrencyCode()));
		accountsRepository.save(account);
		
	}

	@Transactional
	public void open(Boolean toOpen, Integer id) {
		Optional<Account> accToOpen = accountsRepository.findById(id);

		if(accToOpen.isPresent())
			accToOpen.get().setActive(toOpen);
		
	}
	@Transactional
	public void update(Integer id, Account updatedAccount, Person person) {
		updatedAccount.setId(id);
		updatedAccount.setPerson(person);
		updatedAccount.setCurrency(currenciesRepository.findByName(updatedAccount.getCurrencyCode()));
		accountsRepository.save(updatedAccount);
		
	}

	@Transactional
	public void delete(Integer id) {
		accountsRepository.deleteById(id);
	}
	
	public boolean isExists(String name, CurrencyName currency) {
		return accountsRepository.existsByNameAndCurrencyCode(name, currency);
	}
	
	public Account findById(int id) {
		return accountsRepository.findById(id).orElse(null);
	}
	

}
