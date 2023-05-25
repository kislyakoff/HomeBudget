package by.kislyakoff.HomeBudgetApp.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import by.kislyakoff.HomeBudgetApp.model.dict.TransactionType;

@Entity
@Table(name = "transaction")
public class Transaction {

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "transaction_type")
        @NotNull
        private TransactionType type;

        @Column(name = "transaction_date")
        @NotNull
        private LocalDate date;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "acc1_id", referencedColumnName = "id")
        private Account acc1;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "acc2_id", referencedColumnName = "id")
        private Account acc2;

        @Column(name = "amount")
        private BigDecimal amount;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "partner_id", referencedColumnName = "id")
        private Partner partner;

        @Column(name = "comment")
        private String comment;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id", referencedColumnName = "id")
        private Category category;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public TransactionType getType() {
                return type;
        }

        public void setType(TransactionType type) {
                this.type = type;
        }

        public LocalDate getDate() {
                return date;
        }

        public void setDate(LocalDate date) {
                this.date = date;
        }

        public Account getAcc1() {
                return acc1;
        }

        public void setAcc1(Account acc1) {
                this.acc1 = acc1;
        }

        public Account getAcc2() {
                return acc2;
        }

        public void setAcc2(Account acc2) {
                this.acc2 = acc2;
        }

        public BigDecimal getAmount() {
                return amount;
        }

        public void setAmount(BigDecimal amount) {
                this.amount = amount;
        }

        public Partner getPartner() {
                return partner;
        }

        public void setPartner(Partner partner) {
                this.partner = partner;
        }

        public String getComment() {
                return comment;
        }

        public void setComment(String comment) {
                this.comment = comment;
        }

        public Category getCategory() {
                return category;
        }

        public void setCategory(Category category) {
                this.category = category;
        }

        @Override
        public String toString() {
                return "Transaction [id=" + id + ", type=" + type + ", date=" + date + ", acc1=" + acc1 + ", acc2=" + acc2
                                + ", amount=" + amount + ", partner=" + partner + ", comment=" + comment + ", category=" + category
                                + "]";
        }


}