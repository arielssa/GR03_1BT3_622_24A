<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
  String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html>
<head>
  <title>Mis Cuentas</title>
  <link rel="stylesheet" type="text/css" href="styles/cuenta.css">
</head>
<body class="main-body">

<h2 class="welcome-message">Bienvenido, ${usuario.nombre}</h2>
<h3 class="section-title">Mis Cuentas</h3>
<table class="account-table" border="1">
  <tr class="table-header">
    <th class="header-id">Id</th>
    <th class="header-name">Nombre</th>
    <th class="header-account-number">Número de Cuenta</th>
    <th class="header-balance">Balance</th>
    <th class="header-actions">Acciones</th>
  </tr>
  <c:forEach var="cuenta" items="${cuentas}">
    <tr class="account-row">
      <td class="account-id">${cuenta.id}</td>
      <td class="account-name">${cuenta.nombre}</td>
      <td class="account-number">${cuenta.numeroCuenta}</td>
      <td class="account-balance">${cuenta.balance}</td>
      <td class="account-actions">
        <form action="detalleCuenta" method="get" class="details-form">
          <input type="hidden" name="cuentaId" value="${cuenta.id}">
          <button type="submit" class="details-button">Ver Detalles</button>
        </form>

        <form action="ingreso" method="post" class="income-form">
          <input type="hidden" name="action" value="create">
          <input type="hidden" name="cuentaId" value="${cuenta.id}">
          <input type="number" name="valor" placeholder="Valor" required class="input-value">
          <input type="text" name="concepto" placeholder="Concepto" required class="input-concept">
          <select name="etiqueta" required class="input-label">
            <option value="">Selecciona una etiqueta</option>
            <c:forEach var="etiqueta" items="${etiquetas}">
              <option value="${etiqueta.nombre}">${etiqueta.nombre}</option>
            </c:forEach>
          </select>
          <button type="submit" class="income-button">Ingresar</button>
        </form>

        <form action="egreso" method="post" class="expense-form">
          <input type="hidden" name="action" value="create">
          <input type="hidden" name="cuentaId" value="${cuenta.id}">
          <input type="number" name="valor" placeholder="Valor" required class="input-value">
          <input type="text" name="concepto" placeholder="Concepto" required class="input-concept">
          <select name="etiqueta" required class="input-label">
            <option value="">Selecciona una etiqueta</option>
            <c:forEach var="etiqueta" items="${etiquetas}">
              <option value="${etiqueta.nombre}">${etiqueta.nombre}</option>
            </c:forEach>
          </select>
          <button type="submit" class="expense-button">Egresar</button>
        </form>

        <form action="transferencia" method="post" class="transfer-form">
          <input type="hidden" name="action" value="create">
          <input type="hidden" name="cuentaOrigenId" value="${cuenta.id}">
          <input type="number" name="cuentaDestinoId" placeholder="ID Destino" required class="input-destination-id">
          <input type="number" name="valor" placeholder="Valor" required class="input-value">
          <input type="text" name="concepto" placeholder="Concepto" required class="input-concept">
          <select name="etiqueta" required class="input-label">
            <option value="">Selecciona una etiqueta</option>
            <c:forEach var="etiqueta" items="${etiquetas}">
              <option value="${etiqueta.nombre}">${etiqueta.nombre}</option>
            </c:forEach>
          </select>
          <button type="submit" class="transfer-button">Transferir</button>
        </form>
      </td>
    </tr>
  </c:forEach>
</table>
<br>
<h3 class="section-title">Crear Nueva Cuenta</h3>
<form action="cuenta" method="post" class="create-account-form">
  <input type="hidden" name="action" value="createAccount">
  <input type="text" name="nombre" placeholder="Nombre de la cuenta" required class="input-account-name">
  <input type="text" name="numeroCuenta" placeholder="Número de cuenta" required class="input-account-number">
  <input type="number" name="balance" placeholder="Balance inicial" required class="input-balance">
  <button type="submit" class="create-account-button">Crear Cuenta</button>
</form>
<br>
<h3 class="section-title">Ingresar Nueva Etiqueta</h3>
<form action="cuenta" method="post" class="create-tag-form">
  <input type="hidden" name="action" value="createTag">
  <input type="text" name="nombre" placeholder="Nombre de la etiqueta" required class="input-tag-name">
  <button type="submit" class="create-account-button">Agregar Etiqueta</button>
</form>

<br>
<div class="navigation-links">
  <a href="index.jsp" class="nav-link">Volver a inicio</a> |
  <a href="logout.jsp" class="nav-link">Cerrar sesión</a>
</div>
</body>
</html>
