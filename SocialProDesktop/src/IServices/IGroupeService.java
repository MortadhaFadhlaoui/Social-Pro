/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import java.util.List;
import Models.Groupe;
import Models.User;

/**
 *
 * @author Mortadhafff
 */
public interface IGroupeService extends IService<Groupe, Integer>{
    List<User> getUsersByGroup(Integer idGroupe);
    void addUserGroupe(Integer idUser,Integer idGroupe);
    void deleteUserGroupe(Integer idUser,Integer idGroupe);
    List<Groupe> FindbyName(String nom);
    List<Groupe> FindbyOwner(Integer iduser);
    Groupe findbyTitre(String nom);  
    Groupe ChechGroupeOwner(Integer idGroupe,Integer idUser);
    List<Groupe> FindbyUser(Integer iduser);
}
