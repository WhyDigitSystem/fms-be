package com.whydigit.fms.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingCargoDTO {

    private Long id;

    private String commodityDescription;

    private String packageType;

    private int packageCount;

    private BigDecimal grossWeight;

    private BigDecimal netWeight;

    private String weightUnit;

    private BigDecimal volume;

    private String volumeUnit;

    private BigDecimal length;

    private BigDecimal width;

    private BigDecimal height;

    private String dimensionUnit;

    private String marksAndNumbers;

    private boolean hazardous = false;

    private String hazardousClass;

    private String unNumber;

    private String remarks;

 
}