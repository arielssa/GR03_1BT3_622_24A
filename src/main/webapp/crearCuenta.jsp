<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Crear Nueva Cuenta</title>
</head>
<body>
<h3>Crear Nueva Cuenta</h3>
<form action="cuenta" method="post">
    <input type="hidden" name="action" value="create">
    <input type="text" name="nombre" placeholder="Nombre de la cuenta" required>
    <input type="text" name="numeroCuenta" placeholder="Número de cuenta" required>
    <input type="number" name="balance" placeholder="Balance inicial" required>
    <button type="submit">Crear Cuenta</button>
</form>

<br>
<a href="tablero.jsp">Volver al Tablero</a> <!-- Enlace para volver al tablero de cuentas -->
</body>
</html>
