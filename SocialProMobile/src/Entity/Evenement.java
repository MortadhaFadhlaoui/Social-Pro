/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Date;

/**
 *
 * @author dell
 */
public class Evenement {
    private int id_evenement;
    private String nom;
    private Date date;
    private String lieu;
    private String confidentialite;
    private String type;
    private User user;
    private String Description;
    
    private String color;

    public Evenement() {
    }
    

    public Evenement(int id_evenement, String nom, Date date, String lieu, User user,String color) {
        this.id_evenement = id_evenement;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.user = user;
        this.color=color;
    }

    public Evenement(int id_evenement, String nom, String lieu, String confidevent, String typeEvent, String Description) {
        this.id_evenement = id_evenement;
        this.nom = nom;
        this.lieu = lieu;
        this.confidentialite = confidevent;
        this.type = typeEvent;
        this.Description = Description;
    }

    public Evenement(String nom, String lieu, String Description) {
        this.nom = nom;
        this.lieu = lieu;
        this.Description = Description;
    }

    public Evenement(int id_evenement, String nom, String lieu, String Description) {
        this.id_evenement = id_evenement;
        this.nom = nom;
        this.lieu = lieu;
        this.Description = Description;

    }
    
    public Evenement(int id_evenement, String nom, Date date, String lieu, String confidentialite, String type, User user, String Description, String color) {
        this.id_evenement = id_evenement;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.confidentialite = confidentialite;
        this.type = type;
        this.user = user;
        this.Description = Description;
        this.color = color;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
    
    public int getId_evenement() {
        return id_evenement;
    }

    public void setId_evenement(int id_evenement) {
        this.id_evenement = id_evenement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getConfidentialite() {
        return confidentialite;
    }

    public void setConfidentialite(String confidentialite) {
        this.confidentialite = confidentialite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.id_evenement;
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
        final Evenement other = (Evenement) obj;
        return this.id_evenement == other.id_evenement;
    }

    @Override
    public String toString() {
        return "Evenement{" + "id_evenement=" + id_evenement + ", nom=" + nom + ", date=" + date + ", lieu=" + lieu + ", confidentialite=" + confidentialite + ", type=" + type + ", user=" + user + ", color=" + color + '}';
    }
    
    
    
    
}
