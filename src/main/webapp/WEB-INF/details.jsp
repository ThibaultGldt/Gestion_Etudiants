<%@ page import="fr.iut2.ProjetJPA.data.Note" %>
<%@ page import="fr.iut2.ProjetJPA.data.Absence" %><%--
  Created by IntelliJ IDEA.
  User: thibault
  Date: 09/12/2021
  Time: 14:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>--%>

<jsp:useBean id="etudiant" class="fr.iut2.ProjetJPA.data.Etudiant" scope="request" />
<jsp:useBean id="absences" class="java.util.Collection<fr.iut2.ProjetJPA.data.Absence>" scope="request"/>
<jsp:useBean id="notes" class="java.util.Collection<fr.iut2.ProjetJPA.data.Note>" scope="request"/>
<html>
<head>
    <title>Détails <%= etudiant.getPrenom()%></title>
</head>
<body>
    <h1><c:out value="${etudiant.prenom}"/> <c:out value="${etudiant.nom}"/> | <c:out value="${etudiant.groupe.nom}"/></h1>
    <form method="get" action="<%=application.getContextPath()%>/do/supprimerEtudiant">
        <input type="hidden" name="etudiant" value="<c:out value="${etudiant.id}"/>"/>
        <input type="submit" value="Supprimer">
    </form>

    <div id="note">
        <table>
            <th>
                <td>Notes</td>
                <td></td>
            </th>
            <tbody>
                <c:forEach items="${notes}" var="note">
                <form method="get" action="<%= application.getContextPath()%>/do/modificationNote">
                    <tr>
                        <td></td>
                        <td>
                            <input type="number" step="0.01" name="valeur" min="0" max="20" value="<c:out value="${note.valeur}"/>">
                            <input type="hidden" name="idNote" value="<c:out value="${note.id}"/>">
                        </td>
                        <td>
                            <input type="submit" value="Modifier">
                        </td>
                        <td>
                            <button type="submit" formaction="<%=application.getContextPath()%>/do/supprimerNote">
                                Supprimer
                            </button>
                        </td>
                    </tr>
                </form>
                </c:forEach>
            </tbody>
        </table>
        <form method="get" action="ajoutNote">
            <label for="valeur">Note :</label>
            <input type="number" step="0.01" name="valeur" id="valeur"/>
            <input type="hidden" name="etu" value="<c:out value="${etudiant.id}"/>">
            <input type="submit" value="Ajouter">
        </form>
    </div>

    <div id="absence">
        <h3>Absences</h3>
        <table>
            <th>
                <td>Date Début</td>
                <td>Date Fin</td>
                <td>Justification</td>
            </th>
            <tbody>
                <c:forEach items="${absences}" var="absence">
                    <form method="get" action="<%= application.getContextPath()%>/do/modificationAbsence">
                        <tr>
                            <td></td>
                            <td>
                                <input type="date"  name="dateDebut"  value="<fmt:formatDate value="${absence.debut}" pattern="yyyy/mm/dd"/>">
                            </td>
                            <td>
                                <input type="date"  name="dateFin"  value="12/03/2000"/>">
                            </td>
                            <td>
                                <input type="text"  name="justification"  value="<c:out value="${absence.justification}"/>">
                                <input type="hidden" name="idAbs" value="<c:out value="${absence.id}"/>">
                            </td>
                            <td>
                                <input type="submit" value="Modifier">
                            </td>
                            <td>
                                <button type="submit" formaction="<%=application.getContextPath()%>/do/supprimerAbsence">
                                    Supprimer
                                </button>
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </tbody>
        </table>
        <form method="get" action="<%= application.getContextPath()%>/do/ajoutAbsence">
            <label for="dateDebut">Date Début</label>
            <input type="date" name="dateDebut" id="dateDebut"/>
            <label for="dateFin">Date Fin</label>
            <input type="date" name="dateFin" id="dateFin"/>
            <label for="justification">Justification</label>
            <input type="text" name="justification" id="justification"/>

            <input type="hidden" name="etu" value="<c:out value="${etudiant.id}"/>">
            <input type="submit" value="Ajouter">
        </form>
    </div>
</body>
</html>
