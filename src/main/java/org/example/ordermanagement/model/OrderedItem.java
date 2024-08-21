package org.example.ordermanagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "ordered_item")
public class OrderedItem  implements Serializable {

    @Serial
    private static final long serialVersionUID = -1724081860000L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private double rate;

    @ManyToOne()
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference(value = "order-orderedItem")
    private Order order;

    @ManyToOne()
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;

    @Column(name = "created_date", nullable = false)
    private Long createdDate;

    @Column(name = "updated_date", nullable = false)
    private Long updatedDate;

}
