package com.teamLead.reservationVoiture.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.teamLead.reservationVoiture.dto.ReservationDTO;

/**
 * FrontOffice Controller
 * Affiche la liste des réservations en appelant l'API BackOffice
 * N'attaque pas directement la base de données
 */
@Controller
@RequestMapping("/reservations")
public class ReservationFrontController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${backoffice.api.url:http://localhost:8081/api}")
    private String backofficeApiUrl;

    /**
     * Affiche la liste des réservations avec filtre optionnel par date
     */
    @GetMapping("/liste")
    public String listeReservations(
            @RequestParam(required = false) String date,
            Model model
    ) {
        try {
            String url = backofficeApiUrl + "/reservation/liste";
            
            // Ajouter le paramètre date si fourni
            if (date != null && !date.isEmpty()) {
                url += "?date=" + date;
            }

            // Appel API BackOffice pour récupérer les réservations
            ReservationDTO[] reservations = restTemplate.getForObject(
                    url,
                    ReservationDTO[].class
            );

            // Convertir en liste
            List<ReservationDTO> reservationsList = reservations != null 
                    ? Arrays.asList(reservations)
                    : Collections.emptyList();

            model.addAttribute("reservations", reservationsList);
            model.addAttribute("filteredDate", date);

            return "reservations/liste";

        } catch (RestClientException e) {
            model.addAttribute("error", "Erreur de connexion avec le serveur BackOffice: " + e.getMessage());
            model.addAttribute("reservations", Collections.emptyList());
            return "reservations/liste";
        }
    }

    /**
     * Page d'accueil FrontOffice
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
