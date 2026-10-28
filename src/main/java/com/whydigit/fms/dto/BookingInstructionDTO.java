package com.whydigit.fms.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingInstructionDTO {

//    private Long id;

    // Document Information

    private String documentType;

    // Instructions
    private String pickupInstructions;

    private String deliveryInstructions;

    private String internalRemarks;

}

