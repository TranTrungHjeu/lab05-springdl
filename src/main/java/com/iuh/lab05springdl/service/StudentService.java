package com.iuh.lab05springdl.service;

import com.iuh.lab05springdl.model.Student;

import java.util.List;

public interface StudentService {

    Student save(Student student);
    List<Student> findAll();
    Student findById(Integer id);
    List<Student> findByName(String name);
}
