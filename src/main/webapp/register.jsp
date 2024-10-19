<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Registro de Usuario</title>
</head>
<body>
<h2>Registro de Usuario</h2>
<form action="usuario" method="post">
    <input type="hidden" name="action" value="register">
    <label for="nombre">Nombre:</label>
    <input type="text" name="nombre" id="nombre" required><br><br>

    <label for="nombreUsuario">Nombre de Usuario:</label>
    <input type="text" name="nombreUsuario" id="nombreUsuario" required><br><br>

    <label for="contrasena">Contraseña:</label>
    <input type="password" name="contrasena" id="contrasena" required><br><br>

    <button type="submit">Registrarse</button>
</form>
<p>¿Ya tienes una cuenta? <a href="login.jsp">Inicia sesión aquí</a>.</p>
<p><a href="index.jsp">Volver a inicio</a></p>
</body>
</html>
