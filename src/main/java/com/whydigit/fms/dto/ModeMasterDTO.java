package com.whydigit.fms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModeMasterDTO {

    private Long id;

    // Mode Information
    private String modeCode;
    private String modeName;
    private String description;

    // Default Fields
    private Long orgId;
    private String cancelRemarks;
    private String createdBy;
}