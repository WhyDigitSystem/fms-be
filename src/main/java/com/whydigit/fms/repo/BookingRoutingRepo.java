package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.BookingRoutingVO;
import com.whydigit.fms.entity.BookingVO;

@Repository
public interface BookingRoutingRepo  extends JpaRepository<BookingRoutingVO, Long>{

	List<BookingRoutingVO> findByBookingVO(BookingVO bookingVO);

}
