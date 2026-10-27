package com.whydigit.fms.ResponseDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigResponseDTO {

    private Long id;

    private String company;

    private Long orgId;

    private BranchResponsesDTO branch;

    private String active;

    private String cancel;

    private String cancelRemarks;

    private String createdBy;

    private String updatedBy;

    private List<ConfigModeResponsesDTO> configModes;

    private List<ConfigServiceResponsesDTO> configServices;

    private List<ConfigProductResponsesDTO> configProducts;
}