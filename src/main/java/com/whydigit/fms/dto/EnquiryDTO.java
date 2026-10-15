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
    private String customer;
    private String movement;

    // Shipment Mode
    private String shipmentMode;

    // Route
    private String fromLocation;
    private String toLocation;

    // Sea
    private String originPort;
    private String destinationPort;

    // Air
    private String originAirport;
    private String destinationAirport;

    // Road
    private String originTerminal;
    private String destinationTerminal;

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
    private String branch;
    private String remarks;
    private String cancelRemarks;
    private String createdBy;


}