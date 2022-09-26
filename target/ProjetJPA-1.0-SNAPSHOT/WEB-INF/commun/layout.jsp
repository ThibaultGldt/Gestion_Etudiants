<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
      integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">

<!-- jQuery and JS bundle w/ Popper.js -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
        crossorigin="anonymous"></script>

<jsp:useBean id="content" class="java.lang.String" scope="request"/>

<html>
<head>
    <title><%= application.getInitParameter("title")%>
    </title>
</head>
<body>

<%-- Container boostrap --%>
<div class="container-fluid">

    <%-- Ajout de l'entete de page --%>
    <jsp:include page="<%= application.getInitParameter(\"entetedepage\")%>"/>

     <%-- Ajout du paramètre de contexte title --%>
    <h1><%= application.getInitParameter("title")%></h1>

    <a href="<%= application.getContextPath()%>/do/accueil">Accueil</a>
    <a href="<%= application.getContextPath()%>/do/listeEtudiants">Afficher les étudiants</a>
    <a href="<%= application.getContextPath()%>/do/consultationNotes">Consulter les notes</a>
    <a href="<%= application.getContextPath()%>/do/consultationAbsences">Consulter les absences</a>
        <a href="<%= application.getContextPath()%>/do/listeGroupes">Consulter les groupes</a>


    <%-- Ajout de la JSP passée en paramètre sous forme de JavaBean --%>

    <jsp:include page="<%=content%>"/>

    <%-- Ajout du pied de page --%>
    <jsp:include page="<%= application.getInitParameter(\"pieddepage\")%>"/>

</div>

</body>
</html>
