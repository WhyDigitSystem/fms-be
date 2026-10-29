package com.whydigit.fms.dto;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    private Long id;

    private String bookingType;
    
    private String type;

   	private String quotationNo;
   	private LocalDate quotationDate;

    private String transportMode;

    private String serviceType;

    private String customer;

    private String customerReference;

    private String salesperson;

    private String operationsOwner;

    private String bookingStatus ;

    private Long orgId;

    // Branch reference
    private Long branch;

//    private boolean active ;

    private String cancelRemarks;

    private String remarks;

    // Audit fields
    private String createdBy;


    // Child details
    private List<BookingRoutingDTO> routingDetails = new ArrayList<>();

    private List<BookingPartyDTO> partyDetails = new ArrayList<>();

    private List<BookingCargoDTO> cargoDetails = new ArrayList<>();

    private List<BookingEquipmentDTO> equipmentDetails = new ArrayList<>();

    private List<BookingCommercialDTO> commercialDetails = new ArrayList<>();

    private List<BookingInstructionDTO> instructionDetails = new ArrayList<>();
}
