package be.ucll.java.ent.view;

import be.ucll.java.ent.domain.StudentDTO;
import be.ucll.java.ent.controller.StudentEJBLocal;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "StudentServlet", urlPatterns = {"/"})
public class StudentServlet extends HttpServlet {

    @EJB
    private StudentEJBLocal studentEJB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Log the number of records that the database is currently holding
        // long cnt = studentBean.countStudents();
        // this.getServletContext().log("Nr of students in the database: " + cnt);

        // Proceed to the JSP page with no further input (Attributes)
        request.getRequestDispatcher("studentinfo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Op welke knop werd er gedrukt?
        String action = request.getParameter("action");

        // Data van de input boxen ophalen en ID omzetten naar een integer.
        String studentIdStr = request.getParameter("studentId");
        long studentId = 0L;
        if (studentIdStr != null && !studentIdStr.trim().equals("")) {
            try {
                studentId = Long.parseLong(studentIdStr);
            } catch (NumberFormatException e) {
                studentId = 0L;
            }
        }
        String naam = request.getParameter("naam");

        // Boodschap initialiseren/leegmaken
        String infoMsg = "";
        String errMsg = "";

        // Data Transfer Object
        StudentDTO dto;
        try {
            if ("Toevoegen".equalsIgnoreCase(action)) {
                dto = new StudentDTO(0, naam);
                long id = studentEJB.createStudent(dto);
                infoMsg = "Student aangemaakt met id " + id;
            } else if ("Wijzigen".equalsIgnoreCase(action)) {
                dto = new StudentDTO(studentId, naam);
                studentEJB.updateStudent(dto);
            } else if ("Verwijderen".equalsIgnoreCase(action)) {
                studentEJB.deleteStudent(studentId);
            } else if ("Zoeken".equalsIgnoreCase(action)) {
                if (studentId > 0) {
                    dto = studentEJB.getStudentById(studentId);
                    ArrayList<StudentDTO> al = new ArrayList<>();
                    al.add(dto);
                    request.setAttribute("allStudents", al);
                } else if (naam != null && naam.trim().length() > 0) {
                    dto = studentEJB.getStudentByName(naam.trim().toLowerCase());
                    ArrayList<StudentDTO> al = new ArrayList<>();
                    al.add(dto);
                    request.setAttribute("allStudents", al);
                } else {
                    request.setAttribute("allStudents", studentEJB.getAllStudents());
                }
            }
        } catch (EJBException e) {
            errMsg = e.getCausedByException().getMessage();
            request.setAttribute("errMsg", errMsg);
        }

        request.setAttribute("infoMsg", infoMsg);
        request.getRequestDispatcher("studentinfo.jsp").forward(request, response);
    }

}
