package com.backend.Repository;

import com.backend.Entity.ServiceTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceTicketRepo extends JpaRepository<ServiceTicket, Long> {
    List<ServiceTicket> findByCustomerId(Long customerId);
    List<ServiceTicket> findByAssignedToId(Long assignedToId);

    @Query("SELECT MAX(s.id) FROM ServiceTicket s")
    Long getLastTicketId();
}
