<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Offers</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="offers.css">
</head>

<body>
<%@ include file="header/header.jsp" %>

<main class="offers-main">
    <h2>Offers</h2>
    <div class="offers-container">
        <div class="offer-box">
            <div class="offer-image">30% off New User</div>
            <button class="apply-btn">Apply</button>
        </div>
        <div class="offer-box">
            <div class="offer-image">Free Shipping over $100</div>
            <button class="apply-btn">Apply</button>
        </div>
    </div>
</main>

</body>
</html>
