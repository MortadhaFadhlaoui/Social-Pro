/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import IServices.IReponseService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Models.Question;
import Models.Reponse;
import Models.User;
import Models.Utilite;
import Technique.DataSource;

/**
 *
 * @author Mortadhafff
 */
public class ReponseService implements IReponseService{
    private Connection connection;
    private PreparedStatement ps;
    
    public ReponseService()
    {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void add(Reponse reponse) {
        String req="insert into reponse (id_reponse,contenu,utilite,id_question,id_user) values (?,?,?,?,?)";
        try {
            ps = connection.prepareStatement(req);
            ps.setInt(1, reponse.getIdReponse());
            ps.setString(2, reponse.getContenu());
            ps.setString(3, reponse.getUtilite().toString());
            ps.setInt(4, reponse.getQuestion().getIdQuestion());
            ps.setInt(5, reponse.getRepondeur().getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer idReponse) {
        String req = "delete from reponse where id_reponse =?";
        try {
            ps = connection.prepareStatement(req);
            ps.setInt(1, idReponse);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reponse findById(Integer idReponse) {
          String req = "select * from reponse where id_reponse = ?";
        Reponse reponse = null;
        try {
            ps = connection.prepareStatement(req);
            ps.setInt(1, idReponse);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                reponse = new Reponse(resultSet.getInt(1), resultSet.getString(4), Utilite.valueOf(resultSet.getString(5)), new Question(resultSet.getInt(2)), new User(resultSet.getInt(3)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reponse;
    }

    @Override
    public List<Reponse> getAll() {
            String req = "select * from reponse";
        List<Reponse> reponses = new ArrayList<>();
        try {
            ps = connection.prepareStatement(req);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
               Reponse reponse = new Reponse(resultSet.getInt(1), resultSet.getString(4), Utilite.valueOf(resultSet.getString(5)), new Question(resultSet.getInt(2)), new User(resultSet.getInt(3)));

                reponses.add(reponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reponses;
    }    

    @Override
    public List<Reponse> getReponseByQustion(Question question) {
         List<Reponse> reponses = new ArrayList<>();
        try {
            String req = "select * FROM reponse where id_question=?";
            ps = connection.prepareStatement(req);
            ps.setInt(1, question.getIdQuestion());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                User repondeur = new UserService().findById(resultSet.getInt(3));
                Reponse reponse = new Reponse(resultSet.getInt(1),resultSet.getString(4), Utilite.utile, question, repondeur);
                reponses.add(reponse);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return reponses;
    }

    @Override
    public void update(int id, Reponse b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
}
