<%@ page import="fr.iut2.ProjetJPA.data.Note" %><%--
  Created by IntelliJ IDEA.
  User: thibault
  Date: 03/01/2022
  Time: 09:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listeEtudiantsNotes" class="java.util.Collection<fr.iut2.ProjetJPA.data.Note>" scope="request"/>
<html>
<head>
    <title>Consultation notes etudiant</title>
</head>
    <body>
        <c:forEach items="${listeEtudiantsNotes}" var="note">
            <p> <c:out value="${note.etudiant.nom}"/> : <c:out value="${note.valeur}"/></p>
        </c:forEach>
    </body>
</html>
