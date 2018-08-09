/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import Entity.Publication;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.FontImage;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bechir23
 */
public class ShowPublicationForm extends BaseForm {

    public ShowPublicationForm() {
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }

//    public ShowPublicationForm(com.codename1.ui.util.Resources resourceObjectInstance) {
//        initGuiBuilderComponents(resourceObjectInstance);
//        getToolbar().setTitleComponent(
//                FlowLayout.encloseCenterMiddle(
//                        new Label("Les Taches", "Title")
//                )
//        );
//        installSidemenu(resourceObjectInstance);
//
//    }
    public ShowPublicationForm(com.codename1.ui.util.Resources resourceObjectInstance) {

        initGuiBuilderComponents(resourceObjectInstance);
        gui_separator1.setShowEvenIfBlank(true);
        gui_Label_1_1_1.setShowEvenIfBlank(true);

//        ScaleImageLabel sl = new ScaleImageLabel(resourceObjectInstance.getImage("skate-park.jpg"));
//        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
//        gui_imageContainer1.add(BorderLayout.CENTER, sl);
//        sl = new ScaleImageLabel(resourceObjectInstance.getImage("bridge.jpg"));
//        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
//        gui_imageContainer2.add(BorderLayout.CENTER, sl);
        installSidemenu(resourceObjectInstance);
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_PUBLIC, e -> {
        });

        FontImage.setMaterialIcon(gui_LA, FontImage.MATERIAL_LOCATION_ON);
        gui_LA.setIconPosition(BorderLayout.EAST);

        FontImage.setMaterialIcon(gui_newYork, FontImage.MATERIAL_LOCATION_ON);
        gui_newYork.setIconPosition(BorderLayout.EAST);

