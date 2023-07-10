package org.example.mq;

import org.example.model.TransferRequest;
import org.example.model.TransferResponse;
import org.example.storage.AccountStorage;

import javax.jms.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import org.example.model.jaxb.TransferResponseType;

import javax.xml.bind.JAXBElement;

import org.apache.activemq.ActiveMQConnectionFactory;

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
            JAXBContext jaxbContext = JAXBContext.newInstance(TransferRequest.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            TransferRequest transferRequest = (TransferRequest) unmarshaller.unmarshal(new StringReader(xmlPayload));
            return transferRequest;
        } catch (JAXBException e) {
            // Обработка ошибок
            e.printStackTrace();
        }
        return null;
    }

    private String marshalTransferResponse(TransferResponse response) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TransferResponse.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(response, writer);
            return writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendMessage(String xmlPayload) {
        String brokerUrl = "tcp://localhost:61616";
        String queueName = "myQueue"; // Имя очереди для отправки сообщений

        try {

            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);


            Connection connection = connectionFactory.createConnection();
            connection.start();


            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Определение назначения (очереди или топика)
            Destination destination = session.createQueue(queueName);

            // Создание производителя сообщений JMS
            MessageProducer producer = session.createProducer(destination);

            // Создание текстового сообщения
            TextMessage message = session.createTextMessage(xmlPayload);

            // Отправка сообщения
            producer.send(message);

            // Закрытие ресурсов JMS
            producer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            // Обработка ошибок
            e.printStackTrace();
        }
    }
}
