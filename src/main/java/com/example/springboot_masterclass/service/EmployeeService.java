package com.example.springboot_masterclass.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.springboot_masterclass.dto.EmployeeRequestDTO;
import com.example.springboot_masterclass.dto.EmployeeResponseDTO;
import com.example.springboot_masterclass.entity.Employee;
import com.example.springboot_masterclass.exception.EmployeeNotFoundException;
import com.example.springboot_masterclass.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // GET ALL EMPLOYEES
    public List<EmployeeResponseDTO> getAllEmployees() {

        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // GET EMPLOYEE BY ID
    public EmployeeResponseDTO getEmployeeById(int id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee with ID " + id + " not found"));

        return convertToResponseDTO(employee);
    }

    // CREATE EMPLOYEE
    public EmployeeResponseDTO createEmployee(
            EmployeeRequestDTO requestDTO) {

        Employee employee = new Employee();

        employee.setName(requestDTO.getName());
        employee.setAge(requestDTO.getAge());
        employee.setJobTitle(requestDTO.getJobTitle());

        Employee savedEmployee = employeeRepository.save(employee);

        return convertToResponseDTO(savedEmployee);
    }

    // DELETE EMPLOYEE
    public boolean deleteEmployee(int id) {

        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(
                    "Employee with ID " + id + " not found");
        }

        employeeRepository.deleteById(id);

        return true;
    }

    // UPDATE EMPLOYEE
    public EmployeeResponseDTO updateEmployee(
            int id,
            EmployeeRequestDTO requestDTO) {

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee with ID " + id + " not found"));

        existingEmployee.setName(requestDTO.getName());
        existingEmployee.setAge(requestDTO.getAge());
        existingEmployee.setJobTitle(requestDTO.getJobTitle());

        Employee updatedEmployee =
                employeeRepository.save(existingEmployee);

        return convertToResponseDTO(updatedEmployee);
    }

    // ENTITY → RESPONSE DTO
    private EmployeeResponseDTO convertToResponseDTO(Employee employee) {

        return new EmployeeResponseDTO(
                employee.getId(),
                employee.getName(),
                employee.getAge(),
                employee.getJobTitle()
        );
    }
}