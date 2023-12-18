package net.casinovoyage.server.service;

import jakarta.transaction.Transactional;
import net.casinovoyage.server.models.UserModel;
import net.casinovoyage.server.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel registerUser(UserModel userModel){
        return userRepository.save(userModel);
    }

    public boolean verifyUsername(String username){

        return false;
    }

}
