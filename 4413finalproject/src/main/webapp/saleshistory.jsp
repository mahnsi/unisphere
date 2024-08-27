<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - Sales History</title>
    <link rel="stylesheet" href="style/admin.css"> <!-- Link to the admin stylesheet -->
    <!-- Include jQuery from CDN -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        /* Styling for the order rectangles */
        .order-item {
            border: 1px solid #ccc;
            padding: 10px;
            margin: 10px 0;
            border-radius: 5px;
            background-color: #f9f9f9;
            cursor: pointer;
        }
        .order-details {
            display: none; /* Initially hidden */
            padding-top: 10px;
            margin-top: 10px;
            border-top: 1px solid #ddd;
        }
    </style>
    <script>
        $(document).ready(function() {
            // Function to fetch orders and display them
            function fetchOrders() {
                $.ajax({
                    url: 'http://localhost:8080/rest/Orders/',
                    method: 'GET',
                    dataType: 'json',
                    success: function(data) {
                        // Check if data is not empty
                        if (data && data.length > 0) {
                            $.each(data, function(index, order) {
                                let orderItem = $('<div class="order-item"></div>');
                                let orderTitle = '<strong>Order ID:</strong> ' + order.id + '<br>' +
                                                 '<strong>Name:</strong> ' + order.address.firstName + ' ' + order.address.lastName;
                                
                                // Create the main order rectangle with ID and Name
                                orderItem.append('<div class="order-summary">' + orderTitle + '</div>');
                                
                                // Create the details section to be toggled
                                let orderDetails = $('<div class="order-details"></div>');
                                orderDetails.append('<strong>Total Price:</strong> $' + order.cart.totalPrice + '<br>');
                                
                                // Add items to the order
                                $.each(order.items, function(itemIndex, item) {
                                    orderDetails.append('<strong>Product:</strong> ' + item.product.title + 
                                        ' (Qty: ' + item.quantity + ')<br>' +
                                        '<strong>Price:</strong> $' + item.product.price + '<br><br>');
                                });
                                
                                // Add address details
                                orderDetails.append('<strong>Address:</strong><br>' +
                                    order.address.streetAddress + '<br>' +
                                    (order.address.apartment ? order.address.apartment + '<br>' : '') +
                                    order.address.city + ', ' + order.address.province + '<br>' +
                                    order.address.postalCode + '<br>' +
                                    order.address.country + '<br><br>'
                                );
                                
                                // Add payment details
                                orderDetails.append('<strong>Payment:</strong><br>' +
                                    '<strong>Card Holder Name:</strong> ' + order.payment.cardHolderName + '<br>' +
                                    '<strong>Card Number:</strong> ' + order.payment.cardNumber + '<br>' +
                                    '<strong>Expiration Date:</strong> ' + order.payment.expirationDate + '<br>' +
                                    '<strong>CVV:</strong> ' + order.payment.cvv
                                );
                                
                                // Append the order details to the order item
                                orderItem.append(orderDetails);
                                
                                // Append the order item to the main section
                                $('.admin-main').append(orderItem);
                                
                                // Toggle details on click
                                orderItem.click(function() {
                                    $(this).find('.order-details').slideToggle();
                                });
                            });
                        } else {
                            $('.admin-main').append('<p>No orders found.</p>');
                        }
                    },
                    error: function() {
                        $('.admin-main').append('<p>An error occurred while fetching the orders.</p>');
                    }
                });
            }

            // Call the function to fetch orders when the document is ready
            fetchOrders();
        });
    </script>
</head>
<body>
<%@ include file="header.html" %>

<main class="admin-main">
    <h2>Sales History</h2>
    <!-- Orders will be displayed here -->
</main>

</body>
</html>
