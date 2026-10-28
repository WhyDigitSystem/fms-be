package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.BookingCommercialVO;
import com.whydigit.fms.entity.BookingVO;

@Repository
public interface BookingCommercialRepo extends JpaRepository<BookingCommercialVO, Long> {

	List<BookingCommercialVO> findByBookingVO(BookingVO bookingVO);

}
