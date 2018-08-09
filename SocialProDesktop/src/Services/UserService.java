/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import IServices.IUserService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Models.Competence;
import Models.Groupe;
import Models.Role;
import Models.Statut;
import Models.User;
import Technique.DataSource;

/**
 *
 * @author Mortadhafff
 */
public class UserService implements IUserService{

     private Connection connection;
     private PreparedStatement ps;

    public UserService() {
        connection = DataSource.getInstance().getConnection();
    }
    
    @Override
    public void add(User user) {
        String req="insert into user (id,cin,nom,prenom,username,password,role,statut) values (?,?,?,?,?,?,?,?)";
        try {
            ps = connection.prepareStatement(req);
            ps.setInt(1, user.getId());
            ps.setString(2, user.getCin());
            ps.setString(3, user.getNom());
            ps.setString(4, user.getPrenom());
            ps.setString(5, user.getLogin());
            ps.setString(6, user.getPassword());
            ps.setString(7, user.getRole().toString());
            ps.setString(8, user.getStatus().toString());        
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        @Override
    public List<User> getAll() {
        String req = "select id, cin, nom, prenom, username, password, role, statut from user";
        List<User> users = new ArrayList<>();
        try {
            ps = connection.prepareStatement(req);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                
               User user = new User(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),Role.valueOf(resultSet.getString(7)),Statut.valueOf(resultSet.getString(8)));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    @Override
    public void delete(Integer idUser) {
        String req = "delete from user where id = ?";
         try {
             ps =  connection.prepareStatement(req);
             ps.setInt(1, idUser);
             ps.executeUpdate();
         } catch (Exception e) {
             e.printStackTrace();
         }    }

    @Override
    public User findById(Integer idUser) {
        String req = "select id, cin, nom, prenom, username, password, role, statut from user where id = ?";
        User user = null;
        try {
            ps = connection.prepareStatement(req);
            ps.setInt(1, idUser);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
               user = new User(idUser,resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),Role.valueOf(resultSet.getString(7)),Statut.valueOf(resultSet.getString(8)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;     
    }
    
     @Override
    public User findByCIN(String CINUser) {
        String req = "select id, cin, nom, prenom, username, password, role, statut from user where cin = ?";
        User user = null;
        try {
            ps = connection.prepareStatement(req);
            ps.setString(1, CINUser);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {                  
               user = new User(resultSet.getInt(1),CINUser, resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),Role.valueOf(resultSet.getString(7)),Statut.valueOf(resultSet.getString(8)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;     
    }
    @Override
    public void addCompetenceUser(Integer idCompetence,Integer idUser) {
        try {
            String req = "insert into competence_user(id_competence,id_user) values(?,?)";
            ps = connection.prepareStatement(req);

            ps.setInt(1, idCompetence);
            ps.setInt(2, idUser);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
        public void deleteCompetenceUser(Integer idCompetence,Integer idUser) {
        try {
            String req = "DELETE FROM competence_user WHERE id_competence=? and id_user=?";

            ps = connection.prepareStatement(req);

            ps.setInt(1, idCompetence);
            ps.setInt(2, idUser);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

    @Override
    public List<Competence> getCompetencesByUser(User user) {
        List<Competence> competences = new ArrayList<>();
        try {
            String req = "select * FROM competence_user where id_user =?";
            ps = connection.prepareStatement(req);
            ps.setInt(1, user.getId());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {

                Competence competence = new CompetenceService().findById(resultSet.getInt(1));
                competences.add(competence);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return competences;
    }

    @Override
    public List<Groupe> getGroupesByUser(User user) {
         List<Groupe> groupes = new ArrayList<>();
        try {
            String req = "select * FROM user_groupe where id_user =?";
            ps = connection.prepareStatement(req);
            ps.setInt(1, user.getId());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {

                Groupe groupe = new GroupeService().findById(resultSet.getInt(1));
                groupes.add(groupe);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return groupes;
    }

    @Override
    public List<User> getUsersbyNom(String s) {
        String req = "select id, cin, nom, prenom, username, password, role, statut from user where nom like concat('%',?,'%')";
        List<User> users = new ArrayList<>();
        try {
            ps = connection.prepareStatement(req);
            ps.setString(1, s);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {               
                User user = new User(resultSet.getInt(1), resultSet.getString(2), s, resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), Role.valueOf(resultSet.getString(7)),Statut.valueOf(resultSet.getString(8)));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findUserByName(String nom) {
        String req = "select id, cin, nom, prenom, username, password, role, statut from user where nom = ?";
        User user = null;
        try {
            ps = connection.prepareStatement(req);
            ps.setString(1, nom);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {                  
               user = new User(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),Role.valueOf(resultSet.getString(7)),Statut.valueOf(resultSet.getString(8)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;   
    }

    @Override
    public void update(int id, User b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
