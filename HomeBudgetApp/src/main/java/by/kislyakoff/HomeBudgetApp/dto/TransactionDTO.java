package by.kislyakoff.HomeBudgetApp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import by.kislyakoff.HomeBudgetApp.model.dict.TransactionType;

public class TransactionDTO {
	
	private Long id;
	
	private TransactionType type;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate date;
	
	private Integer acc1Id;
	
	private Integer acc2Id;
	
	private BigDecimal amount;
	
	private Integer partnerId;
	
	private String comment;
	
	private Integer categoryId;

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

	public Integer getAcc1Id() {
		return acc1Id;
	}

	public void setAcc1Id(Integer acc1Id) {
		this.acc1Id = acc1Id;
	}

	public Integer getAcc2Id() {
		return acc2Id;
	}

	public void setAcc2Id(Integer acc2Id) {
		this.acc2Id = acc2Id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "TransactionDTO [id=" + id + ", type=" + type + ", date=" + date + ", acc1Id=" + acc1Id + ", acc2Id="
				+ acc2Id + ", amount=" + amount + ", partnerId=" + partnerId + ", comment=" + comment + ", categoryId="
				+ categoryId + "]";
	}
	
	

}
