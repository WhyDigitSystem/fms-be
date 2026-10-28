package com.whydigit.fms.entity;

import java.time.LocalDateTime;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "booking_instruction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingInstructionVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_instructiongen")
	@SequenceGenerator(name = "booking_instructiongen", sequenceName = "booking_instructionseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "booking_instruction_id")
	private Long id;

	// Document information
	@Column(name = "document_type", length = 100)
	private String documentType;

	// Public URL of the uploaded file (same as filePath in your GRN file entity)
	@Column(name = "document")
	private String document;

	// File details (new, same as GrnFileUploadDetailsVO)
	@Column(name = "file_name", length = 255)
	private String fileName;          // saved name on disk

	@Column(name = "original_name", length = 255)
	private String originalName;      // name the user uploaded

	@Column(name = "file_size")
	private Long fileSize;

	@Column(name = "content_type", length = 100)
	private String contentType;

	@Column(name = "upload_on")
	private LocalDateTime uploadOn;

	// Instructions
	@Column(name = "pickup_instructions", length = 2000)
	private String pickupInstructions;

	@Column(name = "delivery_instructions", length = 2000)
	private String deliveryInstructions;

	@Column(name = "internal_remarks", length = 2000)
	private String internalRemarks;

	// Booking reference
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "booking_id")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private BookingVO bookingVO;

}