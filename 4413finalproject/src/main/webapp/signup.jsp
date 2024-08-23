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
<%@ include file="header.html" %>

<main>
    <div class="form-container">
        <h2>Create An Account</h2>
        <form id = 'signUpForm'>
            <label for="email">Email*</label>
            <input name = email type="email" id="email" placeholder="Enter Email" required>

            <label for="username">Username*</label>
            <input name = username type="text" id="username" placeholder="Enter Username" required>
            
            <label for="first-name">First Name*</label>
            <input name="first_name" type="text" id="fname" placeholder="Enter First Name" required>

            <label for="last-name">Last Name*</label>
            <input name="last_name" type="text" id="lname" placeholder="Enter Last Name" required>

            <label for="password">Password *</label>
            <input type="password" id="password" placeholder="Enter Password" required>

            <label for="confirm-password">Confirm Password *</label>
            <input name = password type="password" id="confirm-password" placeholder="Confirm Password" required>

            <button class="addToBag" type="submit">Register</button>
        </form>
        <div id="confirmationMessage"></div>
    </div>
</main>


</body>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
//Function that's called when the above form is submitted
$("#signUpForm").submit(function(e) {
    e.preventDefault();
    
    // Collect the data from the form
    var userData = {
        username: $("#username").val(),
        email: $("#email").val(),
        firstName: $("#fname").val(),
        lastName: $("#lname").val(),
        isAdmin: false,
        password: $("#password").val()
    };
    
    // Register the user
    $.ajax({
        url: "http://localhost:8080/unisphereREST/rest/Users",
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(userData), 
        success: function(result) {
            console.log("Registration successful");

            //after creation, log the new user in to set session and redirect to profile
            $.ajax({
                url: "http://localhost:8080/unisphereREST/rest/Auth/login",
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    username: userData.username,
                    password: userData.password
                }),
                success: function(loginResult) {
                    console.log("Login successful");
                    window.location.href = "profile.jsp";
                },
                error: function(xhr, status, error) {
                    $("#confirmationMessage").text("Auth error");
                }
            });
        },
        error: function(xhr, status, error) {
            if (xhr.status === 409) {
                // Username or email already exists
                $("#confirmationMessage").text("Username already exists. Please try again.");
            } else {
                // Other errors
                $("#confirmationMessage").text("Error adding user. Please try again.");
            }
        }
    });
});


</script></html>