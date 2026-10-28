package com.whydigit.fms.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationCustomerInformationResponseDTO {

    private Long id;

    // Customer Information
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String customerAddress;

    // Agent Information
    private String agentName;
    private String agentPhone;
    private String agentEmail;
}
