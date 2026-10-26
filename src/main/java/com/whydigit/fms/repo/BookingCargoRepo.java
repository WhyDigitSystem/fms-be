
package com.whydigit.fms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.BookingCargoVO;

@Repository
public interface BookingCargoRepo extends JpaRepository<BookingCargoVO, Long>{

}
