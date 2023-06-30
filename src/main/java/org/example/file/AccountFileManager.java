package org.example.file;

import org.example.model.Account;
import org.example.model.CurrencyAmount;
import org.example.storage.AccountStorage;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountFileManager {
    private String filePath;

    public AccountFileManager(String filePath) {
        this.filePath = filePath;
    }

    public void importAccounts(AccountStorage accountStorage) throws IOException {
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            Account account = parseAccount(line);
            accountStorage.addAccount(account);
        }
    }

    public void exportAccounts(AccountStorage accountStorage) throws IOException {
        Path path = Paths.get(filePath);
        List<String> lines = accountStorage.getAllAccounts().stream()
                .map(this::formatAccount)
                .collect(Collectors.toList());
        Files.write(path, lines);
    }

    private Account parseAccount(String line) {
        String[] parts = line.split(",");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid line format: " + line);
        }
        String accountNumber = parts[0];
        Map<String, Double> currencyAmounts = new HashMap<>();
        for (int i = 1; i < parts.length; i += 2) {
            String currency = parts[i];
            BigDecimal amount = new BigDecimal(parts[i + 1]);
            currencyAmounts.put(currency, amount.doubleValue());
        }
        return new Account(accountNumber, currencyAmounts);
    }

    private String formatAccount(Account account) {
        String accountNumber = account.getAccountNumber();
        StringBuilder builder = new StringBuilder();
        builder.append("Account Number: ").append(accountNumber).append("\n");
        builder.append("Currency Amounts:\n");
        for (Map.Entry<String, Double> entry : account.getCurrencyAmounts().entrySet()) {
            String currency = entry.getKey();
            double amount = account.getCurrencyAmount(currency);
            builder.append("- Currency: ").append(currency).append(", Amount: ").append(amount).append("\n");
        }
        return builder.toString();
    }

}
