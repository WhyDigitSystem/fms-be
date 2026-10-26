package com.whydigit.fms.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingInstructionDTO {

    private Long id;

    // Document Information
    private String customerReferenceDocument;

    private String packingList;

    private String commercialInvoice;

    private String dangerousGoodsDocument;

    // Instructions
    private String pickupInstructions;

    private String deliveryInstructions;

    private String internalRemarks;

}

