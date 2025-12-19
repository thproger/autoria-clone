package ua.autoria.demo1.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.autoria.demo1.models.Currency;
import ua.autoria.demo1.models.Offer;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferDTO {
    private long userId;
    private String title;
    private String body;
    private String model;
    private double price;
    private Currency currency;

    public static OfferDTO fromOffer(Offer offer) {
        return OfferDTO.builder()
                .userId(offer.getUser().getId())
                .title(offer.getTitle())
                .body(offer.getBody())
                .model(offer.getModel().getName())
                .price(offer.getPrice())
                .currency(offer.getCurrency())
                .build();
    }
}
