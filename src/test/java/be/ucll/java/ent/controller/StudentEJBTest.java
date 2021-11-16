package be.ucll.java.ent.controller;

import be.ucll.java.ent.domain.StudentDTO;
import be.ucll.java.ent.model.StudentDAO;
import org.junit.*;
import org.junit.rules.ExpectedException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Ignore
public class StudentEJBTest {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private StudentEJB ejb;
    private StudentDTO testStudent;
    private long testStudentID;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before //Before every single test
    public void setup() {
        entityManagerFactory = Persistence.createEntityManagerFactory("LocalPostgresPU2");
        entityManager = entityManagerFactory.createEntityManager();
        ejb = new StudentEJB();
        ejb.setEm(entityManager);
        ejb.setDao(new StudentDAO(entityManager));

        entityManager.getTransaction().begin();
        testStudent = new StudentDTO( "TestNaam");
        testStudentID = ejb.createStudent(testStudent);
        testStudent = ejb.getStudentById(testStudentID);

        // System.out.println(testStudent);

        entityManager.getTransaction().commit();
    }

    // 1. Alle READ / Opzoeken testen

    @Test // Test een student ID 0
    public void getStudentById0(){
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Ongeldig ID");

        ejb.getStudentById(0);
    }

    @Test // Test een student ID met een negatief getal
    public void getStudentByIdNegGetal(){
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Ongeldig ID");

        ejb.getStudentById(-1);
    }

    @Test
    public void getStudentByIdOK(){
        StudentDTO s = ejb.getStudentById(testStudentID);
        assertEquals(s, testStudent);
    }

    @Test // Test dat student Naam niet null is
    public void getStudentByNameNull(){
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Ongeldige naam meegegeven");

        ejb.getStudentByName(null);
    }

    @Test // Test dat student Naam niet leeg is
    public void getStudentByNameLeeg(){
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Geen naam meegegeven");

        ejb.getStudentByName("");
    }

    @Test // Test dat student Naam gelijk aan 'TestNaam' 1 student terug geeft => OK test
    public void getStudentByNameOK(){
        StudentDTO student = ejb.getStudentByName("TestNaam");
        assertNotNull(student);
    }

    @Test // Test dat student Naam gelijk aan 'testnaam' 1 student terug geeft => OK test
    public void getStudentByNameOKCasInsensitive(){
        StudentDTO student = ejb.getStudentByName("testnaam");
        assertNotNull(student);
    }

    // 2. Alle UPDATE / wijzigen testen

    @Test
    public void updateStudentNull() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("data vereist voor het wijzigen");

        StudentDTO student = null;
        ejb.updateStudent(student);
    }

    @Test
    public void updateStudent0() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Gelieve data in te geven.");

        StudentDTO student = new StudentDTO();
        student.setId(0);
        ejb.updateStudent(student);
    }

    // TODO: updateStudent. Test dat student Naam niet null is
    // TODO: updateStudent. Test dat student Naam niet leeg is
    // Voor de Voornaam moet je dit ook testen maar je mag dit voor deze oefening overslaan

    // TODO: updateStudent. Test dat student Geboortedatum niet in de toekomst ligt
    // TODO: updateStudent. wijzig student naam, voornaam en geboortedatum. Haal nu de Student terug op en controleer. => OK test

    // 3. Alle Create / Aanmaken testen

    // TODO: createStudent. Test dat student Naam niet null is
    // TODO: createStudent. Test dat student Naam niet leeg is
    // Voor de Voornaam moet je dit ook testen maar je mag dit voor deze oefening overslaan
    // TODO: createStudent. Test dat student Geboortedatum niet in de toekomst ligt
    // TODO: createStudent: In de @Before werd een student aangemaakt. Test met een getStudent of alles OK werd aangemaakt.


    // 4. Alle Delete / Verwijderen testen

    // TODO: deleteStudent. Test dat student ID niet 0 is
    // TODO: deleteStudent. Maak nog een extra testStudent aan. capteer de ID. Doe die Student weg. Zoek op die ID of die nog bestaat.

    @After // After every single test
    public void tearDown() {
        entityManager.getTransaction().begin();
        // System.out.println("Testdata deleted");
        ejb.deleteStudent(testStudentID);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

}