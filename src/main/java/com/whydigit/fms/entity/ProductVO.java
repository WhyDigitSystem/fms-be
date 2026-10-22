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
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "productgen"
    )
    @SequenceGenerator(
        name = "productgen",
        sequenceName = "productseq",
        initialValue = 1000000001,
        allocationSize = 1
    )
    @Column(name = "product_id")
    private Long id;

    // Product Information
    @Column(name = "product_code")
    private Long productCode;

    @Column(name = "product_description")
    private String productDescription;

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