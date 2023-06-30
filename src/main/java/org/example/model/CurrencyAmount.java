package org.example.model;

import java.math.BigDecimal;

public class CurrencyAmount {
    private String currency;
    private BigDecimal amount;

    public CurrencyAmount(String currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}