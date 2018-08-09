/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Date;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Mortadhafff
 */
public class Question {
    private int idQuestion;
    private String titre;
    private String sujet;
    private StatuQuestion statuquestion;
    private Date DateQuestion;
    private User proposeur;
    private List<Reponse> reponses;
    private List<Competence> competences;
    

    public Question() {
    }

    public Question(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public Question(int idQuestion, String titre) {
        this.idQuestion = idQuestion;
        this.titre = titre;
    }

    
    public Question(String titre, String sujet, StatuQuestion statuquestion, User proposeur) {
        this.titre = titre;
        this.sujet = sujet;
        this.statuquestion = statuquestion;
        this.proposeur = proposeur;
    }
    

    public Question(String titre, String sujet, StatuQuestion statuquestion) {
        this.titre = titre;
        this.sujet = sujet;
        this.statuquestion = statuquestion;
    }

    public Question(int idQuestion, String titre, String sujet, StatuQuestion statuquestion, User proposeur) {
        this.idQuestion = idQuestion;
        this.titre = titre;
        this.sujet = sujet;
        this.statuquestion = statuquestion;
        this.proposeur = proposeur;
    }

    public Question(int idQuestion, String titre, String sujet, StatuQuestion statuquestion, Date DateQuestion, User proposeur, List<Reponse> reponses) {
        this.idQuestion = idQuestion;
        this.titre = titre;
        this.sujet = sujet;
        this.statuquestion = statuquestion;
        this.DateQuestion = DateQuestion;
        this.proposeur = proposeur;
        this.reponses = reponses;
    }

   

    

    public Question(int idQuestion, String titre, String sujet, StatuQuestion statuquestion, User proposeur, List<Reponse> reponses, List<Competence> competences) {
        this.idQuestion = idQuestion;
        this.titre = titre;
        this.sujet = sujet;
        this.statuquestion = statuquestion;
        this.proposeur = proposeur;
        this.reponses = reponses;
        this.competences = competences;
    }

    public Question(String titre, String sujet, StatuQuestion statuquestion, List<Competence> competences) {
        this.titre = titre;
        this.sujet = sujet;
        this.statuquestion = statuquestion;
        this.competences = competences;
    }

 
    

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre=titre;
    }
   
    
    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet=sujet;
    }

    public StatuQuestion getStatuquestion() {
        return statuquestion;
    }

    public void setStatuquestion(StatuQuestion statuquestion) {
        this.statuquestion = statuquestion;
    }

    public List<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }

    public User getProposeur() {
        return proposeur;
    }

    public void setProposeur(User proposeur) {
        this.proposeur = proposeur;
    }

    public List<Reponse> getReponses() {
        return reponses;
    }

    public void setReponses(List<Reponse> reponses) {
        this.reponses = reponses;
    }

    public Date getDateQuestion() {
        return DateQuestion;
    }

    public void setDateQuestion(Date DateQuestion) {
        this.DateQuestion = DateQuestion;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + this.idQuestion;
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
        final Question other = (Question) obj;
        if (this.idQuestion != other.idQuestion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Question{" + "idQuestion=" + idQuestion + ", titre=" + titre + ", sujet=" + sujet + ", statuquestion=" + statuquestion + ", DateQuestion=" + DateQuestion + ", proposeur=" + proposeur + ", reponses=" + reponses + ", competences=" + competences + '}';
    }

   


   
        
    
}
