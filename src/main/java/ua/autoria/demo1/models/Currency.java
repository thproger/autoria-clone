package ua.autoria.demo1.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public enum Currency {
    USD, EUR, UAH;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthenticationRequest {
        private String email;
        private String password;
        private String role;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AuthenticationResponse {
        private String token;
        private String refreshToken;
    }
}
