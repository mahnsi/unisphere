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
        <div class="product-info">
            <a href="specificitem.jsp">
                <img src="example-product.jpg" alt="Example Product">
            </a>
            <div class="product-details">
                <h3>Product Name</h3>
                <p>$XX.XX</p>
            </div>
        </div>
        <div class="remove-item">
            <button class="remove-btn">
                <img src="garbage-icon.png" alt="Remove from Wishlist">
            </button>
        </div>
    </div>
</section>

        
        
</main>

</body>
</html>