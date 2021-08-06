<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Account</title>
</head>
<body>

<h1>The name is already in use</h1>

<form action=CreateUserServlet method="post">
	<label for="username"><b>Username</b></label>
    <input id="username" type="text"
           placeholder="Enter Username" name="username" required>
    <br>

    <label for="password"><b>Password</b></label>
    <input id="password" type="password"
           placeholder="Enter Password" name="password" required>
    <br>

    <button type="submit">Register</button> 

</form>

</body>
</html>