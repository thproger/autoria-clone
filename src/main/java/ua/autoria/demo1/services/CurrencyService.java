package ua.autoria.demo1.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.autoria.demo1.models.CurrenceFetch;

@Service
@AllArgsConstructor
public class CurrencyService {
    public double convertUSD(double value) {
        var url = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";
        var restTemplate = new RestTemplate();
        var result = restTemplate.getForObject(url, CurrenceFetch[].class);
        return value*result[0].getSale();
    }

    public double convertEUR(double value) {
        var url = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";
        var restTemplate = new RestTemplate();
        var result = restTemplate.getForObject(url, CurrenceFetch[].class);
        return value*result[1].getSale();
    }
}
