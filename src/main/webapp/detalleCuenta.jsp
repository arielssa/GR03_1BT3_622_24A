<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Detalle de la Cuenta</title>
    <link rel="stylesheet" type="text/css" href="styles/detalleCuenta.css">
</head>
<body id="cuerpo-detalle-cuenta">

<!-- Título de la página -->
<h2 id="titulo-detalles-cuenta">Detalles de la Cuenta</h2>

<!-- Mostrar los detalles de la cuenta -->
<section id="informacion-cuenta">
    <p class="detalle-cuenta"><strong>ID de la cuenta:</strong> ${cuentaId}</p>
    <p class="detalle-cuenta"><strong>Nombre de la cuenta:</strong> ${cuenta.nombre}</p>
    <p class="detalle-cuenta"><strong>Número de cuenta:</strong> ${cuenta.numeroCuenta}</p>
    <p class="detalle-cuenta"><strong>Balance:</strong> ${cuenta.balance}</p>
    <p class="detalle-cuenta"><strong>Balance Límite:</strong> ${cuenta.balanceLimite}</p>
</section>

<br>
<form action="cuenta" method="post" id="form-actualizar-balance" class="formulario-detalle">
    <input type="hidden" name="action" value="actualizarBalanceLimite">
    <input type="hidden" name="cuentaId" value="${cuenta.id}">
    <input type="number" name="balanceLimite" placeholder="Balance Limite" required class="input-balance-limite">
    <button type="submit" class="boton-actualizar">Actualizar Balance Limite</button>
</form>

<!-- Formulario para ver todas las transacciones sin filtros -->
<h3 class="subtitulo">Ver Todas las Transacciones</h3>
<form action="movimientos" method="get" id="form-ver-todas-transacciones" class="formulario-detalle">
    <input type="hidden" name="cuentaId" value="${cuenta.id}">
    <button type="submit" name="action" value="todas" class="boton-todas-transacciones">Ver Todas las Transacciones</button>
</form>

<!-- Formularios para consultar ingresos, egresos, transferencias o todas las transacciones -->
<h3 class="subtitulo">Consultar Movimientos</h3>

<!-- Formulario para consultar por Fecha -->
<form action="movimientos" method="get" id="form-consulta-fecha" class="formulario-detalle">
    <input type="hidden" name="cuentaId" value="${cuenta.id}">

    <label for="fechaInicio" class="etiqueta-fecha">Fecha de Inicio:</label>
    <input type="date" id="fechaInicio" name="fechaInicio" required class="campo-fecha">

    <label for="fechaFin" class="etiqueta-fecha">Fecha Fin:</label>
    <input type="date" id="fechaFin" name="fechaFin" required class="campo-fecha">

    <br>
    <div id="botones-consulta-fecha" class="botones-consulta">
        <button type="submit" name="action" value="ingresosFecha" class="boton-consulta">Ver Ingresos por Fecha</button>
        <button type="submit" name="action" value="egresosFecha" class="boton-consulta">Ver Egresos por Fecha</button>
        <button type="submit" name="action" value="transferenciasFecha" class="boton-consulta">Ver Transferencias por Fecha</button>
    </div>
</form>

<br>

<!-- Formulario para consultar por Etiqueta -->
<form action="movimientos" method="get" id="form-consulta-etiqueta" class="formulario-detalle">
    <input type="hidden" name="cuentaId" value="${cuenta.id}">

    <label for="etiqueta" class="etiqueta-seleccion">Etiqueta:</label>
    <select id="etiqueta" name="etiqueta" required class="select-etiqueta">
        <option value="">Selecciona una etiqueta</option>
        <c:forEach var="etiqueta" items="${etiquetas}">
            <option value="${etiqueta.nombre}">${etiqueta.nombre}</option>
        </c:forEach>
    </select>

    <br>
    <div id="botones-consulta-etiqueta" class="botones-consulta">
        <button type="submit" name="action" value="ingresosEtiqueta" class="boton-consulta">Ver Ingresos por Etiqueta</button>
        <button type="submit" name="action" value="egresosEtiqueta" class="boton-consulta">Ver Egresos por Etiqueta</button>
    </div>
</form>

<br>

<!-- Enlace para volver a la lista de cuentas -->
<div id="enlace-volver">
    <a href="cuenta" class="enlace-volver">Volver a Mis Cuentas</a>
</div>
<br>
</body>
</html>
