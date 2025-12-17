package ua.autoria.demo1.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.autoria.demo1.dao.OfferDAO;
import ua.autoria.demo1.dao.UserDAO;
import ua.autoria.demo1.dao.ViewDAO;
import ua.autoria.demo1.models.AnalyticsResponse;
import ua.autoria.demo1.models.Currency;
import ua.autoria.demo1.models.Offer;
import ua.autoria.demo1.models.Region;

import java.util.List;

@Service
@AllArgsConstructor
public class AnalyticService {
    private final UserDAO userDAO;
    private ViewDAO viewDAO;
    private OfferDAO offerDAO;
    private CurrencyService currencyService;

    public int getViews(long offerId) throws RuntimeException {
        var offer = offerDAO.findById(offerId).orElseThrow(() -> new RuntimeException("Offer with id " + offerId + " not found"));
        return viewDAO.countByOffer(offer);
    }

    public double getPrice(List<Offer> offers) {
        var result = 0.0;
        for (Offer offer : offers) {
            if (offer.getCurrency() == Currency.USD) {
                result += offer.getPrice()* currencyService.convertUSD(offer.getPrice());
            } else if (offer.getCurrency() == Currency.EUR) {
                result += offer.getPrice()*currencyService.convertEUR(offer.getPrice());
            } else if (offer.getCurrency() == Currency.UAH) result += offer.getPrice();
        }
        return result / offers.size();
    }

    public double getAverageInRegion(String model, Region region) throws RuntimeException {
        var offers = offerDAO.findByModelAndRegion(model, region);
        if (offers.isEmpty()) throw new RuntimeException("no offers");
        return getPrice(offers);
    }

    public double getAverage(String model)  throws RuntimeException {
        var offers = offerDAO.findByModel(model);
        if (offers.isEmpty()) throw new RuntimeException("no offers");
        return getPrice(offers);
    }

    public AnalyticsResponse getAnalytics(long userId, long postId) throws RuntimeException {
        var user = userDAO.findUserById(userId).orElseThrow(() -> new RuntimeException("User with id " + userId + " not found"));
        if (!user.getIsPremium()) throw new RuntimeException("User is not premium");
        var post = offerDAO.findById(postId).orElseThrow(() -> new RuntimeException("Post with id " + postId + " not found"));
        return new AnalyticsResponse().builder()
                .views(getViews(postId))
                .average(getAverage(post.getModel()))
                .currentDay(viewDAO.countByOfferInCurrentDay(post))
                .currentWeek(viewDAO.countByOfferInCurrentWeek(post))
                .currentMonth(viewDAO.countByOfferInCurrentMonth(post))
                .averageInRegion(getAverageInRegion(post.getModel(), post.getRegion()))
                .build();
    }
}
