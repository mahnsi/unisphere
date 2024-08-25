<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - Inventory</title>
    <link rel="stylesheet" href="style/admin.css">
    
</head>

<body>
<%@ include file="header.html" %>

<main class="admin-main">
    <h2>Product Inventory</h2>
    
    <!-- Container for product list and add product form -->
    <div id="admin-container">
        <div id="product-list"></div> <!-- Container for products -->

        <div id="add-product-container">
            <button id="add-product-btn">Add Product</button>

			<div id="add-product-form">
			    <h3>Add New Product</h3>
			    <label for="product-title">Title:</label>
			    <input type="text" id="product-title" required>
			
			    <label for="product-price">Price:</label>
			    <input type="number" id="product-price" required>
			
			    <label for="product-stock">Stock:</label>
			    <input type="number" id="product-stock" required>
			
			    <label for="product-desc">Description:</label>
			    <input type="text" id="product-desc" required>
			    
			    <label for="category">Category:</label>
			    <select id="category" name="category" required>
			    </select>
			    
			    <label for="subcategory">Subcategory:</label>
			    <select id="subcategory" name="subcategory" required>
			        <option value="">Select a category first</option>
			    </select>
			
			    <label for="product-image">Image:</label>
			    <input type="file" id="product-image" accept="image/*">
			
			    <button id="submit-product-btn">Add Product</button>
			</div>

        </div>
    </div>
</main>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="inventory.js"></script>
</body>
</html>
