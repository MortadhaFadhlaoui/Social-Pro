/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.codename1.components.FloatingActionButton;
import com.codename1.ui.Component;
import com.codename1.ui.TextField;
import com.codename1.ui.spinner.Picker;
import Entity.Profil;
import Entity.Proposition;
import Entity.User;

/**
 *
 * @author Choura
 */
public class Context {

    private final static Context instance = new Context();

    public static Context getInstance() {
        return instance;
    }

    public TextField a;
    public TextField b;
    public TextField c;
    public Picker d;
    public TextField e;
    public TextField f;
    
    public boolean visited = false;
    
    public int place = -1;
    
    public FloatingActionButton fab;
    public FloatingActionButton fabDelete;
    
    public int suppression = 0;
    
    public int selected = 1;
    
    public int i = 1;

    //Connected User
    private User user;

    public void setUser(User u) {
        user = u;
    }

    public User getUser() {
        return user;
    }

    //Connected User Profil
    private Profil profile;

    public void setProfil(Profil u) {
        profile = u;
    }

    public Profil getProfil() {
        return profile;
    }

    //Selected Trajet
    private Component toolbarComp;

    public void setToolbarComp(Component t) {
        toolbarComp = t;
    }

    public Component getToolbarComp() {
        return toolbarComp;
    }

    
    private Proposition p;
    private User u;

    public Proposition getP() {
        return p;
    }

    public void setP(Proposition p) {
        this.p = p;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }
    
    
}
