/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Mortadhafff
 */
public class Reponse {
    private int idReponse;
    private String contenu;
    private Utilite utilite;
    private Question question;
    private User repondeur;

    public Reponse() {
    }

    public Reponse(int idReponse) {
        this.idReponse = idReponse;
    }

    public Reponse(String contenu, Utilite utilite, Question question, User repondeur) {
        this.contenu = contenu;
        this.utilite = utilite;
        this.question = question;
        this.repondeur = repondeur;
    }

    public Reponse(int idReponse, String contenu, Utilite utilite, Question question, User repondeur) {
        this.idReponse = idReponse;
        this.contenu = contenu;
        this.utilite = utilite;
        this.question = question;
        this.repondeur = repondeur;
    }

    public int getIdReponse() {
        return idReponse;
    }

    public void setIdReponse(int idReponse) {
        this.idReponse = idReponse;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Utilite getUtilite() {
        return utilite;
    }

    public void setUtilite(Utilite utilite) {
        this.utilite = utilite;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getRepondeur() {
        return repondeur;
    }

    public void setRepondeur(User repondeur) {
        this.repondeur = repondeur;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.idReponse;
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
        final Reponse other = (Reponse) obj;
        if (this.idReponse != other.idReponse) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Reponse{" + "idReponse=" + idReponse + ", contenu=" + contenu + ", utilite=" + utilite + ", question=" + question + ", repondeur=" + repondeur + '}';
    }        
}
