<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="manager.ProductManager, dao.Product, java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Stanford shop</title>
</head>
<body>
<h1>Student store</h1>

<%!
	String getRef(String id, String name){
		return "<li> <a href=\"show-product.jsp?id=" + id + "\"> " + name + " </a> </li> <br>";
	}
%>


<ul>
	<%
		ProductManager manager = (ProductManager)application.getAttribute("productManager");
		List<Product> products = manager.getList();
		for(Product p: products){
			out.println(getRef(p.getID(), p.getName()));
		}
	%>
</ul>

</body>
</html>