package com.example.springboot_masterclass.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot_masterclass.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}