package com.whydigit.fms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.BookingPartyVO;

@Repository
public interface BookingPartyRepo extends JpaRepository<BookingPartyVO, Long>{

}
