/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import java.util.List;
import Models.Question;
import Models.Reponse;

/**
 *
 * @author Mortadhafff
 */
public interface IReponseService extends IService<Reponse, Integer>{
   List<Reponse> getReponseByQustion(Question question);
   
}
