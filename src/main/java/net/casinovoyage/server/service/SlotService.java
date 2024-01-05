package net.casinovoyage.server.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SlotService {

    @Autowired
    private UserService userService;


}
