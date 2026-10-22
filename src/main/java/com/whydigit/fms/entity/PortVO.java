package com.whydigit.fms.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.whydigit.fms.dto.CreatedUpdatedDate;

public class PortVO 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	
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
