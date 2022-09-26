package fr.iut2.ProjetJPA.data;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class AbsenceDAO {

    public static Absence create(Date debut, Date fin, String justification, Etudiant etudiant){
        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        Absence absence = new Absence();
        absence.setDebut(debut);
        absence.setFin(fin);
        absence.setJustification(justification);
        absence.setEtudiant(etudiant);

        em.persist(absence);

        int id = absence.getId();

        em.getTransaction().commit();

        em.close();

        return absence;
    }

    public static Absence update(Absence absence){
        EntityManager em =GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        em.merge(absence);

        em.getTransaction().commit();

        return absence;
    }

    public static List<Absence> getAll(){
        EntityManager em = GestionFactory.factory.createEntityManager();

        Query q = em.createQuery("SELECT a FROM Absence a");

        List<Absence> absenceList = q.getResultList();
        return absenceList;
    }

    public static List<Absence> getAllByEtudiant(int id){
        EntityManager em = GestionFactory.factory.createEntityManager();

        Query q = em.createQuery("SELECT a FROM Absence a WHERE a.etudiant.id = :id")
                .setParameter("id", id);

        List<Absence> absList = q.getResultList();

        return  absList;
    }

    public static Absence getById(int id){
        EntityManager em = GestionFactory.factory.createEntityManager();

        Absence abs = em.find(Absence.class, id);

        em.close();

        return abs;
    }

    public static void remove(int id) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        //
        em.createQuery("DELETE FROM Absence AS a WHERE a.id = :id")
                .setParameter("id", id)
                .executeUpdate();

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();
    }
}
