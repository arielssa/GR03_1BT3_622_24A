<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title><c:out value="${tipo}" /></title>
    <link rel="stylesheet" type="text/css" href="styles/mostrarMovimientos.css">
</head>
<body id="cuerpo-movimientos">

<!-- Validación de existencia de 'tipo' -->
<h2 id="titulo-movimientos">
    <c:choose>
        <c:when test="${not empty tipo}">
            <c:out value="${tipo}"/> de la cuenta
        </c:when>
        <c:otherwise>
            Tipo de movimiento no especificado
        </c:otherwise>
    </c:choose>
</h2>

<!-- Tabla de movimientos, con validación de lista vacía -->
<c:choose>
    <c:when test="${not empty movimientosConTipo}">
        <table id="tabla-movimientos" border="1">
            <tr id="encabezado-tabla">
                <th class="encabezado-columna">Tipo</th>
                <th class="encabezado-columna">Fecha</th>
                <th class="encabezado-columna">Valor</th>
                <th class="encabezado-columna">Concepto</th>
                <th class="encabezado-columna">Etiqueta</th>
            </tr>
            <c:forEach var="item" items="${movimientosConTipo}">
                <tr class="fila-datos">
                    <td class="celda-info"><c:out value="${item.tipo}" /></td>
                    <td class="celda-info"><c:out value="${item.transaccion.fecha}" /></td>
                    <td class="celda-info"><c:out value="${item.transaccion.valor}" /></td>
                    <td class="celda-info"><c:out value="${item.transaccion.concepto}" /></td>
                    <td class="celda-info"><c:out value="${item.transaccion.etiqueta.nombre}" /></td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <p>No se encontraron movimientos para mostrar.</p>
    </c:otherwise>
</c:choose>

<br>

<!-- Enlace para volver a detalles de la cuenta con validación -->
<div id="enlace-volver-detalles">
    <c:choose>
        <c:when test="${not empty cuentaId}">
            <a href="detalleCuenta?cuentaId=${cuentaId}" class="enlace-volver">Volver a Detalles de la Cuenta</a>
        </c:when>
        <c:otherwise>
            <p>ID de la cuenta no especificado.</p>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>