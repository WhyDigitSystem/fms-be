package com.whydigit.fms.ResponseDTO;




import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuatationShippingDetailsResponseDTO {

    private Long id;

    // Package Details
    private int quantity;
    private BigDecimal packageWeight;
    private String weightUnit;

    // Dimensions
    private BigDecimal length;
    private BigDecimal width;
    private BigDecimal height;
    private String dimensionUnit;

    // Shipment Summary
    private BigDecimal grossWeight;
    private BigDecimal volumetricWeight;
    private BigDecimal chargeableWeight;
    private BigDecimal totalVolumeCbm;

    private String cargoDescription;
    private String cargoType;
}