/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import IServices.IMessageService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Models.Message;
import Models.User;
import Models.Vu;
import Technique.DataSource;

/**
 *
 * @author Mortadhafff
 */
public class MessageService implements IMessageService {

    private Connection connection;
    private PreparedStatement ps;

    public MessageService() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void add(Message message) {
        String req = "insert into message_groupe (image_name,id_emetteur,id_groupe) values (?,?,?)";
        try {
            ps = connection.prepareStatement(req);
            ps.setString(1, message.getContenu());
            ps.setInt(2, message.getEmetteur().getId());
            ps.setInt(3, message.getGroupe().getIdGroupe());         
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer idMessage) {
        String req = "delete from message_groupe where id_Message =?";
        try {
            ps = connection.prepareStatement(req);
            ps.setInt(1, idMessage);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Message findById(Integer idMessage) {
        String req = "select * from message_groupe where id_Message =?";
        Message message = null;
        try {
            ps = connection.prepareStatement(req);
            ps.setInt(1, idMessage);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                message = new Message(resultSet.getInt(3), resultSet.getString(5), new UserService().findById(resultSet.getInt(1)),new GroupeService().findById(resultSet.getInt(2)),resultSet.getDate(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public List<Message> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Message> getUserMessage(Integer idUser) {
        List<Message> messages = new ArrayList<>();
        try {
            String req = "select * FROM message_groupe WHERE id_emetteur=?";
            ps = connection.prepareStatement(req);
            ps.setInt(1, idUser);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {

                Message message = new Message(resultSet.getInt(3), resultSet.getString(5), new UserService().findById(resultSet.getInt(1)),new GroupeService().findById(resultSet.getInt(2)),resultSet.getDate(4));

                messages.add(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return messages;
    }
    @Override
    public List<Message> getMessagebyGroup(Integer idGroupe) {
        List<Message> messages = new ArrayList<>();
        try {
            String req = "select * FROM message_groupe WHERE id_groupe=?";
            ps = connection.prepareStatement(req);
            ps.setInt(1, idGroupe);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Message message = new Message(resultSet.getInt(3), resultSet.getString(5), new UserService().findById(resultSet.getInt(1)),new GroupeService().findById(resultSet.getInt(2)),resultSet.getDate(4));
                messages.add(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return messages;
    }
 @Override
    public List<Message> getUserMessageGroupe(Integer idUser,Integer idGroupe) {
        List<Message> messages = new ArrayList<>();
        try {
            String req = "select * FROM message_groupe WHERE id_emetteur=? and id_groupe=?";
            ps = connection.prepareStatement(req);
            ps.setInt(1, idUser);
            ps.setInt(2, idGroupe);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Message message = new Message(resultSet.getInt(3), resultSet.getString(5), new UserService().findById(resultSet.getInt(1)),new GroupeService().findById(resultSet.getInt(2)),resultSet.getDate(4));
                messages.add(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }    

    @Override
    public void update(int id, Message b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
