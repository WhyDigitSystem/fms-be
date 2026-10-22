package com.whydigit.fms.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityResponseDTO {

    private Long id;
    private String cityCode;
    private String cityName;
    private CountryResponseDTO country;
    private StateDetailResponseDTO state;
    private String active;
    private String createdBy;
    private String updatedBy;
    private Long orgId;
    private String cancel;
}
