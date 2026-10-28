package com.whydigit.fms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortDTO 
{

    private Long id;

    private String portType;

    private String portCode;

    private String portName;

    private Long country;

	private Long city;

    private Long orgId;

    private Long branch;

    private boolean active;

    private String cancelRemarks;

    private String createdBy;
}