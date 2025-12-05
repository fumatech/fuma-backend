package com.backend.Entity;

import java.math.BigDecimal;

public class JournalEntry {

    private String accountName;
    private BigDecimal debit = BigDecimal.ZERO;
    private BigDecimal credit = BigDecimal.ZERO;

    public JournalEntry(String accountName) {
        this.accountName = accountName;
    }

    public void addDebit(BigDecimal amount) {
        if (amount != null)
            debit = debit.add(amount);
    }

    public void addCredit(BigDecimal amount) {
        if (amount != null)
            credit = credit.add(amount);
    }

    public String getAccountName() {
        return accountName;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public BigDecimal getBalance() {
        return debit.subtract(credit);
    }
}
