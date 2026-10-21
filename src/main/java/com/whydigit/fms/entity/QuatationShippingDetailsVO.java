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
@Table(name = "quotation_shipping_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuatationShippingDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quotation_shipping_detailsgen")
    @SequenceGenerator(name = "quotation_shipping_detailsgen",
            sequenceName = "quotation_shipping_detailsseq",
            initialValue = 1000000001, allocationSize = 1)
    @Column(name = "quotation_shipping_details_id")
    private Long id;



    // Package Details
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "package_weight", precision = 10, scale = 2, nullable = false)
    private BigDecimal packageWeight;

    @Column(name = "weight_unit", length = 5, nullable = false)
    private String weightUnit;

    // Dimensions
    @Column(name = "length", precision = 10, scale = 2, nullable = false)
    private BigDecimal length;

    @Column(name = "width", precision = 10, scale = 2, nullable = false)
    private BigDecimal width;

    @Column(name = "height", precision = 10, scale = 2, nullable = false)
    private BigDecimal height;

    @Column(name = "dimension_unit", length = 5, nullable = false)
    private String dimensionUnit;

    // Shipment Summary
    @Column(name = "gross_weight", precision = 12, scale = 3, nullable = false)
    private BigDecimal grossWeight;

    @Column(name = "volumetric_weight", precision = 12, scale = 3)
    private BigDecimal volumetricWeight;

    @Column(name = "chargeable_weight", precision = 12, scale = 3)
    private BigDecimal chargeableWeight;

    @Column(name = "total_volume_cbm", precision = 12, scale = 4)
    private BigDecimal totalVolumeCbm;

    @Column(name = "cargo_description", length = 250)
    private String cargoDescription;

    @Column(name = "cargo_type", length = 50)
    private String cargoType;
    
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quotation_id")
    private QuotationVO quotationVO;

   
}