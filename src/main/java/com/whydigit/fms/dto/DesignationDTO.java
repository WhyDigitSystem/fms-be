package com.whydigit.fms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class DesignationDTO {

	private Long id;

	private String designation;
	private Long orgId;
	private boolean active;
	private boolean cancel;
	private String createdBy;

}
