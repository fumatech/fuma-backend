package com.backend.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.Entity.Purchase;
import com.backend.Entity.PurchaseItem;
import com.backend.Entity.PurchasePaymentMethod;
import com.backend.Entity.ShippingDetails;
import com.backend.Repository.PurchaseItemRepo;
import com.backend.Repository.PurchasePaymentMethodRepo;
import com.backend.Repository.PurchaseRepo;
import com.backend.Repository.ShippingDetailsRepo;
import com.backend.Service.PurchaseService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseRepo purchaseRepository;

    @Autowired
    private PurchasePaymentMethodRepo purchasePaymentMethodRepository;

    @Autowired
    private ShippingDetailsRepo shippingDetailsRepository;

    @Autowired
    private PurchaseItemRepo purchaseItemRepository;

    @Override
    public Purchase savePurchase(Purchase purchase) {
        // Save payment method if present
        PurchasePaymentMethod paymentMethod = purchase.getPurchasePaymentMethod();
        if (paymentMethod != null) {
            purchasePaymentMethodRepository.save(paymentMethod);
        }

        // Save shipping details if present
        ShippingDetails shippingDetails = purchase.getShippingDetails();
        if (shippingDetails != null) {
            shippingDetailsRepository.save(shippingDetails);
        }

        // Save purchase items
        List<PurchaseItem> purchaseItems = purchase.getPurchaseItems();
        if (purchaseItems != null) {
            for (PurchaseItem item : purchaseItems) {
                item.setPurchase(purchase); // Set the relationship
                purchaseItemRepository.save(item);
            }
        }

        // Save the purchase itself
        return purchaseRepository.save(purchase);
    }

    
    @Override
    @Transactional // Ensure the operation is transactional
    public Purchase updatePurchase(Long id, Purchase purchaseDetails) {
        // Find existing purchase
        Optional<Purchase> existingPurchaseOpt = purchaseRepository.findById(id);
        if (existingPurchaseOpt.isPresent()) {
            Purchase existingPurchase = existingPurchaseOpt.get();
            
            // Update basic fields
            existingPurchase.setVendor(purchaseDetails.getVendor());
            existingPurchase.setReferenceNumber(purchaseDetails.getReferenceNumber());
            existingPurchase.setStatus(purchaseDetails.getStatus());
            existingPurchase.setAddedBy(purchaseDetails.getAddedBy());
            existingPurchase.setPurchaseDate(purchaseDetails.getPurchaseDate());
            existingPurchase.setLocation(purchaseDetails.getLocation());
            existingPurchase.setPayTermNumber(purchaseDetails.getPayTermNumber());
            existingPurchase.setPayTermType(purchaseDetails.getPayTermType());
            existingPurchase.setFile(purchaseDetails.getFile());
            existingPurchase.setTotalItems(purchaseDetails.getTotalItems());
            existingPurchase.setNetTotalAmount(purchaseDetails.getNetTotalAmount());
            existingPurchase.setDiscountType(purchaseDetails.getDiscountType());
            existingPurchase.setDiscountAmount(purchaseDetails.getDiscountAmount());
            existingPurchase.setPurchaseTax(purchaseDetails.getPurchaseTax());
            existingPurchase.setTaxAmount(purchaseDetails.getTaxAmount());
            existingPurchase.setAdditionalNotes(purchaseDetails.getAdditionalNotes());

            // Update payment method if present
            PurchasePaymentMethod paymentMethod = purchaseDetails.getPurchasePaymentMethod();
            if (paymentMethod != null) {
                existingPurchase.setPurchasePaymentMethod(paymentMethod);
                purchasePaymentMethodRepository.save(paymentMethod); // Consider checking if exists
            }

            // Update shipping details if present
            ShippingDetails shippingDetails = purchaseDetails.getShippingDetails();
            if (shippingDetails != null) {
                existingPurchase.setShippingDetails(shippingDetails);
                shippingDetailsRepository.save(shippingDetails); // Consider checking if exists
            }

            // Clear existing items
            List<PurchaseItem> existingItems = existingPurchase.getPurchaseItems();
            if (existingItems != null) {
                for (PurchaseItem existingItem : existingItems) {
                    purchaseItemRepository.delete(existingItem); // Delete old items
                }
                existingPurchase.getPurchaseItems().clear(); // Clear the collection after deletion
            }

            // Update purchase items
            List<PurchaseItem> purchaseItems = purchaseDetails.getPurchaseItems();
            if (purchaseItems != null) {
                for (PurchaseItem item : purchaseItems) {
                    item.setPurchase(existingPurchase); // Set the relationship
                    purchaseItemRepository.save(item); // Save new items
                    existingPurchase.getPurchaseItems().add(item); // Add to existingPurchase
                }
            }


            return purchaseRepository.save(existingPurchase);


        } else {
            throw new RuntimeException("Purchase not found for id: " + id);
        }
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    @Override
    public Optional<Purchase> getPurchaseById(Long id) {
        // Retrieve purchase by ID, including its purchase items
        return purchaseRepository.findById(id);
    }

    @Override
    public void deletePurchase(Long id) {
        // Now delete the purchase itself
        purchaseRepository.deleteById(id);
    }
}
