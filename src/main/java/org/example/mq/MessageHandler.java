package org.example.mq;

import org.example.model.Account;
import org.example.model.TransferRequest;
import org.example.model.TransferResponse;
import org.example.storage.AccountStorage;

public class MessageHandler {
    private AccountStorage accountStorage;

    public MessageHandler(AccountStorage accountStorage) {
        this.accountStorage = accountStorage;
    }

    public TransferResponse processRequest(TransferRequest request) {
        TransferResponse response = new TransferResponse();

        // Получение аккаунта из хранилища по номеру счета
        Account account = accountStorage.getAccount(request.getTargetAccountNumber());

        // Проверка наличия аккаунта и доступных средств для дебетования
        if (account != null && account.hasCurrency(request.getCurrency()) && request.getAction().equals("DEBIT")
                && account.getCurrencyAmount(request.getCurrency()) >= request.getQuantity()) {
            // Выполнение операции дебетования
            account.debit(request.getCurrency(), request.getQuantity());

            // Установка данных в ответе
            response.setRequestId(request.getRequestId());
            response.setTargetAccountNumber(request.getTargetAccountNumber());
            response.setAction(request.getAction());
            response.setCurrency(request.getCurrency());
            response.setQuantity(request.getQuantity());
            response.setOutcome("ACCEPT");
        } else {
            // Ответ с отклонением запроса
            response.setRequestId(request.getRequestId());
            response.setTargetAccountNumber(request.getTargetAccountNumber());
            response.setAction(request.getAction());
            response.setCurrency(request.getCurrency());
            response.setQuantity(request.getQuantity());
            response.setOutcome("REJECT");
        }

        return response;
    }
}