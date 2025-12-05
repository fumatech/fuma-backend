package com.backend.ServiceImpl;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.*;
import com.backend.Repository.*;

@Service
public class TrailBalanceService {

    @Autowired
    private PurchasePoOrderRepo poRepo;

    @Autowired
    private PurchaseDIOrderRepo diPurchaseRepo;

    @Autowired
    private SaleSoOrderRepo soRepo;

    @Autowired
    private SaleDIOrderRepo diSaleRepo;

    @Autowired
    private TransactionRepo txnRepo;

    @Autowired
    private CustomerRepo customerRepo;

    private Map<String, JournalEntry> ledger;

    // ---------------- Add Debit -----------------
    private void addDebit(String account, BigDecimal amount) {
        ledger.putIfAbsent(account, new JournalEntry(account));
        ledger.get(account).addDebit(amount);
    }

    // ---------------- Add Credit ----------------
    private void addCredit(String account, BigDecimal amount) {
        ledger.putIfAbsent(account, new JournalEntry(account));
        ledger.get(account).addCredit(amount);
    }

    // ---------------- Purchase PO ----------------
    private void handlePurchases() {
        List<PurchasePoOrder> list = poRepo.findAll();

        for (PurchasePoOrder p : list) {
            BigDecimal amount = p.getNetTotalAmount();
            if (amount == null) continue;

            String supplier = "Supplier - " + p.getVendor();

            addDebit("Purchase Account", amount);
            addCredit(supplier, amount);
        }
    }

    // ---------------- Purchase DI ----------------
    private void handlePurchaseDI() {
        List<PurchaseDIOrder> list = diPurchaseRepo.findAll();

        for (PurchaseDIOrder p : list) {
            BigDecimal amount = p.getNetTotalAmount();
            if (amount == null) continue;

            String supplier = "Supplier - " + p.getVendor();

            addDebit("Purchase Account", amount);
            addCredit(supplier, amount);
        }
    }

    // ---------------- Sales SO ----------------
    private void handleSalesSO() {
        List<SaleSoOrder> list = soRepo.findAll();

        for (SaleSoOrder s : list) {
            BigDecimal amount = s.getNetTotalAmount();
            if (amount == null) continue;

            String customer = "Customer - " + s.getCustomerId();

            addDebit(customer, amount);
            addCredit("Sales Account", amount);
        }
    }

    // ---------------- Sales DI ----------------
    private void handleSalesDI() {
        List<SaleDIOrder> list = diSaleRepo.findAll();

        for (SaleDIOrder s : list) {
            BigDecimal amount = s.getNetTotalAmount();
            if (amount == null) continue;

            String customer = "Customer - " + s.getCustomerId();

            addDebit(customer, amount);
            addCredit("Sales Account", amount);
        }
    }

    // ---------------- Generic Transactions ----------------
 // ---------------- Generic Transactions ----------------
    private void handleTransactions() {
        List<Transaction> list = txnRepo.findAll();

        for (Transaction t : list) {
            if (t.getPaymentAccount() == null) continue;

            String accountName = t.getPaymentAccount().getAccountName();

            BigDecimal debit = t.getDebit();
            BigDecimal credit = t.getCredit();

            if (debit.compareTo(BigDecimal.ZERO) > 0) {
                addDebit(accountName, debit);
            }

            if (credit.compareTo(BigDecimal.ZERO) > 0) {
                addCredit(accountName, credit);
            }
        }
    }


    // ---------------- FINAL PUBLIC METHOD ----------------
    public List<JournalEntry> generateTrailBalance() {

        ledger = new LinkedHashMap<>();

        handlePurchases();
        handlePurchaseDI();
        handleSalesSO();
        handleSalesDI();
        handleTransactions();

        return new ArrayList<>(ledger.values());
    }
}
