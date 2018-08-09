/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import Entity.Profil;
import utils.Context;
import com.codename1.ui.spinner.Picker;

/**
 *
 * @author Choura
 */
public class ProfileForm extends BaseForm {

    public ProfileForm() {
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }

    @Override
    protected boolean isCurrentProfil() {
        return true;
    }

    public ProfileForm(Resources resourceObjectInstance) {

        EncodedImage encImg = EncodedImage.createFromImage(resourceObjectInstance.getImage("contact-a.png"), false);

        URLImage imgUrl = URLImage.createToStorage(encImg, Context.getInstance().getProfil().getImageName(), "http://localhost/MobilePHP/" + Context.getInstance().getProfil().getImageName());
        imgUrl.fetch();

        Profil profil = Context.getInstance().getProfil();

        setLayout(new BorderLayout());
        setUIID("StatsForm");
        installSidemenu(resourceObjectInstance);

        getToolbar().addCommandToRightBar("", imgUrl, e -> {
        });

        Container north = createTopGrid(resourceObjectInstance);
        Button toggle = new Button("");
        toggle.setUIID("CenterWhite");
        FontImage.setMaterialIcon(toggle, FontImage.MATERIAL_CHECK);
        toggle.getAllStyles().setMargin(0, 0, 0, 0);
        toggle.getAllStyles().setBorder(RoundBorder.create().
                rectangle(true).
                color(0x9b4c3f));
        toggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                String a = Context.getInstance().d.getText();
                String month = a.substring(a.indexOf(a.charAt(0)), a.indexOf('/'));
                String day = a.substring(a.indexOf('/') + 1, a.indexOf('/', a.indexOf('/') + 1));
                String y = Context.getInstance().d.getDate().toString();
                String year = y.substring(y.lastIndexOf(' '), y.length());
                String fulldate = year + "-" + month + "-" + day;

                ConnectionRequest con = new ConnectionRequest();
                con.setUrl("http://localhost/mobilephp/updateProfil"
                        + "?idProfil=" + profil.getIdProfil()
                        + "&dateNaissance=" + fulldate
                        + "&lieux=" + Context.getInstance().a.getText()
                        + "&sexe=" + Context.getInstance().b.getText()
                        + "&details=" + Context.getInstance().c.getText());
                con.addResponseListener(new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        String response = new String(con.getResponseData());
                        if (response.equals("success")) {
                            ConnectionRequest con1 = new ConnectionRequest();
                            con1.setUrl("http://localhost/mobilephp/updateUser"
                                    + "?id=" + Context.getInstance().getUser().getId()
                                    + "&username=" + Context.getInstance().e.getText()
                                    + "&password=" + Context.getInstance().f.getText());
                            con1.addResponseListener(new ActionListener<NetworkEvent>() {
                                @Override
                                public void actionPerformed(NetworkEvent evt) {
                                    String response = new String(con1.getResponseData());
                                    if (response.equals("success")) {
                                        ToastBar.showMessage("Profile Modifié avec Succès", FontImage.MATERIAL_INFO);
                                    } else {
                                        System.out.println(response);
                                    }
                                }
                            });
                            NetworkManager.getInstance().addToQueue(con1);
                        } else {
                            System.out.println(response);
                        }
                    }
                });
                NetworkManager.getInstance().addToQueue(con);

                profil.setDateNaissance(fulldate);
                profil.setSexe(Context.getInstance().b.getText());
                profil.setDetails(Context.getInstance().c.getText());
                profil.setLieu(Context.getInstance().a.getText());
                
                Context.getInstance().setProfil(profil);
                
                Context.getInstance().getUser().setUsername(Context.getInstance().e.getText());
                Context.getInstance().getUser().setPassword(Context.getInstance().f.getText());

                removeAll();
                add(BorderLayout.NORTH, north);
                add(BorderLayout.CENTER, circleContent(resourceObjectInstance));
                add(BorderLayout.SOUTH, south());
            }
        });

        Button placeholder = new Button("");
        placeholder.setUIID("CenterWhite");
        placeholder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                removeAll();
                add(BorderLayout.NORTH, gridOnlyElement(resourceObjectInstance, "Sauvegarder avec le Boutton en HAUT.", "Modification du Profile"));
                Style s = new Style();
                s.setFgColor(0xffffff);
                s.setBgTransparency(0);

                TextField lieu = new TextField(profil.getLieu());
                TextField sexe = new TextField(profil.getSexe());
                TextField detail = new TextField(profil.getDetails());
                TextField user = new TextField(Context.getInstance().getUser().getUsername());
                TextField pass = new TextField(Context.getInstance().getUser().getPassword(), null, 20, TextField.PASSWORD);
                Picker date = new Picker();

                user.setSelectedStyle(s);
                user.setUnselectedStyle(s);
                user.setPressedStyle(s);
                user.setUIID("SideCommandNoPad");

                pass.setSelectedStyle(s);
                pass.setUnselectedStyle(s);
                pass.setPressedStyle(s);
                pass.setUIID("SideCommandNoPad");

                Context.getInstance().a = lieu;
                Context.getInstance().b = sexe;
                Context.getInstance().c = detail;
                Context.getInstance().d = date;
                Context.getInstance().e = user;
                Context.getInstance().f = pass;

                lieu.setSelectedStyle(s);
                lieu.setUnselectedStyle(s);
                lieu.setPressedStyle(s);
                lieu.setUIID("SideCommandNoPad");

                sexe.setSelectedStyle(s);
                sexe.setUnselectedStyle(s);
                sexe.setPressedStyle(s);
                sexe.setUIID("SideCommandNoPad");

                detail.setSelectedStyle(s);
                detail.setUnselectedStyle(s);
                detail.setPressedStyle(s);
                detail.setUIID("SideCommandNoPad");

                String a = profil.getDateNaissance();
                String year = (a.substring(a.indexOf(a.charAt(0)), a.indexOf('-'))).substring(2, 4);
                String month = a.substring(a.indexOf('-') + 1, a.indexOf('-', a.indexOf('-') + 1));
                String day = a.substring(a.indexOf('-', a.indexOf('-') + 1) + 1, a.length());
                String fulldate = month + "/" + day + "/" + year;

                date.setText(fulldate);
                date.setSelectedStyle(s);
                date.setUnselectedStyle(s);
                date.setPressedStyle(s);
                date.setUIID("SideCommandNoPad");

                s.setFgColor(0xe77c6e);

                add(BorderLayout.CENTER,
                        FlowLayout.encloseCenterMiddle(
                                BoxLayout.encloseY(
                                        GridLayout.encloseIn(2, new Label("  Username :", FontImage.createMaterial(FontImage.MATERIAL_PEOPLE, s, 3), "RedLabel"), user),
                                        GridLayout.encloseIn(2, new Label("  Password :", FontImage.createMaterial(FontImage.MATERIAL_LOCK, s, 3), "RedLabel"), pass),
                                        GridLayout.encloseIn(2, new Label("  Date Naissance :", FontImage.createMaterial(FontImage.MATERIAL_CAKE, s, 3), "RedLabel"), date),
                                        GridLayout.encloseIn(2, new Label("  Lieu :", FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, 3), "RedLabel"), lieu),
                                        GridLayout.encloseIn(2, new Label("  Sexe :", FontImage.createMaterial(FontImage.MATERIAL_CHILD_CARE, s, 3), "RedLabel"), sexe),
                                        GridLayout.encloseIn(2, new Label("  Détails :", FontImage.createMaterial(FontImage.MATERIAL_LABEL, s, 3), "RedLabel"), detail)
                                )
                        )
                );
                add(BorderLayout.SOUTH, south());
            }
        });

        Container buttonGrid = GridLayout.encloseIn(2, toggle, placeholder);
        Label leftLabel = new Label("", "CenterWhite");

        FontImage.setMaterialIcon(leftLabel, FontImage.MATERIAL_CHECK);
        Label rightLabel = new Label("", "CenterWhite");

        FontImage.setMaterialIcon(rightLabel, FontImage.MATERIAL_UPDATE);
        Container labelGrid = GridLayout.encloseIn(2, leftLabel, rightLabel);

        labelGrid.getAllStyles()
                .setBorder(RoundBorder.create().
                        rectangle(true).
                        color(0xd27d61));

        getToolbar()
                .setTitleComponent(
                        FlowLayout.encloseCenterMiddle(
                                LayeredLayout.encloseIn(labelGrid, buttonGrid)
                        )
                );

        ActionListener al = e -> {
            if (buttonGrid.getComponentAt(0) == toggle) {
                toggle.remove();
                buttonGrid.add(toggle);
                buttonGrid.animateLayoutAndWait(150);
                FontImage.setMaterialIcon(toggle, FontImage.MATERIAL_UPDATE);
            } else {
                placeholder.remove();
                buttonGrid.add(placeholder);
                buttonGrid.animateLayoutAndWait(150);
                FontImage.setMaterialIcon(toggle, FontImage.MATERIAL_CHECK);
            }
        };

        toggle.addActionListener(al);

        placeholder.addActionListener(al);

        add(BorderLayout.NORTH, createTopGrid(resourceObjectInstance));
        add(BorderLayout.CENTER, circleContent(resourceObjectInstance));
        add(BorderLayout.SOUTH, south());
    }

    Container circleContent(Resources res
    ) {
        Profil profil = Context.getInstance().getProfil();

        //dateN
        Label lbl = new Label("Date Naissance", "StatsLabel");
        Label info = new Label(profil.getDateNaissance(), "LargeWhileLabel");
        Label darkRect = new Label(res.getImage("welcome-separator.png"), "StatsLabel");
        darkRect.setShowEvenIfBlank(true);
        Container box = BoxLayout.encloseY(
                lbl,
                FlowLayout.encloseCenter(info),
                FlowLayout.encloseCenter(darkRect)
        );
        box.getUnselectedStyle().setPaddingUnit(Style.UNIT_TYPE_DIPS);
        box.getUnselectedStyle().setPadding(4, 0, 4, 4);
        //lieu
        Label lbl1 = new Label("Lieu", "StatsLabel");
        Label info1 = new Label(profil.getLieu(), "LargeWhileLabel");
        Label darkRect1 = new Label(res.getImage("welcome-separator.png"), "StatsLabel");
        darkRect1.setShowEvenIfBlank(true);
        Container box1 = BoxLayout.encloseY(
                lbl1,
                FlowLayout.encloseCenter(info1),
                FlowLayout.encloseCenter(darkRect1)
        );
        box1.getUnselectedStyle().setPaddingUnit(Style.UNIT_TYPE_DIPS);
        box1.getUnselectedStyle().setPadding(4, 0, 4, 4);

        //dateN
        Label lbl2 = new Label("Sexe", "StatsLabel");
        Label info2 = new Label(profil.getSexe(), "LargeWhileLabel");
        Label darkRect2 = new Label(res.getImage("welcome-separator.png"), "StatsLabel");
        darkRect2.setShowEvenIfBlank(true);
        Container box2 = BoxLayout.encloseY(
                lbl2,
                FlowLayout.encloseCenter(info2),
                FlowLayout.encloseCenter(darkRect2)
        );
        box2.getUnselectedStyle().setPaddingUnit(Style.UNIT_TYPE_DIPS);
        box2.getUnselectedStyle().setPadding(4, 0, 4, 4);

        //sexe
        Label lbl3 = new Label("Détails", "StatsLabel");
        Label info3 = new Label(profil.getDetails(), "LargeWhileLabel");
        Label darkRect3 = new Label(res.getImage("welcome-separator.png"), "StatsLabel");
        darkRect3.setShowEvenIfBlank(true);
        Container box3 = BoxLayout.encloseY(
                lbl3,
                FlowLayout.encloseCenter(info3),
                FlowLayout.encloseCenter(darkRect3)
        );
        box3.getUnselectedStyle().setPaddingUnit(Style.UNIT_TYPE_DIPS);
        box3.getUnselectedStyle().setPadding(4, 0, 4, 4);

        Container scroll = BoxLayout.encloseY(
                box,
                box1,
                box2,
                box3
        );

        scroll.setScrollableY(true);
        return scroll;

    }

    Container gridElement(Resources res, String time,
            String label, boolean last
    ) {
        Container c = BorderLayout.centerAbsolute(
                BoxLayout.encloseY(
                        FlowLayout.encloseCenter(new Label(time, "LargeWhileLabel"),
                                new Label(" fois", "SmallWhileLabel")
                        ),
                        new Label(res.getImage("welcome-separator.png"), "CenterNoPadd"),
                        new Label(label, "StatsLabel")
                )
        );
        if (last) {
            c.getAllStyles().setBorder(Border.createCompoundBorder(null,
                    Border.createLineBorder(2, 0x5b636b), null, null));
        } else {
            c.getAllStyles().setBorder(Border.createCompoundBorder(null,
                    Border.createLineBorder(2, 0x5b636b), null,
                    Border.createLineBorder(2, 0x5b636b)));
        }
        return c;
    }

    Container gridOnlyElement(Resources res, String time,
            String label) {
        Container c = BorderLayout.centerAbsolute(
                BoxLayout.encloseY(
                        FlowLayout.encloseCenter(new Label(time, "SmallWhileLabel")
                        ),
                        new Label(res.getImage("welcome-separator.png"), "CenterNoPadd"),
                        new Label(label, "StatsLabel")
                )
        );
        c.getAllStyles().setBorder(Border.createCompoundBorder(null,
                Border.createLineBorder(2, 0x5b636b), null, null));

        return c;
    }

    Container createTopGrid(Resources res) {
        return GridLayout.encloseIn(3,
                gridElement(res, "5", "Publications", false),
                gridElement(res, "4", "Covoiturage", false),
                gridElement(res, "10", "Propositions", true));
    }

    private Container south() {
        Container c = BorderLayout.centerAbsolute(
                BoxLayout.encloseY(
                        FlowLayout.encloseCenter(
                                new Label("Dérnière Modification : 3 Mai 2017 11:50", "SmallWhileLabel")
                        )
                )
        );
        c.getAllStyles().setBorder(Border.createCompoundBorder(
                Border.createLineBorder(2, 0x5b636b), null, null, null));

        return c;
    }
}
