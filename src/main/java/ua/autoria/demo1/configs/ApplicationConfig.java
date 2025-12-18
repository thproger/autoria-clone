package ua.autoria.demo1.configs;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import ua.autoria.demo1.dao.UserDAO;

@Configuration
@AllArgsConstructor
public class ApplicationConfig implements WebSocketMessageBrokerConfigurer {
    private UserDAO userDAO;
    @Bean
    public UserDetailsPasswordService userPasswordDetailsService() {
        return (details, s) -> userDAO.findUserByEmail(s).orElseThrow(() -> new UsernameNotFoundException("no such user"));
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userDAO.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("no such user"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
