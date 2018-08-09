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
public class Vote {
    
    private Proposition proposition;
    private User user;
    private t_avis avis;

    
    
    public Vote() {
    }
    

    public Vote(Proposition proposition, User user, t_avis avis) {
        this.proposition = proposition;
        this.user = user;
        this.avis = avis;
    }

    public Proposition getProposition() {
        return proposition;
    }

    public void setProposition(Proposition proposition) {
        this.proposition = proposition;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public t_avis getAvis() {
        return avis;
    }

    public void setAvis(t_avis avis) {
        this.avis = avis;
    }



    @Override
    public String toString() {
        return "Vote{" + "proposition=" + proposition + ", user=" + user + ", avis=" + avis + '}';
    }
     
    
    
}
