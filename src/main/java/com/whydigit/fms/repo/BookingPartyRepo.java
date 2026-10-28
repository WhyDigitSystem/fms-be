package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.BookingPartyVO;
import com.whydigit.fms.entity.BookingVO;

@Repository
public interface BookingPartyRepo extends JpaRepository<BookingPartyVO, Long>{

	List<BookingPartyVO> findByBookingVO(BookingVO bookingVO);

}
