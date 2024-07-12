package com.crud.crud_angular_spring.controller;

import com.crud.crud_angular_spring.DTO.CreateStudentDTO;
import com.crud.crud_angular_spring.DTO.UpdateStudentDTO;
import com.crud.crud_angular_spring.entity.Student;
import com.crud.crud_angular_spring.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/v1/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody CreateStudentDTO createStudentDTO) {
        var studentId = studentService.createStudent(createStudentDTO);

        return ResponseEntity.created(URI.create("/v1/students" + studentId.toString())).build();
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable("studentId") String studentId) {
        var student = studentService.getStudentById(studentId);

        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Student>> listStudents() {
        var students = studentService.listStudents();

        return ResponseEntity.ok(students);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Void> updateStudentById(@PathVariable("studentId") String studentId, @RequestBody UpdateStudentDTO updateStudentDTO) {
        studentService.updateStudentById(studentId, updateStudentDTO);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable("studentId") String studentId) {
        studentService.deleteStudentById(studentId);

        return ResponseEntity.noContent().build();
    }

}
