package com.whydigit.fms.ResponseDTO;

import java.util.ArrayList;
import java.util.List;

import com.whydigit.fms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {

    private Long id;

    private Long orgId;

    private BranchResponsesDTO branch;

    private String customerCode;

    private String customerName;

    private String customerType;

    private String gstin;

    private String pan;

    private String industryType;

    private String active;

    private String cancel;

    private String cancelRemarks;

    private String createdBy;

    private String updatedBy;

    private CreatedUpdatedDate commonDate;

    private List<CustomerAddressResponseDTO> customerAddresses = new ArrayList<>();
}
