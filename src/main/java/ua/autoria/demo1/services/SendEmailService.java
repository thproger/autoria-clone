package ua.autoria.demo1.services;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ua.autoria.demo1.dao.OfferDAO;
import ua.autoria.demo1.models.ManagerMessage;
import ua.autoria.demo1.models.Offer;
import ua.autoria.demo1.models.RegisterRequest;
import ua.autoria.demo1.models.User;

import java.util.Random;

@Service
@AllArgsConstructor
public class SendEmailService {
    private final JavaMailSender mailSender;
    private final UserService userService;
    private final OfferDAO offerDAO;

    @Async
    public void sendHelloEmail(RegisterRequest user) throws MessagingException {
        System.out.println("sending email");
        var sender = "autoria@super.com";
        var mail = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(mail);
        helper.setFrom(sender);
        helper.setTo(user.getEmail());
        helper.setReplyTo(sender);
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
        var offer = offerDAO.findById(message.getOfferId()).orElseThrow(() -> new RuntimeException("Offer not found"));
        var offerCreator = userService.getUser(offer.getUser().getId());
        var rand = new Random();
        User manager;
        var mail = mailSender.createMimeMessage();
        if (managers.size() == 1) manager = managers.get(rand.nextInt(managers.size()));
        else manager = managers.get(rand.nextInt(managers.size() + 1));
        var helper = new MimeMessageHelper(mail);
        helper.setFrom(user.getEmail());
        helper.setTo(manager.getEmail());
        helper.setSubject("Offers info");
        helper.setText(message.getMessage() + "\nFrom user: " + user.toString() + "\nIn offer" + offer.toString() + "\nPublisher: " + offerCreator.toString(), true);
        mailSender.send(mail);
    }
}
