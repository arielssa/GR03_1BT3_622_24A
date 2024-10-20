<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Detalle de la Cuenta</title>
</head>
<body>
<h2>Detalles de la Cuenta</h2>

<!-- Mostrar los detalles de la cuenta -->
<p><strong>ID de la cuenta:</strong> ${cuentaId}</p>
<p><strong>Nombre de la cuenta:</strong> ${cuenta.nombre}</p>
<p><strong>Número de cuenta:</strong> ${cuenta.numeroCuenta}</p>
<p><strong>Balance:</strong> ${cuenta.balance}</p>

<!-- Formularios para seleccionar fechas y consultar ingresos, egresos y transferencias -->
<h3>Consultar Movimientos por Fecha</h3>
<form action="movimientos" method="get">
    <input type="hidden" name="cuentaId" value="${cuenta.id}">

    <!-- Selección de rango de fechas -->
    <label for="fechaInicio">Fecha de Inicio:</label>
    <input type="date" id="fechaInicio" name="fechaInicio" required>

    <label for="fechaFin">Fecha Fin:</label>
    <input type="date" id="fechaFin" name="fechaFin" required>

    <br><br>
    <!-- Botón para consultar ingresos -->
    <button type="submit" name="action" value="ingresos">Ver Ingresos</button>

    <!-- Botón para consultar egresos -->
    <button type="submit" name="action" value="egresos">Ver Egresos</button>

    <!-- Botón para consultar transferencias -->
    <button type="submit" name="action" value="transferencias">Ver Transferencias</button>
</form>

<br>
<a href="cuenta">Volver a Mis Cuentas</a>
</body>
</html>
