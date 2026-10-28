package com.whydigit.fms.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
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
import com.whydigit.fms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quotation_airfreight_rates")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationAirFreightRatesVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quotation_airfreight_ratesgen")
    @SequenceGenerator(name = "quotation_airfreight_ratesgen",
            sequenceName = "quotation_airfreight_ratesseq",
            initialValue = 1000000001, allocationSize = 1)
    @Column(name = "quotation_airfreight_rates_id")
    private Long id;


    // Airline Details
    @Column(name = "airline_name", length = 100, nullable = false)
    private String airlineName;

    @Column(name = "airline_code", length = 5)
    private String airlineCode;

    @Column(name = "product_name", length = 100, nullable = false)
    private String productName;

    @Column(name = "destination_code", length = 5, nullable = false)
    private String destinationCode;

    @Column(name = "transit_via", length = 100)
    private String transitVia;

    @Column(name = "freight_rate_type", length = 20)
    private String freightRateType;

    // Freight Rates
    @Column(name = "minimum_weight", precision = 10, scale = 2)
    private BigDecimal minimumWeight;

    @Column(name = "rate_per_kg", precision = 12, scale = 4, nullable = false)
    private BigDecimal ratePerKg;

    @Column(name = "fuel_rate", precision = 12, scale = 4)
    private BigDecimal fuelRate;

    @Column(name = "risk_rate", precision = 12, scale = 4)
    private BigDecimal riskRate;

    @Column(name = "freight_amount", precision = 14, scale = 2, nullable = false)
    private BigDecimal freightAmount;

    @Column(name = "surcharges", precision = 14, scale = 2, nullable = false)
    private BigDecimal surcharges;

    @Column(name = "airfreight_total", precision = 14, scale = 2)
    private BigDecimal airfreightTotal;

    @Column(name = "rate_remarks", length = 500)
    private String rateRemarks;


    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quotation_id")
    private QuotationVO quotationVO;

}