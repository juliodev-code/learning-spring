package com.juliodev.lil.learningspring.web;

import com.juliodev.lil.learningspring.business.GuestDTO;
import com.juliodev.lil.learningspring.business.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

@Controller
@RequestMapping("/guests")
public class GuestController {

    private final ReservationService reservationService;

    public GuestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAllGuests(Model model){
        List<GuestDTO> allGuestList = reservationService.getAllGuests();
        model.addAttribute("allGuestList", allGuestList);
        return "guests";
    }
}
