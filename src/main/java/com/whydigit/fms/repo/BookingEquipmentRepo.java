package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.BookingEquipmentVO;
import com.whydigit.fms.entity.BookingVO;

@Repository
public interface BookingEquipmentRepo extends JpaRepository<BookingEquipmentVO, Long>{

	List<BookingEquipmentVO> findByBookingVO(BookingVO bookingVO);

}
