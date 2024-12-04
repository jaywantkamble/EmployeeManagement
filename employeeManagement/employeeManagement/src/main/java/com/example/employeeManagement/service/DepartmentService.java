package com.example.employeeManagement.service;

import com.example.employeeManagement.entity.Department;
import com.example.employeeManagement.entity.Employee;
import com.example.employeeManagement.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {
	@Autowired
	private DepartmentRepository departmentRepository;

	public List<Employee> getAllEmployees() {
		return departmentRepository.findAll().stream().flatMap(dept -> dept.getEmployees().stream()).toList();
	}

	public List<Department> getAllDepartments() {
		return departmentRepository.findAll();
	}

	public List<Employee> getEmployeesByDepartment(String departmentId) {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new RuntimeException("Department not found"));
		return department.getEmployees();
	}
	
    public String createDepartment(Department department) {
        if (departmentRepository.existsById(department.getId())) {
            return "Department with this ID already exists!";
        }
        departmentRepository.save(department);
        return "Department created successfully!";
    }

	@Transactional
	public void addEmployee(String departmentId, Employee employee) {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new RuntimeException("Department not found"));
		department.getEmployees().add(employee);
		departmentRepository.save(department);
	}
	
    public String deleteDepartment(String departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            return "Department not found!";
        }

        departmentRepository.deleteById(departmentId);
        return "Department deleted successfully!";
    }

	@Transactional
	public void deleteEmployee(String departmentId, String employeeId) {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new RuntimeException("Department not found"));
		department.getEmployees().removeIf(emp -> emp.getId().equals(employeeId));
		departmentRepository.save(department);
	}
}
