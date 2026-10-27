package com.whydigit.fms.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.fms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "config")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "configgen")
	@SequenceGenerator(name = "configgen", sequenceName = "configseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "config_id")
	private Long id;

	@Column(name = "company", length = 200)
	private String company;

	@Column(name = "org_id")
	private Long orgId;

	@ManyToOne(fetch = FetchType.LAZY)
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

	// Child: Configuration Modes
	@OneToMany(mappedBy = "config", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<ConfigModeVO> configModes = new ArrayList<>();

	// Child: Configuration Products
	@OneToMany(mappedBy = "config", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<ConfigProductVO> configProducts = new ArrayList<>();

	// Child: Configuration Services
	@OneToMany(mappedBy = "config", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<ConfigServiceVO> configServices = new ArrayList<>();

}