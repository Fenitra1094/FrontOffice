package com.teamLead.reservationVoiture.controller;

import com.teamLead.reservationVoiture.dto.ReservationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/front")
public class ReservationFrontController {

    private final String apiUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public ReservationFrontController(@Value("${external.api.reservations.url:http://localhost:8081/reservations}") String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @GetMapping("/reservations")
    public String listReservations(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            Model model
    ) {
        List<ReservationDto> reservations = fetchReservations();

        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;
        if (date != null && !date.isEmpty()) {
            LocalDate d = LocalDate.parse(date, fmt);
            reservations = reservations.stream()
                    .filter(r -> d.equals(r.getArrivalDate()))
                    .collect(Collectors.toList());
        } else if ((from != null && !from.isEmpty()) || (to != null && !to.isEmpty())) {
            LocalDate f = (from != null && !from.isEmpty()) ? LocalDate.parse(from, fmt) : LocalDate.MIN;
            LocalDate t = (to != null && !to.isEmpty()) ? LocalDate.parse(to, fmt) : LocalDate.MAX;
            reservations = reservations.stream()
                    .filter(r -> r.getArrivalDate() != null && !r.getArrivalDate().isBefore(f) && !r.getArrivalDate().isAfter(t))
                    .collect(Collectors.toList());
        }

        model.addAttribute("reservations", reservations);
        return "reservations";
    }

    private List<ReservationDto> fetchReservations() {
        try {
            ResponseEntity<List<ReservationDto>> resp = restTemplate.exchange(apiUrl, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {
                    });
            return resp.getBody() != null ? resp.getBody() : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
