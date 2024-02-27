package com.example.springsample;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seats")
public class SeatReservationController {

    private final SeatReservationService seatReservationService;

    public SeatReservationController(SeatReservationService seatReservationService) {
        this.seatReservationService = seatReservationService;
    }

    @PostMapping("/reserve")
    public ResponseEntity<?> reserveSeat(@RequestParam String movieId, @RequestParam int seatNumber) {
        try {
            // Call the service to reserve the seat
            final boolean isReserved = seatReservationService.reserveSeat(movieId, seatNumber);
            if (isReserved) {
                return ResponseEntity.ok("Seat reserved successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Seat is already reserved.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while reserving the seat.");
        }
    }
}

