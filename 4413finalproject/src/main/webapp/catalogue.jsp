<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Catalogue</title>
<link rel="stylesheet" href="style.css">
</head>

<body>
<%@ include file="header/header.jsp" %>

<%
        // Retrieve the search term from the request
        String searchQuery = request.getParameter("search");
        String category = request.getParameter("category");
        
        // Display title based on params
        if (searchQuery != null && !searchQuery.isEmpty()) {
            
            out.println("<h2> Search: " + searchQuery + "</h2>");
            
        } else if (category != null) {
            
            out.println("<h2>" + category + "</h2>");
            
        } else {
            out.println("<h2>Shop All</h2>");
            
        }
 %>

<main>
        <aside class="filter-box">
            <h2>Filter By</h2>
            <form>
                <label for="category">Sub Category:</label>
                <select id="category" name="category">
                <!-- change into a loop thatbgets subcategories for category -->
                    <option value="all">All ${param.category} </option>
                    <option value=" ">Sub 1</option>
                    <option value=" ">Sub 2</option>
                    <option value=" ">Sub 3</option>
                </select>

                <label for="price">Price:</label>
                <input type="range" id="price" name="price_minmax" min="0" max="1000" step="10">

                

                <button class = addToBag type="submit">Apply Filters</button>
            </form>
        
        </aside>
        <section class="product-list">
        
        <!-- change into a loop that gets products -->
            <div class="product-item">
            <a href="specificitem.jsp">
                <img src="example-product.jpg" alt="Example Product">
            </a>
                <h3>Product Name</h3>
                <p>$XX.XX</p>
            </div>
            
       
            <!-- Add more product items here -->
            <!-- change into a loop -->
        </section>

</main>


</body>
</html>
