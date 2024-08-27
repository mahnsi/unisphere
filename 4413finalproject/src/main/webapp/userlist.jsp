<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.html" %>
    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - User List</title>
    <link rel="stylesheet" href="style/style.css">
</head>

<body>

<main>
    <h2>List of All Users</h2>
    <div id="user-list"></div> <!-- User list container -->
</main>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function() {
    // URL of the endpoint to fetch users
    const apiUrl = "http://localhost:8080/rest/Users";
    
    console.log("Script running"); // Check if the script is running
    
    // Fetch users from the API
    $.ajax({
        url: apiUrl,
        method: 'GET',
        dataType: 'json',
        success: function(users) {
            // Find the user-list container
            const userList = $('#user-list');
            
            // Check if users exist
            if (users.length > 0) {
                // Iterate through the list of users and create HTML for each user
                users.forEach(function(user) {
                    const userHtml = 
                        '<div class="user-item" data-username="' + user.username + '">' +
                            '<h3><a href="profile.jsp?mode=admin&username=' + encodeURIComponent(user.username) + '">' + user.username + '</a></h3>' +
                            '<p>Email: <span class="email-display">' + user.email + '</span>' +
                            '<input type="email" class="email-edit" value="' + user.email + '" style="display:none;" /></p>' +
                            '<p>Role: ' + (user.isAdmin ? 'Admin' : 'User') + '</p>' +
                            '<button onclick="deleteUser(\'' + user.username + '\')">Delete</button>' +
                        '</div>';
                    
                    // Append the user HTML to the user-list container
                    userList.append(userHtml);
                });
            } else {
                // If no users are found, display a message
                userList.append('<p>No users found.</p>');
            }
        },
        error: function(err) {
            console.error('Error fetching users:', err);
            $('#user-list').append('<p>Error loading users. Please try again later.</p>');
        }
    });
});

// Function to delete a user
function deleteUser(username) {
    $.ajax({
        url: 'http://localhost:8080/rest/Users/' + username,  // Adjust the URL to match your REST service
        method: 'DELETE',
        success: function(response) {
            console.log("User deleted: " + username);
            // Remove the user's element from the DOM
            $('.user-item[data-username="' + username + '"]').remove();
        },
        error: function(error) {
            console.error('Error deleting user:', error);
        }
    });
}
</script>

</body>
</html>
