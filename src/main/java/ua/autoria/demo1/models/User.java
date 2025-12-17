package ua.autoria.demo1.models;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String refreshToken;
    private boolean isPremium = false;
    private boolean isBlocked = false;

    @Override
    public @NonNull List<SimpleGrantedAuthority> getAuthorities() {
        System.out.println("getAuthorities in user class");
        System.out.println("role: " + role);
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public @NonNull String getUsername() {
        return email;
    }

    public boolean getIsPremium() {
        return this.isPremium;
    }
}
