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

            <!-- Add Product Form -->
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

                <button id="submit-product-btn">Add Product</button>
            </div>
        </div>
    </div>
</main>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function() {
    const apiUrl = "http://localhost:8080/unisphereREST/rest/Products";
    var category;
    
    // Fetch and display products
    fetchProducts();

    // Fetch categories on page load
    fetchCategories();

    // Event handler for category selection
    $('#category').on('change', function() {
        const categoryId = $(this).val();
        if (categoryId) {
            fetchSubcategories(categoryId);
        } else {
            $('#subcategory').html('<option value="">Select a category first</option>');
        }
    });

    // Show the Add Product Form when the button is clicked
    $('#add-product-btn').on('click', function() {
        $('#add-product-form').slideToggle(); // Toggle the visibility of the form
    });

    // Handle the submission of the new product form
    $('#submit-product-btn').on('click', function(e) {
        e.preventDefault();

        const newProduct = {
            title: $('#product-title').val(),
            price: $('#product-price').val(),
            stock: $('#product-stock').val(),
            description: $('#product-desc').val(),
            subcategory_id: $('#subcategory').val()
        };

        // Send the new product data to the server
        $.ajax({
            url: apiUrl,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(newProduct),
            success: function(response) {
                alert('Product added successfully');
                location.reload(); // Reload the page to see the new product
            },
            error: function(err) {
                console.error('Error adding product:', err);
                alert('Error adding product. Please try again later.');
            }
        });
    });

    function fetchProducts() {
    	$.ajax({
            url: apiUrl,
            method: 'GET',
            dataType: 'json',
            success: function(products) {
                console.log("Products fetched successfully:", products); // For debugging
                
                // Find the product-list container
                const productList = $('#product-list');
                
                // Check if products exist
                if (products.length > 0) {
                    // Iterate through the list of products and create HTML for each product
                    products.forEach(function(product) {
                        const productHtml = 
                            '<div class="product-item">' +
                                '<a href="product-detail.jsp?id=' + product.id + '">' +
                                    '<h3>' + product.title + '</h3>' +
                                    '<p>Price: $' + product.price.toFixed(2) + '</p>' +
                                    '<p>Stock: <span id="STOCKNUM">' +  product.stock + '</span></p>' +
                                    '<p>Sold: ' + product.sold + '</p>' +
                                '</a>' +
                                '<label for="update-quantity-' + product.id + '">Update Stock:</label>' +
                                '<select id="update-quantity-' + product.id + '" class="update-quantity">' +
                                    '<option value="0">0</option>' +
                                    '<option value="1">1</option>' +
                                    '<option value="2">2</option>' +
                                    '<option value="5">5</option>' +
                                    '<option value="10">10</option>' +
                                    '<option value="20">20</option>' +
                                '</select>' +
                                '<button class="update-btn" data-id="' + product.id + '">Update</button>' +
                            '</div>';
                        
                        // Append the product HTML to the product-list container
                        productList.append(productHtml);
                    });
                    
                    // Attach click event to update buttons
                    $('.update-btn').on('click', function() {
                        const productId = $(this).data('id');
                        const quantity = $('#update-quantity-' + productId).val();
                        
                        // Call function to update inventory
                        updateInventory(productId, quantity);
                    });
                } else {
                    // If no products are found, display a message
                    productList.append('<p>No products found.</p>');
                }
            },
            error: function(err) {
                console.error('Error fetching products:', err);
                $('#product-list').append('<p>Error loading products. Please try again later.</p>');
            }
        });
    }

    function fetchCategories() {
        $.ajax({
            url: 'http://localhost:8080/unisphereREST/rest/Products/getAllCategories',
            method: 'GET',
            dataType: 'json',
            success: function(categories) {
                const categorySelect = $('#category');
                categorySelect.html('<option value="">Select a category</option>');
                console.log(categories);
                $.each(categories, function(index, category) {
                    categorySelect.append('<option value="' + encodeURIComponent(category) + '">' + category + '</option>');
                });
            },
            error: function(err) {
                console.error('Error fetching categories:', err);
                $('#category').html('<option value="">Error loading categories</option>');
            }
        });
    }

    function fetchSubcategories(categoryId) {
        $.ajax({
            url: "http://localhost:8080/unisphereREST/rest/Products/getSubcategoriesByCategory/" + $('#category').val(),
            method: 'GET',
            dataType: 'json',
            success: function(subcategories) {
                const subcategorySelect = $('#subcategory');
                subcategorySelect.html('<option value="">Select a subcategory</option>');
                $.each(subcategories, function(index, subcategory) {
                    subcategorySelect.append('<option value="' + encodeURIComponent(subcategory) + '">' + subcategory + '</option>');
                });
            },
            error: function(err) {
                console.error(err);
                $('#subcategory').html('<option value="">Error loading subcategories</option>');
            }
        });
    }

    function updateInventory(productId, quantity) {
        $.ajax({
            url: "http://localhost:8080/unisphereREST/rest/Products/updateInventory/?id=" + productId +"&quantity="+ quantity,
            method: 'PUT',
            success: function(response) {
            	$('#STOCKNUM').text(quantity);
                alert('Inventory updated successfully');
                
            },
            error: function(xhr, status, error) {
                console.error('Request Status:', status); // Log the status of the request
                console.error('Error Message:', error); // Log the error message
                console.error('Response Text:', xhr.responseText); // Log the response text from the server
                alert('Error updating inventory. Please try again later.');
            }
        });
    }
});

</script>

</body>
</html>
