package fr.iut2.ProjetJPA.data;

import javax.persistence.*;

@Entity
public class Note {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String module;

    @Column
    private Float valeur;

    @ManyToOne
    private Etudiant etudiant;

    public Note() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Float getValeur() {
        return valeur;
    }

    public void setValeur(Float valeur) {
        this.valeur = valeur;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
}
