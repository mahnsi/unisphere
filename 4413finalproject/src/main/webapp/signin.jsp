<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In</title>
    <link rel="stylesheet" href="style.css">
</head>

<body>
<%@ include file="header/header.jsp" %>

<main>
    <div class="form-container">
        <h1>Sign In</h1>
        <form id='signinform'>
        	<p id="confirmationMessage"></p>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            
            <button type="submit" class = addToBag >Sign In</button>
        </form>
        <p>Don't have an account? <a href="signup.jsp">Sign up</a></p>
    </div>
</main>

</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
//Function that's called when the above form is submitted
$("#signinform").submit(function(e) {
    e.preventDefault();
    
    // Collect the data from the form
    var credentials = {
        username: $("#username").val(),
        password: $("#password").val()
    };
    
    $.ajax({
    	url: "http://localhost:8080/unisphereREST/rest/Auth/login",
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(credentials), 
        success: function(result) {
            console.log("Login successful");
            window.location.href = "profile.jsp";
            
        },
        error: function(xhr, status, error) {
        	if (xhr.status === 401) { // unauthorized
                $("#confirmationMessage").text("Invalid username or password. Please try again.");
            } else {
                $("#confirmationMessage").text("Error during login. Please try again.");
            }
        }
    });
});


</script>
</html>