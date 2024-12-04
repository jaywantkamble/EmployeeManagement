package com.example.employeeManagement.report;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.employeeManagement.entity.Department;
import com.example.employeeManagement.entity.Employee;
import com.example.employeeManagement.repository.DepartmentRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class JasperReportService {

    @Autowired
    private DepartmentRepository departmentRepository;  
    
    @Value("${report.template.path}")
    private String reportTemplatePath;
    
    public byte[] generateEmployeeReport() throws Exception {
        // Fetch the departments and employees data
        List<Department> departments = departmentRepository.findAll(); 
        List<Employee> employeeList = flattenEmployeeData(departments);
        
     // Set parameters
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "Employee Report by Department");

        // Create datasource
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employeeList);
        dataSource.getData().forEach(record -> {
        	System.out.println(record);
        });
        
        
        JasperReport jasperReport = null;
        // Compile and fill the report
        try (InputStream inputStream = getClass().getResourceAsStream("/reports/department_employee_report.jrxml")) {
            if (inputStream == null) {
                throw new FileNotFoundException("JRXML file not found in the resources directory.");
            }
            jasperReport = JasperCompileManager.compileReport(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

//        // Compile the Jasper report from .jrxml file
//        InputStream inputStream = new ClassPathResource(reportTemplatePath).getInputStream();
//        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
//
//        // Fill the report with data
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(departments);
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("Departments", departments);

        // Generate the PDF report
       // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
    
    private List<Employee> flattenEmployeeData(List<Department> departments) {
        // Convert nested department data to flat structure for JasperReports
        return departments.stream()
                .flatMap(dept -> dept.getEmployees().stream()
                        .map(emp -> new Employee(emp.getId(), emp.getName(), emp.getPosition(), dept.getName(), emp.getSalary())))
                .toList();
    }
}
