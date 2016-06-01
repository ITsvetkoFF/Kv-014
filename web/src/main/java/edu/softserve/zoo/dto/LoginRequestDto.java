package edu.softserve.zoo.dto;

import edu.softserve.zoo.annotation.IrrespectiveDto;

/**
 * Represents information passed by a user during login attempt.
 *
 * @author Ilya Doroshenko
 */
@IrrespectiveDto
public class LoginRequestDto {
    private String username;
    private String password;

    public LoginRequestDto() {
    }

    public LoginRequestDto(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
