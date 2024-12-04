package com.example.employeeManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.employeeManagement.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
