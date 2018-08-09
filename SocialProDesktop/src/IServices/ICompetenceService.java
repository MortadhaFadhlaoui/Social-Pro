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
public interface ICompetenceService extends IService<Competence, Integer> {
    List<User> getUsersByCompetence(Competence competence);
    List<Question> getQuestionsByCompetence(Competence competence);
    Competence  findbynom(String nom);
    List<Competence> getsearch(String var);
}
