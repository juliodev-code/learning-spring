package com.juliodev.lil.learningspring.business;

import com.juliodev.lil.learningspring.data.*;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;

@Service
public class ReservationService {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getRoomReservationsForDate(Date date) {
        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservationMap.put(room.getId(), roomReservation);
        });
        Iterable<Reservation> reservations = this.reservationRepository.findReservationByReservationDate(new java.sql.Date(date.getTime()));
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
            roomReservation.setDate(date);
            Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
            roomReservation.setGuestId(guest.getGuestId());
        });
        List<RoomReservation> roomReservations = new ArrayList<>();
        for (Long id : roomReservationMap.keySet()) {
            roomReservations.add(roomReservationMap.get(id));
        }
        roomReservations.sort(new Comparator<RoomReservation>() {
            @Override
            public int compare(RoomReservation o1, RoomReservation o2) {
                if (o1.getRoomName().equals(o2.getRoomName())) {
                    return o1.getRoomNumber().compareTo(o2.getRoomNumber());
                }
                return o1.getRoomName().compareTo(o2.getRoomName());
            }
        });
        return roomReservations;
    }

    public List<GuestDTO> getAllGuests(){
        Iterable<Guest> allGuestIterable = this.guestRepository.findAll();
        List<GuestDTO> allGuestList = new ArrayList<>();
        allGuestIterable.forEach((guest)->{
            GuestDTO newGuestElement = new GuestDTO();
            newGuestElement.setGuestId(guest.getGuestId());
            newGuestElement.setFirstName(guest.getFirstName());
            newGuestElement.setLastName(guest.getLastName());
            newGuestElement.setEmailAddress(guest.getEmailAddress());
            newGuestElement.setAddress(guest.getAddress());
            newGuestElement.setCountry(guest.getCountry());
            newGuestElement.setState(guest.getState());
            newGuestElement.setPhoneNumber(guest.getPhoneNumber());

            allGuestList.add(newGuestElement);
        });
        return allGuestList;
    }
}
