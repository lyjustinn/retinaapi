package com.retina.retinaapi.user;

import com.retina.retinaapi.image.Image;
import com.retina.retinaapi.image.ImageRepository;
import com.retina.retinaapi.mapper.Mapper;
import com.retina.retinaapi.mapper.UserDto;
import com.retina.retinaapi.mapper.UserProfileDto;
import com.retina.retinaapi.mapper.UserUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final Mapper mapper;

    private final PasswordEncoder passwordEncoder;

    private final ImageRepository imageRepository;

    @Autowired
    public UserService(UserRepository userRepository, Mapper mapper, PasswordEncoder passwordEncoder, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.imageRepository = imageRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("User DNE"));
    }

    public List<User> getAllUsers () {
        return this.userRepository.findAll();
    }

    public User getUser(long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public User getUser(String username) { return this.userRepository.findByUsername(username).orElse(null);}

    public void addUser(UserDto userDto) {
        Optional<User> exists = this.userRepository.findByUsername(userDto.getUsername());

        if (exists.isPresent()) throw new EntityExistsException("Cannot create a user with that username");

        String saltedPassword = this.passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(saltedPassword);

        User newUser = this.mapper.mapUser(userDto);
        this.userRepository.save(newUser);
    }

    public void updateUser(Long id, UserUpdateDto userDto) {
        Optional<User> isUser = this.userRepository.findById(id);

        if (!isUser.isPresent()) return;

        User user = isUser.get();

        if (userDto.getFirstName() != null && userDto.getFirstName().length() > 0) {
            user.setFirstName(userDto.getFirstName());
        }

        if (userDto.getLastName() != null && userDto.getLastName().length() > 0) {
            user.setLastName(userDto.getLastName());
        }

        if (userDto.getBio() != null) {
            user.setBio(userDto.getBio());
        }

        if (userDto.getLastName() != null && userDto.getPassword().length() > 0) {
            user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        }

        this.userRepository.save(user);
    }

    public UserProfileDto getUserProfileDto (Long id) {
        User user = this.userRepository.findById(id).orElse(null);

        if (user == null) return null;

        List<Image> images = this.imageRepository.findImageByOwner(id);

        return this.mapper.mapUserProfileDto(images, user);
    }

    public UserProfileDto getUserProfileDto (String username) {
        User user = this.userRepository.findByUsername(username).orElse(null);

        if (user == null) return null;

        List<Image> images = this.imageRepository.findImageByOwner(user.getId());

        return this.mapper.mapUserProfileDto(images, user);
    }
}
