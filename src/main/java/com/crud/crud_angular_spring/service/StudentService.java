package com.crud.crud_angular_spring.service;


import com.crud.crud_angular_spring.DTO.CreateStudentDTO;
import com.crud.crud_angular_spring.DTO.UpdateStudentDTO;
import com.crud.crud_angular_spring.entity.Student;
import com.crud.crud_angular_spring.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public UUID createStudent(CreateStudentDTO createStudentDTO) {

        //converter DTO -> entity
        var entity = new Student(
                UUID.randomUUID(),
                createStudentDTO.name(),
                createStudentDTO.email(),
                createStudentDTO.branch(),
                Instant.now(),
                null
        );

        var studentSaved = studentRepository.save(entity);
        return studentSaved.getStudentId();
    }

    public Optional<Student> getStudentById(String studentId) {
        return studentRepository.findById(UUID.fromString(studentId));
    }

    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    public void updateStudentById(String studentId, UpdateStudentDTO updateStudentDTO) {
        var id = UUID.fromString(studentId);

        var studentEntity = studentRepository.findById(id);

        if (studentEntity.isPresent()) {
            var student = studentEntity.get();
            if (updateStudentDTO.name() != null) {
                student.setStudentName(updateStudentDTO.name());
            }
            if (updateStudentDTO.branch() != null) {
                student.setStudentBranch(updateStudentDTO.branch());
            }

            studentRepository.save(student);
        }

    }

    public void deleteStudentById(String studentId) {
        var id = UUID.fromString(studentId);

        var studentExists = studentRepository.existsById(id);

        if (studentExists) {
            studentRepository.deleteById(id);
        }
    }

}
