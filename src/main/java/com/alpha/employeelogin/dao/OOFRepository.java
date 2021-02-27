package com.alpha.employeelogin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alpha.employeelogin.model.OutofOffice;



public interface OOFRepository extends JpaRepository<com.alpha.employeelogin.model.OutofOffice, Long> {
	
	
	@Query("From OutofOffice where empid=?1")
	List<OutofOffice> findByEmpID(Long empid);
	
	@Query("From OutofOffice where empid<>?1")
	List<OutofOffice> findAllforAdmin(Long empid);
	

}
