package be.ucll.java.ent.domain;

import java.io.Serializable;
import java.util.Objects;

// Data Transfer Object for Student information between layers
//   This Java class is sometimes called a POJO - Plain Old Java Object
//   meaning it contains just data and no "programming logic" statements
public class StudentDTO implements Serializable {

    private long id;
    private String naam;

    // Constructors
    public StudentDTO() {
        // Default constructor
    }

    public StudentDTO(String naam) {
        this.naam = naam;
    }

    public StudentDTO(long id, String naam) {
        this.id = id;
        this.naam = naam;
    }

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDTO that = (StudentDTO) o;
        return id == that.id &&
                Objects.equals(naam, that.naam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, naam);
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                '}';
    }
}