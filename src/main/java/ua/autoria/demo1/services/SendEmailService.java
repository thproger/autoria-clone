package ua.autoria.demo1.services;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ua.autoria.demo1.models.ManagerMessage;
import ua.autoria.demo1.models.Offer;
import ua.autoria.demo1.models.RegisterRequest;

import java.util.Random;

@Service
@AllArgsConstructor
public class SendEmailService {
    private final JavaMailSender mailSender;
    private final UserService userService;
    private final OfferService offerService;

    @Async
    public void sendHelloEmail(RegisterRequest user) throws MessagingException {
        var sender = "autoria@super.com";
        var mail = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(mail);
        helper.setFrom(sender);
        helper.setTo(user.getEmail());
        helper.setSubject("Registration Confirmation");
        helper.setText("Hello, " + user.getFirstName() + " " + user.getLastName() + "!\n" + "You have registered on our website, and now you can view ads on our website or post your own ads. To do this, you need to change your account type to “sell");
        mailSender.send(mail);
    }

    @Async
    public void sendOfferCreated(Offer offer) throws MessagingException {
        var sender = "autoria@super.com";
        var user = offer.getUser();
        var mail = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(mail);
        helper.setFrom(sender);
        helper.setTo(user.getEmail());
        helper.setSubject("Offer created");
        helper.setText("Hello, " + user.getFirstName() + " " + user.getLastName() + "!\n" + "You are created offer on " + offer.getTitle() + ".");
        mailSender.send(mail);
    }

    @Async
    public void sendMessageToManager(ManagerMessage message) throws MessagingException {
        var user = userService.getUser(message.getUserId());
        var managers = userService.getAllManagers();
        var offer = offerService.getOfferById(message.getOfferId());
        var offerCreator = userService.getUser(offer.getUser().getId());
        var rand = new Random();
        var manager = managers.get(rand.nextInt(managers.size()));
        var mail = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(mail);
        helper.setFrom(user.getEmail());
        helper.setTo(manager.getEmail());
        helper.setSubject("Offers info");
        helper.setText(message.getMessage() + "\nFrom user: " + user.toString() + "\nIn offer" + offer.toString() + "\nPublisher: " + offerCreator.toString(), true);
        mailSender.send(mail);
    }
}
