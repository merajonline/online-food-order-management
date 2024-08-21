package org.example.ordermanagement.repository;

import org.example.ordermanagement.model.OrderBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBillRepository extends JpaRepository<OrderBill, Long> {
}
