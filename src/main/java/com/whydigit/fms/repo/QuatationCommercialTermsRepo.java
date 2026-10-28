package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.QuatationCommercialTermsVO;
import com.whydigit.fms.entity.QuotationVO;

@Repository
public interface QuatationCommercialTermsRepo extends JpaRepository<QuatationCommercialTermsVO, Long>{

	List<QuatationCommercialTermsVO> findByQuotationVO(QuotationVO quotationVO);

}
