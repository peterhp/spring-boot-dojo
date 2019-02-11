package dojo.spring.boot.security.model;

import lombok.Data;

@Data
public class JwtToken {

    private String tokenType = "Bearer";
    private Integer expiresIn = 3600;
    private String accessToken;
    private String refreshToken;

    public JwtToken() {

    }

    public JwtToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
