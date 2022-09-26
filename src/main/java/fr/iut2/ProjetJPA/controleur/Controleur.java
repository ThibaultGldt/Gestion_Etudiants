/**
 * @author hb
 */

/**
 * @author hb
 *
 */
package fr.iut2.ProjetJPA.controleur;

import fr.iut2.ProjetJPA.data.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.lang.String.valueOf;

@SuppressWarnings("serial")
@WebServlet("/controleur/Controleur")
public class Controleur extends HttpServlet {

    private String urlLayout;
    private String urlAccueil;
    private String urlListeEtudiants;
    private String urlEtudiant;
    private String urlConsultationAbsences;
    private String urlConsultationNotes;
    private String urlListeGroupes;
    private String urlGroupe;

    // INIT
    @Override
    public void init() throws ServletException {
        // Récupération des URLs en paramètre du web.xml
        urlLayout = getInitParameter("urlLayout");
        urlAccueil = getInitParameter("urlAccueil");
        urlListeEtudiants = getInitParameter("urlListeEtudiants");
        urlEtudiant = getInitParameter("urlEtudiant");
        urlConsultationAbsences = getInitParameter("urlConsultationAbsences");
        urlConsultationNotes = getInitParameter("urlConsultationNotes");
        urlListeGroupes = getInitParameter("urlListeGroupes");
        urlGroupe = getInitParameter("urlGroupe");

        // Création de la factory permettant la création d'EntityManager
        // (gestion des transactions)
        GestionFactory.open();

        ///// INITIALISATION DE LA BD
        // Normalement l'initialisation se fait directement dans la base de données
        if ((GroupeDAO.getAll().size() == 0) && (EtudiantDAO.getAll().size() == 0)) {

            // Creation des groupes
            Groupe MIAM = GroupeDAO.create("miam");
            Groupe SIMO = GroupeDAO.create("SIMO");
            Groupe MESSI = GroupeDAO.create("MESSI");

            // Creation des étudiants
            EtudiantDAO.create("Francis", "Brunet-Manquat", MIAM);
            EtudiantDAO.create("Philippe", "Martin", MIAM);
            EtudiantDAO.create("Mario", "Cortes-Cornax", MIAM);
            EtudiantDAO.create("Françoise", "Coat", SIMO);
            EtudiantDAO.create("Laurent", "Bonnaud", MESSI);
            EtudiantDAO.create("Sébastien", "Bourdon", MESSI);
            EtudiantDAO.create("Mathieu", "Gatumel", SIMO);
        }
    }

    @Override
    public void destroy() {
        super.destroy();

        // Fermeture de la factory
        GestionFactory.close();
    }

