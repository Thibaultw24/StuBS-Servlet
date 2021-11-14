package be.ucll.java.ent.controller;

import be.ucll.java.ent.domain.StudentDTO;
import be.ucll.java.ent.model.StudentDAO;
import be.ucll.java.ent.model.StudentEntity;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Stateless
// Student - Enterprise Java Bean
public class StudentEJB implements StudentEJBLocal {

    @PersistenceContext(unitName = "LocalPostgresPU")
    private EntityManager em;
    private StudentDAO dao;

    @PostConstruct
    public void init() {
        dao = new StudentDAO(em);
    }

    @Override
    public long createStudent(StudentDTO student) throws IllegalArgumentException {
        // Algemene input controle
        if (student == null)
            throw new IllegalArgumentException("Alle data vereist voor het aanmaken van een student ontbreekt");

        // Controle op verplicht veld: naam
        String naam = student.getNaam();
        if (naam == null || naam.trim().length() == 0) {
            throw new IllegalArgumentException("Er is geen naam ingevuld. Student werd NIET aangemaakt");
        }

        // Controle of er al een student bestaat met die naam???
        // ...

        StudentEntity s = new StudentEntity(student.getNaam());
        dao.create(s);

        return s.getId();
    }

    @Override
    public void updateStudent(StudentDTO student) throws IllegalArgumentException {
        if (student == null)
            throw new IllegalArgumentException("Alle data vereist voor het wijzigen van een student ontbreekt");

        long studentId = student.getId();
        String naam = student.getNaam();

        if (studentId > 0L && naam != null && naam.trim().length() > 0) {
            dao.update(new StudentEntity(student.getId(), student.getNaam()));
        } else {
            throw new IllegalArgumentException("Gelieve zowel een ID als een naam in te geven.");
        }
    }

    @Override
    public void deleteStudent(long studentId) throws IllegalArgumentException {
        if (studentId <= 0L) throw new IllegalArgumentException("Ongeldig ID");

        // First check if this student exists
        // If not the thrown IllegalArgumentException exits out of this method
        getStudentById(studentId);

        // Check if all preconditions are met to delete this student
        // ...

        // If all is good effectively delete
        dao.delete(studentId);
    }

    @Override
    public StudentDTO getStudentById(long studentId) throws IllegalArgumentException {
        if (studentId <= 0L) throw new IllegalArgumentException("Ongeldig ID: " + studentId);

        Optional<StudentEntity> value = dao.get(studentId);
        if (value.isPresent()) {
            return new StudentDTO(value.get().getId(), value.get().getNaam());
        } else {
            throw new IllegalArgumentException("Geen student gevonden met ID: " + studentId);
        }
    }

    @Override
    public StudentDTO getStudentByName(String studentName) throws IllegalArgumentException {
        if (studentName == null) throw new IllegalArgumentException("Ongeldige naam meegegeven");
        if (studentName.trim().length() == 0) throw new IllegalArgumentException("Geen naam meegegeven");

        Optional<StudentEntity> value = dao.getOneByName(studentName);
        if (value.isPresent()) {
            return new StudentDTO(value.get().getId(), value.get().getNaam());
        } else {
            throw new IllegalArgumentException("Geen student gevonden met naam: " + studentName);
        }
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        Stream<StudentDTO> lst = dao.getAll().stream()
                .map(rec -> {
                    StudentDTO dto = new StudentDTO();
                    dto.setId(rec.getId());
                    dto.setNaam(rec.getNaam());

                    return dto;
                });
        return lst.collect(Collectors.toList());
    }

    // Methods hereunder only needed for Unit testing.
    public void setEm(EntityManager em) {
        this.em = em;
    }
    public void setDao(StudentDAO dao) {
        this.dao = dao;
    }
}



