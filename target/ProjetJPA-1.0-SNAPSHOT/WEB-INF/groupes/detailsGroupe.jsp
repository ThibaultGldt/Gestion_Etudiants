<%@ page import="fr.iut2.ProjetJPA.data.Etudiant" %><%--
  Created by IntelliJ IDEA.
  User: thibault
  Date: 02/02/2022
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="groupe" class="fr.iut2.ProjetJPA.data.Groupe" scope="request"/>
<html>
<head>
    <title><%=groupe.getNom()%></title>
</head>
<body>
    <h2>Notes des élèves du groupe</h2>
    <table>
        <thead>
        </thead>
        <tbody>
            <c:forEach items="${groupe.etudiants}" var="etu">
                <form method="get" action="<%=application.getContextPath()%>/do/modifNotesEtu">
                <tr>
                    <td>
                        <c:out value="${etu.nom}"></c:out>
                        <br/>
                        <c:out value="${etu.prenom}"></c:out>
                    </td>
                    <c:forEach items="${etu.notes}" var="note">
                        <td>
                            <input type="number" step="0.01" min="0" max="20" name="<c:out value="${note.id}"/>" value="<c:out value="${note.valeur}"></c:out>" required>
                            <input type="hidden" name="etudiant" value="<c:out value="${note.etudiant.id}"/>">
                        </td>
                    </c:forEach>
                    <td>
                        <input type="submit" value="Modifier">
                    </td>
                </tr>
                </form>
            </c:forEach>
        </tbody>
    </table>

    <h2>Absences des élèves du groupe</h2>
    <table>
        <tbody>
        <c:forEach items="${groupe.etudiants}" var="etu">
            <tr>
                <td>
                    <c:out value="${etu.nom}"></c:out>
                    <br/>
                    <c:out value="${etu.prenom}"></c:out>
                </td>

                <c:forEach items="${etu.absences}" var="abs">
                    <td>
                        <table>
                            <tr>
                                <td>
                                    <c:out value="${abs.debut}"></c:out>
                                </td>

                                <td>
                                    <c:out value="${abs.fin}"></c:out>
                                </td>

                                <td>
                                    <c:out value="${abs.justification}"></c:out>
                                </td>
                            </tr>
                        </table>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!--Formulaire d'ajout d'un élève au groupe courant-->
    <h2>Ajouter un élève au groupe</h2>
    <form method="get" action="<%=application.getContextPath()%>/do/AjoutEtudiantGroupe">
        <label for="nom">Nom:</label>
        <input type="text" name="nom" id="nom">

        <label for="prenom">Prénom:</label>
        <input type="text" name="prenom" id="prenom">

        <input type="hidden" name="groupe" value="<c:out value="${groupe.id}"/>">

        <input type="submit" value="Ajouter">
    </form>

</body>
</html>
