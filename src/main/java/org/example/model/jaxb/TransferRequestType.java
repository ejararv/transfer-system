package org.example.model.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TransferRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransferRequestType {
    @XmlElement(name = "Sender")
    private String sender;

    @XmlElement(name = "Receiver")
    private String receiver;

    // Другие атрибуты и методы

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
