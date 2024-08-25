<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Catalogue</title>
    
    <link rel="stylesheet" href="style/catalogue.css">
    
</head>

<body>
    <%@ include file="header.html" %>

    <div class="page-title">
        <%
            // Retrieve the search term from the request
            String searchQuery = request.getParameter("search");
            String category = request.getParameter("category");

            // Display title based on params
            if (searchQuery != null && !searchQuery.isEmpty()) {
                out.println("<h1>Search: " + searchQuery + "</h1>");
            } else if (category != null) {
                out.println("<h1>" + category + "</h1>");
            } else {
                out.println("<h1>Shop All</h1>");
            }
        %>
    </div>
        
<main class="category-page-container">
    <div class="category-content">
        <aside class="nav-panel">
            <h2>Filter By</h2>
            <form id="filterForm">
		        <label for="subcategory">Sub Category:</label>
		        <select id="subcategory" name="subcategory">
		            <!-- Subcategories will be populated dynamically -->
		        </select>
		
		        <label for="price">Price:</label>
		        <input type="range" id="price" name="price_minmax" min="0" max="1000" step="10">
		
		        <label for="sort">Sort By:</label>
		        <select id="sort" name="sort">
				    <option value="none">None</option>
				    <option value="3">Price: Low to High</option>
				    <option value="4">Price: High to Low</option>
				    <option value="1">Name: A to Z</option>
				    <option value="2">Name: Z to A</option>
				</select>

		    </form>
        </aside>

        <section class="category-grid" id="productGrid">
            <!-- Products will be populated dynamically -->
        </section>
    </div>
</main>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="script/catalogue.js"></script>

</body>
</html>
