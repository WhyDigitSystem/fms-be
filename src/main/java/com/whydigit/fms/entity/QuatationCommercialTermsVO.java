package com.whydigit.fms.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quotation_commercial_terms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuatationCommercialTermsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quotation_commercial_termsgen")
    @SequenceGenerator(name = "quotation_commercial_termsgen",
            sequenceName = "quotation_commercial_termsseq",
            initialValue = 1000000001, allocationSize = 1)
    @Column(name = "quotation_commercial_terms_id")
    private Long id;

  

    // Quotation Totals
    @Column(name = "airline_quotation_total", precision = 14, scale = 2)
    private BigDecimal airlineQuotationTotal;

    @Column(name = "origin_charge_total", precision = 14, scale = 2)
    private BigDecimal originChargeTotal;

    // Insurance
    @Column(name = "insurance_percentage", precision = 5, scale = 2)
    private BigDecimal insurancePercentage;

    @Column(name = "cargo_value", precision = 16, scale = 2)
    private BigDecimal cargoValue;

    // Profit Sharing
    @Column(name = "profit_sharing_percentage", precision = 5, scale = 2)
    private BigDecimal profitSharingPercentage;

    @Column(name = "bank_exchange_percentage", precision = 5, scale = 2)
    private BigDecimal bankExchangePercentage;

    // Storage
    @Column(name = "free_storage_days")
    private int freeStorageDays;

    @Column(name = "storage_rate", precision = 12, scale = 2)
    private BigDecimal storageRate;

    @Column(name = "storage_minimum_charge", precision = 12, scale = 2)
    private BigDecimal storageMinimumCharge;

    // Terms and Conditions
    @Column(name = "rate_validity_remarks", length = 500)
    private String rateValidityRemarks;

    @Column(name = "terms_and_conditions", columnDefinition = "TEXT")
    private String termsAndConditions;

    @Column(name = "general_remarks", columnDefinition = "TEXT")
    private String generalRemarks;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quotation_id", nullable = false)
    private QuotationVO quotationVO;
   
}