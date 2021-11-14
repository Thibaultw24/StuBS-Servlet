package be.ucll.java.ent.model;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Student")
@NamedQueries({
    @NamedQuery(name = "Student.getAll", query = "SELECT e FROM StudentEntity e"),
})
public class StudentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sseq")
    @SequenceGenerator(name = "sseq", sequenceName = "student_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(length = 128)
    private String naam;

    /* ***** Constructors ***** */
    public StudentEntity() {
        // Default constructor
    }

    public StudentEntity(String naam) {
        this.naam = naam;
    }

    public StudentEntity(long id, String naam) {
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
        StudentEntity student = (StudentEntity) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, naam);
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                '}';
    }
}