/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import java.util.List;
import Models.Message;

/**
 *
 * @author Mortadhafff
 */
public interface IMessageService extends IService<Message, Integer>{
    List<Message> getUserMessage(Integer idUser);
    List<Message> getUserMessageGroupe(Integer idUser,Integer idGroupe);
    List<Message> getMessagebyGroup(Integer idGroupe);
}
