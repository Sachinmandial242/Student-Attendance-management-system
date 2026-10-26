package com.attendance.dao;

import com.attendance.model.Student;
import com.attendance.model.Attendance;
import com.attendance.util.HibernateUtil;
import com.attendance.util.JDBCUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    public void addStudent(Student student) {
        Transaction tx = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(student);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    public List<Student> getStudents() {
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Student ORDER BY id", Student.class)
                    .getResultList();
        }
    }

    public void deleteStudent(int id) {
        Transaction tx = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Student student = session.get(Student.class, id);

            if (student != null) {
                session.remove(student);
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    public List<Student> searchStudents(String rollNo) {
        List<Student> list = new ArrayList<>();

        String sql = "SELECT * FROM students WHERE roll_no LIKE ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + rollNo + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Student s = new Student();
                    s.setId(rs.getInt("id"));
                    s.setName(rs.getString("name"));
                    s.setRollNo(rs.getString("roll_no"));
                    s.setCourse(rs.getString("course"));
                    s.setSemester(rs.getInt("semester"));
                    list.add(s);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Student search failed.", e);
        }

        return list;
    }

    public void saveAttendance(
            int studentId, LocalDate date, String status) {

        if (!status.equals("Present") && !status.equals("Absent")) {
            throw new IllegalArgumentException("Invalid attendance status.");
        }

        Transaction tx = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            List<Attendance> records = session.createQuery(
                    "FROM Attendance WHERE studentId = :sid "
                    + "AND attendanceDate = :date",
                    Attendance.class)
                    .setParameter("sid", studentId)
                    .setParameter("date", date)
                    .getResultList();

            Attendance attendance;

            if (records.isEmpty()) {
                attendance = new Attendance();
                attendance.setStudentId(studentId);
                attendance.setAttendanceDate(date);
                attendance.setStatus(status);
                session.persist(attendance);
            } else {
                attendance = records.get(0);
                attendance.setStatus(status);
            }

            tx.commit();

        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                    "FROM Attendance WHERE attendanceDate = :date "
                    + "ORDER BY studentId",
                    Attendance.class)
                    .setParameter("date", date)
                    .getResultList();
        }
    }

    public List<String[]> getReport() {
        List<String[]> report = new ArrayList<>();

        String sql =
                "SELECT s.roll_no, s.name, "
                + "COUNT(a.id) AS total_days, "
                + "SUM(CASE WHEN a.status='Present' THEN 1 ELSE 0 END) "
                + "AS present_days "
                + "FROM students s LEFT JOIN attendance a "
                + "ON s.id = a.student_id "
                + "GROUP BY s.id, s.roll_no, s.name "
                + "ORDER BY s.roll_no";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int total = rs.getInt("total_days");
                int present = rs.getInt("present_days");

                double percentage = total == 0
                        ? 0
                        : (present * 100.0 / total);

                report.add(new String[]{
                    rs.getString("roll_no"),
                    rs.getString("name"),
                    String.valueOf(total),
                    String.valueOf(present),
                    String.format("%.2f", percentage) + "%"
                });
            }

        } catch (SQLException e) {
            throw new RuntimeException("Report generation failed.", e);
        }

        return report;
    }
}