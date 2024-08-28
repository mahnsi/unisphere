$(document).ready(function() {
    const apiUrl = "http://localhost:8080/unisphereREST/rest/Products";
    
    fetchProducts();
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
        $('#add-product-form').slideToggle(); 
    });

    // Handle the submission of the new product form
	$('#submit-product-btn').on('click', function(e) {
	    e.preventDefault();

	    const productData = {
	        title: $('#product-title').val(),
	        price: parseFloat($('#product-price').val()), 
	        stock: parseInt($('#product-stock').val(), 10),
	        description: $('#product-desc').val(),
	        subCategory: $('#subcategory').val()
	    };

	    // Send the new product data as JSON to the server
	    $.ajax({
	        url: apiUrl,
	        method: 'POST',
	        data: JSON.stringify(productData), // Convert the object to a JSON string
	        contentType: 'application/json', // Set the content type to JSON
	        success: function(response) {
				//write the image
				var image =  $('#product-image')[0].files[0];
				uploadImage(image);
	            alert('Product added successfully');
	            location.reload();
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
                
                const productList = $('#product-list');
                
                if (products.length > 0) {
                    products.forEach(function(product) {
                        const productHtml = 
                            '<div class="product-item">' +
                                '<a href="product-detail.jsp?id=' + product.id + '">' +
                                    '<h3>' + product.title + '</h3>' +
                                    '<p>Price: $' + product.price.toFixed(2) + '</p>' +
                                    '<p>Stock: <span id="STOCKNUM">' +  product.stock + '</span></p>' +
									'<p>Sold: <span>' +  product.sold + '</span></p>' +
                                '</a>' +
                                '<label for="update-quantity-' + product.id + '">Update Stock:</label>' +
								'<input id="update-quantity-' + product.id + '" class="update-quantity" type="number" min="0" value="1">'+
                                '<button class="update-btn" data-id="' + product.id + '">Update</button>' +
                            '</div>';
                        
                        productList.append(productHtml);
                    });
                    
                    $('.update-btn').on('click', function() {
                        const productId = $(this).data('id');
                        const quantity = $('#update-quantity-' + productId).val();
                        updateInventory(productId, quantity);
                    });
                } else {
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
                    subcategorySelect.append('<option value="' + encodeURIComponent(subcategory.id) + '">' + subcategory.name + '</option>');
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
	
	function uploadImage(image) {
		console.log("hi");
	        var fileInput = image
	        if (fileInput) {
	            var formData = new FormData();
	            formData.append('image', fileInput);

	            // Send the image file to the server
	            $.ajax({
	                url: 'http://localhost:8080/unisphereREST/rest/Products/uploadImage',
	                method: 'POST',
	                data: formData,
	                contentType: false,
	                processData: false,
	                success: function(response) {
	                    alert('Image uploaded successfully!');
	                    location.reload(); // Reload to show new product
	                },
	                error: function(err) {
	                    console.error('Error uploading image:', err);
	                    alert('Failed to upload image.');
	                }
	            });
	        }
	  }
});