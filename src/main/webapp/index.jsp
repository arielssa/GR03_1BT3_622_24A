<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="messages_es" />

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="welcome.title" /></title>

    <link rel="stylesheet" type="text/css" href="styles/index.css">
</head>
<body id="main-body">

<!-- Encabezado principal -->
<header class="header">
    <h1 class="welcome-title"><fmt:message key="welcome.title" /></h1>
    <p class="subtitle"><fmt:message key="welcome.subtitle" /></p>
</header>

<!-- Descripción general -->
<section id="description">
    <p class="description-text"><fmt:message key="welcome.note" /></p>
</section>

<!-- Sección de opciones de acceso -->
<section id="access-options">
    <div class="access-container">
        <p class="access-instructions"><fmt:message key="access.instruction" /></p>
        <a href="login.jsp" class="button login-button"><fmt:message key="button.login" /></a>
        <a href="register.jsp" class="button register-button"><fmt:message key="button.register" /></a>
    </div>
</section>

<!-- Información adicional sobre el sistema -->
<section id="additional-info">
    <h2 class="info-title"><fmt:message key="additional.info" /></h2>
    <p class="info-text"><fmt:message key="info.text" /></p>
    <ul class="info-list">
        <li class="info-item"><fmt:message key="info.view.accounts" /></li>
        <li class="info-item"><fmt:message key="info.transfer" /></li>
        <li class="info-item"><fmt:message key="info.transaction.history" /></li>
        <li class="info-item"><fmt:message key="info.organize" /></li>
        <li class="info-item"><fmt:message key="info.access.anywhere" /></li>
    </ul>
</section>

<!-- Mensaje de bienvenida adicional -->
<section id="welcome-message">
    <p class="welcome-note"><fmt:message key="welcome.note" /></p>
</section>

<!-- Información de contacto o soporte -->
<section id="support-info">
    <p class="support-text"><fmt:message key="support.info" /></p>
</section>

<!-- Pie de página -->
<footer id="footer">
    <p class="footer-text"><fmt:message key="footer.rights" /></p>
</footer>

</body>
</html>