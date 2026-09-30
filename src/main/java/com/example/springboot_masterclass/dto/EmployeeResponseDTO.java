package com.example.springboot_masterclass.dto;

public class EmployeeResponseDTO {

    private Integer id;
    private String name;
    private int age;
    private String jobTitle;

    public EmployeeResponseDTO() {
    }

    public EmployeeResponseDTO(Integer id, String name, int age, String jobTitle) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.jobTitle = jobTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}