package ua.autoria.demo1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.autoria.demo1.models.Offer;
import ua.autoria.demo1.models.View;

import java.util.List;

@Repository
public interface ViewDAO extends JpaRepository<View, Long> {
    int countByOffer(Offer offer);
    List<View> findByOfferId(long id);
}
