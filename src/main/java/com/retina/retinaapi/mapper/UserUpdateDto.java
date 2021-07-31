package com.retina.retinaapi.mapper;

public class UserUpdateDto {

    private String firstName;

    private String lastName;

    private String bio;

    private String password;

    public UserUpdateDto(String firstName, String lastName, String bio, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBio() {
        return bio;
    }

    public String getPassword() {
        return password;
    }
}
