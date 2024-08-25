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
            </form>
        </aside>

        <section class="category-grid" id="productGrid">
            <!-- Products will be populated dynamically -->
        </section>
    </div>
</main>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
$(document).ready(function() {
    // Get the category from the URL parameters
    var category = new URLSearchParams(window.location.search).get('category');

    // Populate subcategories based on the selected category
    if (category) {
        $.ajax({
            url: "http://localhost:8080/unisphereREST/rest/Products/getSubcategoriesByCategory/" + encodeURIComponent(category),
            type: "GET",
            dataType: "json",
            success: function(subcategories) {
                var subcategorySelect = $("#subcategory");
                subcategorySelect.empty(); // Clear any existing options

                // Add an "All" option
                subcategorySelect.append('<option value="all">All ' + category + '</option>');

                // Add options for each subcategory
                $.each(subcategories, function(index, subcategory) {
                    subcategorySelect.append('<option value="' + subcategory.name + '">' + subcategory.name + '</option>');
                });

                // Load products for the initial category (or all if no subcategory is selected)
                loadProducts();
            },
            error: function(xhr, status, error) {
                console.log("Failed to load subcategories:", error);
            }
        });
    } else {
        // If no category is specified, just load all products
        $("#subcategory").prop('disabled', true); 
        loadProducts();
    }

    // Load products based on the selected subcategory or all products
    function loadProducts() {
        var subcategory = $("#subcategory").val();
        var url = "http://localhost:8080/unisphereREST/rest/Products/";

        if (category && category !== 'All') {
            url += "getProductsByCategory/" + encodeURIComponent(category);
        }
        
        if (category && category == 'All') {
        	$("#subcategory").empty(); 
        	$("#subcategory").prop('disabled', true); 
        }

        if (subcategory && subcategory !== 'all') {
        	console.log("subcatselected");
            url = "http://localhost:8080/unisphereREST/rest/Products/getProductsBySubcategory/" + encodeURIComponent(subcategory);
            console.log(url);
        } else {
            url += (category && category !== 'All') ? "/" : "";
        }

        $.ajax({
            url: url,
            type: "GET",
            dataType: "json",
            success: function(products) {
                var productGrid = $("#productGrid");
                productGrid.empty(); // Clear the grid before adding new items

                $.each(products, function(index, product) {
                    var productItem = '<div class="product-item">' +
                        '<a href="specificItem.jsp?id=' + product.id + '">' +
                        '<img src="product-images/' + product.id + '.jpg" alt="' + product.title + '">' +
                        '</a>' +
                        '<h3>' + product.title + '</h3>' +
                        '<p>$' + product.price.toFixed(2) + '</p>' +
                        '</div>';

                    productGrid.append(productItem);
                });
            },
            error: function(xhr, status, error) {
                console.log("Failed to load products:", error);
            }
        });
    }

    // Load products when the subcategory is changed
    $("#subcategory").change(function(event) {
        loadProducts(); // Reload products based on the selected filters
    });
});
</script>

</body>
</html>
