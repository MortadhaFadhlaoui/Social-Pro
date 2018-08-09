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
public class Trajet {
    
    private int idTrajet;
    private int idUserTrajet;
    private String depart;
    private String arrive;
    private String date;
    private String heure;
    private int nombrePlace;

    public Trajet() {
    }

    public Trajet(int idTrajet, int idUserTrajet, String depart, String arrive, String date, String heure, int nombrePlace) {
        this.idTrajet = idTrajet;
        this.idUserTrajet = idUserTrajet;
        this.depart = depart;
        this.arrive = arrive;
        this.date = date;
        this.heure = heure;
        this.nombrePlace = nombrePlace;
    }

    public int getIdTrajet() {
        return idTrajet;
    }

    public void setIdTrajet(int idTrajet) {
        this.idTrajet = idTrajet;
    }

    public int getIdUserTrajet() {
        return idUserTrajet;
    }

    public void setIdUserTrajet(int idUserTrajet) {
        this.idUserTrajet = idUserTrajet;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public int getNombrePlace() {
        return nombrePlace;
    }

    public void setNombrePlace(int nombrePlace) {
        this.nombrePlace = nombrePlace;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.idTrajet;
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
        final Trajet other = (Trajet) obj;
        if (this.idTrajet != other.idTrajet) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Trajet{" + "idTrajet=" + idTrajet + ", idUserTrajet=" + idUserTrajet + ", depart=" + depart + ", arrive=" + arrive + ", date=" + date + ", heure=" + heure + ", nombrePlace=" + nombrePlace + '}';
    }   
    
}
