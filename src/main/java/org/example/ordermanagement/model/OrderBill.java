package org.example.ordermanagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "order_bill")
public class OrderBill implements Serializable {

    @Serial
    private static final long serialVersionUID = -1724081847000L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_total", nullable = false)
    private double itemTotal;

    @Column(name = "delivery_charge", nullable = true)
    private double deliveryCharge;

    @Column(name = "discount", nullable = true)
    private double discount;

    @Column(name = "packaging_charge", nullable = true)
    private double packagingCharge;

    @Column(name = "platform_fee", nullable = true)
    private double platformFee;

    @Column(name = "tax", nullable = true)
    private double tax;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference(value = "order-bill")
    private Order order;

    @Column(name = "created_date", nullable = false)
    private Long createdDate;

    @Column(name = "updated_date", nullable = false)
    private Long updatedDate;

}
