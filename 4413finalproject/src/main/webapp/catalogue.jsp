<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Catalogue</title>
<link rel="stylesheet" href="specificItem.css">
</head>
<body>
<header>
        <div class="promo-banner">Summer Sale 30% OFF New User</div>
        <div class="navbar">
            <div class="logo">UNISphere</div>
            <input type="text" placeholder="Search" class="search-bar">
            <div class="icons">
                <button class="icon user-icon"></button>
                <button class="icon wishlist-icon"></button>
                <button class="icon cart-icon"></button>
            </div>
        </div>
        <nav>
            <form class="nav-form" method="get" action="catalogue.jsp">
			  <ul class="nav-links">
			    <li><button type="submit" name="category" value="Sale" class="nav-button">Sale</button></li>
			    <li><button type="submit" name="category" value="Clothing" class="nav-button">Clothing</button></li>
			    <li><button type="submit" name="category" value="Supplies" class="nav-button">Supplies</button></li>
			    <li><button type="submit" name="category" value="Books" class="nav-button">Books</button></li>
			    <li><button type="submit" name="category" value="Tech" class="nav-button">Tech</button></li>
			    <li><button type="submit" name="category" value="Offers" class="nav-button">Offers</button></li>
			  </ul>
			</form>

        </nav>
</header>
    
    <main>
    
    <h2>${param.category}</h2>
    
    
    </main>

</body>
</html>