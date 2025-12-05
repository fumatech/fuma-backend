package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.Sale;
import com.backend.Entity.SalePaymentMethod;
import com.backend.Repository.SaleRepo;
import com.backend.Repository.SalePaymentMethodRepo;
import com.backend.Service.SaleService;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepo saleRepo;

    @Autowired
    private SalePaymentMethodRepo salePaymentMethodRepo;

    // Create a new sale
    @Override
    public Sale createSale(Sale sale) {
        // Ensure that SalePaymentMethod is saved before associating it with Sale
        if (sale.getSalePaymentMethod() != null && sale.getSalePaymentMethod().getId() == null) {
            SalePaymentMethod savedPaymentMethod = salePaymentMethodRepo.save(sale.getSalePaymentMethod());
            sale.setSalePaymentMethod(savedPaymentMethod);
        }
        return saleRepo.save(sale);
    }

    // Retrieve all sales
    @Override
    public List<Sale> getAllSales() {
        return saleRepo.findAll();
    }

    // Retrieve a sale by ID
    @Override
    public Optional<Sale> getSaleById(Long id) {
        return saleRepo.findById(id);
    }

    // Update an existing sale
    @Override
    public Sale updateSale(Long id, Sale saleDetails) {
        Optional<Sale> existingSale = saleRepo.findById(id);
        if (existingSale.isPresent()) {
            Sale sale = existingSale.get();

            // Ensure SalePaymentMethod is saved if it's new
            if (saleDetails.getSalePaymentMethod() != null && saleDetails.getSalePaymentMethod().getId() == null) {
                SalePaymentMethod savedPaymentMethod = salePaymentMethodRepo.save(saleDetails.getSalePaymentMethod());
                sale.setSalePaymentMethod(savedPaymentMethod);
            } else {
                sale.setSalePaymentMethod(saleDetails.getSalePaymentMethod());
            }

            // Update other fields of the sale
            sale.setCustomer(saleDetails.getCustomer());
            sale.setPayTermNumber(saleDetails.getPayTermNumber());
            sale.setPayTermType(saleDetails.getPayTermType());
            sale.setSaleDate(saleDetails.getSaleDate());
            sale.setStatus(saleDetails.getStatus());
            sale.setInvoiceScheme(saleDetails.getInvoiceScheme());
            sale.setInvoiceNo(saleDetails.getInvoiceNo());
            sale.setDiscountType(saleDetails.getDiscountType());
            sale.setDiscountAmount(saleDetails.getDiscountAmount());
            sale.setOrderTax(saleDetails.getOrderTax());
            sale.setTaxAmount(saleDetails.getTaxAmount());
            sale.setSaleNotes(saleDetails.getSaleNotes());
            sale.setShippingDetails(saleDetails.getShippingDetails());
            sale.setShippingCharges(saleDetails.getShippingCharges());
            sale.setShippingStatus(saleDetails.getShippingStatus());
            sale.setDeliveredTo(saleDetails.getDeliveredTo());
            sale.setDeliveryPerson(saleDetails.getDeliveryPerson());

            return saleRepo.save(sale);
        } else {
            throw new RuntimeException("Sale not found with id " + id);
        }
    }

    // Delete a sale by ID
    @Override
    public void deleteSale(Long id) {
        if (saleRepo.existsById(id)) {
            saleRepo.deleteById(id);
        } else {
            throw new RuntimeException("Sale not found with id " + id);
        }
    }
}
