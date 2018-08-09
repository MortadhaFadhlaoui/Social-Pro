/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javafx.scene.layout.VBox;

/**
 *
 * @author Mortadhafff
 */
public class NavigatorData {
    private final static NavigatorData instance = new NavigatorData();

    public static NavigatorData getInstance() {
        return instance;
    }        
          
    private Question SelectedQusetion;
    private User Selecteduser;
    private Groupe selectedGroupe;
    public VBox VBox;
    
    public void setSelectedQusetion(Question selectedQuestion){
        this.SelectedQusetion=selectedQuestion;
    }
    
    public Question getSelectedQusetion() {
        return SelectedQusetion;
    }       
    public void setUserlogedIn (User user)
    {
        this.Selecteduser=user;
    }
     public User getUserlogedIn() {
        return Selecteduser;
    } 

    public void setSelectedGroupe(Groupe selectedGroupe) {
        this.selectedGroupe=selectedGroupe;
    }
    public  Groupe getSelectedGroupe()
    {
        return selectedGroupe;
    }
}
