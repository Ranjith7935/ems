package com.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ems.entity.Compose;

public interface ComposeRepo extends JpaRepository<Compose, Integer> {
	 List<Compose> findByParentUKid(Integer parentUkid);
	
	 List<Compose> findTop5ByParentUKidOrderByCreatedDateDesc(Integer parentUkid);
	
	 List<Compose> findTop5ByOrderByCreatedDateDesc();
	 
	 long countByStatus(String status);
	 long count();
}
