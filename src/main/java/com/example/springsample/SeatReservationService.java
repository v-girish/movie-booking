package com.example.springsample;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SeatReservationService {

    // This map stores the reservations with movieId as key and seatNumber as value
    private final Map<String, Integer> reservations = new HashMap<>();

    public boolean reserveSeat(String movieId, int seatNumber) {
        // Check if the seat is already reserved for the given movieId
        if (reservations.containsKey(movieId) && (reservations.get(movieId) == seatNumber)) {
            // Seat is already reserved
            return false;
        } else {
            // Reserve the seat
            reservations.put(movieId, seatNumber);
            return true;
        }
    }

}
