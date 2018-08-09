/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import Entity.Proposition;
import Entity.Vote;
import Entity.t_avis;
import utils.Context;
import utils.NavigatorData;
import utils.chart;
import com.codename1.components.FloatingActionButton;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import static com.mycompany.myapp.SignInForm.getUser;
import static com.mycompany.myapp.SignInForm.getVotes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dell
 */
public class PropositionForm extends BaseForm {

    @Override
    protected boolean isCurrentForum() {
        return true;
    }

    public PropositionForm(com.codename1.ui.util.Resources resourceObjectInstance)  {
        Resources res3;
        try {
            res3 = Resources.open("/theme.res");
        UIManager.getInstance().setThemeProps(res3.getTheme("Theme"));
        refreshTheme();
        initGuiBuilderComponents(res3);
        installSidemenu(res3);
        } catch (IOException ex) {
        }

        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ASSESSMENT, e -> {

            ConnectionRequest req = new ConnectionRequest();
            req.setUrl("http://localhost/codename/selectProposition.php");
            req.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt2) {
                    try {
                        int j = 0;
                        int k = 0;
                        String[] s1 = new String[getListP(new String(req.getResponseData())).size()];
                        double[] d1 = new double[getListP(new String(req.getResponseData())).size() * 3];
                        double[] d2 = new double[getListP(new String(req.getResponseData())).size() * 3];
                        for (Proposition p : getListP(new String(req.getResponseData()))) {

                            s1[k] = p.getTitre();
                            int pour = 0;
                            int contre = 0;

                            for (Vote v : p.getVotes()) {
                                if (v.getAvis().equals(t_avis.pour)) {
                                    pour++;
                                } else {
                                    contre++;
                                }
                            }
                            d1[j] = pour;
                            d1[j + 1] = 0;
                            d1[j + 2] = 0;
                            d2[j] = contre;
                            d2[j + 1] = 0;
                            d2[j + 2] = 0;
                            j += 3;
                            k++;
                        }
                        chart c = new chart(resourceObjectInstance, d1, d2, s1);

                        c.show();
                    } catch (IOException ex) {
                    }
                }
            });
            NetworkManager.getInstance().addToQueueAndWait((req));

        });

        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        RoundBorder rb = (RoundBorder) fab.getUnselectedStyle().getBorder();
        rb.uiid(true);
        fab.bindFabToContainer(getContentPane());
        fab.addActionListener(e -> {
            Dialog d = new Dialog();
            Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container c1 = new Container(new BorderLayout());
            TextField titre = new TextField();
            titre.setHint("title");
            TextField sujet = new TextField();
            sujet.setHint("description");
            Button add = new Button("add");
            Button cancel = new Button("cancel");

            cancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    d.dispose();
                }
            });
            ConnectionRequest con = new ConnectionRequest();
            add.addActionListener((ActionListener) (ActionEvent evt) -> {
                if (!titre.getText().equals("") && !sujet.getText().equals("")) {
                    Context co = Context.getInstance();
                    con.setUrl("http://localhost/codename/insert.php?titre=" + titre.getText() + "&sujet=" + sujet.getText() + "&user=" + co.getU().getId());
                    con.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {
                            String res = new String(con.getResponseData());
                            if (res.equals("success")) {
                                titre.clear();
                                sujet.clear();

                                Dialog.show("Ajout", "Ajout avec succes", "ok", null);
                                d.dispose();
                                removeAll();
                                initGuiBuilderComponents(resourceObjectInstance);
                                revalidate();

                            } else {
                                Dialog.show("Erreur", "Erreur", "ok", null);
                            }
                        }
                    });
                    NetworkManager.getInstance().addToQueueAndWait((con));

                } else {
                    Dialog.show("Erreur", "veuillez remplir tous les champs", "ok", null);
                }

            });
            c1.add(BorderLayout.WEST, add);
            c1.add(BorderLayout.EAST, cancel);
            c.addComponent(titre);
            c.addComponent(sujet);
            c.add(c1);

            d.addComponent(c);
            d.setLayout(BoxLayout.y());
            d.show();

        });

        //getToolbar().addCommandToRightBar("", resourceObjectInstance.getImage("toolbar-profile-pic.png"), e -> {});
    }

    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {

        ConnectionRequest req = new ConnectionRequest();
        req.setUrl("http://localhost/codename/selectProposition.php");
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt2) {
                try {

                    for (Proposition p : getListP(new String(req.getResponseData()))) {
                        //proposition for
                        com.codename1.ui.Container Container_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
                        com.codename1.ui.Container conta = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
                        com.codename1.components.MultiButton Multi_Button_1 = new com.codename1.components.MultiButton();
                        com.codename1.components.MultiButton LA = new com.codename1.components.MultiButton();
                        com.codename1.components.MultiButton LAA = new com.codename1.components.MultiButton();
                        com.codename1.ui.Container imageContainer1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
                        com.codename1.ui.Container Container_2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
                        com.codename1.ui.TextArea Text_Area_1 = new com.codename1.ui.TextArea();
                        com.codename1.ui.Label separator1 = new com.codename1.ui.Label();
                        FontImage.setMaterialIcon(LA, FontImage.MATERIAL_THUMB_UP);
                        FontImage.setMaterialIcon(LAA, FontImage.MATERIAL_THUMB_DOWN);
                        setLayout(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
                        setTitle("Proposition");
                        setName("PropositionForm");
                        addComponent(Container_1);
                        Container_1.setName("Container_1");
                        Container_1.addComponent(com.codename1.ui.layouts.BorderLayout.WEST, Multi_Button_1);
                        Container_1.addComponent(com.codename1.ui.layouts.BorderLayout.EAST, conta);

                        conta.setName("Conta");
                        boolean b = false;
                        int pour = 0;
                        int contre = 0;
                        for (Vote v : p.getVotes()) {
                            if (v.getUser().getId() == Context.getInstance().getU().getId()) {
                                b = true;
                            }
                            if (v.getAvis().equals(t_avis.pour)) {
                                pour++;
                            } else {
                                contre++;
                            }
                        }
                        //
                        ConnectionRequest conv = new ConnectionRequest();
                        Context cov = Context.getInstance();
                        LA.addActionListener((ActionListener) (ActionEvent evt) -> {
                            conv.setUrl("http://localhost/codename/addVote.php?id=" + p.getIdPropsition() + "&avis=pour&user=" + cov.getU().getId());
                            conv.addResponseListener(new ActionListener<NetworkEvent>() {
                                @Override
                                public void actionPerformed(NetworkEvent evt) {
                                    String res = new String(conv.getResponseData());
                                    if (res.equals("success")) {
                                        //mail
                                        ConnectionRequest mail = new ConnectionRequest();
                                        mail.setUrl("http://localhost/pidev2017/mail.php?message=" + NavigatorData.getInstance().getUserlogedIn().getUsername()+ " a voté pour votre proposition" + "&subject=" + p.getTitre() + "&email=" + p.getOwner().getEmail() + "");
                                        NetworkManager.getInstance().addToQueue(mail);
                                        Dialog.show("Ajout", " vote ajoute avec succes", "ok", null);

                                        LA.setEnabled(false);
                                        LAA.setEnabled(false);
                                        removeAll();
                                        initGuiBuilderComponents(resourceObjectInstance);
                                        revalidate();

                                    } else {
                                        Dialog.show("Erreur", "Erreur", "ok", null);
                                    }
                                }
                            });
                            NetworkManager.getInstance().addToQueue((conv));
                        });
                        LAA.addActionListener((ActionListener) (ActionEvent evt) -> {
                            conv.setUrl("http://localhost/codename/addVote.php?id=" + p.getIdPropsition() + "&avis=contre&user=" + cov.getU().getId());
                            conv.addResponseListener(new ActionListener<NetworkEvent>() {
                                @Override
                                public void actionPerformed(NetworkEvent evt) {
                                    String res = new String(conv.getResponseData());
                                    if (res.equals("success")) {
                                        //mail
                                        ConnectionRequest mail = new ConnectionRequest();
                                        mail.setUrl("http://localhost/pidev2017/mail.php?message=" + NavigatorData.getInstance().getUserlogedIn().getUsername()+ " a voté contre votre proposition" + "&subject=" + p.getTitre() + "&email=" + p.getOwner().getEmail() + "");
                                        NetworkManager.getInstance().addToQueue(mail);
                                        Dialog.show("Ajout", " vote ajoute avec succes", "ok", null);
                                        
                                        LA.setEnabled(false);
                                        LAA.setEnabled(false);
                                        removeAll();
                                        initGuiBuilderComponents(resourceObjectInstance);
                                        revalidate();
                                    } else {
                                        Dialog.show("Erreur", "Erreur", "ok", null);
                                    }
                                }
                            });
                            NetworkManager.getInstance().addToQueue((conv));
                        });
                        LA.setPropertyValue("line1", String.valueOf(pour));
                        LAA.setPropertyValue("line1", String.valueOf(contre));
                        LA.setPropertyValue("uiid1", "Label");
                        LAA.setPropertyValue("uiid1", "RedLabel");
                        if (b) {
                            LA.setEnabled(false);
                            LAA.setEnabled(false);
                        }
                        conta.addComponent(com.codename1.ui.layouts.BorderLayout.NORTH, LA);
                        conta.addComponent(com.codename1.ui.layouts.BorderLayout.SOUTH, LAA);
                        Multi_Button_1.setUIID("Label");
                        Multi_Button_1.setName("Multi_Button_1");

                        EncodedImage enc = EncodedImage.createFromImage(resourceObjectInstance.getImage("contact-a.png"), false);
                        URLImage url = URLImage.createToStorage(enc, p.getOwner().getImageName(), "http://localhost/" + p.getOwner().getImageName());

                        url.fetch();

                        Multi_Button_1.setIcon(url);
                        Multi_Button_1.setPropertyValue("line1", p.getOwner().getUsername());
                        Multi_Button_1.setPropertyValue("line2", p.getTitre());
                        Multi_Button_1.setPropertyValue("uiid1", "Label");
                        Multi_Button_1.setPropertyValue("uiid2", "RedLabel");

                        Container_2.addComponent(com.codename1.ui.layouts.BorderLayout.WEST, Text_Area_1);
                        Text_Area_1.setText(p.getSujet());
                        Text_Area_1.setUIID("SlightlySmallerFontLabelLeft");
                        Text_Area_1.setEnabled(false);
                        addComponent(Container_2);
                        addComponent(separator1);

                        Container_1.revalidate();
                        Container_1.refreshTheme();
                        // end
                    }

                } catch (IOException ex) {

                }

            }
        });
        NetworkManager.getInstance().addToQueue((req));
        revalidate();

    }

    public ArrayList<Proposition> getListP(String json) throws IOException {

        ArrayList<Proposition> listP = new ArrayList<>();

        JSONParser j = new JSONParser();
        Map<String, Object> props = j.parseJSON(new CharArrayReader(json.toCharArray()));
        try {
            List<Map<String, Object>> list = (List<Map<String, Object>>) props.get("proposition");
            for (Map<String, Object> obj : list) {
                Proposition p = new Proposition();
                p.setIdPropsition(Integer.parseInt(obj.get("id_proposition").toString()));

                ConnectionRequest con = new ConnectionRequest();
                con.setUrl("http://localhost/codename/selectUser.php?id=" + Integer.parseInt(obj.get("id_membre_proposition").toString()));
                con.addResponseListener(new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt) {

                        try {
                            p.setOwner(getUser(new String(con.getResponseData())));
                        } catch (ParseException ex) {
                            Log.getReportingLevel();
                        }

                    }

                });
                NetworkManager.getInstance().addToQueueAndWait(con);

                ConnectionRequest con2 = new ConnectionRequest();
                con2.setUrl("http://localhost/codename/selectVote.php?id=" + Integer.parseInt(obj.get("id_proposition").toString()));
                con2.addResponseListener(new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt) {

                        p.setVotes(getVotes(new String(con2.getResponseData())));

                    }

                });
                NetworkManager.getInstance().addToQueueAndWait(con2);

                p.setTitre(obj.get("titre").toString());
                p.setSujet(obj.get("sujet").toString());
                listP.add(p);

            }
        } catch (ClassCastException ce) {
            Map<String, Object> list = (Map<String, Object>) props.get("proposition");
            Proposition p = new Proposition();
            p.setIdPropsition(Integer.parseInt(list.get("id_proposition").toString()));

            ConnectionRequest con = new ConnectionRequest();
            con.setUrl("http://localhost/codename/selectUser.php?id=" + Integer.parseInt(list.get("id_membre_proposition").toString()));
            con.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {

                    try {
                        p.setOwner(getUser(new String(con.getResponseData())));
                    } catch (ParseException ex) {
                        Log.getReportingLevel();
                    }

                }

            });
            NetworkManager.getInstance().addToQueueAndWait(con);

            ConnectionRequest con2 = new ConnectionRequest();
            con2.setUrl("http://localhost/codename/selectVote.php?id=" + Integer.parseInt(list.get("id_proposition").toString()));
            con2.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {

                    p.setVotes(getVotes(new String(con2.getResponseData())));

                }

            });
            NetworkManager.getInstance().addToQueueAndWait(con2);

            p.setTitre(list.get("titre").toString());
            p.setSujet(list.get("sujet").toString());
            listP.add(p);

        }

        return listP;

    }

}
