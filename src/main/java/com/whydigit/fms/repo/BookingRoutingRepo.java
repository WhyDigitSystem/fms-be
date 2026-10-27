package com.whydigit.fms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.BookingRoutingVO;

@Repository
public interface BookingRoutingRepo  extends JpaRepository<BookingRoutingVO, Long>{

}
