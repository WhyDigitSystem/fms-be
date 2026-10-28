package com.whydigit.fms.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateResponseDTO {

    private Long id;
    private String stateCode;
    private String stateName;
    private CountryResponseDTO country;
    private String region;
    private String stateNumber;
    private String active;
    private String createdBy;
    private String updatedBy;
    private Long orgId;
}
