/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Choura
 */
public class Profil {
    
    private int idProfil;
    private String dateNaissance;
    private String lieu;
    private String details;
    private String sexe;
    private String nationalite;    
    private int idUser;
    private String imageName;

    public Profil() {
    }

    public Profil(int idProfil, String dateNaissance, String lieu, String details, String sexe, String nationalite, int idUser, String imageName) {
        this.idProfil = idProfil;
        this.dateNaissance = dateNaissance;
        this.lieu = lieu;
        this.details = details;
        this.sexe = sexe;
        this.nationalite = nationalite;
        this.idUser = idUser;
        this.imageName = imageName;
    }

    public int getIdProfil() {
        return idProfil;
    }

    public void setIdProfil(int idProfil) {
        this.idProfil = idProfil;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.idProfil;
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
        final Profil other = (Profil) obj;
        if (this.idProfil != other.idProfil) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Profil{" + "idProfil=" + idProfil + ", dateNaissance=" + dateNaissance + ", lieu=" + lieu + ", details=" + details + ", sexe=" + sexe + ", nationalite=" + nationalite + ", idUser=" + idUser + ", imageName=" + imageName + '}';
    }    
}
