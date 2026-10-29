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
import com.fasterxml.jackson.annotation.JsonGetter;
import com.whydigit.fms.dto.CreatedUpdatedDate;

import javax.persistence.Embedded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddressVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customeraddressgen")
	@SequenceGenerator(name = "customeraddressgen", sequenceName = "customeraddressseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "customer_address_id")
	private Long id;

	@Column(name = "address", nullable = false)
	private String address;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country")
	private CountryVO country;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state")
	private StateVO state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city")
	private CityVO city;

	@Column(name = "postal_code", length = 20)
	private String postalCode;

	@Column(name = "contact_name", length = 150)
	private String contactName;

	@Column(name = "mobile", length = 20)
	private String mobile;

	@Column(name = "alternate_mobile", length = 20)
	private String alternateMobile;

	@Column(name = "email", length = 150)
	private String email;

	@Column(name = "landmark", length = 200)
	private String landmark;

	// Parent: Customer
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	@JsonBackReference
	private CustomerVO customer;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
