package ua.autoria.demo1.controllers;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.autoria.demo1.models.Offer;
import ua.autoria.demo1.models.OfferManipulations;
import ua.autoria.demo1.models.dto.OfferActiveDTO;
import ua.autoria.demo1.models.dto.OfferDTO;
import ua.autoria.demo1.services.OfferService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/offers")
public class OfferController {
    private OfferService offerService;

    @GetMapping("/get-all")
    public ResponseEntity<List<Offer>> getAllOffers() {
        return new ResponseEntity<>(offerService.getAllOffers(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'SELLER', 'ADMIN')")
    @PostMapping("/create")
    public HttpStatus createOffer(@RequestBody OfferDTO offerDTO) {
        var offer = new OfferDTO(offerDTO.getUserId(), offerDTO.getTitle(), offerDTO.getBody(), offerDTO.getPrice(), offerDTO.getCurrency());
        try {
            offerService.createOffer(offer);
        } catch (MessagingException e) {
            return HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.CREATED;
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'SELLER', 'ADMIN')")
    @GetMapping("/my-offers/{id}")
    public ResponseEntity<List<Offer>> getOffer(@PathVariable long id) {
        var offers = offerService.getAllUserOffers(id);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'SELLER', 'ADMIN')")
    @PostMapping("/update")
    public HttpStatus updateOffer(@RequestBody Offer offer) {
        offerService.updateOffer(offer);
        return HttpStatus.OK;
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'SELLER', 'ADMIN')")
    @DeleteMapping("/{userId}/{id}")
    public HttpStatus deleteOffer(@PathVariable long userId, @PathVariable long id) {
        try {
            offerService.deleteOffer(userId, id);
        } catch (RuntimeException e) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }

    @GetMapping("/get-offer/{id}")
    public ResponseEntity<Offer> getOfferById(@PathVariable long id) {
        try {
            var offer = offerService.getOfferById(id);
            return new ResponseEntity<>(offer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/change-active/{offerId}/{active}")
    public ResponseEntity<OfferManipulations> changeActive(@PathVariable long offerId, @PathVariable boolean active) {
        try {
            offerService.changeStatus(offerId, active);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(OfferManipulations.DELETED,HttpStatus.OK);
        }
    }
}
