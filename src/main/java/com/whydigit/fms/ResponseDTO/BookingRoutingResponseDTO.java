package com.whydigit.fms.ResponseDTO;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRoutingResponseDTO {

    private Long id;

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
