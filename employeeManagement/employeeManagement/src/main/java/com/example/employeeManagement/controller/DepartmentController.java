package com.example.employeeManagement.controller;

import com.example.employeeManagement.dto.EmployeeDTO;
import com.example.employeeManagement.entity.Department;
import com.example.employeeManagement.entity.Employee;
import com.example.employeeManagement.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;

	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return departmentService.getAllEmployees();
	}

	@GetMapping("/departments")
	public List<Department> getAllDepartments() {
		return departmentService.getAllDepartments();
	}

	@GetMapping("/departments/{deptId}/employees")
	public List<Employee> getEmployeesByDepartment(@PathVariable String deptId) {
		return departmentService.getEmployeesByDepartment(deptId);
	}
	
    @PostMapping("/departments")
    public String createDepartment(@RequestBody Department department) {
        return departmentService.createDepartment(department);
    }

	@PostMapping("/departments/{deptId}/employees")
	public ResponseEntity<String> addEmployee(@PathVariable String deptId, @RequestBody EmployeeDTO employeeDTO) {
		Employee employee = new Employee();
		employee.setId(employeeDTO.getId());
		employee.setName(employeeDTO.getName());
		employee.setEmail(employeeDTO.getEmail());
		employee.setPosition(employeeDTO.getPosition());
		employee.setSalary(employeeDTO.getSalary());

		departmentService.addEmployee(deptId, employee);
		return ResponseEntity.ok("Employee added successfully");
	}

	@DeleteMapping("/departments/{deptId}/employees/{empId}")
	public ResponseEntity<String> deleteEmployee(@PathVariable String deptId, @PathVariable String empId) {
		departmentService.deleteEmployee(deptId, empId);
		return ResponseEntity.ok("Employee deleted successfully");
	}
}
