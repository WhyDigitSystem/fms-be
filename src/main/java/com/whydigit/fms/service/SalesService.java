package com.whydigit.fms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.whydigit.fms.ResponseDTO.BookingResponseDTO;
import com.whydigit.fms.ResponseDTO.EnquiryResponseDTO;
import com.whydigit.fms.ResponseDTO.QuotationResponseDTO;
import com.whydigit.fms.dto.BookingDTO;
import com.whydigit.fms.dto.EnquiryDTO;
import com.whydigit.fms.dto.QuotationDTO;
import com.whydigit.fms.exception.ApplicationException;

@Service
public interface SalesService {

	Map<String, Object> createUpdateEnquiry(EnquiryDTO dto) throws ApplicationException;

	EnquiryResponseDTO getEnquiryById(Long id) throws ApplicationException;

	String getEnquiryDocId(Long orgId) throws ApplicationException;

	List<EnquiryResponseDTO> getEnquiryByOrgId(Long orgId, Long branch) throws ApplicationException;

	Map<String, Object> createUpdateQuotation(QuotationDTO quotationDTO) throws ApplicationException;

	QuotationResponseDTO getQuotationById(Long id) throws ApplicationException;

	List<QuotationResponseDTO> getQuotationByOrgIdAndBranch(Long orgId, Long branch) throws ApplicationException;

	Map<String, Object> createUpdateBooking(BookingDTO bookingDTO) throws ApplicationException;

	BookingResponseDTO getBookingById(Long id) throws ApplicationException;

	List<BookingResponseDTO> getBookingByOrgId(Long orgId, Long branch) throws ApplicationException;

	String getBookingDocId(Long orgId) throws ApplicationException;


}
