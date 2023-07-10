package org.example.model.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TransferResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransferResponseType {
    @XmlElement(name = "Status")
    private String status;

    @XmlElement(name = "Message")
    private String message;

    // Другие атрибуты и методы

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
