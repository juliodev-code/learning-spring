package com.juliodev.lil.learningspring.util;

import com.juliodev.lil.learningspring.data.Reservation;
import com.juliodev.lil.learningspring.data.ReservationRepository;
import com.juliodev.lil.learningspring.data.RoomRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {

    private final ReservationRepository reservationRepository;

    public AppStartupEvent(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate date = LocalDate.parse("2022-01-01");
        Date dateForFind = Date.from(date.atStartOfDay(defaultZoneId).toInstant());
        Iterable<Reservation> findReservationByDate = this.reservationRepository.findAllByReservationDate(dateForFind);
        findReservationByDate.forEach(System.out::println);
    }
}
