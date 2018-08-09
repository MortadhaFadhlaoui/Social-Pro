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
public class CommentaireProposition {
    
    private int id;
    private String text;
    private User user;
    private Proposition proposition;

    public CommentaireProposition(String text, User user, Proposition proposition) {
        this.text = text;
        this.user = user;
        this.proposition = proposition;
    }

    
    public CommentaireProposition(int id, String text, User user, Proposition proposition) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.proposition = proposition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Proposition getProposition() {
        return proposition;
    }

    public void setProposition(Proposition proposition) {
        this.proposition = proposition;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
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
        final CommentaireProposition other = (CommentaireProposition) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "CommentaireProposition{" + "id=" + id + ", text=" + text + ", user=" + user + ", proposition=" + proposition + '}';
    }
    
    
}
