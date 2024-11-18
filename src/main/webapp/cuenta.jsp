<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Mis Cuentas</title>
    <link rel="stylesheet" type="text/css" href="styles/cuenta.css">
    <script>
        // Reemplaza tildes y permite solo caracteres alfanuméricos en los nombres
        function normalizeInput(inputField) {
            let value = inputField.value;
            value = value.replace(/[áàäâ]/g, "a")
                .replace(/[éèëê]/g, "e")
                .replace(/[íìïî]/g, "i")
                .replace(/[óòöô]/g, "o")
                .replace(/[úùüû]/g, "u")
                .replace(/[^a-zA-Z0-9 ]/g, ""); // Solo alfanuméricos
            inputField.value = value;
        }

        // Validación para evitar valores negativos y cero
        function validateNegativeNumber(inputField) {
            if (inputField.value < 0) {
                alert("El valor no puede ser negativo.");
                inputField.value = "";
            }
        }

        function validateNumber(inputField) {
            if (inputField.value <= 0) {
                alert("El valor no puede ser cero o negativo.");
                inputField.value = "";
            }
        }
    </script>
</head>
<body class="main-body">
<%
    if ("SobrepasoLimite".equals(error)) {
%>
<script>
    alert("Alerta: Ha sobrepasado el límite configurado de balance. Evite realizar más egresos.");
</script>
<%
} else if ("EstaEnLimite".equals(error)) {
%>
<script>
    alert("Alerta: Esta en el límite configurado de balance.");
</script>
<%
} else if ("EstaCercaLimite".equals(error)) {
%>
<script>
    alert("Alerta: Esta cerca del límite configurado de balance.");
</script>
<%
    }
%>
<h2 class="welcome-message">Bienvenido, ${usuario.nombre}</h2>
<h3 class="section-title">Mis Cuentas</h3>
<table class="account-table" border="1">
    <tr class="table-header">
        <th class="header-id">Id</th>
        <th class="header-name">Nombre</th>
        <th class="header-account-number">Número de Cuenta</th>
        <th class="header-balance">Balance</th>
        <th class="header-balance-limit">Balance Límite</th>
        <th class="header-actions">Acciones</th>
    </tr>
    <c:forEach var="cuenta" items="${cuentas}">
        <tr class="account-row">
            <td class="account-id">${cuenta.id}</td>
            <td class="account-name">${cuenta.nombre}</td>
            <td class="account-number">${cuenta.numeroCuenta}</td>
            <td class="account-balance">${cuenta.balance}</td>
            <td class="account-balance-limit">${cuenta.balanceLimite}</td>
            <td class="account-actions">
                <form action="detalleCuenta" method="get" class="details-form">
                    <input type="hidden" name="cuentaId" value="${cuenta.id}">
                    <button type="submit" class="details-button">Ver Detalles</button>
                </form>

                <div id="transacciones-${cuenta.id}" style="margin-top: 10px;">
                    <c:if test="${cuenta.bloqueada}">
                        <p style="color: red;">Cuenta bloqueada. No se pueden realizar transacciones.</p>
                    </c:if>

                    <c:if test="${!cuenta.bloqueada}">
                        <!-- Formulario para Ingreso -->
                        <form action="ingreso" method="post" class="income-form">
                            <input type="hidden" name="action" value="create">
                            <input type="hidden" name="cuentaId" value="${cuenta.id}">
                            <input type="number" name="valor" placeholder="Valor" required class="input-value"
                                   min="1" oninput="validateNumber(this)">
                            <input type="text" name="concepto" placeholder="Concepto" required class="input-concept"
                                   oninput="normalizeInput(this)">
                            <select name="etiqueta" required class="input-label">
                                <option value="">Selecciona una etiqueta</option>
                                <c:forEach var="etiqueta" items="${etiquetas}">
                                    <option value="${etiqueta.nombre}">${etiqueta.nombre}</option>
                                </c:forEach>
                            </select>
                            <button type="submit" class="income-button">Ingresar</button>
                        </form>

                        <!-- Formulario para Egreso -->
                        <form action="egreso" method="post" class="expense-form">
                            <input type="hidden" name="action" value="create">
                            <input type="hidden" name="cuentaId" value="${cuenta.id}">
                            <input type="number" name="valor" placeholder="Valor" required class="input-value"
                                   min="1" oninput="validateNumber(this)">
                            <input type="text" name="concepto" placeholder="Concepto" required class="input-concept"
                                   oninput="normalizeInput(this)">
                            <select name="etiqueta" required class="input-label">
                                <option value="">Selecciona una etiqueta</option>
                                <c:forEach var="etiqueta" items="${etiquetas}">
                                    <option value="${etiqueta.nombre}">${etiqueta.nombre}</option>
                                </c:forEach>
                            </select>
                            <button type="submit" class="expense-button">Egresar</button>
                        </form>

                        <!-- Formulario para Transferencia -->
                        <form action="transferencia" method="post" class="transfer-form">
                            <input type="hidden" name="action" value="create">
                            <input type="hidden" name="cuentaOrigenId" value="${cuenta.id}">
                            <input type="number" name="cuentaDestinoId" placeholder="ID Destino" required class="input-destination-id" min="1">
                            <input type="number" name="valor" placeholder="Valor" required class="input-value"
                                   min="1" oninput="validateNumber(this)">
                            <input type="text" name="concepto" placeholder="Concepto" required class="input-concept"
                                   oninput="normalizeInput(this)">
                            <select name="etiqueta" required class="input-label">
                                <option value="">Selecciona una etiqueta</option>
                                <c:forEach var="etiqueta" items="${etiquetas}">
                                    <option value="${etiqueta.nombre}">${etiqueta.nombre}</option>
                                </c:forEach>
                            </select>
                            <button type="submit" class="transfer-button">Transferir</button>
                        </form>
                    </c:if>
                </div>
            </td>
        </tr>
    </c:forEach>
</table>
<br>
<h3 class="section-title">Crear Nueva Cuenta</h3>
<form action="cuenta" method="post" class="create-account-form">
    <input type="hidden" name="action" value="createAccount">
    <input type="text" name="nombre" placeholder="Nombre de la cuenta" required class="input-account-name"
           oninput="normalizeInput(this)">
    <input type="text" name="numeroCuenta" placeholder="Número de cuenta" required class="input-account-number"
           oninput="normalizeInput(this)">
    <input type="number" name="balance" placeholder="Balance inicial" required class="input-balance"
           min="0" oninput="validateNegativeNumber(this)">
    <input type="number" name="balanceLimite" placeholder="Balance Límite" required class="input-balance-limit"
           min="0" oninput="validateNegativeNumber(this)">
    <button type="submit" class="create-account-button">Crear Cuenta</button>
</form>
<br>
<h3 class="section-title">Ingresar Nueva Etiqueta</h3>
<form action="cuenta" method="post" class="create-tag-form">
    <input type="hidden" name="action" value="createTag">
    <input type="text" name="nombre" placeholder="Nombre de la etiqueta" required class="input-tag-name"
           oninput="normalizeInput(this)">
    <button type="submit" class="create-account-button">Agregar Etiqueta</button>
</form>

<br>
<h3 class="section-title">Objetivos de Ahorro</h3>
<div class="goals-button">
    <a href="objetivos" class="nav-link">Mis Objetivos de Ahorro</a>
</div>

<br>
<div class="navigation-links">
    <a href="index.jsp" class="nav-link">Volver a inicio</a> |
    <a href="logout.jsp" class="nav-link">Cerrar sesión</a>
</div>
</body>
</html>