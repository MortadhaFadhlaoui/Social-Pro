/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import IServices.IQuestionService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Models.Competence;
import Models.Question;
import Models.Reponse;
import Models.StatuQuestion;
import Models.User;
import Technique.DataSource;

/**
 *
 * @author Mortadhafff
 */
public class QuestionService implements IQuestionService {

    private Connection connection;
    private PreparedStatement ps;

    public QuestionService() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void add(Question question) {
        String req = "insert into question (titre,sujet,statut,id_user) values (?,?,?,?)";
        try {
            ps = connection.prepareStatement(req);
            ps.setString(1, question.getTitre());
            ps.setString(2, question.getSujet());
            ps.setString(3, question.getStatuquestion().toString());
            ps.setInt(4, question.getProposeur().getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer idQuestion) {
        String req = "delete from question where id_question =?";
        try {
            ps = connection.prepareStatement(req);
            ps.setInt(1, idQuestion);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Question findById(Integer idQuestion) {
        String req = "select * from question where id_question = ?";
        Question question = null;
        try {
            ps = connection.prepareStatement(req);
            ps.setInt(1, idQuestion);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                question = new Question(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4),StatuQuestion.valueOf(resultSet.getString(5)) , new User(resultSet.getInt(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return question;
    }

    @Override
    public List<Question> getAll() {
        String req = "select * from question";
        List<Question> questions = new ArrayList<>();
        try {
            ps = connection.prepareStatement(req);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                User proposeur = new UserService().findById(resultSet.getInt(2));
                
                Question question = new Question(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4), StatuQuestion.valueOf(resultSet.getString(5)),resultSet.getDate(6),proposeur,new ReponseService().getReponseByQustion(new Question(resultSet.getInt(1))));

                questions.add(question);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public List<Question> getQuestionsByUser(Integer idUser) {
        List<Question> questions = new ArrayList<>();
        try {
            String req = "select * FROM question WHERE id_user =?";
            ps = connection.prepareStatement(req);
            ps.setInt(1, idUser);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                 User proposeur = new UserService().findById(idUser);
                Question question = new Question(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4), StatuQuestion.valueOf(resultSet.getString(5)),resultSet.getDate(6),proposeur,new ReponseService().getReponseByQustion(new Question(resultSet.getInt(1))));
                questions.add(question);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }


    @Override
    public List<Competence> getCompetencesByQustion(Question question) {
        List<Competence> competences = new ArrayList<>();
        try {
            String req = "select * FROM question_competence where id_question=?";
            ps = connection.prepareStatement(req);
            ps.setInt(1, question.getIdQuestion());
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
    public void deleteQuestionCompetence(Integer idQuestion) {
         try {
            String req = "DELETE FROM question_competence WHERE id_question=?";
            ps = connection.prepareStatement(req);           
            ps.setInt(1, idQuestion);
            ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Question findbyTitre(String titre) {               
       String req = "select * from question where titre=?";
        Question question = null;
        try {
            ps = connection.prepareStatement(req);
                ps.setString(1, titre);
                ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                User proposeur = new UserService().findById(resultSet.getInt(2));
                question = new Question(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4), StatuQuestion.valueOf(resultSet.getString(5)),resultSet.getDate(6),proposeur,new ReponseService().getReponseByQustion(new Question(resultSet.getInt(1))));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return question;   
    }
    @Override
    public void addQuestionCompetence(Integer idQuestion, Integer idCompetence) {
         String req = "insert into question_competence (id_competence,id_question) values(?,?)";
            try {           
            ps = connection.prepareStatement(req);
            ps.setInt(1, idCompetence);
            ps.setInt(2, idQuestion);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Question> FindbyTitre(String titre) {
        String req = "select * from question where titre like concat('%',?,'%')";
        List<Question> questions = new ArrayList<>();
        try {
            ps = connection.prepareStatement(req);
            ps.setString(1, titre);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                User proposeur = new UserService().findById(resultSet.getInt(2));
                Question question = new Question(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4), StatuQuestion.valueOf(resultSet.getString(5)),resultSet.getDate(6),proposeur,new ReponseService().getReponseByQustion(new Question(resultSet.getInt(1))));
                questions.add(question);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public void update(int idQuestion, Question q) {
          try {
            String req = "UPDATE question SET titre=?,sujet=?,statut=? WHERE id_question=?";
            PreparedStatement ps = connection.prepareStatement(req);

            ps.setString(1, q.getTitre());
            ps.setString(2, q.getSujet());
            ps.setString(3, q.getStatuquestion().toString());
            ps.setInt(4,idQuestion);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void UpdateQuestionCompetence(Integer idQuestion, Integer idCompetence) {
         try {
            String req = "UPDATE question_competence SET id_competence=? WHERE id_question=?";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, idCompetence);
            ps.setInt(2,idQuestion);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
@Override
    public Question TestIsUser(Integer idUser,Integer idQuestion) {               
       String req = "select * from question where id_user=? and id_question=?";
        Question question = null;
        try {
            ps = connection.prepareStatement(req);
                ps.setInt(1, idUser);
                ps.setInt(2, idQuestion);
                ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                User proposeur = new UserService().findById(resultSet.getInt(2));
                question = new Question(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4), StatuQuestion.valueOf(resultSet.getString(5)),resultSet.getDate(6),proposeur,new ReponseService().getReponseByQustion(new Question(resultSet.getInt(1))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return question;   
    }   
    
}
