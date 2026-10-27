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
@Table(name = "port")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortVO 

{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "portgen")
	@SequenceGenerator(name = "portgen", sequenceName = "portseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "port_id")
	private Long id;

	@Column(name = "port_type", length = 25)
	private String portType;

	@Column(name = "port_code", length = 25)
	private String portCode;

	@Column(name = "port_name", length = 100)
	private String portName;

	@ManyToOne
	@JoinColumn(name = "country")
	private CountryVO country;

	@Column(name = "org_id")
	private Long orgId;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@Column(name = "active")
	private boolean active;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "cancel_remarks")
	private String cancelRemarks;

	// AUDIT
	@Column(name = "createdby", length = 25)
	private String createdBy;

	@Column(name = "modifiedby", length = 25)
	private String updatedBy;

	@JsonGetter("active")
	public String getActive() 
	{
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancel")
	public String getCancel() 
	{
		return cancel ? "T" : "F";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}