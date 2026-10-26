package com.whydigit.fms.ResponseDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {

    private Long id;
    private String docId;
    private LocalDate docDate;

    private String bookingType;
    private String transportMode;
    private String serviceType;

    private String customer;
    private String customerReference;
    private String salesperson;
    private String operationsOwner;
    private String bookingStatus;

    private Long orgId;
    private BranchResponseDTO branch;

    private boolean active;
    private boolean cancel;

    private String cancelRemarks;
    private String remarks;

    private String createdBy;
    private String updatedBy;


    private List<BookingRoutingResponseDTO> routingDetails = new ArrayList<>();
    private List<BookingPartyResponseDTO> partyDetails = new ArrayList<>();
    private List<BookingCargoResponseDTO> cargoDetails = new ArrayList<>();
    private List<BookingEquipmentResponseDTO> equipmentDetails = new ArrayList<>();
    private List<BookingCommercialResponseDTO> commercialDetails = new ArrayList<>();
    private List<BookingInstructionResponseDTO> instructionDetails = new ArrayList<>();

}