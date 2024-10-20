<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>Mis Cuentas</title>
  <style>
    form {
      display: block;
      margin-bottom: 10px;
    }
  </style>
</head>
<body>
<h2>Bienvenido, ${usuario.nombre}</h2>
<h3>Mis Cuentas</h3>
<table border="1">
  <tr>
    <th>Id</th>
    <th>Nombre</th>
    <th>Número de Cuenta</th>
    <th>Balance</th>
    <th>Acciones</th>
  </tr>
  <c:forEach var="cuenta" items="${cuentas}">
    <tr>
      <td>${cuenta.id}</td>
      <td>${cuenta.nombre}</td>
      <td>${cuenta.numeroCuenta}</td>
      <td>${cuenta.balance}</td>
      <td>
        <form action="detalleCuenta" method="get">
          <input type="hidden" name="cuentaId" value="${cuenta.id}">
          <button type="submit">Ver Detalles</button>
        </form>

        <form action="ingreso" method="post">
          <input type="hidden" name="action" value="create">
          <input type="hidden" name="cuentaId" value="${cuenta.id}">
          <input type="number" name="valor" placeholder="Valor" required>
          <input type="text" name="concepto" placeholder="Concepto" required>
          <input type="text" name="categoria" placeholder="Categoría">
          <button type="submit">Ingresar</button>
        </form>

        <form action="egreso" method="post">
          <input type="hidden" name="action" value="create">
          <input type="hidden" name="cuentaId" value="${cuenta.id}">
          <input type="number" name="valor" placeholder="Valor" required>
          <input type="text" name="concepto" placeholder="Concepto" required>
          <input type="text" name="categoria" placeholder="Categoría">
          <button type="submit">Egresar</button>
        </form>

        <form action="transferencia" method="post">
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

<h3>Crear Nueva Cuenta</h3>
<form action="cuenta" method="post">
  <input type="hidden" name="action" value="create">
  <input type="text" name="nombre" placeholder="Nombre de la cuenta" required>
  <input type="text" name="numeroCuenta" placeholder="Número de cuenta" required>
  <input type="number" name="balance" placeholder="Balance inicial" required>
  <button type="submit">Crear Cuenta</button>
</form>

<br>
<a href="index.jsp">Volver a inicio</a> | <a href="logout.jsp">Cerrar sesión</a>
</body>
</html>
