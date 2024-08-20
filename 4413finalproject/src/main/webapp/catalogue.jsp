<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Catalogue</title>
    
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="catalogue.css">
    
</head>

<body>
    <%@ include file="header/header.jsp" %>

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
                <form>
                    <label for="subcategory">Sub Category:</label>
                    <select id="subcategory" name="subcategory">
                        <!-- change to a loop that retrieves subcategories for the selected category from db -->
                        <option value="all">All ${param.category} </option>
                        <option value="sub1">Sub 1</option>
                        <option value="sub2">Sub 2</option>
                        <option value="sub3">Sub 3</option>
                    </select>

                    <label for="price">Price:</label>
                    <input type="range" id="price" name="price_minmax" min="0" max="1000" step="10">

                    <button class="addToBag" type="submit">Apply Filters</button>
                </form>
            </aside>

            <section class="category-grid">
                <!--change into loop that gets these from the db -->
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
                    <h3>Product Name</h3>
                    <p>$XX.XX</p>
                </div>
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
                    <h3>Product Name</h3>
                    <p>$XX.XX</p>
                </div>
                <div class="product-item">
                    <a href="specificitem.jsp">
                        <img src="example-product.jpg" alt="Example Product">
                    </a>
                    <h3>Product Name</h3>
                    <p>$XX.XX</p>
                </div>
                <!-- Add more product items here based on dynamic content -->
            </section>
        </div>
    </main>
</body>
</html>
