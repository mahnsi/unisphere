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
            
            <div id="orders" class="section" style="display: none;">
			    <h2>Order History</h2>
			    <ul id="orderList"></ul>
			    <div id="orderDetails" style="display: none;"></div>
			</div>

            

            <div id="addresses" class="section" style="display: none;">
                <h2>My Addresses</h2>
                <form id="addressForm">
                    <label for="streetAddress">Street Address</label>
                    <input type="text" id="streetAddress" name="streetAddress"><br>
                    
                    <label for="apt">Apt. / Suite</label>
                    <input type="text" id="apt" name="apt"><br>

                    <label for="city">City:</label>
                    <input type="text" id="city" name="city"><br>

                    <label for="province">Province:</label>
                    <input type="text" id="province" name="province"><br>

                    <label for="postalCode">Postal Code:</label>
                    <input type="text" id="postalCode" name="postalCode"><br>

                    <label for="country">Country:</label>
                    <input type="text" id="country" name="country"><br>

                    <button type="button" id="updateAddressButton">Update Address</button>
                    <p id="addressUpdateMessage" style="color: green; display: none;">Successfully updated address</p>
                </form>
            </div>

            <div id="payments" class="section" style="display: none;">
                <h2>My Payments</h2>
                <form id="paymentForm">
                    <label for="cardHolderName">Card Holder Name:</label>
                    <input type="text" id="cardHolderName" name="cardHolderName"><br>

                    <label for="cardNumber">Card Number:</label>
                    <input type="text" id="cardNumber" name="cardNumber"><br>

                    <label for="expiry">Expiry Date:</label>
                    <input type="text" id="expiry" name="expiry"><br>

                    <label for="cvv">CVV:</label>
                    <input type="text" id="cvv" name="cvv"><br>

                    <button type="button" id="updatePaymentButton">Update Payment</button>
                    <p id="paymentUpdateMessage" style="color: green; display: none;">Successfully updated payment</p>
                </form>
            </div>
        </div>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="script/profile.js"> </script>
</body>
</html>
