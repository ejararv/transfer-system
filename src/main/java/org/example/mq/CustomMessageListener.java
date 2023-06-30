package org.example.mq;

import org.example.model.TransferRequest;
import org.example.model.TransferResponse;
import org.example.model.jaxb.ObjectFactory;
import org.example.model.jaxb.TransferRequestType;
import org.example.model.jaxb.TransferResponseType;
import org.example.storage.AccountStorage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.bind.*;

public class CustomMessageListener implements MessageListener {

    private AccountStorage accountStorage;
    private MessageHandler messageHandler;

    public CustomMessageListener(AccountStorage accountStorage, MessageHandler messageHandler) {
        this.accountStorage = accountStorage;
        this.messageHandler = messageHandler;
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String xmlPayload = ((TextMessage) message).getText();
                TransferRequest request = unmarshalTransferRequest(xmlPayload);

                TransferResponse response = messageHandler.processRequest(request);

                String responseXml = marshalTransferResponse(response);
                sendMessage(responseXml);
            } catch (JMSException e) {
                // Обработка ошибок
                e.printStackTrace();
            }
        }
    }

    private TransferRequest unmarshalTransferRequest(String xmlPayload) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            JAXBElement<TransferRequestType> jaxbElement = (JAXBElement<TransferRequestType>) unmarshaller.unmarshal(new StringReader(xmlPayload));
            TransferRequestType requestType = jaxbElement.getValue();
            // Преобразование TransferRequestType в TransferRequest
            // Ваш код для создания объекта TransferRequest из requestType
            // ...
            return transferRequest;
        } catch (JAXBException e) {
            // Обработка ошибок
            e.printStackTrace();
        }
        return null;
    }

    private String marshalTransferResponse(TransferResponse response) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            TransferResponseType responseType = // Преобразование TransferResponse в TransferResponseType
                    JAXBElement<TransferResponseType> jaxbElement = new ObjectFactory().createTransferResponse(responseType);
            StringWriter writer = new StringWriter();
            marshaller.marshal(jaxbElement, writer);
            return writer.toString();
        } catch (JAXBException e) {
            // Обработка ошибок
            e.printStackTrace();
        }
        return null;
    }

    private void sendMessage(String xmlPayload) {
        // Отправка XML-представления ответа в MQ
        // Здесь вам необходимо использовать JMS для отправки сообщения

        // Пример кода для отправки сообщения:
        // producer.send(session.createTextMessage(xmlPayload));
    }
}
