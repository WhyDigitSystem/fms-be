package com.whydigit.fms.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationOriginChargesDTO {


    // Origin Charge Details
    private String chargeDescription;
    private BigDecimal rate;
    private String rateUnit;
    private BigDecimal minimumCharge;
    private BigDecimal quantityUnits;
    private String chargeableBasis;
    private BigDecimal chargeAmount;
    private String currency;
    private String chargeNotes;

}