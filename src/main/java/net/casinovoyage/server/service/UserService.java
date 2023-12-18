package net.casinovoyage.server.service;

import jakarta.transaction.Transactional;
import net.casinovoyage.server.dto.UserRegistrationDto;
import net.casinovoyage.server.execptions.InvalidUserException;
import net.casinovoyage.server.models.UserModel;
import net.casinovoyage.server.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel registerUser(UserRegistrationDto userRegistrationDto){
        if(!verifyUsername(userRegistrationDto.getUsername())){
            throw new InvalidUserException("Username is not valid");
        }
        if(!verifyUsernameFree(userRegistrationDto.getUsername())){
            throw new InvalidUserException("Username is already taken");
        }
        if(!verifyEmail(userRegistrationDto.getEmail())){
            throw new InvalidUserException("Email is not valid");
        }
        if(!verifyEmailFree(userRegistrationDto.getEmail())){
            throw new InvalidUserException("Email is already taken");
        }
        if(!verifyPassword(userRegistrationDto.getPassword())){
            throw new InvalidUserException("Password is not valid");
        }
        if(!verifyOldEnough(userRegistrationDto.getDateOfBirth())){
            throw new InvalidUserException("Age is below 18");
        }

        String salt = generateSalt();
        String password = userRegistrationDto.getPassword();
        String hashedPassword = hashPassword(password, salt);

        UserModel user = new UserModel(
                userRegistrationDto.getUsername(),
                userRegistrationDto.getEmail(),
                userRegistrationDto.getDateOfBirth(),
                true,
                0.0,
                hashedPassword,
                salt
        );
        return userRepository.save(user);
    }

    public boolean verifyUsername(String username){
        return username.matches("^[a-zA-Z][a-zA-Z0-9-_]{4,16}$");
    }

    public boolean verifyUsernameFree(String username){
        return userRepository.findByUsername(username).isEmpty();
    }

    public boolean verifyEmail(String email){
        return email.matches("^[\\w-.+]+@([\\w-]+.)+[\\w-]{2,16}$");
    }

    public boolean verifyEmailFree(String email){
        return userRepository.findByEmail(email).isEmpty();
    }

    public boolean verifyPassword(String password){
        return password.matches("^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W]).{8,64})$");
    }

    public boolean verifyOldEnough(Date dateOfBirth){
        return (new Date().getTime() - dateOfBirth.getTime()) / 31536000000L >= 18;
    }

    public String generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public String hashPassword(String password, String salt){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String plainPassword = password + "." + salt;
        return passwordEncoder.encode(plainPassword);
    }

}
