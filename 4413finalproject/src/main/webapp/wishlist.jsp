<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Wishlist</title>
    <link rel="stylesheet" href="style.css">
    
</head>

<body>
<%@ include file="header/header.jsp" %>

<main>

<section class="product-list">
        
        <!-- change into a loop that gets products -->
            <div class="product-item">
            <a href="specificitem.jsp">
                <img src="example-product.jpg" alt="Example Product">
            </a>
                <h3>Product Name</h3>
                <p>$XX.XX</p>
            </div>
            
            <div class="product-item">
            <a href="specificitem.jsp">
                <img src="example-product.jpg" alt="Example Product">
            </a>
                <h3>Product Name 2</h3>
                <p>Product Description 2</p>
                <p>$YY.YY</p>
            </div>
            <!-- Add more product items here -->
            <!-- change into a loop -->
        </section>
        
        
</main>

</body>
</html>