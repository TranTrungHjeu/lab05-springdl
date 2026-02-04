package com.iuh.lab05springdl.repository;

import com.iuh.lab05springdl.model.Student;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface StudentRepository extends JpaRepository<Student, Integer> {
    
    // Tìm sinh viên theo tên (không phân biệt hoa thường, tìm kiếm một phần)
    List<Student> findByNameContainingIgnoreCase(String name);
}
