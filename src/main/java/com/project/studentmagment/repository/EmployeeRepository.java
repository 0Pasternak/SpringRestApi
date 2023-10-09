package com.project.studentmagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.studentmagment.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
