<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Mis Cuentas</title>
</head>
<body>
<h2>Bienvenido, ${usuario.nombre}</h2>
<h3>Mis Cuentas</h3>
<table border="1">
    <tr>
        <th>Nombre</th>
        <th>Número de Cuenta</th>
        <th>Balance</th>
        <th>Acciones</th>
    </tr>
    <c:forEach var="cuenta" items="${cuentas}">
        <tr>
            <td>${cuenta.nombre}</td>
            <td>${cuenta.numeroCuenta}</td>
            <td>${cuenta.balance}</td>
            <td>
                <form action="ingreso" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="create">
                    <input type="hidden" name="cuentaId" value="${cuenta.id}">
                    <input type="number" name="valor" placeholder="Valor" required>
                    <input type="text" name="concepto" placeholder="Concepto" required>
                    <input type="text" name="categoria" placeholder="Categoría">
                    <button type="submit">Ingresar</button>
                </form>
                <form action="egreso" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="create">
                    <input type="hidden" name="cuentaId" value="${cuenta.id}">
                    <input type="number" name="valor" placeholder="Valor" required>
                    <input type="text" name="concepto" placeholder="Concepto" required>
                    <input type="text" name="categoria" placeholder="Categoría">
                    <button type="submit">Egresar</button>
                </form>
                <form action="transferencia" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="create">
                    <input type="hidden" name="cuentaOrigenId" value="${cuenta.id}">
                    <input type="number" name="cuentaDestinoId" placeholder="ID Destino" required>
                    <input type="number" name="valor" placeholder="Valor" required>
                    <input type="text" name="concepto" placeholder="Concepto" required>
                    <button type="submit">Transferir</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<br>
<a href="crearCuenta.jsp">Crear Nueva Cuenta</a> <!-- Enlace para crear una nueva cuenta -->
<br><br>
<a href="index.jsp">Volver a inicio</a> | <a href="logout.jsp">Cerrar sesión</a>
</body>
</html>
