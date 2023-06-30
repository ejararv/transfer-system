package org.example.messaging;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class RequestListener implements MessageListener {
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private MessageConsumer consumer;
    private String requestQueueName;

    public RequestListener(String requestQueueName) {
        this.requestQueueName = requestQueueName;
    }

    public void startListening() throws JMSException {
        connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(requestQueueName);
        consumer = session.createConsumer(destination);
        consumer.setMessageListener(this);
    }

    public void stopListening() throws JMSException {
        consumer.close();
        session.close();
        connection.close();
    }

    @Override
    public void onMessage(Message message) {
        // Обработка входящего сообщения
    }
}