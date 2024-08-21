package org.example.ordermanagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "payment")
public class Payment implements Serializable {

    @Serial
    private static final long serialVersionUID = -1724083513000L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "txn_id", nullable = false)
    private String txnId;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private int mode;

    @Column(nullable = false)
    private int status;

    @Column(name = "txn_type", nullable = false)
    private int txnType;

    @Column(name = "txn_date", nullable = false)
    private Long txnDate;

    @ManyToOne()
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference(value = "order-payment")
    private Order order;

    @Column(name = "created_date", nullable = false)
    private Long createdDate;

    @Column(name = "updated_date", nullable = false)
    private Long updatedDate;

}
