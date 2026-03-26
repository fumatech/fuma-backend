package com.backend.Service;

import com.backend.Entity.ServiceTicket;
import com.backend.Entity.TicketComment;
import com.backend.Entity.TicketStatus;

import java.util.List;

public interface ServiceTicketService {
    ServiceTicket createTicket(ServiceTicket ticket);
    List<ServiceTicket> getAllTickets();
    ServiceTicket getTicketById(Long id);
    ServiceTicket updateTicket(Long id, ServiceTicket ticket);
    void deleteTicket(Long id);
    ServiceTicket assignTicket(Long id, Long employeeId);
    ServiceTicket updateStatus(Long id, TicketStatus status, String comment, String changedBy);
    TicketComment addComment(Long ticketId, TicketComment comment);
    List<ServiceTicket> getTicketsByCustomer(Long customerId);
    List<ServiceTicket> getTicketsByEmployee(Long employeeId);
}
