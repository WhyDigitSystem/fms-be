package com.whydigit.fms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    private Long productCode;

    private String productDescription;

    private Long orgId;

    private Long branch;

    private String cancelRemarks;

    private String createdBy;
}