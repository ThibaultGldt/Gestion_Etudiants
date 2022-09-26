package fr.iut2.ProjetJPA.data;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class NoteDAO {

    public static Note create(/*String module,*/ Float valeur, Etudiant etudiant){
        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        Note note = new Note();
        //note.setModule(module);
        note.setValeur(valeur);
        note.setEtudiant(etudiant);

        em.persist(note);

        em.getTransaction().commit();
        em.close();

        return note;
    }

    public static Note update(Note note){
        EntityManager em =GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        em.merge(note);

        em.getTransaction().commit();

        return note;
    }

    public static List<Note> getAll(){
        EntityManager em = GestionFactory.factory.createEntityManager();

        Query q = em.createQuery("SELECT n FROM Note n");

        List<Note> noteList = q.getResultList();

        return noteList;
    }

    public static List<Note> getAllByEtudiant(int id){
        EntityManager em = GestionFactory.factory.createEntityManager();

        Query q = em.createQuery("SELECT n FROM Note n WHERE n.etudiant.id = :id")
                .setParameter("id", id);

        List<Note> noteList = q.getResultList();

        return  noteList;
    }

    public static Note getById(int id){
        EntityManager em = GestionFactory.factory.createEntityManager();

        Note note = em.find(Note.class, id);

        em.close();

        return note;
    }

    public static void remove(int id) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        //
        em.createQuery("DELETE FROM Note AS n WHERE n.id = :id")
                .setParameter("id", id)
                .executeUpdate();

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();
    }
}
