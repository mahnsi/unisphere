<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
</head>
<body>
    <%@ include file="header.html" %>

    <h1 class="page-title">Welcome, <span id="usernameDisplay"></span></h1>

    <div class="container">
        <div class="nav-panel">
            <ul>
                <li><a id="nav-info" onclick="showSection('info', this)">My Info</a></li>
                <li><a id="nav-orders" onclick="showSection('orders', this)">Order History</a></li>
                <li><a id="nav-payments" onclick="showSection('payments', this)">My Payments</a></li>
                <li><a id="nav-address" onclick="showSection('addresses', this)">My Addresses</a></li>
            </ul>
        </div>

        <div class="content-panel">
            <div id="info" class="section" style="display: block;">
                <h2>My Info</h2>
                <form id="updateForm">
                    <label for="firstName">First Name:</label>
                    <input type="text" id="firstName" name="firstName"><br>

                    <label for="lastName">Last Name:</label>
                    <input type="text" id="lastName" name="lastName"><br>

                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username"><br>

                    <button type="button" id="updateButton">Update</button>
                    <p id="updateMessage" style="color: green; display: none;">Successfully updated</p>
                </form>
                <button id="logoutButton">Log Out</button>
            </div>

            <!-- Other sections like Order History, Payments, and Addresses can be added similarly -->
        </div>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(function() {
            // Get the current user information from the session
            $.ajax({
                url: 'http://localhost:8080/unisphereREST/rest/Auth/session', 
                method: 'GET',
                dataType: 'json',
                xhrFields: {
                    withCredentials: true 
                },
                success: function(response) {
                    $('#usernameDisplay').text(response.firstName);
                    $('#firstName').val(response.firstName);
                    $('#lastName').val(response.lastName);
                    $('#username').val(response.username);
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.error("Error fetching session data:", textStatus, errorThrown);
                    window.location.href = 'signin.jsp';
                }
            });

            // Handle the update button click
            $('#updateButton').click(function() {
                var updatedUser = {
                    firstName: $('#firstName').val(),
                    lastName: $('#lastName').val(),
                    username: $('#username').val()
                };

                $.ajax({
                    url: 'http://localhost:8080/unisphereREST/rest/Auth/updateUser',
                    method: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(updatedUser),
                    success: function(response) {
                        $('#usernameDisplay').text(updatedUser.firstName);
                        $('#updateMessage').show().delay(3000).fadeOut();

                        // Update the username display
                        $('#usernameDisplay').text(updatedUser.firstName);
                        $('#username').val(updatedUser.username);
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.error("Error updating user info:", textStatus, errorThrown);
                    }
                });
            });

            // Handle logout button click
            $('#logoutButton').click(function() {
                $.ajax({
                    url: 'http://localhost:8080/unisphereREST/rest/Auth/logout', 
                    method: 'POST',
                    xhrFields: {
                        withCredentials: true 
                    },
                    success: function(response) {
                        window.location.href = 'home.jsp';
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.error("Error logging out:", textStatus, errorThrown);
                    }
                });
            });
        });

        function showSection(section, element) {
            document.querySelectorAll('.section').forEach(section => {
                section.style.display = 'none';
            });

            document.getElementById(section).style.display = 'block';

            document.querySelectorAll('.nav-panel a').forEach(navItem => {
                navItem.classList.remove('active');
            });

            element.classList.add('active');
        }
    </script>
</body>
</html>
