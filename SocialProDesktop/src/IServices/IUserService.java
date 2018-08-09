/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import java.util.List;
import Models.Competence;
import Models.Groupe;
import Models.User;

/**
 *
 * @author Mortadhafff
 */
public interface IUserService extends IService<User, Integer>{
    List<Competence> getCompetencesByUser(User user);
    List<Groupe> getGroupesByUser(User user);
    void addCompetenceUser(Integer idCompetence,Integer idUser);
    void deleteCompetenceUser(Integer idCompetence,Integer idUser);
    User findByCIN(String CINUser);
    List<User> getUsersbyNom(String s);
    User findUserByName(String nom);
}
