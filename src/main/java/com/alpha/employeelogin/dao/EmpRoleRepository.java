package com.alpha.employeelogin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.employeelogin.model.EmployeeRole;

public interface EmpRoleRepository extends JpaRepository<EmployeeRole, Long> {

}
