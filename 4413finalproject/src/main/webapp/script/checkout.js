$(document).ready(function() {
    let formData = {};

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

    $.ajax({
        url: 'http://localhost:8080/unisphereREST/rest/Auth/session',
        method: 'GET',
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        success: function(response) {
            console.log("User data from session:", response);
            let username = response.username;
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
        let total = subtotal + shipping + tax;

        console.log("Final Estimated Total:", total);

        $('#subtotal').text(`Subtotal: $${subtotal.toFixed(2)}`);
        $('#shipping-cost').text(`Shipping: $${shipping.toFixed(2)}`);
        $('#tax').text(`Tax: $${tax.toFixed(2)}`);
        $('#estimated-total').text(`Estimated Total: $${total.toFixed(2)}`);
    }

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
                console.log("success fetching all user info");
                if(response){
                    let address = response.address; 
                    let payment = response.payment;
                    $("#existing-address").text(
                        address.firstName + " " + address.lastName + "\n" +
                        address.streetAddress + ", " + address.apartment + "\n" +
                        address.city + ", " + address.province
                    );

                    $("#saved-payment").text(
                        payment.cardHolderName + "\n" +
                        payment.cardNumber + ", " + payment.expiryDate + "\n" +
                        payment.cvv
                    );
                } else {
                    $("#existing-address").text("No Saved Address. Please enter one below or on your profile.");
                    $("#saved-payment").text("No Saved Payment. Please enter one below or on your profile.");
                }
            },
            error: function() {
                console.log("Error fetching user info");
            }
        });
    }

    $("input[name='payment-option']").change(function() {
        if ($(this).val() === "saved") {
            $("#saved-payment-section").show();
            $("#new-payment-section").hide();
        } else {
            $("#saved-payment-section").hide();
            $("#new-payment-section").show();
        }
    });

    $("#place-order-button").click(function() {
        processOrder();
    });

    function processOrder() {
        let addressOption = $("input[name='address-option']:checked").val();
        let paymentOption = $("input[name='payment-option']:checked").val();

        formData.address = addressOption === "new" ? {
            firstName: $("#first-name").val() || null,
            lastName: $("#last-name").val() || null,
            streetAddress: $("#street-address").val() || null,
            apartment: $("#apartment").val() || null,
            city: $("#city").val() || null,
            province: $("#province").val() || null
        } : null;

        formData.payment = paymentOption === "new" ? {
            cardNumber: $("#card-number").val() || null,
            expiryDate: $("#expiry-date").val() || null,
            cvv: $("#cvv").val() || null
        } : null;

        formData.cart = formData.cart || {}; // Ensure the cart is attached to the order

        console.log("Form Data being sent: ", JSON.stringify(formData, null, 2));

        // Generate a random order number for the confirmation
        let randomOrderNumber = Math.floor(Math.random() * 1000000000);  // Generates a random 9-digit order number

        // Proceed with "placing" the order and redirect to the confirmation page
        console.log("Generated Order Number:", randomOrderNumber);

        // Simulate order processing (could be saved to backend in real cases)
        // Redirect to the order confirmation page with the generated order number
        window.location.href = 'orderconfirmation.jsp?orderNumber=' + randomOrderNumber;
    }
});
