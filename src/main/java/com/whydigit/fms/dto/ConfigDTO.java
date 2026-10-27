package com.whydigit.fms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigDTO {

    private Long id;

    private String company;

    private Long orgId;

    // Existing branch ID used to find BranchVO
    private Long branch;

    private String cancelRemarks;

    private String createdBy;

    private List<ConfigModeDTO> configModes;

    private List<ConfigProductDTO> configProducts;

    private List<ConfigServiceDTO> configServices;
}