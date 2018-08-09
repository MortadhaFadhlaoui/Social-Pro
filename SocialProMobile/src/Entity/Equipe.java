/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.List;

/**
 *
 * @author dell
 */
public class Equipe {
    
    private int idEquipe;
    private String nom;
    private t_etat etat;
    private Projet projet;

    List<User> users;
    public Equipe() {
    }

    public Equipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }
    

    public Equipe(String nom, t_etat etat) {
        this.nom = nom;
        this.etat = etat;
    }
    
    public Equipe(int idEquipe, String nom, t_etat etat) {
        this.idEquipe = idEquipe;
        this.nom = nom;
        this.etat = etat;
    }

    public Equipe(int idEquipe, String nom, t_etat etat, Projet projet, List<User> users) {
        this.idEquipe = idEquipe;
        this.nom = nom;
        this.etat = etat;
        this.projet = projet;
        this.users = users;
    }

    public Equipe(int idEquipe, String nom, t_etat etat, Projet projet) {
        this.idEquipe = idEquipe;
        this.nom = nom;
        this.etat = etat;
        this.projet = projet;
    }

   

   
    
    

    public int getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public t_etat getEtat() {
        return etat;
    }

    public void setEtat(t_etat etat) {
        this.etat = etat;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }
    
    
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.idEquipe;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Equipe other = (Equipe) obj;
        if (this.idEquipe != other.idEquipe) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "equipe{" + "idEquipe=" + idEquipe + ", nom=" + nom + ", etat=" + etat + '}';
    }
    
    
    
    
}
