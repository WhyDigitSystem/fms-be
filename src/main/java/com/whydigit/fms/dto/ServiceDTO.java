package com.whydigit.fms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO {
	private Long id;

    private String serviceCode;

    private String serviceDescription;

    private Long orgId;

    private Long branch;

    private String cancelRemarks;

    private String createdBy;

}