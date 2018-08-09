/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.Map;
import Entity.Profil;
import Entity.User;
import utils.Context;
import utils.NavigatorData;

/**
 *
 * @author Choura
 */
public class ParsingJSON {

    public void getAccess(String username, String password) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/MobilePHP/getAccess.php?username=" + username + "&password=" + password);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                String response = new String(con.getResponseData());
                if (response.equals("Nothing{}")) {
                    Dialog.show("Réponse", "Wrong Username or Password", "Ok", null);
                } else {
                    User user = getResultUser(response);
                    Context.getInstance().setUser(user);
                    Context.getInstance().setU(user);
                    NavigatorData.getInstance().setUserlogedIn(user);

                    ConnectionRequest con1 = new ConnectionRequest();
                    con1.setUrl("http://localhost/MobilePHP/getProfil.php?id_user=" + Context.getInstance().getUser().getId());
                    con1.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {
                            String response = new String(con1.getResponseData());
                            if (response.equals("Nothing{}")) {
                                Dialog.show("Profile", "Merci de Mettre à Jours Votre Profile", "Ok", null);
                                new CovoiturageForm().show();
                            } else {
                                Context.getInstance().setProfil(getResultProfil(response));
                                new CovoiturageForm().show();
                            }
                        }
                    });
                    NetworkManager.getInstance().addToQueue(con1);
                }
            }
        });
        NetworkManager.getInstance().addToQueue(con);
    }

    public User getResultUser(String json) {

        User u = new User();
        try {
            JSONParser j = new JSONParser();

            Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
            Map<String, Object> list = (Map<String, Object>) user.get("user");
            u.setId(Integer.parseInt(list.get("id").toString()));
            u.setCin(list.get("cin").toString());
            u.setNom(list.get("nom").toString());
            u.setPrenom(list.get("prenom").toString());
            u.setUsername(list.get("username").toString());
            u.setPassword(list.get("password").toString());
            u.setEmail(list.get("email").toString());
        } catch (IOException ex) {
        }
        return u;
    }

    public Profil getResultProfil(String json) {

        Profil u = new Profil();
        try {
            JSONParser j = new JSONParser();

            Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
            Map<String, Object> list = (Map<String, Object>) user.get("profil");
            u.setIdProfil(Integer.parseInt(list.get("idProfil").toString()));
            u.setDateNaissance(list.get("dateNaissance").toString());
            u.setDetails(list.get("details").toString());
            u.setImageName(list.get("image_name").toString());
            u.setLieu(list.get("lieux").toString());
            u.setSexe(list.get("sexe").toString());
            u.setNationalite(list.get("nationalite").toString());
        } catch (IOException ex) {
        }
        return u;
    }
}
