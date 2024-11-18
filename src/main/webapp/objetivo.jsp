<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>Mis Objetivos de Ahorro</title>
  <link rel="stylesheet" type="text/css" href="styles/objetivos.css">
</head>
<body class="main-body">

<!-- Mostrar mensaje de progreso o error -->
<c:if test="${not empty mensaje}">
  <script>
    alert("${mensaje}");
  </script>
</c:if>
<c:if test="${not empty mensajeError}">
  <script>
    alert("${mensajeError}");
  </script>
</c:if>

<h2 class="welcome-message">Bienvenido, ${usuario.nombre}</h2>

<h3 class="section-title">Mis Objetivos de Ahorro</h3>
<table class="goal-table" border="1">
  <tr class="table-header">
    <th class="header-id">Id</th>
    <th class="header-description">Descripción</th>
    <th class="header-target-amount">Monto Objetivo</th>
    <th class="header-current-amount">Monto Actual</th>
    <th class="header-progress">Progreso (%)</th>
    <th class="header-remaining-amount">Monto Restante</th>
    <th class="header-actions">Acciones</th>
  </tr>
  <c:forEach var="objetivo" items="${objetivos}">
    <tr class="goal-row">
      <td class="goal-id">${objetivo.id}</td>
      <td class="goal-description">${objetivo.descripcion}</td>
      <td class="goal-target-amount">${objetivo.montoObjetivo}</td>
      <td class="goal-current-amount">${objetivo.montoActual}</td>

      <!-- Cálculo del progreso evitando división por cero -->
      <c:choose>
        <c:when test="${objetivo.montoObjetivo > 0}">
          <c:set var="progreso" value="${(objetivo.montoActual / objetivo.montoObjetivo) * 100}" />
        </c:when>
        <c:otherwise>
          <c:set var="progreso" value="0" />
        </c:otherwise>
      </c:choose>

      <!-- Cálculo del monto restante, con ajuste para valores negativos -->
      <c:set var="montoRestante" value="${objetivo.montoObjetivo - objetivo.montoActual}" />
      <c:if test="${montoRestante < 0}">
        <c:set var="montoRestante" value="0" />
      </c:if>

      <td class="goal-progress">${progreso}%</td>
      <td class="goal-remaining-amount">${montoRestante}</td>

      <td class="goal-actions">
        <!-- Formulario para ingresar dinero al objetivo -->
        <form action="objetivos" method="post" class="income-form">
          <input type="hidden" name="action" value="deposit">
          <input type="hidden" name="objetivoId" value="${objetivo.id}">
          <input type="number" name="monto" placeholder="Monto a ingresar" required class="input-amount">
          <button type="submit" class="income-button">Ingresar</button>
        </form>

        <!-- Formulario para retirar dinero del objetivo -->
        <form action="objetivos" method="post" class="withdraw-form">
          <input type="hidden" name="action" value="withdraw">
          <input type="hidden" name="objetivoId" value="${objetivo.id}">
          <input type="number" name="monto" placeholder="Monto a retirar" required class="input-amount">
          <button type="submit" class="withdraw-button">Retirar</button>
        </form>
      </td>
    </tr>
  </c:forEach>
</table>

<h3 class="section-title">Crear Nuevo Objetivo de Ahorro</h3>
<form action="objetivos" method="post" class="create-goal-form" onsubmit="return validateGoalCreation()">
  <input type="hidden" name="action" value="createGoal">
  <input type="text" name="descripcion" placeholder="Descripción del objetivo" required class="input-goal-description">
  <input type="number" name="montoObjetivo" placeholder="Monto Objetivo" required class="input-goal-target-amount" min="0">
  <input type="number" name="montoActual" placeholder="Monto Actual" required class="input-goal-current-amount" min="0">
  <button type="submit" class="create-goal-button">Crear Objetivo</button>
</form>

<div id="enlace-volver">
  <a href="cuenta" class="enlace-volver">Volver a Mis Cuentas</a>
</div>


<script>
  // Validar que los montos al crear el objetivo no sean negativos
  function validateGoalCreation() {
    const montoObjetivo = parseFloat(document.querySelector('input[name="montoObjetivo"]').value);
    const montoActual = parseFloat(document.querySelector('input[name="montoActual"]').value);

    if (montoObjetivo < 0 || montoActual < 0) {
      alert("Los montos no pueden ser negativos. Por favor, ingrese valores válidos.");
      return false;
    }

    if (montoActual > montoObjetivo) {
      alert("El monto actual no puede ser mayor que el monto objetivo. Ajuste los valores e inténtelo de nuevo.");
      return false;
    }

    return true; // Permitir el envío si todo está correcto
  }
</script>

</body>
</html>
