/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import java.util.List;
import Models.Competence;
import Models.Question;
import Models.User;

/**
 *
 * @author Mortadhafff
 */
public interface IQuestionService extends IService<Question, Integer>{
    List<Question> getQuestionsByUser(Integer idUser);    
    void addQuestionCompetence(Integer idQuestion,Integer idCompetence);
    void deleteQuestionCompetence(Integer idQuestion);
    List<Competence> getCompetencesByQustion(Question question);  
    Question findbyTitre(String titre);
    List<Question> FindbyTitre(String titre);
    void UpdateQuestionCompetence(Integer idQuestion,Integer idCompetence);
    Question TestIsUser(Integer idUser,Integer idQuestion);
}
