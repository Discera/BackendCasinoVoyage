package net.casinovoyage.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/slot")
public class SlotController {

    @GetMapping("/spin")
    public ResponseEntity<?> spin(){
        return new ResponseEntity<>("Spin", HttpStatus.OK);
    }
}
