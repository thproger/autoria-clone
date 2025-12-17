package ua.autoria.demo1.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.autoria.demo1.dao.OfferDAO;
import ua.autoria.demo1.dao.UserDAO;
import ua.autoria.demo1.dao.ViewDAO;
import ua.autoria.demo1.models.Offer;
import ua.autoria.demo1.models.Role;
import ua.autoria.demo1.models.View;
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

        var offer = new Offer().builder()
                .title(offerDTO.getTitle())
                .body(offerDTO.getBody())
                .price(offerDTO.getPrice())
                .currency(offerDTO.getCurrency())
                .user(user).build();
        sendEmailService.sendOfferCreated(offer);
        offerDAO.save(offer);
    }

    public Offer updateOffer(Offer offer) {
        return offerDAO.save(offer);
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

    public List<Offer> getAllOffers() {
        return offerDAO.findAll();
    }

    public List<Offer> getAllUserOffers(long id) {
        return offerDAO.findByUser_Id(id);
    }

    public Offer getOfferById(long id) {
        var offer = offerDAO.findById(id).orElseThrow(() -> new RuntimeException("Offer not found"));
        viewDAO.save(new View(offer, LocalDate.now()));
        var date = LocalDate.now();
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
}
