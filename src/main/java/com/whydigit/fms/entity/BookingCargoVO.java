package com.whydigit.fms.entity;

import java.math.BigDecimal;

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
@Table(name = "booking_cargo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingCargoVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_cargogen")
	@SequenceGenerator(name = "booking_cargogen", sequenceName = "booking_cargoseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "booking_cargo_id")
	private Long id;

	@Column(name = "commodity_description", length = 500, nullable = false)
	private String commodityDescription;

	@Column(name = "package_type", length = 100)
	private String packageType;

	@Column(name = "package_count")
	private int packageCount;

	@Column(name = "gross_weight", precision = 15, scale = 3)
	private BigDecimal grossWeight;

	@Column(name = "net_weight", precision = 15, scale = 3)
	private BigDecimal netWeight;

	@Column(name = "weight_unit", length = 20)
	private String weightUnit;

	@Column(name = "volume", precision = 15, scale = 3)
	private BigDecimal volume;

	@Column(name = "volume_unit", length = 20)
	private String volumeUnit;

	@Column(name = "length", precision = 15, scale = 3)
	private BigDecimal length;

	@Column(name = "width", precision = 15, scale = 3)
	private BigDecimal width;

	@Column(name = "height", precision = 15, scale = 3)
	private BigDecimal height;

	@Column(name = "dimension_unit", length = 20)
	private String dimensionUnit;

	@Column(name = "marks_and_numbers", length = 500)
	private String marksAndNumbers;

	@Column(name = "hazardous")
	private Boolean hazardous = false;

	@Column(name = "hazardous_class", length = 50)
	private String hazardousClass;

	@Column(name = "un_number", length = 20)
	private String unNumber;

	@Column(name = "remarks", length = 1000)
	private String remarks;

	@ManyToOne
	@JoinColumn(name = "booking_id", nullable = false)
	@JsonBackReference
	private BookingVO bookingVO;
}