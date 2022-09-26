<%@ page import="fr.iut2.ProjetJPA.data.Groupe" %><%--
  Created by IntelliJ IDEA.
  User: thibault
  Date: 02/02/2022
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listeGroupes" class="java.util.Collection<fr.iut2.ProjetJPA.data.Groupe>" scope="request"/>
<html>
<head>
    <title>Liste des groupes</title>
</head>
<body>
<h1></h1>

<%for (Groupe groupe : listeGroupes) {%>
    <br/>
    <a href="<%= application.getContextPath()%>/do/groupe?id=<%=groupe.getId()%>"><%=groupe.getNom()%></a>
<%}%>

<h3>Créer un groupe</h3>
<form action="<%= application.getContextPath()%>/do/ajoutGroupe">
    <label for="nomGroupe">Nom du groupe</label>
    <input type="text" name="nomGroupe" id="nomGroupe">

    <input type="submit" value="Ajouter">
</form>
</body>
</html>
