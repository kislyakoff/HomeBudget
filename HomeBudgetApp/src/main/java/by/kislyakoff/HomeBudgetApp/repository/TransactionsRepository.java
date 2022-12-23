package by.kislyakoff.HomeBudgetApp.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import by.kislyakoff.HomeBudgetApp.dto.projections.TransactionSumView;
import by.kislyakoff.HomeBudgetApp.dto.projections.TransactionView;
import by.kislyakoff.HomeBudgetApp.model.Transaction;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {
	
	boolean existsByAcc1IdOrAcc2Id(int accId, int accId2);
//	List<Transaction> findByAcc1PersonId(int personId);
	List<TransactionView> findByAcc1PersonIdOrderByDateDesc(int personId);
	
	@Query(value = "SELECT transaction_type AS type, SUM(amount) AS amount FROM transaction WHERE acc1_id=?1 " +
					"GROUP BY transaction_type", nativeQuery = true)
	List<TransactionSumView> findAllSumByAcc1IdGroupByType(Integer id);
	
	
	@Query(value = "SELECT SUM(amount) FROM transaction WHERE acc2_id = ?1", nativeQuery = true)
	Optional<BigDecimal> findIncomeTransferSumByAcc2Id(Integer id);
	
	@Query(value = "SELECT (SUM(CASE WHEN acc1_id= ?1 AND transaction_type='I' THEN amount ELSE 0 END) + " +
							"SUM(CASE WHEN acc2_id=?1 AND transaction_type='T' THEN amount ELSE 0 END) - " +
							"SUM(CASE WHEN acc1_id=?1 AND transaction_type='E' THEN amount ELSE 0 END) - " +
							"SUM(CASE WHEN acc1_id=?1 AND transaction_type='T' THEN amount ELSE 0 END)) " +
							"AS amount FROM transaction", nativeQuery = true)
	Optional<BigDecimal> getBalanceByAccId(Integer id);
	
	long deleteByIdAndAcc1PersonId(long id, int ownerId);
	Optional<Transaction> findByIdAndAcc1PersonId(long id, int ownerId);

}
