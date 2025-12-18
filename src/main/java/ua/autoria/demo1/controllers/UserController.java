package ua.autoria.demo1.controllers;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.autoria.demo1.models.ManagerMessage;
import ua.autoria.demo1.services.OfferService;
import ua.autoria.demo1.services.SendEmailService;
import ua.autoria.demo1.services.UserService;

@RestController
@RequestMapping("/api/v1/users/")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final SendEmailService sendEmailService;
    private final OfferService offerService;

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public HttpStatus delete (@PathVariable long id) {
        try {
            userService.deleteUser(id);
        } catch (RuntimeException e) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/block/{id}")
    public HttpStatus block (@PathVariable long id) {
        try {
            userService.blockUser(id);
        } catch (RuntimeException e) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/unblock/{id}")
    public HttpStatus unblock (@PathVariable long id) {
        try {
            userService.unBlockUser(id);
        } catch (RuntimeException e) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-meneger/{id}")
    public HttpStatus createManager(@PathVariable long id) {
        userService.createManager(id);
        return HttpStatus.OK;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-meneger/{id}")
    public HttpStatus deleteManager(@PathVariable long id) {
        userService.deleteManager(id);
        return HttpStatus.OK;
    }

    @GetMapping("/premium/{id}")
    public HttpStatus premium(@PathVariable long id) {
        try {
            userService.setPremium(id);
            return HttpStatus.OK;
        } catch (RuntimeException e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @GetMapping("/unpremium/{id}")
    public HttpStatus unPremium(@PathVariable long id) {
        try {
            userService.setNotPremium(id);
            return HttpStatus.OK;
        } catch (RuntimeException e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PostMapping("/write-to-manager")
    public HttpStatus writeToManager(@RequestBody ManagerMessage managerMessage) {
        try {
            sendEmailService.sendMessageToManager(managerMessage);
            return HttpStatus.OK;
        } catch (MessagingException e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @GetMapping("/create-seller/{id}")
    public HttpStatus createSeller(@PathVariable long id) {
        try {
            userService.createSeller(id);
            return HttpStatus.OK;
        } catch (RuntimeException e) {
            return HttpStatus.BAD_REQUEST;
        }
    }
}
