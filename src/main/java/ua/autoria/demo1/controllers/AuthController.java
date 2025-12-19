package ua.autoria.demo1.controllers;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.autoria.demo1.models.AuthenticationResponse;
import ua.autoria.demo1.models.RefreshRequest;
import ua.autoria.demo1.models.RegisterRequest;
import ua.autoria.demo1.services.AuthenticationService;
import ua.autoria.demo1.models.AuthenticationRequest;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        System.out.println("starting register");
        System.out.println("registerRequest: " + registerRequest);
        System.out.println("In try block");
        var response = authenticationService.register(registerRequest);
        System.out.println("registered register");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh (@RequestBody RefreshRequest refreshRequest) {
        return ResponseEntity.ok(authenticationService.refresh(refreshRequest));
    }
}
