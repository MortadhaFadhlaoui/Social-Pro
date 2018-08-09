/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Date;

/**
 *
 * @author Mortadhafff
 */
public class Message {
    private int idMessage;
    private String contenu;   
    private User Emetteur;
    private Groupe groupe;
     private Date dateEnvoie;

    public Message() {
    }

    public Message(int idMessage) {
        this.idMessage = idMessage;
    }

    public Message(String contenu, User Emetteur, Groupe groupe, Date dateEnvoie) {
        this.contenu = contenu;
        this.Emetteur = Emetteur;
        this.groupe = groupe;
        this.dateEnvoie = dateEnvoie;
    }

    public Message(String contenu, User Emetteur, Groupe groupe) {
        this.contenu = contenu;
        this.Emetteur = Emetteur;
        this.groupe = groupe;
    }

    public Message(int idMessage, String contenu, User Emetteur, Groupe groupe, Date dateEnvoie) {
        this.idMessage = idMessage;
        this.contenu = contenu;
        this.Emetteur = Emetteur;
        this.groupe = groupe;
        this.dateEnvoie = dateEnvoie;
    }
   
   

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public User getEmetteur() {
        return Emetteur;
    }

    public void setEmetteur(User Emetteur) {
        this.Emetteur = Emetteur;
    }

    public Date getDateEnvoie() {
        return dateEnvoie;
    }

    public void setDateEnvoie(Date dateEnvoie) {
        this.dateEnvoie = dateEnvoie;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.idMessage;
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
        final Message other = (Message) obj;
        if (this.idMessage != other.idMessage) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Message{" + "idMessage=" + idMessage + ", contenu=" + contenu + ", Emetteur=" + Emetteur + ", groupe=" + groupe + ", dateEnvoie=" + dateEnvoie + '}';
    }

  
}
