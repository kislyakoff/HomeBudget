package by.kislyakoff.HomeBudgetApp.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.kislyakoff.HomeBudgetApp.dto.projections.TransactionProjection;
import by.kislyakoff.HomeBudgetApp.dto.projections.TransactionProjection2;
import by.kislyakoff.HomeBudgetApp.dto.projections.TransactionSumView;
import by.kislyakoff.HomeBudgetApp.dto.projections.TransactionView;
import by.kislyakoff.HomeBudgetApp.model.Account;
import by.kislyakoff.HomeBudgetApp.model.Transaction;
import by.kislyakoff.HomeBudgetApp.repository.AccountsRepository;
import by.kislyakoff.HomeBudgetApp.repository.TransactionsRepository;

@Service
@Transactional(readOnly = true)
public class TransactionsService {
	
	public static int DEFAULT_TRANSACTIONS_PER_PAGE = 5;
	
	private final TransactionsRepository transactionsRepository;
	private final AccountsRepository accountsRepository;

	@Autowired
	public TransactionsService(TransactionsRepository transactionsRepository, AccountsRepository accountsRepository) {
		this.transactionsRepository = transactionsRepository;
		this.accountsRepository = accountsRepository;
	}
	
	public List<TransactionView> getList (int personId) {
		return transactionsRepository.findByAcc1PersonIdOrderByDateDesc(personId);
	}
	
	public Page<TransactionProjection> getTransactionsListPaginated(int personId, 
									String seek, int page, int transactionsPerPage) {
		
		return seek == null ? 
		transactionsRepository.findByAcc1PersonIdOrderByDateDescPaginated(
											personId, PageRequest.of(page, transactionsPerPage)) :
		  transactionsRepository.findByAcc1PersonIdOrderByDateDesc_WithSearchPaginated(
				  							personId, seek, PageRequest.of(page, transactionsPerPage));
	}
	
	public List<TransactionView> getList2 (int personId) {
		return transactionsRepository.findByAcc1PersonIdOrderByDateDescTest(personId);
	}
	
	public List<TransactionProjection2> getList3 (int personId) {
		return transactionsRepository.findByAcc1PersonIdOrderByDateDescTest1(personId);
	}
	
	@Transactional
	public boolean create(Transaction transaction, int ownerId) {
		Optional<Account> acc1 = accountsRepository.findByIdAndPersonId(transaction.getAcc1().getId(), ownerId);
		if(acc1.isPresent()) {
			Account account = acc1.get();
			switch(transaction.getType()) {
			case EXPENCE:
				account.setBalance(account.getBalance().subtract(transaction.getAmount()));
				break;
			case INCOME:
				account.setBalance(account.getBalance().add(transaction.getAmount()));
				break;
			case TRANSFER:
				Optional<Account> acc2 = accountsRepository
						.findByIdAndPersonId(transaction.getAcc2().getId(), ownerId);
				if(acc2.isPresent()) {
					Account account2 = acc2.get();
					account.setBalance(account.getBalance().subtract(transaction.getAmount()));
					account2.setBalance(account2.getBalance().add(transaction.getAmount()));
				}
				else
					return false;
				break;
			}
		}
		else
			return false;
		
		transactionsRepository.save(transaction);
		return true;
	}
	
	@Transactional
	public boolean update(long id, Transaction updatedTransaction, int ownerId) {
		Optional<Transaction> t = transactionsRepository.findByIdAndAcc1PersonId(id, ownerId);
		if (t.isEmpty() || id != updatedTransaction.getId()) {
			return false;
		} else {
			Optional<Account> acc1 = 
					accountsRepository.findByIdAndPersonId(updatedTransaction.getAcc1().getId(), ownerId);
			if(acc1.isEmpty() || !updatedTransaction.getType().equals(t.get().getType())) {
				return false;
			}
			else {
				BigDecimal oldAmount = t.get().getAmount();
				Account account = acc1.get();
				switch(updatedTransaction.getType()) {
				case EXPENCE:
					account.setBalance(account.getBalance().add(oldAmount).subtract(updatedTransaction.getAmount()));
					break;
				case INCOME:
					account.setBalance(account.getBalance().subtract(oldAmount).add(updatedTransaction.getAmount()));
					break;
				case TRANSFER:
					Optional<Account> acc2 = accountsRepository
							.findByIdAndPersonId(updatedTransaction.getAcc2().getId(), ownerId);
					if(acc2.isEmpty()) {
						return false;
					}
					else {
						Account account2 = acc2.get();
						account.setBalance(account.getBalance().add(oldAmount).subtract(updatedTransaction.getAmount()));
						account2.setBalance(account2.getBalance().subtract(oldAmount).add(updatedTransaction.getAmount()));
					}
						
					break;
				}
			}
				
			transactionsRepository.save(updatedTransaction);
			return true;
		}
	}
	
	// TODO just for test - pending to delete
	public List<TransactionSumView> list3(int id) {
		return transactionsRepository.findAllSumByAcc1IdGroupByType(id);
	}
	
	public BigDecimal getAccountBalance2(int id) {
		
		Optional<BigDecimal> incomeTransferSum = transactionsRepository.findIncomeTransferSumByAcc2Id(id);
		List<TransactionSumView> listSum = transactionsRepository.findAllSumByAcc1IdGroupByType(id);
		
//		BigDecimal sum = BigDecimal.ZERO;
//		
//		for (TransactionSumView list : listSum) {
//			switch (list.getType()) {
//			case "E":
//			case "T":
//				sum = sum.subtract(list.getAmount());
//				break;
//			case "I":
//				sum = sum.add(list.getAmount());
//				break;
//			}
//		}
		
		
		BigDecimal sum = listSum.stream()
			    .map(sumView -> "I".equals(sumView.getType()) ?
			        sumView.getAmount() : sumView.getAmount().negate()
			    )
			    .reduce(BigDecimal.ZERO, BigDecimal::add);
		
		
		if(incomeTransferSum.isPresent())
			sum = sum.add(incomeTransferSum.get());
		return sum;
	}
	public BigDecimal getAccountBalance(int id) {
		return transactionsRepository.getBalanceByAccId(id).orElse(BigDecimal.ZERO);
	}
	
	@Transactional
	public boolean delete(long id, int ownerId) {
		
		Optional<Transaction> transaction = transactionsRepository.findById(id);
		
		if(transaction.isEmpty() || transactionsRepository.deleteByIdAndAcc1PersonId(id, ownerId) !=1)
			return false;
		else {
			Account acc1 = transaction.get().getAcc1();
			BigDecimal amount = transaction.get().getAmount();
			switch(transaction.get().getType()) {
			case EXPENCE:
				acc1.setBalance(acc1.getBalance().add(amount));
				break;
			case INCOME:
				acc1.setBalance(acc1.getBalance().subtract(amount));
				break;
			case TRANSFER:
				Account acc2 = transaction.get().getAcc2();
				acc2.setBalance(acc2.getBalance().subtract(amount));
				acc1.setBalance(acc1.getBalance().add(amount));
				break;
			}
		}
		
		return true;
	}

}
