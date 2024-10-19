<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Iniciar Sesión</title>
</head>
<body>
<h2>Iniciar Sesión</h2>
<form action="usuario" method="post">
    <input type="hidden" name="action" value="login">
    <label for="nombreUsuario">Nombre de Usuario:</label>
    <input type="text" name="nombreUsuario" id="nombreUsuario" required><br><br>

    <label for="contrasena">Contraseña:</label>
    <input type="password" name="contrasena" id="contrasena" required><br><br>

    <button type="submit">Iniciar Sesión</button>
</form>
<c:if test="${param.error}">
    <p style="color:red;">Nombre de usuario o contraseña incorrectos.</p>
</c:if>
<p>¿No tienes una cuenta? <a href="register.jsp">Regístrate aquí</a>.</p>
<p><a href="index.jsp">Volver a inicio</a></p>
</body>
</html>
