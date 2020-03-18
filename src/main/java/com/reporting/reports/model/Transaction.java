package com.reporting.reports.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Data
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private String description;
    @Column(name = "trans_type")
    private String transactionType;
    private BigDecimal amount;

    public Transaction(Date date, String investment, String checkpaid, BigDecimal amount) {
    }

//public Transaction(){}
//
//    public Transaction(Date date, String description, String transactionType, BigDecimal amount) {
//        this.date = date;
//        this.description = description;
//        this.transactionType = transactionType;
//        this.amount = amount;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getTransactionType() {
//        return transactionType;
//    }
//
//    public void setTransactionType(String transactionType) {
//        this.transactionType = transactionType;
//    }
//
//    public BigDecimal getAmount() {
//        return amount;
//    }
//
//    public void setAmount(BigDecimal amount) {
//        this.amount = amount;
//    }
//
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Transaction)) return false;
//        Transaction that = (Transaction) o;
//        return Objects.equals(getId(), that.getId()) &&
//                Objects.equals(getDate(), that.getDate()) &&
//                Objects.equals(getDescription(), that.getDescription()) &&
//                Objects.equals(getTransactionType(), that.getTransactionType()) &&
//                Objects.equals(getAmount(), that.getAmount());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId(), getDate(), getDescription(), getTransactionType(), getAmount());
//    }
//
//    @Override
//    public String toString() {
//
//        StringBuilder builder = new StringBuilder();
//        builder.append("Transaction{id=").append(id).append(", name=")
//                .append(description).append(", population=")
//                .append(transactionType).append("}");
//
//        return builder.toString();
//    }
}