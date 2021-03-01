package com.alpha.employeelogin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alpha.employeelogin.dto.EmployeeOOFDTO;
import com.alpha.employeelogin.model.OutofOffice;





public interface OOFRepository extends JpaRepository<OutofOffice, Long> {
	
	
	@Query("From OutofOffice where empid=?1")
	public List<OutofOffice> findByEmpID(Long empid);
	
	 //@Query("From OutofOffice where empid<>?1")
 	//public List<OutofOffice> findAllforAdmin(Long empid);
	
//	
// 	 @Query("SELECT  e,oof   FROM  Employee e LEFT JOIN OutofOffice oof  on e.emp_id=oof.emp_id   where e.emp_id = ?1")
//  public List<Object[]> findAllforAdmin(@Param("empid") Long empid);
//	
	

	
	

}
