package com.whydigit.fms.ResponseDTO;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingCommercialResponseDTO {

    private Long id;

    private String freightPaymentTerms;
    private String quotationReference;

    private BigDecimal sellRate;
    private String sellCurrency;

    private BigDecimal buyRate;
    private String buyCurrency;

    private String paymentTerms;
    private String creditCheckStatus;
    private String commercialRemarks;
}