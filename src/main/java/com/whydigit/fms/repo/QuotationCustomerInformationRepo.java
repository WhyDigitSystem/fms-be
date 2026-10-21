package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.QuotationCustomerInformationVO;
import com.whydigit.fms.entity.QuotationVO;

@Repository
public interface QuotationCustomerInformationRepo  extends JpaRepository<QuotationCustomerInformationVO, Long>{

	List<QuotationCustomerInformationVO> findByQuotationVO(QuotationVO quotationVO);

}

