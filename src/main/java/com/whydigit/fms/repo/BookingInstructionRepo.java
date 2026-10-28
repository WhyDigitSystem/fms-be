package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.BookingInstructionVO;
import com.whydigit.fms.entity.BookingVO;

@Repository
public interface BookingInstructionRepo extends JpaRepository<BookingInstructionVO, Long>{

	List<BookingInstructionVO> findByBookingVO(BookingVO bookingVO);

}
