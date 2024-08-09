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

<h1>${param.category}</h1>

<main>
        <aside class="filter-box">
            <h2>Filter By</h2>
            <form>
                <label for="category">Sub Category:</label>
                <select id="category" name="category">
                <!-- change into a loop thatbgets subcategories for category -->
                    <option value="all">All ${param.category} </option>
                    <option value="electronics">Sub 1</option>
                    <option value="clothing">Sub 2</option>
                    <option value="home">Sub 3</option>
                </select>

                <label for="price">Price:</label>
                <input type="range" id="price" name="price" min="0" max="1000" step="10">

                

                <button type="submit">Apply Filters</button>
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
