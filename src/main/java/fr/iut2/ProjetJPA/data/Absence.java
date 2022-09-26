package fr.iut2.ProjetJPA.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Absence implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private Date debut;

    @Column
    private Date fin;

    @Column
    private String justification;

    @ManyToOne
    private Etudiant etudiant;

    public Absence() {
        super();
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
