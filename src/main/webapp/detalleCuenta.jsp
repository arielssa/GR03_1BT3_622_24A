<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Detalle de la Cuenta</title>
    <link rel="stylesheet" type="text/css" href="styles/detalleCuenta.css">
    <script>
        // Validación del campo balance limite
        function validateBalanceLimite() {
            let balanceLimite = document.querySelector('.input-balance-limite').value;
            if (balanceLimite <= 0) {
                alert("El balance límite debe ser un valor positivo.");
                return false;
            }
            return true;
        }

        // Validación de fechas de inicio y fin
        function validateFechaConsulta() {
            let fechaInicio = document.getElementById("fechaInicio").value;
            let fechaFin = document.getElementById("fechaFin").value;

            if (fechaInicio > fechaFin) {
                alert("La fecha de inicio debe ser anterior o igual a la fecha de fin.");
                return false;
            }
            return true;
        }

        // Validación de la selección de etiqueta
        function validateEtiqueta() {
            let etiqueta = document.getElementById("etiqueta").value;
            if (etiqueta === "") {
                alert("Debe seleccionar una etiqueta.");
                return false;
            }
            return true;
        }

        // Validaciones al enviar formularios
        function validateActualizarBalance() {
            return validateBalanceLimite();
        }

        function validateConsultaFecha() {
            return validateFechaConsulta();
        }

        function validateConsultaEtiqueta() {
            return validateEtiqueta();
        }
    </script>
</head>
<body id="cuerpo-detalle-cuenta">

<c:if test="${not empty sessionScope.mensaje}">
    <script>
        alert("${sessionScope.mensaje}");
    </script>
    <c:remove var="mensaje" scope="session"/>
</c:if>

<h2 id="titulo-detalles-cuenta">Detalles de la Cuenta</h2>

<section id="informacion-cuenta">
    <p class="detalle-cuenta"><strong>ID de la cuenta:</strong> ${cuentaId}</p>
    <p class="detalle-cuenta"><strong>Nombre de la cuenta:</strong> ${cuenta.nombre}</p>
    <p class="detalle-cuenta"><strong>Número de cuenta:</strong> ${cuenta.numeroCuenta}</p>
    <p class="detalle-cuenta"><strong>Balance:</strong> ${cuenta.balance}</p>
    <p class="detalle-cuenta"><strong>Balance Límite:</strong> ${cuenta.balanceLimite}</p>

    <form action="detalleCuenta" method="post" class="formulario-detalle">
        <input type="hidden" name="cuentaId" value="${cuenta.id}">
        <button type="submit" name="action" value="toggleBloqueo" class="boton-bloqueo">
            <c:choose>
                <c:when test="${cuenta.bloqueada}">
                    Desbloquear Cuenta
                </c:when>
                <c:otherwise>
                    Bloquear Cuenta
                </c:otherwise>
            </c:choose>
        </button>
    </form>
</section>

<br>
<form action="cuenta" method="post" id="form-actualizar-balance" class="formulario-detalle" onsubmit="return validateActualizarBalance()">
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

<br>

<h3 class="subtitulo">Consultar Movimientos</h3>

<form action="movimientos" method="get" id="form-consulta-fecha" class="formulario-detalle" onsubmit="return validateConsultaFecha()">
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

<form action="movimientos" method="get" id="form-consulta-etiqueta" class="formulario-detalle" onsubmit="return validateConsultaEtiqueta()">
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

<!-- Formulario para exportar los movimientos en PDF -->
<h3>Exportar Movimientos en PDF</h3>
<form action="exportarMovimientos" method="post" id="form-exportar-pdf" class="formulario-detalle" onsubmit="return validateConsultaFecha()">
    <input type="hidden" name="cuentaId" value="${cuenta.id}">

    <label for="fechaInicio">Fecha de Inicio (opcional):</label>
    <input type="date" name="fechaInicio" class="input-date">

    <label for="fechaFin">Fecha de Fin (opcional):</label>
    <input type="date" name="fechaFin" class="input-date">

    <button type="submit" class="boton-consulta">Exportar a PDF</button>
</form>

<!-- Enlace para volver a la lista de cuentas -->
<div id="enlace-volver">
    <a href="cuenta" class="enlace-volver">Volver a Mis Cuentas</a>
</div>
<br>
</body>
</html>
