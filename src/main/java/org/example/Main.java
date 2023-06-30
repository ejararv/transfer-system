package org.example;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Main {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = createConnectionFactory();
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;

        try {
            // Создание подключения и сессии JMS
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Создание очереди назначения
            Destination destination = session.createQueue("exampleQueue");

            // Создание производителя сообщений
            producer = session.createProducer(destination);

            // Создание текстового сообщения
            TextMessage message = session.createTextMessage("Hello, ActiveMQ!");

            // Отправка сообщения
            producer.send(message);
            System.out.println("Message sent successfully");
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            // Закрытие ресурсов JMS
            try {
                if (producer != null) {
                    producer.close();
                }
                if (session != null) {
                    session.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private static ConnectionFactory createConnectionFactory() {
        // Создание фабрики подключений ActiveMQ
        String brokerUrl = "tcp://localhost:61616"; // URL брокера ActiveMQ
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);

        // Настройка фабрики подключений (например, установка имени пользователя и пароля)
        ((ActiveMQConnectionFactory) connectionFactory).setUserName("your-username");
        ((ActiveMQConnectionFactory) connectionFactory).setPassword("your-password");

        return connectionFactory;
    }
}
