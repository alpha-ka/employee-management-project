package com.alpha.employeelogin.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alpha.employeelogin.model.Employee;

 

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
	
	
	 
	
	@Query("From Employee WHERE email=?1  and status='Active'")
	public Employee findByEmail(String email);
	
//	@Query("From Employee WHERE email=?1  and status='Inactive'")
//	public Employee findByEmailStatus(String email);
	
	@Query("From Employee WHERE email=?1 and password=?2 and status='Active'")
	public Employee findByEmailPassword(String email,String password);
	
	@Query("From Employee WHERE email=?1 and password=?2 and admin=?3  and status='Active'")
	public Employee findByAdminEmailPassword(String email,String password,String admin);
	
//	@Query("From Employee Where emp_id!=?1")
//	public List<Employee> findAllforAdmin(Long empid);
	
	@Query(value="SELECT * FROM Employees Where emp_id!=?1", nativeQuery = true)
	public List<Employee> findAllforAdmin(long empid);
	
	@Query("From Employee WHERE status='Active'")
	public List<Employee> findAllActiveEmp();
	
	@Query("From Employee WHERE status='Inactive'")
	public List<Employee> findAllInActiveEmp();

	@Query("From Employee WHERE admin='Yes'")
	public List<Employee> findAllAdmin();
	
//	@Query("From Employee WHERE admin='Yes'")
//	public List<Employee> findNameByID(long empid);
	
	
	
	
	
}
