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
@Table(name = "quotation_origin_charges")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationOriginChargesVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quotation_origin_chargesgen")
    @SequenceGenerator(name = "quotation_origin_chargesgen",
            sequenceName = "quotation_origin_chargesseq",
            initialValue = 1000000001, allocationSize = 1)
    @Column(name = "quotation_origin_charges_id")
    private Long id;



    // Origin Charge Details
    @Column(name = "charge_description", length = 150, nullable = false)
    private String chargeDescription;

    @Column(name = "rate", precision = 12, scale = 4, nullable = false)
    private BigDecimal rate;

    @Column(name = "rate_unit", length = 30, nullable = false)
    private String rateUnit;

    @Column(name = "minimum_charge", precision = 12, scale = 2)
    private BigDecimal minimumCharge;

    @Column(name = "quantity_units", precision = 12, scale = 3, nullable = false)
    private BigDecimal quantityUnits;

    @Column(name = "chargeable_basis", length = 30)
    private String chargeableBasis;

    @Column(name = "charge_amount", precision = 14, scale = 2, nullable = false)
    private BigDecimal chargeAmount;

    @Column(name = "currency", length = 3, nullable = false)
    private String currency;

    @Column(name = "charge_notes", length = 500)
    private String chargeNotes;
    
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quotation_id")
    private QuotationVO quotationVO;

  
}