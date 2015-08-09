package org.addhen.smssync.domains;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.addhen.smssync.models.Message;

import java.util.Date;

/**
 * Created by dassi on 06/08/15.
 */
@ParseClassName("SmsReceiver")
public class SmsReceiverP extends ParseObject {

    private String messageBody;
    private String messageFrom;
    private Date messageDate;
    private String messageUuid;
    private String messageType;
    private int sentResultCode;
    private String sentResultMessage;
    private int deliveryResultCode;
    private String deliveryResultMessage;
    private int retries;
    private String status;


    public String getBody() {
        return getString("messageBody");
    }

    public void setBody(String messageBody) {
        put("messageBody", messageBody);
    }

    public String getPhoneNumber() {
        return getString("messageFrom");
    }

    public void setPhoneNumber(String messageFrom) {
        put("messageFrom", messageFrom);
    }

    public Date getDate() {
        return getDate("messageDate");
    }

    public void setDate(Date messageDate) {
        put("messageDate", messageDate);
    }

    public String getUuid() {
        return getString("messageUuid");
    }

    public void setUuid(String messageUuid) {
        put("messageUuid", messageUuid);
    }

    public String getType() {
        return getString("messageType");
    }

    public void setType(String messageType) {
        put("messageType", messageType);
    }

    public int getSentResultCode() {
        return getInt("sentResultCode");
    }

    public void setSentResultCode(int sentResultCode) {
        put("sentResultCode", sentResultCode);
    }

    public String getSentResultMessage() {
        return getString("sentResultMessage");
    }

    public void setSentResultMessage(String sentResultMessage) {
        put("sentResultMessage", sentResultMessage);
    }

    public int getDeliveryResultCode() {
        return getInt("deliveryResultCode");
    }

    public void setDeliveryResultCode(int deliveryResultCode) {
        put("deliveryResultCode", deliveryResultCode);
    }

    public String getDeliveryResultMessage() {
        return getString("deliveryResultMessage");
    }

    public void setDeliveryResultMessage(String deliveryResultMessage) {
        put("deliveryResultMessage", deliveryResultMessage);
    }

    public int getRetries() {
        return getInt("retries");
    }

    public void setRetries(int retries) {
        put("retries", retries);
    }

    public String getStatus() {
        return getString("status");
    }

    public void setStatus(String status) {
        put("status", status);
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageBody='" + getBody() + '\'' +
                ", messageFrom='" + getPhoneNumber() + '\'' +
                ", messageDate='" + getDate() + '\'' +
                ", messageUuid='" + getUuid() + '\'' +
                ", messageType=" + getType() +
                ", sentResultCode=" + getSentResultCode() +
                ", sentResultMessage='" + getSentResultMessage() + '\'' +
                ", deliveryResultCode=" + getDeliveryResultCode() +
                ", deliveryResultMessage='" + getDeliveryResultMessage() + '\'' +
                ", retries=" + getRetries() +
                ", status=" + getStatus() +
                '}';
    }

    public enum Status {
        UNCONFIRMED, FAILED, SENT
    }

    public enum Type {
        TASK, PENDING
    }

    public void copyFromSms(Message message) {
        this.setBody(message.getBody());
        this.setDate(message.getDate());
        // this.setDeliveryResultCode(message.getDeliveryResultCode());
        this.setPhoneNumber(message.getPhoneNumber());
        this.setUuid(message.getUuid());
    }

    public Message copyToSms() {
        Message message = new Message();

        message.setBody(this.getBody());
        message.setDate(this.getDate());
        message.setPhoneNumber(this.getPhoneNumber());
        message.setUuid(this.getUuid());

        return message;
    }

}
