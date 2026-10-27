package com.whydigit.fms.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuatationCommercialTermsDTO {


    // Quotation Totals
    private BigDecimal airlineQuotationTotal;
    private BigDecimal originChargeTotal;

    // Insurance
    private BigDecimal insurancePercentage;
    private BigDecimal cargoValue;

    // Profit Sharing
    private BigDecimal profitSharingPercentage;
    private BigDecimal bankExchangePercentage;

    // Storage
    private Integer freeStorageDays;
    private BigDecimal storageRate;
    private BigDecimal storageMinimumCharge;

    // Terms and Conditions
    private String rateValidityRemarks;
    private String termsAndConditions;
    private String generalRemarks;

}