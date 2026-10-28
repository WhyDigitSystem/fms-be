package com.whydigit.fms.dto;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingCommercialDTO {

//    private Long id;

    // Commercial Information
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
