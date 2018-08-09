/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.List;



public class Competence {

    private int idCompetence;
    private String nom;
    private List<User> users;

    public Competence() {
    }
    
    public Competence(int idCompetence) {
        this.idCompetence = idCompetence;
    }

    public Competence(String nom) {
        this.nom = nom;
    }

    public Competence(int idCompetence, String nom) {
        this.idCompetence = idCompetence;
        this.nom = nom;
    }

    public int getIdCompetence() {
        return idCompetence;
    }

    public void setIdCompetence(int idCompetence) {
        this.idCompetence = idCompetence;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.idCompetence;
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
        final Competence other = (Competence) obj;
        if (this.idCompetence != other.idCompetence) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Competence{" + "idCompetence=" + idCompetence + ", nom=" + nom + '}';
    }
}
