package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.QuotationAirFreightRatesVO;
import com.whydigit.fms.entity.QuotationVO;

@Repository
public interface QuotationAirFreightRatesRepo extends JpaRepository<QuotationAirFreightRatesVO, Long>{

	List<QuotationAirFreightRatesVO> findByQuotationVO(QuotationVO quotationVO);

}
