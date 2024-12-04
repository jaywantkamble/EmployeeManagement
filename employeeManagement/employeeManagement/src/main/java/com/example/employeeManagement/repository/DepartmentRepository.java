package com.example.employeeManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.employeeManagement.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, String> {}
