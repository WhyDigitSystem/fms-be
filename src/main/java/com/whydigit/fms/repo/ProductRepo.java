package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.ProductVO;

@Repository
public interface ProductRepo extends JpaRepository<ProductVO, Long> {

    List<ProductVO> findByOrgId(Long orgId);

    @Query("SELECT p FROM ProductVO p "
         + "WHERE p.orgId = :orgId "
         + "AND p.branch.id = :branch")
    List<ProductVO> findByOrgIdAndBranch(
            @Param("orgId") Long orgId,
            @Param("branch") Long branch
    );
}