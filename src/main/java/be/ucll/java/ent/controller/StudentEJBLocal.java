package be.ucll.java.ent.controller;

import be.ucll.java.ent.domain.StudentDTO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface StudentEJBLocal {
    // Create methods
    long createStudent(StudentDTO student) throws IllegalArgumentException;

    // Update methods
    void updateStudent(StudentDTO student);

    // Delete methods
    void deleteStudent(long studentId);

    // Read methods
    StudentDTO getStudentById(long studentId);
    StudentDTO getStudentByName(String studentName);

    // Search methods
    List<StudentDTO> getAllStudents();
}
