package com.retina.retinaapi.mapper;

import com.retina.retinaapi.user.User;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

    public Mapper() {
    }

    public User mapUser (UserDto userDto) {
        if (userDto == null) return null;
        return new User(userDto.getUsername(), userDto.getPassword());
    }
}
