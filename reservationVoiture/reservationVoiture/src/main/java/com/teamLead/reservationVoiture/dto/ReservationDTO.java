package com.teamLead.reservationVoiture.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservationDTO {
    
    private Long id;
    
    @JsonProperty("idClient")
    private String idClient;
    
    @JsonProperty("nbPassager")
    private Integer nbPassager;
    
    @JsonProperty("dateHeureArrivee")
    private String dateHeureArrivee;
    
    @JsonProperty("nomHotel")
    private String nomHotel;

    // Constructors
    public ReservationDTO() {
    }

    public ReservationDTO(Long id, String idClient, Integer nbPassager, String dateHeureArrivee, String nomHotel) {
        this.id = id;
        this.idClient = idClient;
        this.nbPassager = nbPassager;
        this.dateHeureArrivee = dateHeureArrivee;
        this.nomHotel = nomHotel;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public Integer getNbPassager() {
        return nbPassager;
    }

    public void setNbPassager(Integer nbPassager) {
        this.nbPassager = nbPassager;
    }

    public String getDateHeureArrivee() {
        return dateHeureArrivee;
    }

    public void setDateHeureArrivee(String dateHeureArrivee) {
        this.dateHeureArrivee = dateHeureArrivee;
    }

    public String getNomHotel() {
        return nomHotel;
    }

    public void setNomHotel(String nomHotel) {
        this.nomHotel = nomHotel;
    }
}
