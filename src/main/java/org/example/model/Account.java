package org.example.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account {
    private String accountNumber;
    private Map<String, Double> currencyAmounts;

    public Account(String accountNumber, Map<String, Double> currencyAmounts) {
        this.accountNumber = accountNumber;
        this.currencyAmounts = currencyAmounts;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Map<String, Double> getCurrencyAmounts() {
        return currencyAmounts;
    }

    public double getCurrencyAmount(String currency) {
        return currencyAmounts.getOrDefault(currency, 0.0);
    }

    public void setCurrencyAmount(String currency, double amount) {
        currencyAmounts.put(currency, amount);
    }

    public void debit(String currency, double amount) {
        double currentAmount = currencyAmounts.getOrDefault(currency, 0.0);
        currencyAmounts.put(currency, currentAmount - amount);
    }

    public void credit(String currency, double amount) {
        double currentAmount = currencyAmounts.getOrDefault(currency, 0.0);
        currencyAmounts.put(currency, currentAmount + amount);
    }

    public boolean hasCurrency(String currency) {
        return currencyAmounts.containsKey(currency);
    }
}
