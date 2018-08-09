/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import Entity.Tache;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Container;
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

/**
 *
 * @author bechir23
 */
public class ShowTacheForm extends BaseForm {

    public ShowTacheForm() {
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }

    public ShowTacheForm(com.codename1.ui.util.Resources resourceObjectInstance) {
        initGuiBuilderComponents(resourceObjectInstance);
        getToolbar().setTitleComponent(
                FlowLayout.encloseCenterMiddle(
                        new Label("Les Taches", "Title")
                )
        );
        installSidemenu(resourceObjectInstance);

    }

    @Override
    protected boolean isCurrentShwoST() {
        return true;
    }

    public ArrayList<Tache> getListTaches(String json) {
        ArrayList<Tache> listTaches = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> taches = j.parseJSON(new CharArrayReader(json.toCharArray()));

            // System.out.println();
            List<Map<String, Object>> list = (List<Map<String, Object>>) taches.get("tache");

            for (Map<String, Object> obj : list) {
                Tache e = new Tache();
                e.setId_tache(Integer.parseInt(obj.get("id_tache").toString()));
                e.setNom(obj.get("nom").toString());
                e.setDescription(obj.get("description").toString());
                e.setComplexite(obj.get("complexite").toString());

                listTaches.add(e);
            }

        } catch (IOException ex) {
        }
        return listTaches;

    }

    private void initGuiBuilderComponents(Resources resourceObjectInstance) {
        setLayout(new BorderLayout());
        SpanLabel sp = new SpanLabel();
        Container C1 = new Container(BoxLayout.y());
        C1.setScrollableY(true);
        C1.setName("C1");
        C1.removeAll();
        add(BorderLayout.CENTER, C1);
//         Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev2017/select2.php");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                String response = new String(con.getResponseData());
                System.out.println(response);

                for (Tache u : getListTaches(new String(con.getResponseData()))) {
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
                    String id = String.valueOf(u.getId_tache());
                    //String nom= u.getNom().substring(1, u.getNom().length()-1);
                    m.setPropertyValue("line1", "Nom de la tache : " + u.getNom());
                    m.setPropertyValue("line2", "Description : " + u.getDescription());
                    m.setPropertyValue("line3", "Complexite : " + u.getComplexite());

                    //  m.setPropertyValue("line4", id);
                    m.setPropertyValue("uiid1", "Label");
                    m.setPropertyValue("uiid2", "RedLabel");
                    m.setPropertyValue("uiid3", "Label");
                    // m.setPropertyValue("uiid4", "Label");
                    Label l = new Label();
                    C1.add(l);
                    c.setName("c");

                    l.setUIID("Separator");
                    l.setName("separator1");
                    l.setShowEvenIfBlank(true);
                    m.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {

                            // new EvenementForm().show();
                        }
                    });
                    C1.revalidate();

                }

            }
        });

        NetworkManager.getInstance().addToQueue(con);

    }

}
