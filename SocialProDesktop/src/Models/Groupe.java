/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Mortadhafff
 */
public class Groupe {

    private int idGroupe;
    private String nom;
    private String description;
    private Date DateCreation;
    private String Image;
    private User owner;
    private List<User> users;    
    public Groupe() {
    }

    public Groupe(int idGroupe) {
        this.idGroupe = idGroupe;
    }

    public Groupe(String nom, String description, String Image, User owner) {
        this.nom = nom;
        this.description = description;
        this.Image = Image;
        this.owner = owner;
    }

    public Groupe(int idGroupe, String nom, String description, String Image, User owner) {
        this.idGroupe = idGroupe;
        this.nom = nom;
        this.description = description;
        this.Image = Image;
        this.owner = owner;
    }    

    public Groupe(int idGroupe, String nom, String description, String Image, User owner, List<User> users) {
        this.idGroupe = idGroupe;
        this.nom = nom;
        this.description = description;
        this.Image = Image;
        this.owner = owner;
        this.users = users;
    }

    public Groupe(int idGroupe, String nom, String description, Date DateCreation, String Image, User owner, List<User> users) {
        this.idGroupe = idGroupe;
        this.nom = nom;
        this.description = description;
        this.DateCreation = DateCreation;
        this.Image = Image;
        this.owner = owner;
        this.users = users;
    }
    
    public int getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(int idGroupe) {
        this.idGroupe = idGroupe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreation() {
        return DateCreation;
    }

    public void setDateCreation(Date DateCreation) {
        this.DateCreation = DateCreation;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.idGroupe;
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
        final Groupe other = (Groupe) obj;
        if (this.idGroupe != other.idGroupe) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Groupe{" + "idGroupe=" + idGroupe + ", nom=" + nom + ", description=" + description + ", DateCreation=" + DateCreation + ", Image=" + Image + ", owner=" + owner + ", users=" + users + '}';
    }

}
