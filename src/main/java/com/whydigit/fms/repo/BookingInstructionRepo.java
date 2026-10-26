package com.whydigit.fms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.BookingInstructionVO;

@Repository
public interface BookingInstructionRepo extends JpaRepository<BookingInstructionVO, Long>{

}
