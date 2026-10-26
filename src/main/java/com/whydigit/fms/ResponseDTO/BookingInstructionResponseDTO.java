package com.whydigit.fms.ResponseDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingInstructionResponseDTO {

    private Long id;

    private String customerReferenceDocument;
    private String packingList;
    private String commercialInvoice;
    private String dangerousGoodsDocument;

    private String pickupInstructions;
    private String deliveryInstructions;
    private String internalRemarks;
}
