<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - UNISphere</title>
    <link rel="stylesheet" href="style/admin.css"> <!-- Link to the admin stylesheet -->
</head>

<body>
<%@ include file="header.html" %>

<main class="admin-main">
    <h2>Admin</h2>
    <div class="admin-buttons">
        <form action="saleshistory.jsp">
            <button type="submit" class="admin-btn">View Sales History</button>
        </form>
        <form action="userlist.jsp">
            <button type="submit" class="admin-btn">View User List</button>
        </form>
        <form action="inventory.jsp">
            <button type="submit" class="admin-btn">View Inventory</button>
        </form>
    </div>
</main>

</body>
</html>
