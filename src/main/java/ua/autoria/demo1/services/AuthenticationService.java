package ua.autoria.demo1.services;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.autoria.demo1.dao.UserDAO;
import ua.autoria.demo1.models.*;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private UserDAO userDAO;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private SendEmailService sendEmailService;

    public AuthenticationResponse register(RegisterRequest registerRequest) throws MessagingException {
        var user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.valueOf(registerRequest.getRole()))
                .build();
        userDAO.save(user);
        jwtService.generateToken(user);
        var token = jwtService.generateToken(user);

        var refreshToken = jwtService.generateRefreshToken(user);
        user.setRefreshToken(refreshToken);

        System.out.println("Token: " + token);
        var response = AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
        sendEmailService.sendHelloEmail(registerRequest);
        return response;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(), authenticationRequest.getPassword()
        ));
        var user = userDAO.findUserByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        user.setRefreshToken(refreshToken);
        userDAO.save(user);
        return AuthenticationResponse.builder().token(token).build();
    }

    public AuthenticationResponse refresh(RefreshRequest refreshRequest) {
        var token = refreshRequest.getRefreshToken();
        var username = jwtService.extractUsername(token);
        var user = userDAO.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));
        String newAccessToken = null;
        String newRefreshToken = null;
        if(user.getRefreshToken().equals(token)) {
            newAccessToken = jwtService.generateToken(user);
            newRefreshToken = jwtService.generateRefreshToken(user);
            user.setRefreshToken(newRefreshToken);
            userDAO.save(user);
        }

        return AuthenticationResponse.builder()
                .token(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}