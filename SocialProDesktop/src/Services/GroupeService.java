/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import IServices.IGroupeService;
import IServices.IUserService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Models.Groupe;
import Models.User;
import Technique.DataSource;

/**
 *
 * @author Mortadhafff
 */
public class GroupeService implements IGroupeService{

     private Connection connection;
    private PreparedStatement ps;
    
    public GroupeService()
    {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void add(Groupe groupe) {
        String req="insert into groupe (nom,description,picture,owner) values (?,?,?,?)";
        try {
            ps = connection.prepareStatement(req);
            ps.setString(1, groupe.getNom());
            ps.setString(2, groupe.getDescription());
            ps.setString(3, groupe.getImage());
            ps.setInt(4, groupe.getOwner().getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer idGroupe) {
        String req = "delete from groupe where id_groupe =?";
        try {
            ps = connection.prepareStatement(req);
            ps.setInt(1, idGroupe);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Groupe findById(Integer idGroupe) {
        String req = "select * from groupe where id_groupe = ?";
        Groupe groupe = null;
        try {
            ps = connection.prepareStatement(req);
            ps.setInt(1, idGroupe);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                    User Owner = new User(resultSet.getInt(2));
                    IGroupeService g = new GroupeService();
                   List<User> Users = g.getUsersByGroup(idGroupe);                    
                    groupe = new Groupe(idGroupe, resultSet.getString(3), resultSet.getString(4),resultSet.getDate(5),resultSet.getString(6) ,Owner,Users);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupe;
    }

    @Override
    public List<Groupe> getAll() {
        String req = "select * from groupe";
        List<Groupe> groupes = new ArrayList<>();
        try {
            ps = connection.prepareStatement(req);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {                
                  IUserService uu = new UserService();
                  User Owner = uu.findById(resultSet.getInt(2));
                  IGroupeService g = new GroupeService();
                  List<User> Users = g.getUsersByGroup(resultSet.getInt(1));                    
                  Groupe  groupe = new Groupe(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4),resultSet.getDate(5),resultSet.getString(6), Owner,Users);
                groupes.add(groupe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupes;    
    }
    //les services de table usergroupe (many to many) sans classe d'association
    @Override
    public void addUserGroupe(Integer idUser,Integer idGroupe) {
        try {
            String req = "insert into user_groupe(id_groupe,id_user) values(?,?)";
            ps = connection.prepareStatement(req);

            ps.setInt(1, idGroupe);
            ps.setInt(2, idUser);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUserGroupe(Integer idUser,Integer idGroupe) {
         try {
            String req = "DELETE FROM user_groupe WHERE id_groupe=? and id_user=?";

            ps = connection.prepareStatement(req);
            ps.setInt(1, idGroupe);
            ps.setInt(2, idUser);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<User> getUsersByGroup(Integer IdGroupe) {
         List<User> users = new ArrayList<>();
        try {
            String req = "select * FROM user_groupe where id_groupe =?";
            ps = connection.prepareStatement(req);
            ps.setInt(1, IdGroupe);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                User user = new UserService().findById(resultSet.getInt(2));
                users.add(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return users;
    }

    @Override
    public void update(int id, Groupe b) {
         try {
            String req = "UPDATE groupe SET nom=?,description=?,picture=? WHERE id_groupe=?";
            PreparedStatement ps = connection.prepareStatement(req);

            ps.setString(1, b.getNom());
            ps.setString(2, b.getDescription());
            ps.setString(3, b.getImage());
            ps.setInt(4,id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Groupe> FindbyName(String nom) {
            String req = "select * from groupe where nom like concat('%',?,'%')";
        List<Groupe> groups = new ArrayList<>();
        try {
            ps = connection.prepareStatement(req);
            ps.setString(1, nom);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
               IUserService uu = new UserService();
                  User Owner = uu.findById(resultSet.getInt(2));
                  IGroupeService g = new GroupeService();
                  List<User> Users = g.getUsersByGroup(resultSet.getInt(1));                    
                  Groupe  groupe = new Groupe(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4),resultSet.getDate(5),resultSet.getString(6), Owner,Users);
                  groups.add(groupe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groups;
    }
    @Override
    public Groupe findbyTitre(String nom) {               
       String req = "select * from groupe where nom=?";
        Groupe groupe = null;
        try {
            ps = connection.prepareStatement(req);
                ps.setString(1, nom);
                ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                    User Owner = new User(resultSet.getInt(2));
                    IGroupeService g = new GroupeService();
                   List<User> Users = g.getUsersByGroup(resultSet.getInt(1));                    
                    groupe = new Groupe(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4),resultSet.getDate(5),resultSet.getString(6), Owner,Users);                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupe;   
    }

    @Override
    public List<Groupe> FindbyOwner(Integer iduser) {
        String req = "select * from groupe where owner like concat('%',?,'%')";
        List<Groupe> groups = new ArrayList<>();
        try {
            ps = connection.prepareStatement(req);
            ps.setInt(1, iduser);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                  IUserService uu = new UserService();
                  User Owner = uu.findById(resultSet.getInt(2));
                  IGroupeService g = new GroupeService();
                  List<User> Users = g.getUsersByGroup(resultSet.getInt(1));                    
                  Groupe  groupe = new Groupe(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4),resultSet.getDate(5),resultSet.getString(6), Owner,Users);
                  groups.add(groupe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groups;
    }

    @Override
    public Groupe ChechGroupeOwner(Integer idGroupe, Integer idUser) {
        String req = "select * from user_groupe where id_groupe=? and id_user=?";
        Groupe groupe = null;
        try {
            ps = connection.prepareStatement(req);
            ps.setInt(1, idGroupe);
            ps.setInt(2, idUser);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                  groupe = new Groupe(resultSet.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }   
        return groupe;
    }

    @Override
    public List<Groupe> FindbyUser(Integer iduser) {
        String req = "select * from user_groupe where id_user=?";
        List<Groupe> groups = new ArrayList<>();
        try {
            ps = connection.prepareStatement(req);
            ps.setInt(1, iduser);            
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {                                
                  Groupe  groupe = new Groupe(resultSet.getInt(1));
                   groups.add(groupe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }   
        return groups;
    }
    
    
}
