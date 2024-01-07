package net.casinovoyage.server.controller;

import net.casinovoyage.server.dto.UserLoginDto;
import net.casinovoyage.server.dto.UserRegistrationDto;
import net.casinovoyage.server.execptions.InvalidUserException;
import net.casinovoyage.server.service.UserService;
import net.casinovoyage.server.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto userRegistrationDto){
        try {
            userService.registerUser(userRegistrationDto);
            return new ResponseEntity<>("Register success", HttpStatus.OK);
        }catch (InvalidUserException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto){
        try {
            String token = userService.login(userLoginDto);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }catch (InvalidUserException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(@RequestHeader("Authorization") String jwt){
        boolean validToken = JwtUtils.verifyToken(jwt);
        if(!validToken){
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
        }

        String userId = JwtUtils.getUserIdFromToken(jwt);
        double balance = userService.getBalance(userId);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }
}
