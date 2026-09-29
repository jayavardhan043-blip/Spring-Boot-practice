package com.example.springboot_masterclass.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot_masterclass.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
