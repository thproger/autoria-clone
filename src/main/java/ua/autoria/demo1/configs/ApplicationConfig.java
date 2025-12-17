package ua.autoria.demo1.configs;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import ua.autoria.demo1.dao.UserDAO;

@Configuration
@AllArgsConstructor
@EnableWebSocketMessageBroker
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
    public AuthenticationProvider authenficationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(username -> userDAO.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("no such user")));
        authenticationProvider.setUserDetailsPasswordService(userPasswordDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint for clients to connect
        registry.addEndpoint("/chat")
                .setAllowedOriginPatterns("*")
                .withSockJS(); // Enable SockJS fallback
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Prefix for topics (server-to-client)
        registry.enableSimpleBroker("/topic");
        // Prefix for client-to-server messages
        registry.setApplicationDestinationPrefixes("/app");
    }
}
