package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.OrderItems;

@Repository
public interface OrderItemsRepo extends JpaRepository<OrderItems, Long> {

}
