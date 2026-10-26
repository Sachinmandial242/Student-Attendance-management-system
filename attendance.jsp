<%@ page import="java.util.List" %>
<%@ page import="com.attendance.model.Attendance" %>
<%@ page import="com.attendance.model.Student" %>
<%
    List<Attendance> records =
        (List<Attendance>) request.getAttribute("records");

    List<Student> students =
        (List<Student>) request.getAttribute("students");
%>
<!DOCTYPE html>
<html>
<head>
    <title>View Attendance</title>
</head>
<body>
    <h1>Attendance Records</h1>

    <form action="<%= request.getContextPath() %>/app" method="get">
        <input type="hidden" name="action" value="attendance">
        <input type="date" name="date"
               value="<%= request.getAttribute("date") %>" required>
        <button type="submit">View Date</button>
    </form>

    <table border="1" cellpadding="8">
        <tr>
            <th>Student ID</th>
            <th>Name</th>
            <th>Roll Number</th>
            <th>Date</th>
            <th>Status</th>
        </tr>

        <% if (records != null) {
            for (Attendance a : records) {
                String studentName = "Unknown";
                String rollNo = "Unknown";

                if (students != null) {
                    for (Student s : students) {
                        if (s.getId() == a.getStudentId()) {
                            studentName = s.getName();
                            rollNo = s.getRollNo();
                            break;
                        }
                    }
                }
        %>
        <tr>
            <td><%= a.getStudentId() %></td>
            <td><%= studentName %></td>
            <td><%= rollNo %></td>
            <td><%= a.getAttendanceDate() %></td>
            <td><%= a.getStatus() %></td>
        </tr>
        <% } } %>
    </table>

    <p><a href="<%= request.getContextPath() %>/app">Back to Home</a></p>
</body>
</html>