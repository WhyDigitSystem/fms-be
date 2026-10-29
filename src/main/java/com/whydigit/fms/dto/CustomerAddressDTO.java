package com.whydigit.fms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddressDTO {

    private Long id;

    private String address;

    private Long country;

    private Long state;

    private Long city;

    private String postalCode;

    private String contactName;

    private String mobile;

    private String alternateMobile;

    private String email;

    private String landmark;


}
