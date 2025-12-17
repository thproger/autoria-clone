package ua.autoria.demo1.models.dto;

import lombok.Data;

@Data
public class OfferActiveDTO {
    private long id;
    private boolean isActive;
    private int inspections;
}
