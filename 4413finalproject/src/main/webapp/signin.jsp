<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In</title>
    <link rel="stylesheet" href="style.css">
</head>

<body>
<%@ include file="header.html" %>

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
        <p>Don't have an account? <a id = "signup-link" href="signup.jsp">Sign up</a></p>
    </div>
</main>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
var redirectUrl = "profile.jsp";
var urlParams = new URLSearchParams(window.location.search);
if (urlParams.has("redir") && urlParams.get("redir") === "true") {
    redirectUrl = "checkout.jsp";
    $("#signup-link").attr("href","signup.jsp?redir=true");
}

//Function that's called when the above form is submitted
$("#signinform").submit(function(e) {
    e.preventDefault();
    
    // Collect the data from the form
    var credentials = {
        username: $("#username").val(),
        password: $("#password").val()
    };
    
    $.ajax({
    	url: "http://localhost:8080/rest/Auth/login",
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(credentials), 
        success: function(result) {
            console.log("Login successful");
            window.location.href = redirectUrl;
            
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
    <script>
    $(document).ready(function() {
        // Make the AJAX request to the backend to get the session data
        $.ajax({
            url: 'http://localhost:8080/rest/Auth/session',
            method: 'GET',
            dataType: 'json',
            xhrFields: {
                withCredentials: true // Include cookies in the request
            },
            success: function(response) {
            	window.location.href = 'profile.jsp';
            },
            error: function(jqXHR, textStatus, errorThrown) {
                // Handle errors
               //window.location.href = 'profile.jsp';
                console.error("Error fetching session data:", textStatus, errorThrown);
            }
        });
    });

    </script>
    </body>
</html>