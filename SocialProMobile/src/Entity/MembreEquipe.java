/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;



/**
 *
 * @author dell
 */
public class MembreEquipe {
    
    
    private Equipe equipe;
    private User user;
    private int note;
    

    public MembreEquipe() {
    }

    public MembreEquipe(Equipe equipe, User user) {
        this.equipe = equipe;
        this.user = user;
    }

    public MembreEquipe(Equipe equipe, User user, int note) {
        this.equipe = equipe;
        this.user = user;
        this.note = note;
    }
    
    

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }
    


    @Override
    public String toString() {
        return "MembreEquipe{" + "equipe=" + equipe + ", user=" + user + ", note=" + note + '}';
    }

   
    
    
}
