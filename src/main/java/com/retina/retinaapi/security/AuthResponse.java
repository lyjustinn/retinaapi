package com.retina.retinaapi.security;

public class AuthResponse {

    private final String jwt;

    private final String error;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
        this.error = "";
    }

    public AuthResponse(String jwt, String error) {
        this.jwt = jwt;
        this.error = error;
    }

    public String getJwt() {
        return jwt;
    }

    public String getError() {
        return error;
    }
}
