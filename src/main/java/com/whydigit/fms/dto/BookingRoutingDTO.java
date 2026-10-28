package com.whydigit.fms.dto;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRoutingDTO {

//    private Long id;

    private String placeOfReceipt;

    private String origin;

    private String destination;

    private String placeOfDelivery;

    private LocalDate requestedETD;

    private LocalDate requestedETA;

    private String carrierPreference;

    private String routingPreference;

    private String incoterm;

}

