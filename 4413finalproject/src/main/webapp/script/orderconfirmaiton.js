$(document).ready(function() {
    // Get the order number from the URL
    const urlParams = new URLSearchParams(window.location.search);
    const orderNumber = urlParams.get('orderNumber');

    if (orderNumber) {
        // Fetch additional order details if needed
        $.ajax({
            url: "http://localhost:8080/unisphereREST/rest/Orders/" + orderNumber,
            method: "GET",
            success: function(orderDetails) {
                // Display additional order details if necessary
                console.log("Order details:", orderDetails);
                // Example: Update the order details section
                $("#order-number").text("Order #" + orderDetails.orderNumber);
                // Add more code here to display other order details
            },
            error: function() {
                alert("Error fetching order details. Please try again later.");
            }
        });
    }
});
