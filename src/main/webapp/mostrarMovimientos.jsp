<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${tipo}</title>
    <link rel="stylesheet" type="text/css" href="styles/mostrarMovimientos.css" >
</head>
<body id="cuerpo-movimientos">
    <h2 id="titulo-movimientos">${tipo} de la cuenta</h2>
    <table border="1">
        <tr id="encabezado-tabla">
            <th>Tipo</th>
            <th>Fecha</th>
            <th>Valor</th>
            <th>Concepto</th>
            <th>Etiqueta</th>
        </tr>
        <c:forEach var="item" items="${movimientosConTipo}">
            <tr>
                <td class="celda-info">${item.tipo}</td>
                <td class="celda-info">${item.transaccion.fecha}</td>
                <td class="celda-info">${item.transaccion.valor}</td>
                <td class="celda-info">${item.transaccion.concepto}</td>
                <td class="celda-info">${item.transaccion.etiqueta.nombre}</td>
            </tr>
        </c:forEach>
    </table>

    <br>
    <!-- Enlace para volver a detalles de la cuenta -->
    <div id="enlace-volver-detalles">
        <a href="detalleCuenta?cuentaId=${cuentaId}" class="enlace-volver-detalles">Volver a Detalles de la Cuenta</a>
    </div>
</body>
</html>
