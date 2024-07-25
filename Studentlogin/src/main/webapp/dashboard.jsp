<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Dashboard</title>
<link rel="stylesheet" href="styles.css">
</head>
<body>
  <div class="container">
    <h2>Welcome to Your Dashboard</h2>
    <p>Hello, <%= session.getAttribute("fullname") %>!</p>
    <p>Your username: <%= session.getAttribute("username") %></p>
    <a href="logout">Logout</a>
  </div>
</body>
</html>
