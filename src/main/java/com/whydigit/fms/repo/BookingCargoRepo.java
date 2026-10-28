
package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.BookingCargoVO;
import com.whydigit.fms.entity.BookingVO;

@Repository
public interface BookingCargoRepo extends JpaRepository<BookingCargoVO, Long>{

	List<BookingCargoVO> findByBookingVO(BookingVO bookingVO);

}
