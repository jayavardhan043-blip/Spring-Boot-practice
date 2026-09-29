package com.example.springboot_masterclass;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springboot_masterclass.Repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // GET ALL
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // GET BY ID
    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id).orElse(null);
    }

    // CREATE
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // DELETE
    public boolean deleteEmployee(int id) {

        if (!employeeRepository.existsById(id)) {
            return false;
        }

        employeeRepository.deleteById(id);
        return true;
    }

    // UPDATE
    public Employee updateEmployee(int id, Employee updatedEmployee) {

        Employee existingEmployee = getEmployeeById(id);

        if (existingEmployee == null) {
            return null;
        }

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setAge(updatedEmployee.getAge());
        existingEmployee.setJobTitle(updatedEmployee.getJobTitle());

        return employeeRepository.save(existingEmployee);
    }
}