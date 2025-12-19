package ua.autoria.demo1.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.autoria.demo1.dao.OfferDAO;
import ua.autoria.demo1.dao.UserDAO;
import ua.autoria.demo1.dao.ViewDAO;
import ua.autoria.demo1.models.*;
import ua.autoria.demo1.models.dto.OfferDTO;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class OfferService {
    private OfferDAO offerDAO;
    private ViewDAO viewDAO;
    private UserDAO userDAO;
    private SendEmailService sendEmailService;

    public void createOffer(OfferDTO offerDTO) throws Exception {
        var offers = getAllUserOffers(offerDTO.getUserId());
        var user = userDAO.findById(offerDTO.getUserId()).orElseThrow(() -> new RuntimeException("user not found"));
        if (!offers.isEmpty() && !user.getIsPremium()) {
            throw  new Exception("You don't premium user :(");
        }

        var offer = Offer.builder()
                .title(offerDTO.getTitle())
                .body(offerDTO.getBody())
                .model(new Model(offerDTO.getModel()))
                .price(offerDTO.getPrice())
                .currency(offerDTO.getCurrency())
                .user(user).build();
        try {
            sendEmailService.sendOfferCreated(offer);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        offerDAO.save(offer);
    }

    public void updateOffer(Offer offer) {
        offerDAO.save(offer);
    }

    public void deleteOffer(long userId, long offerId) throws RuntimeException {
        var user = userDAO.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        var offer = offerDAO.findById(offerId).orElseThrow(() -> new RuntimeException("offer not found"));
        if (offer.getUser().getId() == userId) {
            offerDAO.deleteById(offerId);
        } else if(user.getRole() == Role.ROLE_MANAGER || user.getRole() == Role.ROLE_ADMIN) {
            offerDAO.deleteById(offerId);
        }
    }

    public List<OfferDTO> getAllOffers() {
        return offerDAO.findAll().stream().map(OfferDTO::fromOffer).toList();
    }

    public List<Offer> getAllUserOffers(long id) {
        return offerDAO.findByUser_Id(id);
    }

    public Offer getOfferById(long id) {
        var offer = offerDAO.findById(id).orElseThrow(() -> new RuntimeException("Offer not found"));
        viewDAO.save(new View(offer, LocalDate.now()));
        offerDAO.save(offer);
        return offer;
    }

    public void changeStatus(long id, boolean isActive) throws Exception {
        var offer = offerDAO.findById(id).orElseThrow(() -> new RuntimeException("Offer not found"));
        offer.setActive(isActive);
        if(!isActive && offer.getInspections() == 2) {
            deleteOffer(offer.getUser().getId(), id);
            throw new Exception("Offer has been deleted");
        }
        offerDAO.save(offer);
    }

    public double averageByModel(String name) {
        var models = offerDAO.findByModelName(name);
        var sum = 0.0;
        for(var model : models) {
            sum += model.getPrice();
        }

        return sum / models.size();
    }

    public double averageByModelAndRegion(String name, Region region) {
        var models = offerDAO.findByModelNameAndRegion(name, region);
        var sum = 0.0;
        for(var model : models) {
            sum += model.getPrice();
        }

        return sum / models.size();
    }

    public double averageByWeek(long offerId) {
        var views = viewDAO.findByOfferId(offerId);
        var count = views.stream()
                .filter(view -> !view.getDate().isBefore(LocalDate.now().minusDays(7)))
                .count();
        return (double) count / views.size();
    }

    public double averageByMonth(long offerId) {
        var views = viewDAO.findByOfferId(offerId);
        var count = views.stream()
                .filter(view -> !view.getDate().isBefore(LocalDate.now().minusMonths(1)))
                .count();
        return (double) count / views.size();
    }

    public AnalyticsResponse createAnalytics(long userId, long offerId) {
        var user = userDAO.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getIsPremium()) {
            throw new RuntimeException("You don't premium user :(");
        }
        var offer = offerDAO.findById(offerId).orElseThrow(() -> new RuntimeException("offer not found"));
        return new AnalyticsResponse(viewDAO.countByOffer(offer), averageByWeek(offerId), averageByMonth(offerId), averageByModel(offer.getModel().getName()), averageByModelAndRegion(offer.getModel().getName(), offer.getRegion()));
    }
}
