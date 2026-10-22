package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.ServiceVO;

@Repository
public interface ServiceRepo extends JpaRepository<ServiceVO, Long> {

    List<ServiceVO> findByOrgId(Long orgId);

    @Query("SELECT s FROM ServiceVO s "
         + "WHERE s.orgId = :orgId "
         + "AND s.branch.id = :branch")
    List<ServiceVO> findByOrgIdAndBranch(
            @Param("orgId") Long orgId,
            @Param("branch") Long branch
    );
}