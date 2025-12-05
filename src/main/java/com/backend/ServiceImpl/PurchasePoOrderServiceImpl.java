package com.backend.ServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.PaymentAccount;
import com.backend.Entity.PurchasePoItem;
import com.backend.Entity.PurchasePoOrder;
import com.backend.Entity.ShippingPoDetails;
import com.backend.Entity.StockTransaction;
import com.backend.Entity.Transaction;
import com.backend.Repository.PaymentAccountRepo;
import com.backend.Repository.PurchasePoOrderRepo;
import jakarta.transaction.Transactional;

@Service
public class PurchasePoOrderServiceImpl implements com.backend.Service.PurchasePoOrderService {

    @Autowired
    private PurchasePoOrderRepo purchaseOrderRepo;
    
    @Autowired
    private PaymentAccountRepo paymentAccountRepo;
    
    @Transactional
    @Override
    public PurchasePoOrder savePurchasePoOrder(PurchasePoOrder purchasePoOrder) {
        if (purchasePoOrder.getPurchasePoItem() != null) {
            for (PurchasePoItem item : purchasePoOrder.getPurchasePoItem()) {
                item.setPurchasePoOrder(purchasePoOrder);
            }
        }

        if (purchasePoOrder.getTransaction() != null) {
            for (Transaction transaction : purchasePoOrder.getTransaction()) {
                transaction.setPurchasePoOrder(purchasePoOrder);

                if (transaction.getPaymentAccount() == null && transaction.getPaymentAccountId() != null) {
                    PaymentAccount paymentAccount = paymentAccountRepo.findById(transaction.getPaymentAccountId())
                            .orElseThrow(() -> new RuntimeException("Payment Account not found for ID: " + transaction.getPaymentAccountId()));

                    transaction.setPaymentAccount(paymentAccount);
                }

                if (transaction.getPaymentAccount() == null) {
                    throw new RuntimeException("Transaction must be associated with a valid Payment Account.");
                }

                BigDecimal previousBalance = transaction.getPaymentAccount().getBalance() != null
                        ? transaction.getPaymentAccount().getBalance()
                        : BigDecimal.ZERO;
                BigDecimal newBalance = previousBalance;

                switch (transaction.getTransactionType().toLowerCase()) {
                    case "deposit":
                    case "sale":
                    case "opening_balance":
                        newBalance = previousBalance.add(transaction.getAmount());
                        break;
                    case "purchase":
                    case "expense":
                        newBalance = previousBalance.subtract(transaction.getAmount());
                        break;
                    default:
                        throw new RuntimeException("Unknown transaction type: " + transaction.getTransactionType());
                }

                transaction.setBalance(newBalance);
                transaction.getPaymentAccount().setBalance(newBalance);
                paymentAccountRepo.save(transaction.getPaymentAccount());
            }
        }

        if (purchasePoOrder.getShippingPoDetails() != null) {
            for (ShippingPoDetails ship : purchasePoOrder.getShippingPoDetails()) {
                ship.setPurchasePoOrder(purchasePoOrder);
            }
        }

        if (purchasePoOrder.getStockTransactions() != null) {
            for (StockTransaction stock : purchasePoOrder.getStockTransactions()) {
                stock.setPurchasePoOrder(purchasePoOrder);
            }
        }

        return purchaseOrderRepo.save(purchasePoOrder);
    }


    @Override
    public List<PurchasePoOrder> getAllPurchasePoOrders() {
        return purchaseOrderRepo.findAll();
    }

    @Override
    public Optional<PurchasePoOrder> getPurchasePoOrderById(Long id) {
        return purchaseOrderRepo.findById(id);
    }

