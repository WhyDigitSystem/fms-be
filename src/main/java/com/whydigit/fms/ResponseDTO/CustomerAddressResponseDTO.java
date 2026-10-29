package com.whydigit.fms.ResponseDTO;

import com.whydigit.fms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddressResponseDTO {

    private Long id;
    private String address;
    private CountryResponseDTO country;
    private StateDetailResponseDTO state;
    private CityResponseDetailsDTO city;
    private String postalCode;
    private String contactName;
    private String mobile;
    private String alternateMobile;
    private String email;
    private String landmark;
   
}
