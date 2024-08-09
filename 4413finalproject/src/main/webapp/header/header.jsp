<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<header>
    <div class="promo-banner">Summer Sale 30% OFF New User</div>
    <div class="navbar">
        <div class="logo">UNISphere</div>
        <form class="search-form" method="get" action="catalogue.jsp">
            <input type="text" name="search" placeholder="Search" class="search-bar">
        </form>
        <div class="icons">
		    <a href="profile.jsp" class="icon user-icon">
		        <i class="fas fa-user"></i>
		    </a>
		    <a href="wishlist.jsp" class="icon wishlist-icon">
		        <i class="fas fa-heart"></i>
		    </a>
		    <a href="cart.jsp" class="icon cart-icon">
		        <i class="fas fa-shopping-cart"></i>
		    </a>
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