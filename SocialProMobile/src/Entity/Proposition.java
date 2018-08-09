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
public class Proposition {
    
  private  int idPropsition ;
  private String titre;
  private String sujet;
  private User owner;
  
  List<Vote> votes;

    public Proposition() {
    }

    public Proposition(int idPropsition) {
        this.idPropsition = idPropsition;
    }

    public Proposition(String titre, String sujet, User owner) {
        this.titre = titre;
        this.sujet = sujet;
        this.owner = owner;
    }
    

    public Proposition(int idPropsition, String titre, String sujet, User owner) {
        this.idPropsition = idPropsition;
        this.titre = titre;
        this.sujet = sujet;
        this.owner = owner;
    }

    public Proposition(int idPropsition, String titre, String sujet, User owner, List<Vote> votes) {
        this.idPropsition = idPropsition;
        this.titre = titre;
        this.sujet = sujet;
        this.owner = owner;
        this.votes = votes;
    }
   

    public int getIdPropsition() {
        return idPropsition;
    }

    public void setIdPropsition(int idPropsition) {
        this.idPropsition = idPropsition;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.idPropsition;
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
        final Proposition other = (Proposition) obj;
        if (this.idPropsition != other.idPropsition) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Proposition{" + "idPropsition=" + idPropsition + ", titre=" + titre + ", sujet=" + sujet + ", owner=" + owner + '}';
    }
    
    
  
}
