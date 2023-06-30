package org.example.storage;

import org.example.model.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountStorage {
    private Map<String, Account> accounts;

    public AccountStorage() {
        this.accounts = new HashMap<>();
    }

    public void addAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    public void removeAccount(String accountNumber) {
        accounts.remove(accountNumber);
    }

    public boolean containsAccount(String accountNumber) {
        return accounts.containsKey(accountNumber);
    }
}