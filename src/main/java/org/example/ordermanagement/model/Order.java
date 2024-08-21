package org.example.ordermanagement.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "online_order")
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = -1724081834000L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int status;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "customer_address_id", nullable = false)
    private Long customerAddressId;

    @Column(name = "delivery_partner_id")
    private Long deliveryPartnerId;

    @Column(name = "delivery_date")
    private Long deliveryDate;

    @ManyToOne()
    @JoinColumn(name = "restaurant_id", nullable = false, updatable = false)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "order-orderedItem")
    private List<OrderedItem> orderedItems;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "order-bill")
    private OrderBill orderBill;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "order-payment")
    private List<Payment> payments;

    @Column(name = "created_date", nullable = false)
    private Long createdDate;

    @Column(name = "updated_date", nullable = false)
    private Long updatedDate;

}
