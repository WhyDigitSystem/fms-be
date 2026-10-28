package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.QuotationOriginChargesVO;
import com.whydigit.fms.entity.QuotationVO;

@Repository
public interface QuotationOriginChargesRepo extends JpaRepository<QuotationOriginChargesVO, Long>{

	List<QuotationOriginChargesVO> findByQuotationVO(QuotationVO quotationVO);

}
