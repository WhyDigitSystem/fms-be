package com.whydigit.fms.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.CustomerVO;
import com.whydigit.fms.entity.ProductVO;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerVO, Long> {

    List<CustomerVO> findByOrgId(Long orgId);

    List<CustomerVO> findByOrgIdAndCancelFalse(Long orgId);

    List<CustomerVO> findByOrgIdAndBranch_Id(Long orgId, Long branchId);
    
    boolean existsByCustomerCode(String customerCode);

    boolean existsByCustomerCodeAndIdNot(String customerCode, Long id);
 
}