package com.backend.ServiceImpl;

import com.backend.Entity.*;
import com.backend.Repository.ServiceTicketRepo;
import com.backend.Repository.TicketCommentRepo;
import com.backend.Repository.TicketStatusHistoryRepo;
import com.backend.Service.IdGenerator;
import com.backend.Service.ServiceTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceTicketServiceImpl implements ServiceTicketService {

    @Autowired
    private ServiceTicketRepo ticketRepo;

    @Autowired
    private TicketCommentRepo commentRepo;

    @Autowired
    private TicketStatusHistoryRepo historyRepo;

    @Autowired
    private IdGenerator idGenerator;

    @Override
    @Transactional
    public ServiceTicket createTicket(ServiceTicket ticket) {
        ticket.setTicketNo(idGenerator.generateTicketNo());
        if (ticket.getStatus() == null) {
            ticket.setStatus(TicketStatus.OPEN);
        }
        return ticketRepo.save(ticket);
    }

    @Override
    public List<ServiceTicket> getAllTickets() {
        return ticketRepo.findAll();
    }

    @Override
    public ServiceTicket getTicketById(Long id) {
        return ticketRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public ServiceTicket updateTicket(Long id, ServiceTicket ticket) {
        ServiceTicket existing = ticketRepo.findById(id).orElse(null);
        if (existing != null) {
            existing.setSubject(ticket.getSubject());
            existing.setDescription(ticket.getDescription());
            existing.setPriority(ticket.getPriority());
            existing.setUpdatedBy(ticket.getUpdatedBy());
            return ticketRepo.save(existing);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteTicket(Long id) {
        ticketRepo.deleteById(id);
    }

    @Override
    @Transactional
    public ServiceTicket assignTicket(Long id, Long employeeId) {
        ServiceTicket ticket = ticketRepo.findById(id).orElse(null);
        if (ticket != null) {
            ticket.setAssignedToId(employeeId);
            return ticketRepo.save(ticket);
        }
        return null;
    }

    @Override
    @Transactional
    public ServiceTicket updateStatus(Long id, TicketStatus status, String comment, String changedBy) {
        ServiceTicket ticket = ticketRepo.findById(id).orElse(null);
        if (ticket != null) {
            TicketStatus fromStatus = ticket.getStatus();
            if (fromStatus != status) {
                ticket.setStatus(status);
                
                TicketStatusHistory history = new TicketStatusHistory();
                history.setTicket(ticket);
                history.setFromStatus(fromStatus);
                history.setToStatus(status);
                history.setComment(comment);
                history.setChangedBy(changedBy);
                historyRepo.save(history);
                
                return ticketRepo.save(ticket);
            }
        }
        return ticket;
    }

    @Override
    @Transactional
    public TicketComment addComment(Long ticketId, TicketComment comment) {
        ServiceTicket ticket = ticketRepo.findById(ticketId).orElse(null);
        if (ticket != null) {
            comment.setTicket(ticket);
            return commentRepo.save(comment);
        }
        return null;
    }

    @Override
    public List<ServiceTicket> getTicketsByCustomer(Long customerId) {
        return ticketRepo.findByCustomerId(customerId);
    }

    @Override
    public List<ServiceTicket> getTicketsByEmployee(Long employeeId) {
        return ticketRepo.findByAssignedToId(employeeId);
    }
}
