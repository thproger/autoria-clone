package ua.autoria.demo1.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.autoria.demo1.models.Currency;

@Data
@AllArgsConstructor
public class OfferDTO {
    private long userId;
    private String title;
    private String body;
    private String model;
    private int price;
    private Currency currency;
}
