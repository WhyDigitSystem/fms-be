package com.whydigit.fms.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO 
{

    private Long id;

    private String productCode;

    private String productDescription;

    private Long orgId;

    private BranchResponsesDTO branch;

    private String active;

    private String cancel;

    private String cancelRemarks;

    private String createdBy;

    private String updatedBy;

}