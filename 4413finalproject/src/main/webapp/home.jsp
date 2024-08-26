<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="header.html" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>UNISphere</title>
	<link rel="stylesheet" href="style/home.css">
</head>

<body>

<main>
    <!-- Banner Section -->
    <section class="banner-items">
        <div class="banners-container">
            <div class="banner-box">
                <div class="banner-image">
                    <img src="product-images/supplies.jpg" alt="Top-Rated Supplies">
                </div>
                <div class="banner-content">
                    <h2>Top-Rated Supplies</h2>
                    <p>Start the School Year Right!</p>
                    <a href="catalogue.jsp" class="btn">Shop Now</a>
                </div>
            </div>
            <div class="banner-box">
                <div class="banner-image">
                    <img src="product-images/banner.jpg" alt="Shop our Catalogue">
                </div>
                <div class="banner-content">
                    <h2>Shop our Catalogue</h2>
                    <p>Books for Your Courses</p>
                    <a href="catalogue.jsp" class="btn">Shop Now</a>
                </div>
            </div>
        </div>
    </section>

    <!-- Featured Items Section -->
    <section class="featured-items">
        <h2>Featured Items</h2>
        <div class="items-container">
            <div class="item-box">
                <div class="item-image">
                    <img src="product-images/1.jpg" alt="Product 1">
                </div>
                <h3>Gel Pen Set</h3>
                <p>$19.99</p>
                <a href="specificItem.jsp?id=1" class="btn">View Details</a>
            </div>
            <div class="item-box">
                <div class="item-image">
                    <img src="product-images/2.jpg" alt="Product 2">
                </div>
                <h3>Algorithms - Dasgupta</h3>
                <p>$29.99</p>
                <a href="specificItem.jsp?id=2" class="btn">View Details</a>
            </div>
            <div class="item-box">
                <div class="item-image">
                    <img src="product-images/3.jpg" alt="Product 3">
                </div>
                <h3>I heart UniSphere Cap</h3>
                <p>$39.99</p>
                <a href="specificItem.jsp?id=3" class="btn">View Details</a>
            </div>
            <div class="item-box">
                <div class="item-image">
                    <img src="product-images/4.jpg" alt="Product 4">
                </div>
                <h3>BIC Mechanical Pencils</h3>
                <p>$49.99</p>
                <a href="specificItem.jsp?id=4" class="btn">View Details</a>
            </div>
        </div>
    </section>
</main>

<!-- Footer Section -->
<footer>
    <div class="footer-content">
        <p>&copy; 2024 UNISphere. All rights reserved.</p>
        <p><a href="contact.jsp">Contact Us</a> | <a href="privacy.jsp">Privacy Policy</a></p>
    </div>
</footer>

</body>
</html>

