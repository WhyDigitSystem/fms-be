package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.ListOfValuesDetailsVO;
import com.whydigit.fms.entity.ListOfValuesVO;

@Repository
public interface ListOfValuesDetailsRepo extends JpaRepository<ListOfValuesDetailsVO, Long>{

	List<ListOfValuesDetailsVO> findBylistOfValuesVO(ListOfValuesVO listOfValuesVO);

	void deleteByListOfValuesVO_Id(Long id);

	List<ListOfValuesDetailsVO> findByListOfValuesVO(ListOfValuesVO listOfValuesVO);
}

