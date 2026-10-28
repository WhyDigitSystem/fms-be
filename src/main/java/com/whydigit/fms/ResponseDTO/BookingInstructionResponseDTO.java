package com.whydigit.fms.ResponseDTO;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingInstructionResponseDTO {

    private Long id;

    private String documentType;
    private String document;
    private String fileName;
    private String originalName;
    private Long fileSize;
    private String contentType;
    private LocalDateTime uploadOn;
    private String pickupInstructions;
    private String deliveryInstructions;
    private String internalRemarks;
}
