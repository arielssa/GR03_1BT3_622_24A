<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Iniciar Sesión</title>
    <link rel="stylesheet" type="text/css" href="styles/login.css">
</head>
<body id="login-body">

<!-- Encabezado principal con logo -->
<header class="header">
    <img src="img/logo.png" alt="Logo Monedero Virtual" class="logo">
    <h2 class="login-title">Iniciar Sesión</h2>
    <p class="login-subtitle">Accede a tu cuenta para administrar tus finanzas de manera segura.</p>
</header>

<!-- Formulario de inicio de sesión -->
<form action="usuario" method="post" id="login-form" class="form" onsubmit="return validateForm()">
    <input type="hidden" name="action" value="login">

    <label for="nombreUsuario" class="form-label">Nombre de Usuario:</label>
    <input type="text" name="nombreUsuario" id="nombreUsuario" class="form-input" required>
    <p id="username-error" class="error-message" style="display: none;">El nombre de usuario debe tener al menos 4 caracteres.</p>

    <label for="contrasena" class="form-label">Contraseña:</label>
    <input type="password" name="contrasena" id="contrasena" class="form-input" required>
    <p id="password-error" class="error-message" style="display: none;">La contraseña debe tener al menos 6 caracteres.</p>

    <button type="submit" class="submit-button">Iniciar Sesión</button>
</form>

<!-- Mensaje de error -->
<p id="error-message" class="error-message" style="display: none;">Nombre de usuario o contraseña incorrectos. Por favor, inténtalo nuevamente.</p>

<!-- Información adicional de registro -->
<section class="additional-info">
    <p class="register-link">¿No tienes una cuenta? <a href="register.jsp" class="link">Regístrate aquí</a> para empezar a gestionar tus finanzas.</p>
    <p class="home-link"><a href="index.jsp" class="link">Volver a inicio</a></p>
</section>

<script>
    // Función para obtener el valor de un parámetro de URL
    function getUrlParameter(name) {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(name);
    }

    // Verificar si el parámetro error es true y mostrar el mensaje de error
    document.addEventListener("DOMContentLoaded", function () {
        const error = getUrlParameter('error');
        if (error === 'true') {
            document.getElementById("error-message").style.display = "block";
        }
    });

    // Validaciones del formulario
    function validateForm() {
        let isValid = true;

        // Validación de nombre de usuario
        const username = document.getElementById("nombreUsuario").value;
        const usernameError = document.getElementById("username-error");
        if (username.length < 1) {
            usernameError.style.display = "block";
            isValid = false;
        } else {
            usernameError.style.display = "none";
        }

        // Validación de contraseña
        const password = document.getElementById("contrasena").value;
        const passwordError = document.getElementById("password-error");
        if (password.length < 1) {
            passwordError.style.display = "block";
            isValid = false;
        } else {
            passwordError.style.display = "none";
        }

        return isValid;
    }
</script>

</body>
</html>