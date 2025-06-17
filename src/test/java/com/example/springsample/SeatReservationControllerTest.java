package com.example.springsample;

import org.junit.jupiter.api.Test; // Import JUnit 5 Test annotation
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus; // Import HttpStatus
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.mockito.Mockito.when;

@WebFluxTest(SeatReservationController.class)
public class SeatReservationControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private SeatReservationService seatReservationService;

    @Test
    void reserveSeat_whenServiceReturnsTrue_shouldReturnOk() {
        // Mock the service layer
        when(seatReservationService.reserveSeat("movie123", 10)).thenReturn(true);

        // Perform the request and assert the response
        webTestClient.post().uri("/api/seats/reserve?movieId=movie123&seatNumber=10")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Seat reserved successfully.");
    }

    @Test
    void reserveSeat_whenServiceReturnsFalse_shouldReturnConflict() {
        // Mock the service layer
        when(seatReservationService.reserveSeat("movie456", 20)).thenReturn(false);

        // Perform the request and assert the response
        webTestClient.post().uri("/api/seats/reserve?movieId=movie456&seatNumber=20")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT) // Use isEqualTo(HttpStatus.CONFLICT)
                .expectBody(String.class).isEqualTo("Seat is already reserved.");
    }

    @Test
    void reserveSeat_whenServiceThrowsException_shouldReturnInternalServerError() {
        // Mock the service layer to throw an exception
        when(seatReservationService.reserveSeat("movie789", 30)).thenThrow(new RuntimeException("Service failure"));

        // Perform the request and assert the response
        webTestClient.post().uri("/api/seats/reserve?movieId=movie789&seatNumber=30")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(String.class).isEqualTo("An error occurred while reserving the seat.");
    }
}
