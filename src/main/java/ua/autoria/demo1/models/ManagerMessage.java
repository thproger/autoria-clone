package ua.autoria.demo1.models;

import lombok.Data;

@Data
public class ManagerMessage {
    private long offerId;
    private String message;
    private long userId;
}
