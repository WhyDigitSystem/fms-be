package com.whydigit.fms.entity;


import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.whydigit.fms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mode_master")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModeMasterVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mode_mastergen")
    @SequenceGenerator(
            name = "mode_mastergen",
            sequenceName = "mode_masterseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "mode_id")
    private Long id;

    // Mode Information
    @Column(name = "mode_code", length = 20, nullable = false, unique = true)
    private String modeCode;

    @Column(name = "mode_name", length = 100, nullable = false)
    private String modeName;

    @Column(name = "description", length = 500)
    private String description;

    // Default Fields
    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "active")
    private boolean active = true;

    @Column(name = "cancel")
    private boolean cancel = false;

    @Column(name = "cancel_remarks", length = 500)
    private String cancelRemarks;

    @Column(name = "createdby", length = 25)
    private String createdBy;

    @Column(name = "modifiedby", length = 25)
    private String updatedBy;
    
    
    @JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}