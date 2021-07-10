package com.retina.retinaapi.user;

import com.retina.retinaapi.mapper.Mapper;
import com.retina.retinaapi.mapper.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final Mapper mapper;

    @Autowired
    public UserService(UserRepository userRepository, Mapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new User("user", "password");
    }

    public User getUser(long id) throws EntityNotFoundException {
        return this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " DNE"));
    }

    public void addUser(UserDto userDto) {
        Optional<User> exists = this.userRepository.findByUsername(userDto.getUsername());

        if (exists.isPresent()) throw new EntityExistsException("Cannot create a user with that username");

        User newUser = this.mapper.mapUser(userDto);
        this.userRepository.save(newUser);
    }

    public void updateUser(Long id, UserDto userDto) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " DNE"));

        if (userDto.getUsername().length() > 0) {
            user.setUsername(userDto.getUsername());
        }

        if (userDto.getPassword().length() > 0) {
            user.setPassword(userDto.getPassword());
        }
    }
}
