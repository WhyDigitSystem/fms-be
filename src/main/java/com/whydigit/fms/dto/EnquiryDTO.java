package com.whydigit.fms.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnquiryDTO {

    private Long id;

    // Enquiry Information
    private String company;
    private String movement;

    // Shipment Mode
    private String shipmentMode;

    // Route
    private Long fromLocation;
    private Long toLocation;

    // Sea
    private Long originPort;
    private Long destinationPort;

    // Air
    private Long originAirport;
    private Long destinationAirport;

    // Road
    private Long originTerminal;
    private Long destinationTerminal;

    // Cargo
    private String cargoDescription;
    private String cargoType;
    private BigDecimal volumeCbm;
    private BigDecimal grossWeightKg;
    private int noOfPackages;

    // Air dimensions
    private BigDecimal lengthCm;
    private BigDecimal widthCm;
    private BigDecimal heightCm;

    // Road
    private String vehicleType;

    // Audit
    private Long orgId;
    private Long branch;
    private String remarks;
    private String cancelRemarks;
    private String createdBy;


}