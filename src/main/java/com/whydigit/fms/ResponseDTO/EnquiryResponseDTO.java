package com.whydigit.fms.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnquiryResponseDTO {

    private Long id;

    private String docId;
    private LocalDate docDate;
    // Enquiry Information
    private String company;
    private String movement;

    // Shipment Mode
    private String shipmentMode;

    // Route
    private CityResponseDetailsDTO fromLocation;
    private CityResponseDetailsDTO toLocation;

    // Sea
    private PortResponseDetailsDTO originPort;
    private PortResponseDetailsDTO destinationPort;

    // Air
    private PortResponseDetailsDTO originAirport;
    private PortResponseDetailsDTO destinationAirport;

    // Road
    private PortResponseDetailsDTO originTerminal;
    private PortResponseDetailsDTO destinationTerminal;

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
    private BranchResponseDTO branch;
    private String active;
    private String cancel;

    private String remarks;
    private String cancelRemarks;
    private String createdBy;
    private String updatedBy;


}
