
<%@ page import="java.util.List" %>
<%
    List<String[]> report =
        (List<String[]>) request.getAttribute("report");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Attendance Report</title>
</head>
<body>
    <h1>Student Attendance Report</h1>

    <table border="1" cellpadding="8">
        <tr>
            <th>Roll Number</th>
            <th>Name</th>
            <th>Total Marked Days</th>
            <th>Present Days</th>
            <th>Attendance Percentage</th>
        </tr>

        <% if (report != null) {
            for (String[] r : report) { %>
        <tr>
            <td><%= r[0] %></td>
            <td><%= r[1] %></td>
            <td><%= r[2] %></td>
            <td><%= r[3] %></td>
            <td><%= r[4] %></td>
        </tr>
        <% } } %>
    </table>

    <p><a href="<%= request.getContextPath() %>/app">Back to Home</a></p>
</body>
</html>