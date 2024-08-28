$(document).ready(function() {
    let formData = {};
    let existingAddress = null;
    let existingPayment = null;
	let total = 0;

    // Fetch the cart data and update the order summary
    $.ajax({
        url: 'http://localhost:8080/unisphereREST/rest/Cart/getCart',
        method: 'GET',
        dataType: 'json',
        success: function(cart) {
            console.log("Cart data:", cart);
            formData.cart = cart; // Store the cart data for later use
            updateOrderSummary(cart);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error("Error fetching cart data:", textStatus, errorThrown);
        }
    });

    // Fetch user session data
    $.ajax({
        url: 'http://localhost:8080/unisphereREST/rest/Auth/session',
        method: 'GET',
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        success: function(response) {
            console.log("User data from session:", response);
            username = response.username;
            fetchExistingAddressandPayment(username);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error("Error fetching session data:", textStatus, errorThrown);
        }
    });

    function updateOrderSummary(cart) {
        if (!cart || typeof cart.totalPrice !== 'number') {
            console.error("Cart data is not valid:", cart);
            return;
        }

        let subtotal = cart.totalPrice || 0;
        let shipping = 5.00;
        let tax = subtotal * 0.13;
        total = subtotal + shipping + tax;

        console.log("Final Estimated Total:", total);

        $('#subtotal').text(`Subtotal: $${subtotal.toFixed(2)}`);
        $('#shipping-cost').text(`Shipping: $${shipping.toFixed(2)}`);
        $('#tax').text(`Tax: $${tax.toFixed(2)}`);
        $('#estimated-total').text(`Estimated Total: $${total.toFixed(2)}`);
    }

    // Handle address option change
    $("input[name='address-option']").change(function() {
        if ($(this).val() === "existing") {
            $("#existing-address-section").show();
            $("#new-address-section").hide();
        } else {
            $("#existing-address-section").hide();
            $("#new-address-section").show();
        }
    });

    function fetchExistingAddressandPayment(username) {
        $.ajax({
            url: "http://localhost:8080/unisphereREST/rest/Users/getAllUserInfo/" + username,
            method: "GET",
            success: function(response) {
                console.log("Success fetching all user info");
                if (response) {
					if(response.address.id == 2){
						$("#existing-address").text("No Saved Address. Please enter one below or on your profile.");
						$('#exad').prop('disabled', true);

					}
					
					else{
						existingAddress = response.address;
						$("#existing-address").text(
	                        existingAddress.firstName + " " + existingAddress.lastName + "\n" +
	                        existingAddress.streetAddress + ", " + "\n" +
	                        existingAddress.city + ", " + existingAddress.province + ", " + existingAddress.country
	                    );
					}
					
					if(response.payment.id == 2){
						$("#saved-payment").text("No Saved Payment. Please enter one below or on your profile.");
						$('#expa').prop('disabled', true);
					}
					else{
	                    existingPayment = response.payment;
	                    $("#saved-payment").text(
	                        existingPayment.cardHolderName + "\n" +
	                        existingPayment.cardNumber + ", " + existingPayment.expirationDate + "\n" +
	                        existingPayment.cvv
	                    );
					}
                } else {
                    
                    
                }
            },
            error: function() {
                console.log("Error fetching user info");
            }
        });
    }

    // Handle payment option change
    $("input[name='payment-option']").change(function() {
        if ($(this).val() === "saved") {
            $("#saved-payment-section").show();
            $("#new-payment-section").hide();
        } else {
            $("#saved-payment-section").hide();
            $("#new-payment-section").show();
        }
    });

    // Handle the place order button click
    $("#place-order-button").click(function() {
        $.ajax({
            url: 'http://localhost:8080/unisphereREST/rest/Auth/session',
            method: 'GET',
            dataType: 'json',
            xhrFields: {
                withCredentials: true
            },
            success: function(response) {
                console.log("User data from session:", response);
                username = response.username;
                processOrder(username);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error("Error fetching session data:", textStatus, errorThrown);
            }
        });
    });

    function processOrder(username) {
        let addressOption = $("input[name='address-option']:checked").val();
        let paymentOption = $("input[name='payment-option']:checked").val();

        formData.address = addressOption === "new" ? {
            firstName: $("#first-name").val() || null,
            lastName: $("#last-name").val() || null,
            streetAddress: $("#street-address").val() || null,
            apartment: $("#apartment").val() || null,
            city: $("#city").val() || null,
            province: $("#province").val() || null,
            postalCode: $("#postal-code").val() || null,
            country: $("#country").val() || null
        } : existingAddress;

        formData.payment = paymentOption === "new" ? {
            cardHolderName: $("#card-holder-name").val() || null,
            cardNumber: $("#card-number").val() || null,
            expirationDate: $("#expiry-date").val() || null,
            cvv: $("#cvv").val() || null
        } : existingPayment;

        formData.cart = formData.cart || {}; // Ensure the cart is attached to the order
        formData.createdBy = username;
		formData.total = total;
		console.log($('#estimated-total').val());
		
		
		// Validation for address and payment fields
		    let isValid = true;
		    let missingFields = [];

		    if (addressOption === "new") {
		        // Validate all required fields except 'apartment'
		        for (const [key, value] of Object.entries(formData.address)) {
		            if (key !== 'apartment' && !value) {
		                isValid = false;
		                missingFields.push(key);
		            }
		        }
		    }

		    if (paymentOption === "new") {
		        for (const [key, value] of Object.entries(formData.payment)) {
		            if (!value) {
		                isValid = false;
		                missingFields.push(key);
		            }
		        }
		    }

		    if (!isValid) {
		        alert("Please enter all required information. Missing fields: " + missingFields.join(', '));
		        return;
		    }


        console.log("Form Data being sent: ", JSON.stringify(formData, null, 2));

        // Send the order data to the server
        $.ajax({
            url: "http://localhost:8080/unisphereREST/rest/Orders",
            method: "POST",
            data: JSON.stringify(formData),
            contentType: "application/json",
            success: function(response) {
				console.log(response);
                window.location.href = 'orderconfirmation.jsp?orderNumber=' + response.id;
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert("Please enter all information");
                console.error("Order processing error:", textStatus, errorThrown);
            }
        });
    }
});
