package ua.autoria.demo1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.autoria.demo1.models.Model;
import ua.autoria.demo1.models.Offer;
import ua.autoria.demo1.models.Region;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferDAO extends JpaRepository<Offer, Long> {
    List<Offer> findByUser_Id(Long id);
    void deleteById(Long id);
    Optional<Offer> findById(Long id);
    List<Offer> findByModelName(String name);

    List<Offer> findByModelNameAndRegion(String name, Region region);
}
