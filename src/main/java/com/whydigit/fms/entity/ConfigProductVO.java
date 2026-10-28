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
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "config_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigProductVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "configproductgen")
	@SequenceGenerator(name = "configproductgen", sequenceName = "configproductseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "config_product_id")
	private Long id;

	@Column(name = "mode", length = 30)
	private String mode;

	@Column(name = "active")
	private boolean active;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Many configuration-product records belong to one configuration
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "config_id", nullable = false)
	@JsonBackReference

	private ConfigVO config;

	// Many configuration-product records can reference one product
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product", nullable = false)
	private ProductVO product;
}