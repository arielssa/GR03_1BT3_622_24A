<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Registro de Usuario</title>
    <link rel="Stylesheet" type="text/css" href="styles/register.css">
</head>
<body id="cuerpo-registro">

<!-- Título de la página -->
<h2 id="titulo-registro">Registro de Usuario</h2>

<!-- Formulario de registro de usuario -->
<form action="usuario" method="post" id="formulario-registro">
    <input type="hidden" name="action" value="register">

    <!-- Campos de entrada para el formulario -->
    <label for="nombre" class="etiqueta-formulario">Nombre:</label>
    <input type="text" name="nombre" id="nombre" class="campo-entrada" required><br>

    <label for="nombreUsuario" class="etiqueta-formulario">Nombre de Usuario:</label>
    <input type="text" name="nombreUsuario" id="nombreUsuario" class="campo-entrada" required><br>

    <label for="contrasena" class="etiqueta-formulario">Contraseña:</label>
    <input type="password" name="contrasena" id="contrasena" class="campo-entrada" required><br>

    <!-- Botón de registro -->
    <button type="submit" id="boton-registrar">Registrarse</button>
</form>

<!-- Enlaces para iniciar sesión y volver a inicio -->
<p class="texto-enlace">¿Ya tienes una cuenta? <a href="login.jsp" class="enlace-inicio-sesion">Inicia sesión aquí</a>.</p>
<p class="texto-enlace"><a href="index.jsp" class="enlace-inicio">Volver a inicio</a></p>

</body>
</html>
