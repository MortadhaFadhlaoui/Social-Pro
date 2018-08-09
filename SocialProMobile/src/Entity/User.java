/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Choura
 */
public class User {

    private int id;
    private String username;
    private String email;
    private String password;
    private String cin;
    private String nom;
    private String prenom;
    private String role;
    private t_statut statut;
    private t_etat etat;
    private Date lastLogin;
    private Date lastLogout;
    private List<Proposition> propositions;
    private List<Equipe> equipes;
    private List<Vote> votes;
    private List<Competence> competences;
    private String imageName;
    private List<Message> messages;
    private Profil profil;

    public User() {
    }

    public User(int id, String username, String email, String password, String cin, String nom, String prenom) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
    }
    
     public User(int id) {
        this.id = id;
    }

    public User(String login, String imageName) {
        this.username = login;
        this.imageName = imageName;
    }
    
    public User(int id, String email, String nom, String prenom, String imageName) {
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.imageName = imageName;
    }

    public User(int id, String cin, String email, String nom, String prenom, String login, String password, String imageName, Profil profil, Date lastLogin, Date lastLogout, List<Message> messages) {
        this.id = id;
        this.cin = cin;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.username = login;
        this.password = password;
        this.imageName = imageName;
        this.profil = profil;
        this.lastLogin = lastLogin;
        this.lastLogout = lastLogout;
        this.messages = messages;
    }
        
    public User(int id, String cin, String nom, String prenom, String login, String password) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.username = login;
        this.password = password;
    }

    public User(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }
    
    

    public User(String cin, String nom, String prenom, String login, String password, String role, t_statut statut, t_etat etat) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.username = login;
        this.password = password;
        this.role = role;
        this.statut = statut;
        this.etat = etat;
    }

    public User(int id, String cin, String nom, String prenom, String login, String password, String role, t_statut statut, t_etat etat) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.username = login;
        this.password = password;
        this.role = role;
        this.statut = statut;
        this.etat = etat;
    }

    public User(int id, String cin, String nom, String prenom, String username, String password, String role, t_statut statut, t_etat etat, List<Proposition> propositions, List<Equipe> equipes, List<Vote> votes) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.password = password;
        this.role = role;
        this.statut = statut;
        this.etat = etat;
        this.propositions = propositions;
        this.equipes = equipes;
        this.votes = votes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.id;
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
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", cin=" + cin + ", nom=" + nom + ", prenom=" + prenom + '}';
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public t_statut getStatut() {
        return statut;
    }

    public void setStatut(t_statut statut) {
        this.statut = statut;
    }

    public t_etat getEtat() {
        return etat;
    }

    public void setEtat(t_etat etat) {
        this.etat = etat;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getLastLogout() {
        return lastLogout;
    }

    public void setLastLogout(Date lastLogout) {
        this.lastLogout = lastLogout;
    }

    public List<Proposition> getPropositions() {
        return propositions;
    }

    public void setPropositions(List<Proposition> propositions) {
        this.propositions = propositions;
    }

    public List<Equipe> getEquipes() {
        return equipes;
    }

    public void setEquipes(List<Equipe> equipes) {
        this.equipes = equipes;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public List<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }
    
    
}
