package ua.autoria.demo1.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshRequest {
    private String refreshToken;
}
