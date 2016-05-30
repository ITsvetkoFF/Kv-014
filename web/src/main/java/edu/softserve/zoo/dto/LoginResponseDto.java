package edu.softserve.zoo.dto;

/**
 * Represents remember-me token returned to user after successful login attempt.
 *
 * @author Ilya Doroshenko
 */
public class LoginResponseDto {

    private String token;

    public LoginResponseDto() {
    }

    public LoginResponseDto(String token) {
        this.setToken(token);
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
