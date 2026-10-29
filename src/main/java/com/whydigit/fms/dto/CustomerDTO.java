package com.whydigit.fms.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Long id;

    private Long orgId;

    private Long branch;

    private String customerName;

    private String customerType;

    private String gstin;

    private String pan;

    private String industryType;

    private boolean active;

    private boolean cancel;

    private String cancelRemarks;

    private String createdBy;

    private List<CustomerAddressDTO> customerAddresses = new ArrayList<>();
}
