package by.kislyakoff.HomeBudgetApp.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.SqlResultSetMapping;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import by.kislyakoff.HomeBudgetApp.dto.projections.TransactionProjection;
import by.kislyakoff.HomeBudgetApp.dto.projections.TransactionProjection2;
import by.kislyakoff.HomeBudgetApp.dto.projections.TransactionSumView;
import by.kislyakoff.HomeBudgetApp.dto.projections.TransactionView;
import by.kislyakoff.HomeBudgetApp.model.Transaction;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {

    boolean existsByAcc1IdOrAcc2Id(int accId, int accId2);
//      List<Transaction> findByAcc1PersonId(int personId);
    List<TransactionView> findByAcc1PersonIdOrderByDateDesc(int personId);


//      Page<TransactionView>
//              findByAcc1NameContainingOrAcc2NameContainingOrPartnerNameContainingOrCategoryNameContainingOrCommentContainingAndAcc1PersonIdOrderByDateDesc
//                      (String seek, String seek2, int personId, Pageable pageable);

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


//      @Query(value = "SELECT t.id AS id, " +
//                                 "t.transaction_type AS type, " +
//                                 "t.transaction_date AS date, " +
//                                 "t.amount AS amount, " +
//                                 "t.comment AS comment, " +
//                                 "a1.balance AS acc1Balance, " +
//                                 "a1.currency_code AS acc1CurrencyCode, " +
//                                 "a1.name AS acc1Name, " +
//                                 "a2.balance AS acc2Balance, " +
//                                 "a2.currency_code AS acc2CurrencyCode, " +
//                                 "a2.name AS acc2Name, " +
//                                 "par.name AS partnerName, " +
//                                 "cat.name AS categoryName, " +
//                                 "cat.category_type AS categoryType " +
//                                 "FROM transaction t " +
//                                 "LEFT OUTER JOIN account a1 ON t.acc1_id=a1.id " +
//                                 "LEFT OUTER JOIN person per ON a1.person_id=per.id " +
//                                 "LEFT OUTER JOIN account a2 ON t.acc2_id=a2.id " +
//                                 "LEFT OUTER JOIN partner par ON t.partner_id=par.id " +
//                                 "LEFT OUTER JOIN category cat ON t.category_id=cat.id " +
//                                 "WHERE per.id=?1 ORDER BY t.transaction_date DESC", nativeQuery = true)
//      List<TransactionView> findByAcc1PersonIdOrderByDateDescTest(int personId);

    @Query("SELECT t.id AS id, " +
            "t.type AS type, " +
            "t.date AS date, " +
            "t.amount AS amount, " +
            "t.comment AS comment, " +
            "t.acc1.balance AS acc1Balance, " +
            "t.acc1.currencyCode AS acc1CurrencyCode, " +
            "t.acc1.name AS acc1Name, " +
            "t.acc2.balance AS acc2Balance, " +
            "t.acc2.currencyCode AS acc2CurrencyCode, " +
            "t.acc2.name AS acc2Name, " +
            "t.partner.name AS partnerName, " +
            "t.category.name AS categoryName, " +
            "t.category.type AS category " +
            "FROM Transaction t " +
            "LEFT JOIN t.acc1 " +
            "LEFT JOIN t.acc1.person " +
            "LEFT JOIN t.acc2 " +
            "LEFT JOIN t.partner " +
            "LEFT JOIN t.category " +
            "WHERE t.acc1.person.id= :personId ORDER BY t.date DESC")
    List<TransactionView> findByAcc1PersonIdOrderByDateDescTest(@Param("personId")int personId);

//      @Query(value = "SELECT t.id AS id, " +
//                         "t.transaction_type AS type, " +
//                         "t.transaction_date AS date, " +
//                         "t.amount AS amount, " +
//                         "t.comment AS comment, " +
//                         "a1.balance AS acc1Balance, " +
//                         "a1.currency_code AS acc1CurrencyCode, " +
//                         "a1.name AS acc1Name, " +
//                         "a2.balance AS acc2Balance, " +
//                         "a2.currency_code AS acc2CurrencyCode, " +
//                         "a2.name AS acc2Name, " +
//                         "par.name AS partnerName, " +
//                         "cat.name AS categoryName, " +
//                         "cat.category_type AS categoryType " +
//                         "FROM transaction t " +
//                         "LEFT OUTER JOIN account a1 ON t.acc1_id=a1.id " +
//                         "LEFT OUTER JOIN person per ON a1.person_id=per.id " +
//                         "LEFT OUTER JOIN account a2 ON t.acc2_id=a2.id " +
//                         "LEFT OUTER JOIN partner par ON t.partner_id=par.id " +
//                         "LEFT OUTER JOIN category cat ON t.category_id=cat.id " +
//                         "WHERE per.id= :personId ORDER BY t.transaction_date DESC", nativeQuery = true)
//      List<TransactionProjection> findByAcc1PersonIdOrderByDateDescTest1(@Param("personId")int personId);

    @Query("SELECT t.id AS id, " +
                       "t.type AS type, " +
                       "t.date AS date, " +
                       "t.amount AS amount, " +
                       "t.comment AS comment, " +
                       "a1.balance AS acc1Balance, " +
                       "a1.currencyCode AS acc1CurrencyCode, " +
                       "a1.name AS acc1Name, " +
                       "a2.balance AS acc2Balance, " +
                       "a2.currencyCode AS acc2CurrencyCode, " +
                       "a2.name AS acc2Name, " +
                       "t.partner.name AS partnerName, " +
                       "cat.name AS categoryName, " +
                       "cat.type AS category " +
                       "FROM Transaction t " +
                       "LEFT JOIN t.acc1 a1 " +
                       "LEFT JOIN a1.person per " +
                       "LEFT JOIN t.acc2 a2 " +
                       "LEFT JOIN t.partner par " +
                       "LEFT JOIN t.category cat " +
                       "WHERE t.acc1.person.id= :personId ORDER BY t.date DESC")
    List<TransactionProjection2> findByAcc1PersonIdOrderByDateDescTest1(@Param("personId")int personId);

//    @Query(value = "SELECT t.id AS id, " +
//                       "t.transaction_type AS type, " +
//                       "t.transaction_date AS date, " +
//                       "t.amount AS amount, " +
//                       "t.comment AS comment, " +
//                       "a1.balance AS acc1Balance, " +
//                       "a1.currency_code AS acc1CurrencyCode, " +
//                       "a1.name AS acc1Name, " +
//                       "a2.balance AS acc2Balance, " +
//                       "a2.currency_code AS acc2CurrencyCode, " +
//                       "a2.name AS acc2Name, " +
//                       "par.name AS partnerName, " +
//                       "cat.name AS categoryName, " +
//                       "cat.category_type AS categoryType " +
//                       "FROM transaction t " +
//                       "LEFT OUTER JOIN account a1 ON t.acc1_id=a1.id " +
//                       "LEFT OUTER JOIN person per ON a1.person_id=per.id " +
//                       "LEFT OUTER JOIN account a2 ON t.acc2_id=a2.id " +
//                       "LEFT OUTER JOIN partner par ON t.partner_id=par.id " +
//                       "LEFT OUTER JOIN category cat ON t.category_id=cat.id " +
//                       "WHERE per.id= :personId " +
//                       "AND (LOWER (par.name) LIKE %:seek% OR LOWER (t.comment) LIKE %:seek% OR LOWER (cat.name) LIKE %:seek%) " +
//                       "ORDER BY t.transaction_date DESC", nativeQuery = true)
//    Page<TransactionProjection> findByAcc1PersonIdOrderByDateDesc_WithSearchPaginated(@Param("personId")int personId, 
//    		@Param("seek") String seek, Pageable pageable);

//    @Query(value = "SELECT t.id AS id, " +
//                       "t.transaction_type AS type, " +
//                       "t.transaction_date AS date, " +
//                       "t.amount AS amount, " +
//                       "t.comment AS comment, " +
//                       "a1.balance AS acc1Balance, " +
//                       "a1.currency_code AS acc1CurrencyCode, " +
//                       "a1.name AS acc1Name, " +
//                       "a2.balance AS acc2Balance, " +
//                       "a2.currency_code AS acc2CurrencyCode, " +
//                       "a2.name AS acc2Name, " +
//                       "par.name AS partnerName, " +
//                       "cat.name AS categoryName, " +
//                       "cat.category_type AS categoryType " +
//                       "FROM transaction t " +
//                       "LEFT OUTER JOIN account a1 ON t.acc1_id=a1.id " +
//                       "LEFT OUTER JOIN person per ON a1.person_id=per.id " +
//                       "LEFT OUTER JOIN account a2 ON t.acc2_id=a2.id " +
//                       "LEFT OUTER JOIN partner par ON t.partner_id=par.id " +
//                       "LEFT OUTER JOIN category cat ON t.category_id=cat.id " +
//                       "WHERE per.id= :personId ORDER BY t.transaction_date DESC", nativeQuery = true)
//    Page<TransactionProjection> findByAcc1PersonIdOrderByDateDescPaginated(@Param("personId")int personId, 
//    		Pageable pageable);
    
    @Query("SELECT t.id AS id, " +
            "t.type AS type, " +
            "t.date AS date, " +
            "t.amount AS amount, " +
            "t.comment AS comment, " +
            "a1.balance AS acc1Balance, " +
            "a1.currencyCode AS acc1CurrencyCode, " +
            "a1.name AS acc1Name, " +
            "a2.balance AS acc2Balance, " +
            "a2.currencyCode AS acc2CurrencyCode, " +
            "a2.name AS acc2Name, " +
            "par.name AS partnerName, " +
            "cat.name AS categoryName, " +
            "cat.type AS category " +
            "FROM Transaction t " +
            "LEFT JOIN t.acc1 a1 " +
            "LEFT JOIN a1.person per " +
            "LEFT JOIN t.acc2 a2 " +
            "LEFT JOIN t.partner par " +
            "LEFT JOIN t.category cat " +
            "WHERE a1.person.id= :personId ORDER BY t.date DESC")
    Page<TransactionProjection2> findByAcc1PersonIdOrderByDateDescPaginated(@Param("personId")int personId, 
    		Pageable pageable);
    
    @Query("SELECT t.id AS id, " +
            "t.type AS type, " +
            "t.date AS date, " +
            "t.amount AS amount, " +
            "t.comment AS comment, " +
            "a1.balance AS acc1Balance, " +
            "a1.currencyCode AS acc1CurrencyCode, " +
            "a1.name AS acc1Name, " +
            "a2.balance AS acc2Balance, " +
            "a2.currencyCode AS acc2CurrencyCode, " +
            "a2.name AS acc2Name, " +
            "par.name AS partnerName, " +
            "cat.name AS categoryName, " +
            "cat.type AS category " +
            "FROM Transaction t " +
            "LEFT OUTER JOIN t.acc1 a1 " +
            "LEFT OUTER JOIN a1.person per " +
            "LEFT OUTER JOIN t.acc2 a2 " +
            "LEFT OUTER JOIN t.partner par " +
            "LEFT OUTER JOIN t.category cat " +
            "WHERE a1.person.id= :personId " + 
            "AND (LOWER (par.name) LIKE %:seek% OR LOWER (t.comment) LIKE %:seek% OR LOWER (cat.name) LIKE %:seek%) " +
    		"ORDER BY t.date DESC")
    Page<TransactionProjection2> findByAcc1PersonIdOrderByDateDesc_WithSearchPaginated(@Param("personId")int personId, 
    		@Param("seek") String seek, Pageable pageable);

}