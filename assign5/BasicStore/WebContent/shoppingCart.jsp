<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.Product, java.util.*, manager.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Your Cart</title>
</head>
<body>
<h1>Shopping Cart</h1>
<%
  double total = 0; 
%>
	
<form action="ShoppingCartServlet" method="post">
        <%
        	ProductManager manager = (ProductManager)application.getAttribute("productManager");
        	ShoppingCart cart = (ShoppingCart)session.getAttribute("shoppingCart");
        	if(cart != null){
	            for (int i = 0; i < cart.getProducts().size(); i++) {
	            	Product p = cart.getProducts().get(i);
	            	total += (p.getPrice() * cart.getFrequency(p));
	                out.print("<li> <input type =number value=" + cart.getFrequency(p) + " name=" + p.getID() + ">"
	                        + p.getName() + ", $" + p.getPrice() + "</li>");
	            }
        	}
        %>
    Total: $<%= String.format("%.2f", total)%>
    
	<input type="submit" value="Update Cart"/>    
</form>

<a href="/BasicStore">Continue shopping</a>
</body>
</html>