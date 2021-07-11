package com.retina.retinaapi.user;

import com.retina.retinaapi.mapper.UserDto;
import com.retina.retinaapi.mapper.UserUpdateDto;
import com.retina.retinaapi.security.AuthRequest;
import com.retina.retinaapi.security.AuthResponse;
import com.retina.retinaapi.security.JwtUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(path = "api/user")
public class UserController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtilities jwtUtilities;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtilities jwtUtilities) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtilities = jwtUtilities;
    }

    @GetMapping(path = "/allUsers")
    public ResponseEntity<List<?>> getAllUsers() {

        User temp = new User();

        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @GetMapping(path = "{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") Long userId) {
        User user = this.userService.getUser(userId);

        if (user == null) return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(user);
    }

    @PostMapping()
    public void createUser(@RequestBody UserDto newUser) {
        this.userService.addUser(newUser);
    }

    @PostMapping(path= "/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity(new AuthResponse("", "Invalid Credentials"), HttpStatus.BAD_REQUEST);
        }

        final UserDetails userDetails = this.userService.loadUserByUsername(authRequest.getUsername());

        final String jwt = this.jwtUtilities.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PutMapping(path = "{userId}")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String authheader, @PathVariable("userId") Long userId,
                                        @RequestBody UserUpdateDto userUpdates) {

        // auth header must have valid token if it reaches this point
        final String token = authheader.substring(7);

        UserDetails user = this.userService.getUser(userId);
        final String username = this.jwtUtilities.extractUsername(token);

        if (user == null) return new ResponseEntity<String>("Could not perform update", HttpStatus.BAD_REQUEST);

        if (username.equals(user.getUsername())) {
            this.userService.updateUser(userId, userUpdates);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }

        return new ResponseEntity<String>("Could not perform update", HttpStatus.BAD_REQUEST);
    }
}
