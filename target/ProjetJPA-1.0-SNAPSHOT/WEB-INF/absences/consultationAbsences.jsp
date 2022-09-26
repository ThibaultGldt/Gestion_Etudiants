<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="fr.iut2.ProjetJPA.data.Absence" %><%--
  Created by IntelliJ IDEA.
  User: thibault
  Date: 03/01/2022
  Time: 09:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listeEtudiantsAbsents" class="java.util.List<Absence>" scope="request"/>
<html>
<head>
    <title>Liste des absences</title>
</head>
<body>
<c:forEach items="${listeEtudiantsAbsents}" var="absence">
    <p><c:out value="${absence.etudiant.nom}"></c:out> <c:out value="${absence.etudiant.prenom}"></c:out> :
    <c:out value="${absence.debut}"></c:out>
    <c:out value="${absence.fin}"></c:out>
    <c:out value="${absence.justification}"></c:out></p>
</c:forEach>
</body>
</html>
