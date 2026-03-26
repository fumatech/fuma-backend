package com.backend.Controller;

import com.backend.Entity.ServiceTicket;
import com.backend.Entity.TicketComment;
import com.backend.Entity.TicketStatus;
import com.backend.Service.ServiceTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/service-tickets")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ServiceTicketController {

    @Autowired
    private ServiceTicketService ticketService;

    @PostMapping("/save")
    public ResponseEntity<ServiceTicket> createTicket(@RequestBody ServiceTicket ticket) {
        return ResponseEntity.ok(ticketService.createTicket(ticket));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<ServiceTicket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceTicket> getTicketById(@PathVariable Long id) {
        ServiceTicket ticket = ticketService.getTicketById(id);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ServiceTicket> updateTicket(@PathVariable Long id, @RequestBody ServiceTicket ticket) {
        ServiceTicket updated = ticketService.updateTicket(id, ticket);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<ServiceTicket> assignTicket(@PathVariable Long id, @RequestBody Map<String, Long> payload) {
        Long employeeId = payload.get("employeeId");
        ServiceTicket updated = ticketService.assignTicket(id, employeeId);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ServiceTicket> updateStatus(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        TicketStatus status = TicketStatus.valueOf((String) payload.get("status"));
        String comment = (String) payload.get("comment");
        String changedBy = (String) payload.get("changedBy");
        ServiceTicket updated = ticketService.updateStatus(id, status, comment, changedBy);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<TicketComment> addComment(@PathVariable Long id, @RequestBody TicketComment comment) {
        TicketComment saved = ticketService.addComment(id, comment);
        return saved != null ? ResponseEntity.ok(saved) : ResponseEntity.notFound().build();
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ServiceTicket>> getTicketsByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(ticketService.getTicketsByCustomer(customerId));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ServiceTicket>> getTicketsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(ticketService.getTicketsByEmployee(employeeId));
    }
}
