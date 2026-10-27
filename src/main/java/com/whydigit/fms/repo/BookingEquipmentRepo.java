package com.whydigit.fms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.BookingEquipmentVO;

@Repository
public interface BookingEquipmentRepo extends JpaRepository<BookingEquipmentVO, Long>{

}
