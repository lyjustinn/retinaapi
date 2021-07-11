package com.retina.retinaapi.mapper;

public class UserUpdateDto {

    private String name;

    private String bio;

    private String password;

    public UserUpdateDto(String name, String bio, String password) {
        this.name = name;
        this.bio = bio;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
