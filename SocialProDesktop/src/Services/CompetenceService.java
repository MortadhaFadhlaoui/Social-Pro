/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import IServices.ICompetenceService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Models.Competence;
import Models.Question;
import Models.User;
import Technique.DataSource;

/**
 *
 * @author Mortadhafff
 */
public class CompetenceService implements ICompetenceService{

    private Connection connection;
    private PreparedStatement ps;
    
    public CompetenceService()
    {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void add(Competence competence) {
        String req="insert into competence (id_competence,nom) values (?,?)";
        try {
            ps = connection.prepareStatement(req);
            ps.setInt(1, competence.getIdCompetence());
            ps.setString(2, competence.getNom());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer idcompetence) {
        String req = "delete from competence where id_competence =?";
        try {
            ps = connection.prepareStatement(req);
            ps.setInt(1, idcompetence);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Competence findById(Integer idcompetence) {
        String req = "select * from competence where id_competence =?";
        Competence competence = null;
        try {
            ps = connection.prepareStatement(req);
            ps.setInt(1, idcompetence);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                competence = new Competence(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return competence;
    }

    @Override
    public List<Competence> getAll() {
           String req = "select * from competence";
        List<Competence> competences = new ArrayList<>();
        try {
            ps = connection.prepareStatement(req);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
               Competence competence = new Competence(resultSet.getString(2));

                competences.add(competence);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return competences;   
    }
    @Override
    public List<User> getUsersByCompetence(Competence competence) {
        List<User> users = new ArrayList<>();
        try {
            String req = "select * FROM competence_user where id_competence =?";
            ps = connection.prepareStatement(req);
            ps.setInt(1, competence.getIdCompetence());
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
    public List<Question> getQuestionsByCompetence(Competence competence) {
       List<Question> questions = new ArrayList<>();
        try {
            String req = "select * FROM question_competence where id_competence=?";
            ps = connection.prepareStatement(req);
            ps.setInt(1, competence.getIdCompetence());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Question question = new QuestionService().findById(resultSet.getInt(2));
                questions.add(question);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return questions; 
    }

    @Override
    public Competence findbynom(String nom) {
       String req = "select * from competence where nom =?";
        Competence competence = null;
        try {
            ps = connection.prepareStatement(req);
            ps.setString(1, nom);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                competence = new Competence(resultSet.getInt(1), nom);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return competence;   
    }

    @Override
    public List<Competence> getsearch(String var) {
        String req = "select * from competence where nom like concat('%',?,'%')";
        List<Competence> competences = new ArrayList<>();
        try {
            ps = connection.prepareStatement(req);
            ps.setString(1, var);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
               Competence competence = new Competence(resultSet.getString(2));
                competences.add(competence);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return competences;   
    }

    @Override
    public void update(int id, Competence b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
