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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "config_service")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigServiceVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "configservicegen")
	@SequenceGenerator(name = "configservicegen", sequenceName = "configserviceseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "config_service_id")
	private Long id;

	@Column(name = "mode", length = 30)
	private String mode;

	@Column(name = "active")
	private boolean active;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Many configuration-service records belong to one configuration
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "config_id", nullable = false)
	@JsonBackReference
	private ConfigVO config;

	// Many configuration-service records can reference one service
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service", nullable = false)
	private ServiceVO service;

}