package com.whydigit.fms.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponseDTO 
{

    private Long id;

    private String serviceCode;

    private String serviceDescription;

    private Long orgId;

    private BranchResponsesDTO branch;

    private String active;

    private String cancel;

    private String cancelRemarks;

    private String createdBy;

    private String updatedBy;

}