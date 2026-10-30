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
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customergen")
	@SequenceGenerator(name = "customergen", sequenceName = "customerseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "customer_id")
	private Long id;

	@Column(name = "org_id")
	private Long orgId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@Column(name = "customer_code", nullable = false, unique = true, length = 20)
	private String customerCode;
	
	@Column(name = "customer_name", length = 200)
	private String customerName;

	@Column(name = "customer_type", length = 50)
	private String customerType;

	@Column(name = "gstin", length = 20)
	private String gstin;

	@Column(name = "pan", length = 15)
	private String pan;

	@Column(name = "industry_type", length = 100)
	private String industryType;

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

	// Child: Customer Addresses
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<CustomerAddressVO> customerAddresses = new ArrayList<>();
}