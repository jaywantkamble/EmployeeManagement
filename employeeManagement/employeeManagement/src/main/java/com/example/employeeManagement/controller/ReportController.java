package com.example.employeeManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employeeManagement.report.JasperReportService;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

	@Autowired
	private JasperReportService jasperReportService;

	@GetMapping("/employee-department")
	public void generateEmployeeDepartmentReport(HttpServletResponse response) throws Exception {
		byte[] pdfReport = jasperReportService.generateEmployeeReport();

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=department_employee_report.pdf");

		try (ServletOutputStream outputStream = response.getOutputStream()) {
			outputStream.write(pdfReport);
			outputStream.flush();
		}
	}
}
