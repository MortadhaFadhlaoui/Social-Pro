/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import java.util.Date;



/**
 *
 * @author Mortadhafff
 */
public class Message {   
     private int idMessage;
    private String contenu;   
    private int Emetteur;
    private int Recepteur;
    private int Reding;  
    private String vu;  
     private Date dateEnvoie;

    public Message() {
    }

    public Message(int idMessage, String contenu, int Emetteur, int Recepteur, int Reding, String vu, Date dateEnvoie) {
        this.idMessage = idMessage;
        this.contenu = contenu;
        this.Emetteur = Emetteur;
        this.Recepteur = Recepteur;
        this.Reding = Reding;
        this.vu = vu;
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

    public int getEmetteur() {
        return Emetteur;
    }

    public void setEmetteur(int Emetteur) {
        this.Emetteur = Emetteur;
    }

    public int getRecepteur() {
        return Recepteur;
    }

    public void setRecepteur(int Recepteur) {
        this.Recepteur = Recepteur;
    }

    public int getReding() {
        return Reding;
    }

    public void setReding(int Reding) {
        this.Reding = Reding;
    }

    public String getVu() {
        return vu;
    }

    public void setVu(String vu) {
        this.vu = vu;
    }

    public Date getDateEnvoie() {
        return dateEnvoie;
    }

    public void setDateEnvoie(Date dateEnvoie) {
        this.dateEnvoie = dateEnvoie;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.idMessage;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
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
        return "Message{" + "idMessage=" + idMessage + ", contenu=" + contenu + ", Emetteur=" + Emetteur + ", Recepteur=" + Recepteur + ", Reding=" + Reding + ", vu=" + vu + ", dateEnvoie=" + dateEnvoie + '}';
    }
         
}
