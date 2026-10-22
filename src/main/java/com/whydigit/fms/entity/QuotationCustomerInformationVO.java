package com.whydigit.fms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quotation_customer_information")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationCustomerInformationVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quotation_customer_informationgen")
    @SequenceGenerator(name = "quotation_customer_informationgen",
            sequenceName = "quotation_customer_informationseq",
            initialValue = 1000000001, allocationSize = 1)
    @Column(name = "quotation_customer_information_id")
    private Long id;



    // Customer Information
    @Column(name = "customer_name", length = 150, nullable = false)
    private String customerName;

    @Column(name = "customer_phone", length = 20)
    private String customerPhone;

    @Column(name = "customer_email", length = 254)
    private String customerEmail;

    @Column(name = "customer_address", length = 250)
    private String customerAddress;

    // Agent Information
    @Column(name = "agent_name", length = 100)
    private String agentName;

    @Column(name = "agent_phone", length = 20)
    private String agentPhone;

    @Column(name = "agent_email", length = 254)
    private String agentEmail;
    
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quotation_id")
    private QuotationVO quotationVO;

}