package com.attendance.servlet;

import com.attendance.dao.AttendanceDAO;
import com.attendance.model.Student;
import com.attendance.model.Attendance;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/app")
public class AttendanceServlet extends HttpServlet {

    private final AttendanceDAO dao = new AttendanceDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                request.getRequestDispatcher(
                        "/WEB-INF/views/add-student.jsp")
                        .forward(request, response);

            } else if ("mark".equals(action)) {
                request.setAttribute("students", dao.getStudents());
                request.getRequestDispatcher(
                        "/WEB-INF/views/mark-attendance.jsp")
                        .forward(request, response);

            } else if ("attendance".equals(action)) {
                String dateText = request.getParameter("date");

                LocalDate date = (dateText == null || dateText.isBlank())
                        ? LocalDate.now()
                        : LocalDate.parse(dateText);

                List<Attendance> records =
                        dao.getAttendanceByDate(date);

                request.setAttribute("date", date);
                request.setAttribute("records", records);
                request.setAttribute("students", dao.getStudents());

                request.getRequestDispatcher(
                        "/WEB-INF/views/attendance.jsp")
                        .forward(request, response);

            } else if ("report".equals(action)) {
                request.setAttribute("report", dao.getReport());
                request.getRequestDispatcher(
                        "/WEB-INF/views/report.jsp")
                        .forward(request, response);

            } else {
                String rollNo = request.getParameter("rollNo");

                List<Student> students =
                        (rollNo == null || rollNo.isBlank())
                        ? dao.getStudents()
                        : dao.searchStudents(rollNo);

                request.setAttribute("students", students);
                request.getRequestDispatcher(
                        "/WEB-INF/views/students.jsp")
                        .forward(request, response);
            }

        } catch (RuntimeException e) {
            throw new ServletException("Unable to process request.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        try {
            if ("addStudent".equals(action)) {
                Student student = new Student();

                student.setName(request.getParameter("name").trim());
                student.setRollNo(request.getParameter("rollNo").trim());
                student.setCourse(request.getParameter("course").trim());
                student.setSemester(
                        Integer.parseInt(request.getParameter("semester")));

                dao.addStudent(student);

            } else if ("deleteStudent".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.deleteStudent(id);

            } else if ("saveAttendance".equals(action)) {
                int studentId =
                        Integer.parseInt(request.getParameter("studentId"));

                LocalDate date = LocalDate.parse(
                        request.getParameter("date"));

                String status = request.getParameter("status");

                dao.saveAttendance(studentId, date, status);
            }

            response.sendRedirect(
                    request.getContextPath() + "/app");

        } catch (RuntimeException e) {
            throw new ServletException("Unable to save data.", e);
        }
    }
}