package net.casinovoyage.server.service;

import jakarta.transaction.Transactional;
import net.casinovoyage.server.dto.SlotSpinDto;
import net.casinovoyage.server.repositorys.UserRepository;
import net.casinovoyage.server.slots.BetSizes;
import net.casinovoyage.server.slots.SpinGenerator;
import net.casinovoyage.server.slots.results.NormalSpinResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SlotService {

    @Autowired
    private UserRepository userRepository;

    public NormalSpinResult spin(double betSize){
        return SpinGenerator.generateSpinResult(betSize);
    }

    public double getBetSize(int betLevel){
        BetSizes betSize = switch (betLevel) {
            case 0 -> BetSizes.TINY;
            case 1 -> BetSizes.SMALL;
            case 2 -> BetSizes.MEDIUM;
            case 3 -> BetSizes.LARGE;
            case 4 -> BetSizes.HUGE;
            case 5 -> BetSizes.EXTREME;
            case 6 -> BetSizes.INSANE;
            case 7 -> BetSizes.MAX;
            default -> null;
        };
        if (betSize == null) {
            return 0;
        }
        return betSize.bet();
    }




}
