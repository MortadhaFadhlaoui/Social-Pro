/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import Entity.Evenement;
import com.codename1.components.MultiButton;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.Context;

/**
 *
 * @author ASUS
 */
public class ShowEvenementForm extends BaseForm {

    static int idev;

    public ShowEvenementForm() {
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }
    
    @Override
    protected boolean isCurrentEvenement() {
        return true;
    }

    public ShowEvenementForm(com.codename1.ui.util.Resources resourceObjectInstance) {
        initGuiBuilderComponents(resourceObjectInstance);
        getToolbar().setTitleComponent(
                FlowLayout.encloseCenterMiddle(
                        new Label("Mes Evenments", "Title")
                )
        );
        installSidemenu(resourceObjectInstance);
        getToolbar().addCommandToOverflowMenu("Mes invitations", null, e -> new InvitationForm().show());
        getToolbar().addCommandToOverflowMenu("Mes Participation", null, e -> new ParticipationForm().show());
        getToolbar().addCommandToOverflowMenu("Ajouter un evenement", null, e -> new EvenementForm().show());
    }

    public ArrayList<Evenement> getListEvents(String json) {
        ArrayList<Evenement> listevents = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> evenements = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) evenements.get("evenement");

            for (Map<String, Object> obj : list) {
                Evenement e = new Evenement();
                e.setId_evenement(Integer.parseInt(obj.get("id_evenement").toString()));
                e.setNom(obj.get("nom").toString());
                e.setLieu(obj.get("lieu").toString());
                e.setDescription(obj.get("description").toString());

                listevents.add(e);
            }

        } catch (IOException ex) {
        }
        return listevents;

    }

    private void initGuiBuilderComponents(Resources resourceObjectInstance) {
        setLayout(new BorderLayout());

        Container C1 = new Container(BoxLayout.y());
        C1.setScrollableY(true);
        C1.setName("C1");
        C1.removeAll();
        add(BorderLayout.CENTER, C1);

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/SocialPro1/searchEvent.php?id_organisateur=" + Context.getInstance().getUser().getId());

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

//                String response = new String(con.getResponseData());
//                System.out.println(response);

                for (Evenement u : getListEvents(new String(con.getResponseData()))) {
                    Container c = new Container(new BorderLayout());
                    MultiButton m = new MultiButton();
                    Label on = new Label();
                    C1.add(c);
                    c.setName("c");
                    c.add(com.codename1.ui.layouts.BorderLayout.CENTER, m);

                    FontImage.setMaterialIcon(on, FontImage.MATERIAL_EVENT);

                    c.add(com.codename1.ui.layouts.BorderLayout.EAST, on);
                    m.setUIID("Label");
                    m.setName("Multi_Button_1");
                    
                    String id = String.valueOf(u.getId_evenement());
                    m.setPropertyValue("line1", "Nom de l'evenement : " + u.getNom());
                    m.setPropertyValue("line2", "Description : " + u.getDescription());
                    m.setPropertyValue("line3", "lieu : " + u.getLieu());
                    
                    m.setPropertyValue("uiid1", "Label");
                    m.setPropertyValue("uiid2", "RedLabel");
                    m.setPropertyValue("uiid3", "Label");

                    Label l = new Label();
                    C1.add(l);
                    c.setName("c");

                    l.setUIID("Separator");
                    l.setName("separator1");
                    l.setShowEvenIfBlank(true);
                    m.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {

                            if (Dialog.show("Confirm", "vous voulez vraiment supprimer cette évenement ?", "ok", "annuler")) {

                                ConnectionRequest req = new ConnectionRequest();
                                req.setUrl("http://localhost/SocialPro1/removeEvent.php?id_evenement="+id);

                                req.addResponseListener(new ActionListener<NetworkEvent>() {

                                    @Override
                                    public void actionPerformed(NetworkEvent evt) {
                                        byte[] data = (byte[]) evt.getMetaData();
                                        String s = new String(data);

                                        if (s.equals("success")) {
                                            Dialog.show("Confirmation", "evenement supprimer avec succée", "Ok", null);
                                            new ShowEvenementForm().show();
                                        }
                                    }
                                });

                                NetworkManager.getInstance().addToQueue(req);

                            }
                        }
                    });
                    C1.revalidate();

                }

            }
        });

        NetworkManager.getInstance().addToQueue(con);

    }

}
