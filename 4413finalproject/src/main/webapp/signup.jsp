<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up</title>
    <link rel="stylesheet" href="style.css">
    
</head>

<body>
<%@ include file="header/header.jsp" %>

<main>
    <div class="form-container">
        <h2>Create An Account</h2>
        <form method = "post" action="SignUpServlet">
            <label for="email">Email*</label>
            <input name = email type="email" id="email" placeholder="Enter Email" required>

            <label for="username">Username*</label>
            <input name = username type="text" id="username" placeholder="Enter Username" required>

            <label for="password">Password *</label>
            <input type="password" id="password" placeholder="Enter Password" required>

            <label for="confirm-password">Confirm Password *</label>
            <input name = password type="password" id="confirm-password" placeholder="Confirm Password" required>

            <button class="addToBag" type="submit">Register</button>
        </form>
    </div>
</main>


</body>
</html>