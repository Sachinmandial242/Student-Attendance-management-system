<%@ page import="java.util.List" %>
<%@ page import="com.attendance.model.Student" %>
<%
    List<Student> students =
        (List<Student>) request.getAttribute("students");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Mark Attendance</title>
</head>
<body>
    <h1>Mark Student Attendance</h1>

    <form action="<%= request.getContextPath() %>/app" method="post">
        <input type="hidden" name="action" value="saveAttendance">

        <p>Date:
            <input type="date" name="date" required>
        </p>

        <p>Student:
            <select name="studentId" required>
                <% if (students != null) {
                    for (Student s : students) { %>
                    <option value="<%= s.getId() %>">
                        <%= s.getRollNo() %> - <%= s.getName() %>
                    </option>
                <% } } %>
            </select>
        </p>

        <p>Status:
            <select name="status">
                <option value="Present">Present</option>
                <option value="Absent">Absent</option>
            </select>
        </p>

        <button type="submit">Save Attendance</button>
    </form>

    <p><a href="<%= request.getContextPath() %>/app">Back to Home</a></p>
</body>
</html>