$(document).ready(function() {
          // Get the current user information from the session
          $.ajax({
              url: 'http://localhost:8080/unisphereREST/rest/Auth/session', 
              method: 'GET',
              dataType: 'json',
              xhrFields: {
                  withCredentials: true 
              },
              success: function(response) {
                  $('#usernameDisplay').text(response.username);
                  $('#firstName').val(response.firstName);
                  $('#lastName').val(response.lastName);
                  $('#username').val(response.username);

                  // Set the address fields
                  $('#streetAddress').val(response.address.streetAddress);
                  $('#apt').val(response.address.apartment);
                  $('#city').val(response.address.city);
                  $('#province').val(response.address.province);
                  $('#postalCode').val(response.address.postalCode);
                  $('#country').val(response.address.country);

                  // Set the payment fields
                  $('#cardHolderName').val(response.payment.cardHolderName);
                  $('#cardNumber').val(response.payment.cardNumber);
                  $('#expiry').val(response.payment.expirationDate);
                  $('#cvv').val(response.payment.cvv);
              },
              error: function(jqXHR, textStatus, errorThrown) {
                  console.error("Error fetching session data:", textStatus, errorThrown);
                  window.location.href = 'signin.jsp';
              }
          });

          // Handle the update button click
          $('#updateButton').click(function() {
              var updatedUser = {
                  firstName: $('#firstName').val(),
                  lastName: $('#lastName').val(),
                  username: $('#username').val()
              };

              $.ajax({
                  url: 'http://localhost:8080/unisphereREST/rest/Auth/updateUser',
                  method: 'POST',
                  contentType: 'application/json',
                  data: JSON.stringify(updatedUser),
                  success: function(response) {
                      $('#usernameDisplay').text(updatedUser.firstName);
                      $('#updateMessage').show().delay(3000).fadeOut();

                      // Update the username display
                      $('#usernameDisplay').text(updatedUser.firstName);
                      $('#username').val(updatedUser.username);
                  },
                  error: function(jqXHR, textStatus, errorThrown) {
                      console.error("Error updating user info:", textStatus, errorThrown);
                  }
              });
          });

          // Handle the update address button click
          $('#updateAddressButton').click(function() {
              var updatedAddress = {
              	streetAddress: $('#streetAddress').val(),
                  city: $('#city').val(),
                  province: $('#province').val(),
                  postalCode: $('#postalCode').val(),
                  country: $('#country').val()
              };

              $.ajax({
                  url: "http://localhost:8080/unisphereREST/rest/Users/updateAddress/" + $('#username').val(),
                  method: 'PUT',
                  contentType: 'application/json',
                  data: JSON.stringify(updatedAddress),
                  success: function(response) {
                      $('#addressUpdateMessage').show().delay(3000).fadeOut();
                  },
                  error: function(jqXHR, textStatus, errorThrown) {
                      console.error("Error updating address:", textStatus, errorThrown);
                  }
              });
          });

          // Handle the update payment button click
          $('#updatePaymentButton').click(function() {
              var updatedPayment = {
                  cardHolderName: $('#cardHolderName').val(),
                  cardNumber: $('#cardNumber').val(),
                  expirationDate: $('#expiry').val(),
                  cvv: $('#cvv').val()
              };

              $.ajax({
                  url: 'http://localhost:8080/unisphereREST/rest/Users/updatePayment/' + $('#username').val(),
                  method: 'PUT',
                  contentType: 'application/json',
                  data: JSON.stringify(updatedPayment),
                  success: function(response) {
                      $('#paymentUpdateMessage').show().delay(3000).fadeOut();
                  },
                  error: function(jqXHR, textStatus, errorThrown) {
                      console.error("Error updating payment:", textStatus, errorThrown);
                  }
              });
          });

          // Handle logout button click
          $('#logoutButton').click(function() {
              $.ajax({
                  url: 'http://localhost:8080/unisphereREST/rest/Auth/logout', 
                  method: 'POST',
                  xhrFields: {
                      withCredentials: true 
                  },
                  success: function(response) {
                      window.location.href = 'home.jsp';
                  },
                  error: function(jqXHR, textStatus, errorThrown) {
                      console.error("Error logging out:", textStatus, errorThrown);
                  }
              });
          });
      });

	  function showSection(section, element) {
	      document.querySelectorAll('.section').forEach(section => {
	          section.style.display = 'none';
	      });

	      document.getElementById(section).style.display = 'block';

	      document.querySelectorAll('.nav-panel a').forEach(navItem => {
	          navItem.classList.remove('active');
	      });

	      element.classList.add('active');
	      if (section === 'orders') {
	          fetchOrders();
	      }
	  }

	  function fetchOrders() {
	      $.ajax({
	          url: 'http://localhost:8080/unisphereREST/rest/Orders/getOrdersByUsername/' + $('#username').val(),
	          method: 'GET',
	          dataType: 'json',
	          success: function(orders) {
	              $('#orderList').empty();  // Clear the existing order list

	              if (orders.length === 0) {
	                  $('#orderList').append('<p>No orders found.</p>');
	                  return;
	              }

	              orders.forEach(function(order) {
	                  $('#orderList').append(
	                      `<li><a href="#" onclick="showOrderDetails(${order.id})">Order Number: ${order.id} - Total: $${order.cart.totalPrice}</a></li>`
	                  );
	              });
	          },
	          error: function(jqXHR, textStatus, errorThrown) {
	              console.error("Error fetching orders:", textStatus, errorThrown);
	              $('#orderList').append('<p>Error loading order history. Please try again later.</p>');
	          }
	      });
	  }

	  
	  function showOrderDetails(orderId) {
	      $.ajax({
	          url: 'http://localhost:8080/unisphereREST/rest/Orders/getOrderById/' + orderId,
	          method: 'GET',
	          dataType: 'json',
	          success: function(order) {
	              $('#orderDetails').empty();  // Clear previous order details

	              var orderHtml = `
	                  <h3>Order Number: ${order.id}</h3>
	                  <p><strong>Date:</strong> ${order.date}</p>
	                  <p><strong>Total:</strong> $${order.cart.totalPrice}</p>
	                  <h4>Items:</h4>
	                  <ul>`;

	              order.cart.items.forEach(function(item) {
	                  orderHtml += `<li>${item.product.title} -   $${item.product.price} -   quantity: ${item.quantity}</li>`;
	              });

	              orderHtml += `
	                  </ul>
	                  <button type="button" onclick="backToOrderList()">Back to Order History</button>
	              `;

	              $('#orderDetails').append(orderHtml);

	              // Hide the order list and show the order details
	              $('#orderList').hide();
	              $('#orderDetails').show();
	          },
	          error: function(jqXHR, textStatus, errorThrown) {
	              console.error("Error fetching order details:", textStatus, errorThrown);
	              $('#orderDetails').append('<p>Error loading order details. Please try again later.</p>');
	          }
	      });
	  }


	  function backToOrderList() {
	      $('#orderDetails').hide();
	      $('#orderList').show();
	  }

