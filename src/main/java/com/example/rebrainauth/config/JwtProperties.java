package com.example.rebrainauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secret;
    private long tokenValidity;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getTokenValidity() {
        return tokenValidity;
    }

    public void setTokenValidity(long tokenValidity) {
        this.tokenValidity = tokenValidity;
    }
}