        gui_Text_Area_2.setRows(4);
        gui_Text_Area_2.setColumns(100);
        gui_Text_Area_2.setGrowByContent(false);
        gui_Text_Area_2.setEditable(false);
        gui_Text_Area_1.setRows(4);
        gui_Text_Area_1.setColumns(100);
        gui_Text_Area_1.setGrowByContent(false);
        gui_Text_Area_1.setEditable(false);
    }

    public ArrayList<Publication> getListPub(String json) {
        ArrayList<Publication> listPub = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> publications = j.parseJSON(new CharArrayReader(json.toCharArray()));

            // System.out.println();
            List<Map<String, Object>> list = (List<Map<String, Object>>) publications.get("publication");

            for (Map<String, Object> obj : list) {
                Publication pub = new Publication();
                pub.setId_publication(Integer.parseInt(obj.get("id_publication").toString()));
                // pub.setDate_publication(obj.get("date_publication"));
                pub.setContenu_publication(obj.get("contenu_publication").toString());

                listPub.add(pub);
            }

        } catch (IOException ex) {
        }
        return listPub;

    }

    protected boolean isCurrentShwoPub() {
        return true;
    }
    private com.codename1.ui.Container gui_Container_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
    private com.codename1.components.MultiButton gui_Multi_Button_1 = new com.codename1.components.MultiButton();
    private com.codename1.components.MultiButton gui_LA = new com.codename1.components.MultiButton();
    private com.codename1.ui.Container gui_imageContainer1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
    private com.codename1.ui.Container gui_Container_2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
    private com.codename1.ui.TextArea gui_Text_Area_1 = new com.codename1.ui.TextArea();
    private com.codename1.ui.Button gui_Button_1 = new com.codename1.ui.Button();
    private com.codename1.ui.Label gui_separator1 = new com.codename1.ui.Label();
    private com.codename1.ui.Container gui_null_1_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
    private com.codename1.components.MultiButton gui_null_1_1_1 = new com.codename1.components.MultiButton();
    private com.codename1.components.MultiButton gui_newYork = new com.codename1.components.MultiButton();
    private com.codename1.ui.Container gui_imageContainer2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
    private com.codename1.ui.Container gui_Container_3 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
    private com.codename1.ui.TextArea gui_Text_Area_2 = new com.codename1.ui.TextArea();
    private com.codename1.ui.Button gui_Button_2 = new com.codename1.ui.Button();
    private com.codename1.ui.Label gui_Label_1_1_1 = new com.codename1.ui.Label();

    private void initGuiBuilderComponents(Resources resourceObjectInstance) {
//        setLayout(new BorderLayout());
//        SpanLabel sp = new SpanLabel();
//        Container C1 = new Container(BoxLayout.y());
//        C1.setScrollableY(true);
//        C1.setName("C1");
//        C1.removeAll();
//        add(BorderLayout.CENTER, C1);
//         Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/mobile/select.php");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                String response = new String(con.getResponseData());
                System.out.println(response);

                for (Publication pub : getListPub(new String(con.getResponseData()))) {
//                    Container c = new Container(new BorderLayout());
//                    MultiButton m = new MultiButton();
//                    Label on = new Label();
//                    C1.add(c);
//                    c.setName("c");
//                    c.add(com.codename1.ui.layouts.BorderLayout.CENTER, m);
//
//                   // FontImage.setMaterialIcon(on, FontImage.MATERIAL_EVENT);
//
//                    c.add(com.codename1.ui.layouts.BorderLayout.EAST, on);
//                    m.setUIID("Label");
//                    m.setName("Multi_Button_1");
//                    String id = String.valueOf(pub.getId_publication());
//                    //String nom= u.getNom().substring(1, u.getNom().length()-1);
//                    m.setPropertyValue("line1", "Publication : " + pub.getContenu_publication()+ pub.getDate_publication());
//                  //  m.setPropertyValue("line4", id);
//                    m.setPropertyValue("uiid1", "Label");
//                    m.setPropertyValue("uiid2", "RedLabel");
//                    m.setPropertyValue("uiid3", "Label");
//                   // m.setPropertyValue("uiid4", "Label");
//                    Label l = new Label();
//                    C1.add(l);
//                    c.setName("c");
//
//                    l.setUIID("Separator");
//                    l.setName("separator1");
//                    l.setShowEvenIfBlank(true);
//                    m.addActionListener(new ActionListener() {
//                        @Override
//                        public void actionPerformed(ActionEvent evt) {
//
//                            // new EvenementForm().show();
//                          
//
//                        }
//                    });
//                    C1.revalidate();
//                    Button Btn2 = new Button("Partager sur facbook");
//                    Btn2.addActionListener(e -> {
//                        String accessToken = "EAACEdEose0cBAC8F8BbUsGwihJbSyCxF65ldxKRKsX9llGmAIJpt6382pgDvhJoEHSj5lFMK5AECnEIGHeZBoNJGxun1uUPZCnjViBS8GaDxvh0cqHZB53PO7ZC4eRodPZADPDg79cw8HAtzRHJ7L6sIyLaPXI7kfuaznQVHp2kMBaBZAIq8hEWm6vnbzetRMZD";
//                        FacebookClient fbClient = new DefaultFacebookClient(accessToken);
//                        FacebookType publication = fbClient.publish("me/feed", FacebookType.class,
//                                Parameter.with("message", pub.getContenu_publication()+"\t #Social_Pro ")
//                                
//                        );
//                        System.out.println("Votre evenement à été publié sur facebook");
//                        System.out.println("fb.com/" + publication.getId());
//                    });
//                    C1.add(Btn2);
//
//                }
//
//            }
//        });

                    // NetworkManager.getInstance().addToQueue(con);
                    //declaration 
                    com.codename1.ui.Container Container_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
                    com.codename1.components.MultiButton Multi_Button_1 = new com.codename1.components.MultiButton();
                    com.codename1.components.MultiButton LA = new com.codename1.components.MultiButton();
                    com.codename1.ui.Container imageContainer1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
                    com.codename1.ui.Container Container_2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
                    com.codename1.ui.TextArea Text_Area_1 = new com.codename1.ui.TextArea();
                    com.codename1.ui.Button Button_1 = new com.codename1.ui.Button();
                    com.codename1.ui.Label separator1 = new com.codename1.ui.Label();
                    com.codename1.ui.Container null_1_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
                    com.codename1.components.MultiButton null_1_1_1 = new com.codename1.components.MultiButton();
                    com.codename1.components.MultiButton newYork = new com.codename1.components.MultiButton();
                    com.codename1.ui.Container imageContainer2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
                    com.codename1.ui.Container Container_3 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
                    com.codename1.ui.TextArea Text_Area_2 = new com.codename1.ui.TextArea();
                    com.codename1.ui.Button Button_2 = new com.codename1.ui.Button();
                    com.codename1.ui.Label Label_1_1_1 = new com.codename1.ui.Label();
                    //end declaration
                    setLayout(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
                    setTitle("Trending");
                    setName("TrendingForm");
                    addComponent(Container_1);
                    Container_1.setName("Container_1");
                    Container_1.addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, Multi_Button_1);
                    Multi_Button_1.setUIID("Label");
                    Multi_Button_1.setName("Multi_Button_1");
                    Multi_Button_1.setIcon(resourceObjectInstance.getImage("contact-c.png"));
                    Multi_Button_1.setPropertyValue("line1", "Ami Koehler");
                    Multi_Button_1.setPropertyValue("line2", pub.getDate_publication());
                    Multi_Button_1.setPropertyValue("uiid1", "Label");
                    Multi_Button_1.setPropertyValue("uiid2", "RedLabel");

                    addComponent(imageContainer1);
                    imageContainer1.setName("imageContainer1");
                    imageContainer1.addComponent(com.codename1.ui.layouts.BorderLayout.SOUTH, Container_2);
                    Container_2.setName("Container_2");
                    Container_2.addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, Text_Area_1);
                    String id = String.valueOf(pub.getId_publication());

                    //gui_Text_Area_1.setText(" The park is complete with plenty of smooth banks to gain a ton of speed in the flow bowl.");
                    Text_Area_1.setPropertyValue("line1", "Publication : " + pub.getContenu_publication());
                    Text_Area_1.setUIID("SlightlySmallerFontLabelLeft");
                    Text_Area_1.setName("Text_Area_1");
                    Button_1.setText("");
                    Button_1.setUIID("Label");
                    Button_1.setName("Button_1");

                    separator1.setUIID("Separator");
                    separator1.setName("separator1");
                    null_1_1.setName("null_1_1");
                    imageContainer2.setName("imageContainer2");
                    Label_1_1_1.setUIID("Separator");
                    Label_1_1_1.setName("Label_1_1_1");

                }

            }

        });

        NetworkManager.getInstance().addToQueue(con);

    }
}
