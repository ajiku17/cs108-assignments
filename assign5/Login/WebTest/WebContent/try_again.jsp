<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Incorrect login</title>
</head>
<body>
<h1>Please Try again</h1>
<p>Either your username or password was incorrect</p>

<form action=LoginServlet method="post">

	<label for="username"><b>Username</b></label>
    <input id="username" type="text"
           placeholder="Enter Username" name="username" required>
    <br>

    <label for="password"><b>Password</b></label>
    <input id="password" type="password"
           placeholder="Enter Password" name="password" required>
    <br>

    <button type="submit">Login</button>
    
    <a href="acc_create.jsp">Create New Account</a> 

</form>

</body>
</html>