package be.ucll.java.ent.model;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class StudentDAO {

    // JPA EntityManager
    private final EntityManager em;

    // Constructor with EntityManager
    public StudentDAO(EntityManager em) {
        this.em = em;
    }

    public void create(StudentEntity student) {
        em.persist(student);
    }

    public void update(StudentEntity student) {
        em.merge(student);
    }

    public void delete(long studentId) {
        StudentEntity ref = em.getReference(StudentEntity.class, studentId);
        if (ref != null){
            em.remove(ref);
        } else {
            // T'is al weg hé
        }
    }

    // Gebruik Optional om aanroepende code af te dwingen en rekening te houden met NULL
    public Optional<StudentEntity> get(long studentId) {
        return Optional.ofNullable(em.find(StudentEntity.class, studentId));
    }

    // Zonder Optional kan de return value null zijn en kan je alleen maar hopen
    // dat de aanroepende code daarmee rekening houdt
    public StudentEntity read(long studentId) {
        return em.find(StudentEntity.class, studentId);
    }

    public Optional<StudentEntity> getOneByName(String name) {
        try {
            StudentEntity stud = null;
            try {
                Query q = em.createQuery("select e from StudentEntity e where lower(e.naam) = :p1");
                q.setParameter("p1", name.toLowerCase());
                stud = (StudentEntity) q.getSingleResult();
            } catch (NoResultException e) {
                // ignore
            }
            return Optional.ofNullable(stud);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<StudentEntity> getAll() {
        return em.createNamedQuery("Student.getAll").getResultList();
    }

}



