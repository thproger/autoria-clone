package ua.autoria.demo1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.autoria.demo1.models.Offer;
import ua.autoria.demo1.models.View;

@Repository
public interface ViewDAO extends JpaRepository<View, Long> {
    int countByOffer(Offer offer);
    int countByOfferInCurrentMonth(Offer offer);
    int countByOfferInCurrentDay(Offer offer);
    int countByOfferInCurrentWeek(Offer offer);
}
