package ua.autoria.demo1.models;

import lombok.Data;

@Data
public class CurrenceFetch {
    private String ccy;
    private String base_ccy;
    private float buy;
    private float sale;
}
