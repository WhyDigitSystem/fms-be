package com.whydigit.fms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.BookingCommercialVO;

@Repository
public interface BookingCommercialRepo extends JpaRepository<BookingCommercialVO, Long> {

}
