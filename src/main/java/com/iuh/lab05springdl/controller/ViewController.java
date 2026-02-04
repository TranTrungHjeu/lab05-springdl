package com.iuh.lab05springdl.controller;

import com.iuh.lab05springdl.model.Student;
import com.iuh.lab05springdl.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ViewController {

    private final StudentService service;

    public ViewController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(@RequestParam(required = false) String search, Model model) {
        List<Student> students;
        
        // Nếu có search param thì tìm theo tên, không thì lấy tất cả
        if (search != null && !search.trim().isEmpty()) {
            students = service.findByName(search.trim());
            model.addAttribute("searchKeyword", search);
        } else {
            students = service.findAll();
        }
        
        model.addAttribute("students", students);
        model.addAttribute("newStudent", new Student());
        return "index";
    }

    @PostMapping("/add")
    public String addStudent(@ModelAttribute Student student) {
        service.save(student);
        return "redirect:/";
    }

    @GetMapping("/student/{id}")
    public String viewStudent(@PathVariable Integer id, Model model) {
        Student student = service.findById(id);
        if (student != null) {
            model.addAttribute("student", student);
            return "student-detail";
        }
        return "redirect:/";
    }
}

