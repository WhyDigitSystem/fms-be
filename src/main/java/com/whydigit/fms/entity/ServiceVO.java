package com.whydigit.fms.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.whydigit.fms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "service")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servicegen")
	@SequenceGenerator(name = "servicegen", sequenceName = "serviceseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "service_id")
	private Long id;

	// Service Information
	@Column(name = "service_code")
	private Long serviceCode;

	@Column(name = "service_description")
	private String serviceDescription;

	// Audit Fields
	@Column(name = "org_id")
	private Long orgId;

	// Normalized Branch Relationship
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@Column(name = "active")
	private boolean active = true;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "cancel_remarks")
	private String cancelRemarks;

	@Column(name = "createdby", length = 25)
	private String createdBy;

	@Column(name = "modifiedby", length = 25)
	private String updatedBy;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}