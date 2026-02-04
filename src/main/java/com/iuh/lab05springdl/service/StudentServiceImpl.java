package com.iuh.lab05springdl.service;

import com.iuh.lab05springdl.model.Student;
import com.iuh.lab05springdl.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    // Constructor Injection với @Qualifier để chỉ định bean cụ thể
    public StudentServiceImpl(@Qualifier("studentRepository") StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student save(Student student) {
        return repository.save(student);
    }

    @Override
    public List<Student> findAll() {
        return repository.findAll();
    }

    @Override
    public Student findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Student> findByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }
}
