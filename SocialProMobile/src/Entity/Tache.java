/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

/**
 *
 * @author bechir23
 */
public class Tache {
    private int id_tache;
    private String nom;
    private String description;
    private String complexite;

    public Tache(int id_tache, String nom, String description, String complexite) {
        this.id_tache = id_tache;
        this.nom = nom;
        this.description = description;
        this.complexite = complexite;
    }

    public Tache() {
    }

     public int getId_tache() {
        return id_tache;
    }
    public String getNom() {
        return nom;
    }
    

    public String getDescription() {
        return description;
    }
    
     public String getComplexite() {
        return complexite;
    }

    
    public void setId_tache(int id_tache) {
        this.id_tache = id_tache;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
   
    public void setDescription(String description) {
        this.description = description;
    }
    
     public void setComplexite(String complexite) {
        this.complexite = complexite;
    }


    

    @Override
    public String toString() {
        return "Tache{" + "id_tache=" + id_tache + ", nom=" + nom + ", description=" + description + ", complexite=" + complexite + '}';
    }

  
    
    
    
    
          
}
