<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${tipo}</title>
</head>
<body>
<h2>${tipo} en el rango de fechas seleccionado</h2>
<table border="1">
    <tr>
        <th>Fecha</th>
        <th>Valor</th>
        <th>Concepto</th>
        <th>Categoría</th>
    </tr>
    <c:forEach var="movimiento" items="${movimientos}">
        <tr>
            <td>${movimiento.fecha}</td>
            <td>${movimiento.valor}</td>
            <td>${movimiento.concepto}</td>
            <td>${movimiento.categoria}</td>
        </tr>
    </c:forEach>
</table>

<br>
<a href="detalleCuenta?cuentaId=${cuentaId}">Volver a Detalles de la Cuenta</a>
</body>
</html>
