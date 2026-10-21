package com.whydigit.fms.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationAirFreightRatesDTO {


    // Airline Details
    private String airlineName;
    private String airlineCode;
    private String productName;
    private String destinationCode;
    private String transitVia;
    private String freightRateType;

    // Freight Rates
    private BigDecimal minimumWeight;
    private BigDecimal ratePerKg;
    private BigDecimal fuelRate;
    private BigDecimal riskRate;
    private BigDecimal freightAmount;
    private BigDecimal surcharges;
    private BigDecimal airfreightTotal;
    private String rateRemarks;

}