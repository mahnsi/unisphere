$(document).ready(function() {
    // Get the category from the URL parameters
    var category = new URLSearchParams(window.location.search).get('category');
	
	var search = new URLSearchParams(window.location.search).get('search');

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
    } 
	else{
		// If no category is specified, just load all products
		        $("#subcategory").prop('disabled', true); 
		        loadProducts();
	}

    // Load products based on the selected subcategory or all products
    function loadProducts(sortval) {
        var subcategory = $("#subcategory").val();
        var url = "http://localhost:8080/unisphereREST/rest/Products/";
		
		if(search){
			url="http://localhost:8080/unisphereREST/rest/Products/getProductsByKeyword/" + encodeURIComponent(search);
		}

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
		
		if (sortval) {

		         url = `http://localhost:8080/unisphereREST/rest/Products/sort/${sortval}`

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
    $("#subcategory").change(function() {
        loadProducts(); // Reload products based on the selected filters
    });
	
	$('#sort').change(function() {
	        // Get selected value (integer)
	        const selectedValue = $(this).val();
	        if (selectedValue !== 'none') {
				loadProducts(selectedValue);

	        }
	    });
});