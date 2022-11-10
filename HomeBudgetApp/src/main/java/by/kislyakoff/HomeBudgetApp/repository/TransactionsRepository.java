package by.kislyakoff.HomeBudgetApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.kislyakoff.HomeBudgetApp.model.Transaction;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {
	
	boolean existsByAcc1IdOrAcc2Id(int accId, int accId2);

}
