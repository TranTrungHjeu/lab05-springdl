package com.iuh.lab05springdl.service;

import com.iuh.lab05springdl.model.Student;
import com.iuh.lab05springdl.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit Test cho StudentServiceImpl sử dụng Mock StudentRepository
 * Minh họa cách sử dụng Constructor Injection giúp dễ dàng mock dependencies
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        student1 = new Student("Nguyễn Văn A", "a@example.com", 20);
        student1.setId(1);
        
        student2 = new Student("Trần Thị B", "b@example.com", 21);
        student2.setId(2);
    }

    @Test
    @DisplayName("Test save() - Lưu sinh viên mới")
    void testSave() {
        // Arrange
        Student newStudent = new Student("Lê Văn C", "c@example.com", 22);
        Student savedStudent = new Student("Lê Văn C", "c@example.com", 22);
        savedStudent.setId(3);
        
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        // Act
        Student result = studentService.save(newStudent);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.getId());
        assertEquals("Lê Văn C", result.getName());
        verify(studentRepository, times(1)).save(newStudent);
    }

    @Test
    @DisplayName("Test findAll() - Lấy danh sách tất cả sinh viên")
    void testFindAll() {
        // Arrange
        List<Student> students = Arrays.asList(student1, student2);
        when(studentRepository.findAll()).thenReturn(students);

        // Act
        List<Student> result = studentService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Nguyễn Văn A", result.get(0).getName());
        assertEquals("Trần Thị B", result.get(1).getName());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test findById() - Tìm sinh viên theo ID thành công")
    void testFindById_Found() {
        // Arrange
        when(studentRepository.findById(1)).thenReturn(Optional.of(student1));

        // Act
        Student result = studentService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Nguyễn Văn A", result.getName());
        verify(studentRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Test findById() - Không tìm thấy sinh viên")
    void testFindById_NotFound() {
        // Arrange
        when(studentRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Student result = studentService.findById(999);

        // Assert
        assertNull(result);
        verify(studentRepository, times(1)).findById(999);
    }

    @Test
    @DisplayName("Test findByName() - Tìm sinh viên theo tên")
    void testFindByName() {
        // Arrange
        List<Student> students = Arrays.asList(student1);
        when(studentRepository.findByNameContainingIgnoreCase("Nguyễn")).thenReturn(students);

        // Act
        List<Student> result = studentService.findByName("Nguyễn");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getName().contains("Nguyễn"));
        verify(studentRepository, times(1)).findByNameContainingIgnoreCase("Nguyễn");
    }

    @Test
    @DisplayName("Test findByName() - Không tìm thấy kết quả")
    void testFindByName_NoResults() {
        // Arrange
        when(studentRepository.findByNameContainingIgnoreCase("XYZ")).thenReturn(Arrays.asList());

        // Act
        List<Student> result = studentService.findByName("XYZ");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(studentRepository, times(1)).findByNameContainingIgnoreCase("XYZ");
    }
}
