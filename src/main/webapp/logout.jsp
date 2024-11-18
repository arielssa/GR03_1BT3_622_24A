<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Cerrar la sesión del usuario
    session.invalidate();
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="refresh" content="3;url=index.jsp">
    <title>Cerrando sesión</title>
    <link rel="stylesheet" type="text/css" href="styles/logout.css">
</head>
<body>
<section class="logout-message">
    <h2>Se ha cerrado la sesión correctamente</h2>
    <p>Serás redirigido a la página de inicio en unos momentos.</p>
</section>

<script>
    // Redirección en caso de que el meta refresh no funcione
    setTimeout(function() {
        window.location.href = "index.jsp";
    }, 3000);
</script>
</body>
</html>