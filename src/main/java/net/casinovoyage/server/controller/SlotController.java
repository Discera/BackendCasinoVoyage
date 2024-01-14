package net.casinovoyage.server.controller;

import jakarta.servlet.http.HttpServletRequest;
import net.casinovoyage.server.dto.SlotSpinDto;
import net.casinovoyage.server.service.SlotService;
import net.casinovoyage.server.service.UserService;
import net.casinovoyage.server.slots.results.NormalSpinResult;
import net.casinovoyage.server.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/slot")
public class SlotController {

    @Autowired
    private SlotService slotService;

    @Autowired
    private UserService userService;

    @PostMapping("/spin")
    public ResponseEntity<?> spin(@RequestBody SlotSpinDto slotSpinDto, @RequestHeader("Authorization") String jwt){
        boolean validToken = JwtUtils.verifyToken(jwt);
        if(!validToken){
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
        }
        String userId = JwtUtils.getUserIdFromToken(jwt);

        double betSize = slotService.getBetSize(slotSpinDto.getBetLevel());
        boolean hasEnoughBalance = userService.hasEnoughBalance(userId, betSize);
        if(!hasEnoughBalance){
            return new ResponseEntity<>("Not enough balance", HttpStatus.BAD_REQUEST);
        }

        NormalSpinResult spinResult = slotService.spin(betSize);
        if (spinResult == null) {
            return new ResponseEntity<>("Invalid bet size", HttpStatus.BAD_REQUEST);
        }

        boolean balanceUpdated = userService.updateBalance(userId, spinResult.getWinnings() - spinResult.getBetSize());
        if(!balanceUpdated){
            return new ResponseEntity<>("Error while performing the spin action", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(spinResult, HttpStatus.OK);
    }
}
