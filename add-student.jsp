<!DOCTYPE html>
<html>
<head>
    <title>Add Student</title>
</head>
<body>
    <h1>Add New Student</h1>

    <form action="<%= request.getContextPath() %>/app" method="post">
        <input type="hidden" name="action" value="addStudent">

        <p>Name: <input type="text" name="name" required></p>
        <p>Roll Number: <input type="text" name="rollNo" required></p>
        <p>Course: <input type="text" name="course" required></p>
        <p>Semester:
            <input type="number" name="semester" min="1" required>
        </p>

        <button type="submit">Save Student</button>
    </form>

    <p><a href="<%= request.getContextPath() %>/app">Back to Home</a></p>
</body>
</html>