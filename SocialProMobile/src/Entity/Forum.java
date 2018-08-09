/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import java.util.Date;

/**
 *
 * @author bechir23
 */
public class Forum {
    private int id_publication;
    private String contenu_publication;
    private Date date_publication;
   

    public Forum(int id_publication, String contenu_publication, Date date_publication) {
        this.id_publication = id_publication;
        this.contenu_publication = contenu_publication;
        this.date_publication = date_publication;

        
    }

    public Forum() {
    }

     public int getId_publication() {
        return id_publication;
    }
    public String getContenu_publication() {
        return contenu_publication;
    }
     public Date getDate_publication() {
        return date_publication;
    }
    

   

    
    public void setId_publication(int id_publication) {
        this.id_publication = id_publication;
    }
    public void setContenu_publication(String contenu_publication) {
        this.contenu_publication = contenu_publication;
    }
     public void setDate_publication(Date date_publication) {
        this.date_publication = date_publication;
    }
   
    


    

    @Override
    public String toString() {
        return "Forum{" + "id_publication=" + id_publication+ ", contenu_publication=" + contenu_publication + ", date_publication=" + date_publication + '}';
    }

  
    
    
    
    
          
}