    @Override
    public PurchasePoOrder updatePurchasePoOrder(Long id, PurchasePoOrder updatedPurchasePoOrder) {
        Optional<PurchasePoOrder> existingOrderOptional = purchaseOrderRepo.findById(id);
        if (existingOrderOptional.isPresent()) {
            PurchasePoOrder existingOrder = existingOrderOptional.get();

            existingOrder.setStatus(updatedPurchasePoOrder.getStatus());
            existingOrder.setVendor(updatedPurchasePoOrder.getVendor());
            existingOrder.setAddedBy(updatedPurchasePoOrder.getAddedBy());
            existingOrder.setOrderedBy(updatedPurchasePoOrder.getOrderedBy());
            existingOrder.setReferenceNumber(updatedPurchasePoOrder.getReferenceNumber());
            existingOrder.setPurchaseReferenceNumber(updatedPurchasePoOrder.getPurchaseReferenceNumber());
            existingOrder.setOrderDate(updatedPurchasePoOrder.getOrderDate());
            existingOrder.setPurchaseDate(updatedPurchasePoOrder.getPurchaseDate());
            existingOrder.setPayTermNumber(updatedPurchasePoOrder.getPayTermNumber());
            existingOrder.setPayTermType(updatedPurchasePoOrder.getPayTermType());
            existingOrder.setLocation(updatedPurchasePoOrder.getLocation());
            existingOrder.setFile(updatedPurchasePoOrder.getFile());
            existingOrder.setTotalItems(updatedPurchasePoOrder.getTotalItems());
            existingOrder.setNetTotalAmount(updatedPurchasePoOrder.getNetTotalAmount());
            existingOrder.setDiscountType(updatedPurchasePoOrder.getDiscountType());
            existingOrder.setDiscountAmount(updatedPurchasePoOrder.getDiscountAmount());
            existingOrder.setPurchaseTax(updatedPurchasePoOrder.getPurchaseTax());
            existingOrder.setTaxAmount(updatedPurchasePoOrder.getTaxAmount());
            existingOrder.setAdditionalNotes(updatedPurchasePoOrder.getAdditionalNotes());

            if (updatedPurchasePoOrder.getPurchasePoItem() != null) {
                existingOrder.getPurchasePoItem().clear();  
                for (PurchasePoItem item : updatedPurchasePoOrder.getPurchasePoItem()) {
                    item.setPurchasePoOrder(existingOrder); 
                    existingOrder.getPurchasePoItem().add(item); 
                }
            }


            if (updatedPurchasePoOrder.getShippingPoDetails() != null) {
                existingOrder.getShippingPoDetails().clear();  
                for (ShippingPoDetails shippingDetails : updatedPurchasePoOrder.getShippingPoDetails()) {
                    shippingDetails.setPurchasePoOrder(existingOrder); 
                    existingOrder.getShippingPoDetails().add(shippingDetails); 
                }
            }
            // Update Transactions
            if (updatedPurchasePoOrder.getTransaction() != null) {
                existingOrder.getTransaction().clear();
                for (Transaction transaction : updatedPurchasePoOrder.getTransaction()) {
                    transaction.setPurchasePoOrder(existingOrder);

                    if (transaction.getPaymentAccountId() == null) {
                        throw new RuntimeException("Payment Account ID is required for transactions.");
                    }

                    // Fetch PaymentAccount entity and assign it
                    PaymentAccount paymentAccount = paymentAccountRepo.findById(transaction.getPaymentAccountId())
                            .orElseThrow(() -> new RuntimeException("Payment Account not found with ID: " + transaction.getPaymentAccountId()));

                    transaction.setPaymentAccount(paymentAccount);

                    BigDecimal previousBalance = paymentAccount.getBalance() != null ? paymentAccount.getBalance() : BigDecimal.ZERO;
                    BigDecimal newBalance = previousBalance;

                    switch (transaction.getTransactionType().toLowerCase()) {
                        case "deposit":
                        case "sale":
                        case "opening_balance":
                            newBalance = previousBalance.add(transaction.getAmount());
                            break;
                        case "purchase":
                        case "expense":
                            newBalance = previousBalance.subtract(transaction.getAmount());
                            break;
                        default:
                            throw new RuntimeException("Unknown transaction type: " + transaction.getTransactionType());
                    }

                    transaction.setBalance(newBalance);
                    paymentAccount.setBalance(newBalance);
                    paymentAccountRepo.save(paymentAccount);

                    existingOrder.getTransaction().add(transaction);
                }
            }

            if (updatedPurchasePoOrder.getStockTransactions() != null) {
                existingOrder.getStockTransactions().clear();  
                for (StockTransaction transactions : updatedPurchasePoOrder.getStockTransactions()) {
                    transactions.setPurchasePoOrder(existingOrder); 
                    existingOrder.getStockTransactions().add(transactions); 
                }
            }

            return purchaseOrderRepo.save(existingOrder);
        } else {
            return null; 
        }
    }

    @Override
    public void deletePurchasePoOrder(Long id) {
        if (purchaseOrderRepo.existsById(id)) {
            purchaseOrderRepo.deleteById(id);
        }
    }

    @Override
    public List<String> getAllOrderIds() {
        return null;
    }
}
