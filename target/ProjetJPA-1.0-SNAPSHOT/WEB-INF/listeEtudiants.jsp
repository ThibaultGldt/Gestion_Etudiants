<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ page import="fr.iut2.ProjetJPA.data.Etudiant" %>

<jsp:useBean id="listeEtudiants" class="java.util.Collection<fr.iut2.ProjetJPA.data.Etudiant>" scope="request"/>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1>Liste des étudiants</h1>
<c:forEach items="${listeEtudiants}" var="etudiant">
<br/>
<a href="<%= application.getContextPath()%>/do/details?id=<c:out value="${etudiant.id}"/>"><c:out value="${etudiant.prenom}"/> <c:out value="${etudiant.nom}"/></a>
</c:forEach>
</body>
</html>