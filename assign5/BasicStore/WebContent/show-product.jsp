<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="manager.ProductManager, dao.Product" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%
	String id = request.getParameter("id");
	ProductManager manager = (ProductManager)application.getAttribute("productManager");
	Product p = manager.getProduct(id);
	String title = p.getName();
%>
<title>Product view</title>
</head>
<body>
<h1><%=title%></h1>

<img src="<%="store-images/" + p.getImageFileName()%>" alt="something went wrong">
<hr>
<p><%= ("$" + p.getPrice()) %></p>

<form action=ShoppingCartServlet method="post">
    <input name="productID" type="hidden" value="<%=p.getID()%>"/>
    <button type="submit">Add to cart</button>
</form>


</body>
</html>