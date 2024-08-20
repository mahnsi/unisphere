<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - E-commerce Website</title>
    <link rel="stylesheet" href="style.css">
</head>

<body>
<%@ include file="header/header.jsp" %>

<main>
    <!-- Banner Section -->
    <section class="banner">
        <div class="banner-content">
            <h1>Welcome to Our Store</h1>
            <p>Discover amazing products at the best prices!</p>
            <a href="shop.jsp" class="banner-btn">Shop Now</a>
        </div>
    </section>

    <!-- Featured Items Section -->
    <section class="featured-items">
        <h2>Featured Items</h2>
        <div class="items-container">
            <div class="item-box">
                <img src="images/item1.jpg" alt="Item 1">
                <h3>Product 1</h3>
                <p>$19.99</p>
                <a href="product.jsp?id=1" class="btn">View Details</a>
            </div>
            <div class="item-box">
                <img src="images/item2.jpg" alt="Item 2">
                <h3>Product 2</h3>
                <p>$29.99</p>
                <a href="product.jsp?id=2" class="btn">View Details</a>
            </div>
            <div class="item-box">
                <img src="images/item3.jpg" alt="Item 3">
                <h3>Product 3</h3>
                <p>$39.99</p>
                <a href="product.jsp?id=3" class="btn">View Details</a>
            </div>
        </div>
    </section>

    <!-- Sign In Link -->
    <section class="sign-in">
        <a href="signin.jsp">Sign In</a>
    </section>
</main>

<!-- Footer Section -->
<footer>
    <div class="footer-content">
        <p>&copy; 2024 E-commerce Website. All rights reserved.</p>
        <p><a href="contact.jsp">Contact Us</a> | <a href="privacy.jsp">Privacy Policy</a></p>
    </div>
</footer>

</body>
</html>