    // POST
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // on passe la main au GET
        doGet(request, response);
    }

    // GET
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String action = request.getPathInfo();
        // Log action
        System.out.println("PROJET JPA : action = " + action);

        // Exécution action

        if(action == null){
            action = "/accueil";
        }

        if(action.equals("/accueil")){
            doAccueil(request, response);
        }else if(action.equals("/listeEtudiants")){
            doListeEtudiants(request, response);
        }else if (action.equals("/details")){
            doDetails(request, response);
        }else if (action.equals("/consultationAbsences")){
            doConsultationAbsences(request, response);
        }else if (action.equals("/consultationNotes")){
            doConsultationNotes(request, response);
        }else if (action.equals("/listeGroupes")){
            doListeGroupes(request, response);
        }else if (action.equals("/groupe")){
            doGroupe(request, response);
        }else if(action.equals("/ajoutNote")){
            doAjoutNote(request, response);
        }else if(action.equals("/modificationNote")){
            doModificationNote(request, response);
        }else if(action.equals("/ajoutAbsence")){
            try {
                doAjoutAbs(request, response);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if(action.equals("/modificationAbsence")){
            try {
                doModificationAbs(request, response);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if(action.equals("/ajoutGroupe")){
            doAjoutGroupe(request, response);
        }else if(action.equals("/modifNotesEtu")){
            doModifNotesEtu(request, response);
        }else if(action.equals("/AjoutEtudiantGroupe")){
            doAjoutEtudiantGroupe(request, response);
        }else if(action.equals("/supprimerEtudiant")){
            doSupprimerEtudiant(request, response);
        }else if(action.equals("/supprimerNote")){
            doSupprimerNote(request, response);
        }else if(action.equals("/supprimerAbsence")){
            doSupprimerAbsence(request, response);
        }else{
            doAccueil(request, response);
        }
    }

    private void doSupprimerAbsence(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Absence absence = AbsenceDAO.getById(Integer.parseInt(request.getParameter("idAbs")));

        AbsenceDAO.remove(absence.getId());

        response.sendRedirect(request.getContextPath() + "/do/details?id=" + absence.getEtudiant().getId());
    }

    private void doSupprimerNote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Note note = NoteDAO.getById(Integer.parseInt(request.getParameter("idNote")));

        NoteDAO.remove(note.getId());

        response.sendRedirect(request.getContextPath() + "/do/details?id=" + note.getEtudiant().getId());
    }

    private void doSupprimerEtudiant(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Etudiant etudiant = EtudiantDAO.retrieveById(Integer.parseInt(request.getParameter("etudiant")));
        //on supprime les notes de l'étudiant
        for (Note note :
                etudiant.getNotes()) {
            NoteDAO.remove(note.getId());
        }
        //on supprime les absences de l'étudiant
        for (Absence abs :
                etudiant.getAbsences()) {
            AbsenceDAO.remove(abs.getId());
        }
        //on supprime l'étudiant
        EtudiantDAO.remove(etudiant.getId());

        response.sendRedirect(request.getContextPath() + "/do/accueil");
    }

    private void doAjoutEtudiantGroupe(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException{
        Groupe groupe = GroupeDAO.getById(Integer.parseInt(request.getParameter("groupe")));
        EtudiantDAO.create(
                request.getParameter("prenom"),
                request.getParameter("nom"),
                groupe
        );

        request.setAttribute("id", groupe.getId());
        response.sendRedirect(request.getContextPath() + "/do/groupe?id=" + groupe.getId());
    }

    private void doModifNotesEtu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Etudiant etudiant = EtudiantDAO.retrieveById(Integer.parseInt(request.getParameter("etudiant")));
        for (Note note :
                etudiant.getNotes()) {
            if (request.getParameter(valueOf(note.getId())) != null) {
                note.setValeur(Float.valueOf(request.getParameter(String.valueOf(note.getId()))));
                NoteDAO.update(note);
            }
        }

        request.setAttribute("id", etudiant.getGroupe().getId());
        response.sendRedirect(request.getContextPath() + "/do/groupe?id=" + etudiant.getGroupe().getId());
    }

    private void doAjoutGroupe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomGroupe = request.getParameter("nomGroupe");
        Groupe nouveauGroupe = GroupeDAO.create(nomGroupe);

        response.sendRedirect(request.getContextPath() + "/do/listeGroupes");
    }

    private void doAjoutNote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Etudiant etudiant = EtudiantDAO.retrieveById(Integer.parseInt(request.getParameter("etu")));
        Note note = NoteDAO.create(Float.valueOf(request.getParameter("valeur")), etudiant);

        request.setAttribute("id", etudiant.getId());

        response.sendRedirect(request.getContextPath() + "/do/details?id=" + etudiant.getId());
        //request.setAttribute("content", urlEtudiant);
        //loadJSP(urlEtudiant, request, response);
    }

    private void doModificationNote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Note note = NoteDAO.getById(Integer.parseInt(request.getParameter("idNote")));
        Etudiant etudiant = EtudiantDAO.retrieveById(note.getEtudiant().getId());

        note.setValeur(Float.valueOf(request.getParameter("valeur")));
        NoteDAO.update(note);
        request.setAttribute("id", etudiant.getId());

        response.sendRedirect(request.getContextPath() + "/do/details?id=" + etudiant.getId());
        //request.setAttribute("content", urlEtudiant);
        //loadJSP(urlEtudiant, request, response);
    }

    private void doAjoutAbs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH);

        Etudiant etudiant = EtudiantDAO.retrieveById(Integer.parseInt(request.getParameter("etu")));
        Date dateDebut = formatter.parse(request.getParameter("dateDebut"));
        Date dateFin = formatter.parse(request.getParameter("dateFin"));
        String justification = request.getParameter("justification");

        Absence absence = AbsenceDAO.create(dateDebut, dateFin, justification, etudiant);

        request.setAttribute("id", etudiant.getId());

        response.sendRedirect(request.getContextPath() + "/do/details?id=" + etudiant.getId());
        //request.setAttribute("content", urlEtudiant);
        //loadJSP(urlEtudiant, request, response);
    }

    private void doModificationAbs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH);
        Date dateDebut = formatter.parse(request.getParameter("dateDebut"));
        Date dateFin = formatter.parse(request.getParameter("dateFin"));
        String justification = request.getParameter("justification");
        Absence absence = AbsenceDAO.getById(Integer.parseInt(request.getParameter("idAbs")));
        Etudiant etudiant = EtudiantDAO.retrieveById(absence.getEtudiant().getId());

        absence.setDebut(dateDebut);
        absence.setFin(dateFin);
        absence.setJustification(justification);
        AbsenceDAO.update(absence);
        request.setAttribute("id", etudiant.getId());

        response.sendRedirect(request.getContextPath() + "/do/details?id=" + etudiant.getId());
        //request.setAttribute("content", urlEtudiant);
        //loadJSP(urlEtudiant, request, response);
    }

    private void doGroupe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idGroupe = Integer.parseInt(request.getParameter("id"));
        Groupe groupe = GroupeDAO.getById(idGroupe);

        request.setAttribute("groupe", groupe);
        request.setAttribute("content", urlGroupe);

        loadJSP(urlLayout, request, response);
    }

    private void doListeGroupes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Groupe> groupeList = GroupeDAO.getAll();

        request.setAttribute("listeGroupes", groupeList);
        request.setAttribute("content", urlListeGroupes);
        loadJSP(urlLayout, request, response);
    }

    private void doAccueil(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute("content", urlAccueil);
        loadJSP(urlLayout, request, response);
    }

    private void doListeEtudiants(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<Etudiant> listeEtudiants = EtudiantDAO.getAll();

        request.setAttribute("listeEtudiants", listeEtudiants);
        request.setAttribute("content", urlListeEtudiants);
        loadJSP(urlLayout, request, response);
    }

    private void doDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int idEtudiant = Integer.parseInt(request.getParameter("id"));
        fr.iut2.ProjetJPA.data.Etudiant etudiant = fr.iut2.ProjetJPA.data.EtudiantDAO.retrieveById(idEtudiant);
        List<Absence> absences = AbsenceDAO.getAllByEtudiant(idEtudiant);
        List<Note> notes = NoteDAO.getAllByEtudiant(idEtudiant);

        request.setAttribute("etudiant", etudiant);
        request.setAttribute("absences", absences);
        request.setAttribute("notes", notes);

        request.setAttribute("content", urlEtudiant);
        loadJSP(urlLayout, request, response);
    }

    private void doConsultationAbsences(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<Absence> listEtudiantAbsence = AbsenceDAO.getAll();

        request.setAttribute("listeEtudiantsAbsents", listEtudiantAbsence);

        request.setAttribute("content", urlConsultationAbsences);
        loadJSP(urlLayout, request, response);
    }

    private void doConsultationNotes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<Note> listEtudiantNotes= NoteDAO.getAll();

        request.setAttribute("listeEtudiantsNotes", listEtudiantNotes);

        request.setAttribute("content", urlConsultationNotes);
        loadJSP(urlLayout, request, response);
    }

    // ///////////////////////
    //

    /**
     * Charge la JSP indiquée en paramètre
     *
     * @param url
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void loadJSP(String url, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // L'interface RequestDispatcher permet de transférer le contrôle à une
        // autre servlet
        // Deux méthodes possibles :
        // - forward() : donne le contrôle à une autre servlet. Annule le flux
        // de sortie de la servlet courante
        // - include() : inclus dynamiquement une autre servlet
        // + le contrôle est donné à une autre servlet puis revient à la servlet
        // courante (sorte d'appel de fonction).
        // + Le flux de sortie n'est pas supprimé et les deux se cumulent

        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(url);
        rd.forward(request, response);
    }

}
