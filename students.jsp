<%@ page import="java.util.List" %>
<%@ page import="com.attendance.model.Student" %>
<%
    List<Student> students =
        (List<Student>) request.getAttribute("students");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Student Attendance System</title>
</head>
<body>
    <h1>Student Attendance Management System</h1>

    <p>
        <a href="<%= request.getContextPath() %>/app?action=add">
            Add Student
        </a> |
        <a href="<%= request.getContextPath() %>/app?action=mark">
            Mark Attendance
        </a> |
        <a href="<%= request.getContextPath() %>/app?action=attendance">
            View Attendance
        </a> |
        <a href="<%= request.getContextPath() %>/app?action=report">
            Attendance Report
        </a>
    </p>

    <h3>Search Student by Roll Number</h3>
    <form action="<%= request.getContextPath() %>/app" method="get">
        <input type="text" name="rollNo" placeholder="Enter roll number">
        <button type="submit">Search</button>
        <a href="<%= request.getContextPath() %>/app">Show All</a>
    </form>

    <h3>Student List</h3>

    <table border="1" cellpadding="8">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Roll Number</th>
            <th>Course</th>
            <th>Semester</th>
            <th>Action</th>
        </tr>

        <% if (students != null) {
            for (Student s : students) { %>
        <tr>
            <td><%= s.getId() %></td>
            <td><%= s.getName() %></td>
            <td><%= s.getRollNo() %></td>
            <td><%= s.getCourse() %></td>
            <td><%= s.getSemester() %></td>
            <td>
                <form action="<%= request.getContextPath() %>/app"
                      method="post">
                    <input type="hidden" name="action"
                           value="deleteStudent">
                    <input type="hidden" name="id"
                           value="<%= s.getId() %>">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
        <% } } %>
    </table>
</body>
</html>