package org.example.messaging;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ResponseProducer {
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private MessageProducer producer;
    private String responseQueueName;

    public ResponseProducer(String responseQueueName) {
        this.responseQueueName = responseQueueName;
    }

    public void start() throws JMSException {
        connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(responseQueueName);
        producer = session.createProducer(destination);
    }

    public void stop() throws JMSException {
        producer.close();
        session.close();
        connection.close();
    }

    public void sendResponse(Message request, Message response) throws JMSException {
        response.setJMSCorrelationID(request.getJMSCorrelationID());
        producer.send(response);
    }
}