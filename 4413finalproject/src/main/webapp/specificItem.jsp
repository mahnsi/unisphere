<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Specific Item Page</title>
    <link rel="stylesheet" href="specificItem.css">
</head>

<body>
<%@ include file="header/header.jsp" %>

<main>
        <div class="item-page">
            <div class="item-image">Item Image</div>
            <div class="item-details">
                <h1>Item Name</h1>
                <p>Item details</p>
                <div class="rating">★★★★★ 10</div>
                <div>
                <select>
                    <option value="size">Select a size</option>
                    <!-- Add more options as needed -->
                </select>                  
                </div>
                <div>
                <button class="addToBag">Add to Bag</button>            
                <button class="wishlistButton">❤</button>
            </div>
            </div>
        </div>
    </main>

</body>
</html>