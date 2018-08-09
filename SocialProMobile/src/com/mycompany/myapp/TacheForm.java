/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import static com.codename1.io.Log.e;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
//import com.codename1.io.Properties;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.sun.mail.smtp.SMTPTransport;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author bechir23
 */
public class TacheForm extends BaseForm {

    private Form current;
    private Resources theme;

    public TacheForm() {
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }

    @Override
    protected boolean isCurrentShwoT() {
        return true;
    }

    public TacheForm(com.codename1.ui.util.Resources resourceObjectInstance) {
        initGuiBuilderComponents(resourceObjectInstance);

        getToolbar().setTitleComponent(
                FlowLayout.encloseCenterMiddle(
                        new Label("Tache", "Title")
                )
        );

        installSidemenu(resourceObjectInstance);

        //getToolbar().addCommandToRightBar("", resourceObjectInstance.getImage("toolbar-profile-pic.png"), e -> {});
    }

    private void initGuiBuilderComponents(Resources resourceObjectInstance) {
        setLayout(new com.codename1.ui.layouts.BorderLayout());
        Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        setTitle("Taches");
        setName("TachesForm");
        // TextField tfId_tache = new TextField("", "Id Tache");
//        Label l5 = new Label("Complexite:");
//             String[] characters3 = { "facile", "moyenne", "dificle" /* cropped */
//};
//Picker p3 = new Picker();
//p3.setStrings(characters3);
//p3.setSelectedString(characters3[0]);
//p3.addActionListener(e -> ToastBar.showMessage("You picked " + p3.getSelectedString(), FontImage.MATERIAL_INFO));
        TextField tfNom = new TextField("", "Nom");
        TextField tfDescription = new TextField("", "Description");
        TextField tfComplexite = new TextField("", "Complexite");
        TextField tfMail = new TextField("", "mail de responsable tache");
        addComponent(BorderLayout.CENTER, c1);
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.addActionListener(e -> ToastBar.showErrorMessage("Not implemented yet..."));
        // fab.bindFabToContainer(getContentPane());
        // f.add(tfId_tache);
        c1.add(tfNom);
        c1.add(tfDescription);
        // c1.add(p3);
        c1.add(tfComplexite);
        c1.add(tfMail);

        Button btnOk = new Button("Ajouter");
        // Button btnM = new Button("Envoyer Mail");

        c1.add(btnOk);
        // c1.add(btnM);
        c1.add(fab);
        btnOk.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                ConnectionRequest req = new ConnectionRequest();
                req.setUrl("http://localhost/pidev2017/insert2.php?nom=" + tfNom.getText() + "&description=" + tfDescription.getText() + "&complexite=" + tfComplexite.getText() + "");

                req.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        byte[] data = (byte[]) evt.getMetaData();
                        String s = new String(data);

                        if (s.equals("success")) {
                            Dialog.show("Confirmation", "ajout ok", "Ok", null);
                            new ShowTacheForm().show();
                        }
                    }

                });

                NetworkManager.getInstance().addToQueue(req);
                try {

                    Properties props = new Properties();
                    props.put("mail.transport.protocol", "smtp");
                    props.put("mail.smtps.host", "smtp.gmail.com");
                    props.put("mail.smtps.auth", "true");
                    Session session = Session.getInstance(props, null);

                    MimeMessage msg = new MimeMessage(session);

                    msg.setFrom(new InternetAddress("Mot de passe <my_email@myDomain.com>"));
                    msg.setRecipients(Message.RecipientType.TO, tfMail.getText());
                    System.out.println(tfMail.getText());
                    msg.setSubject("Social Pro");
                    msg.setSentDate(new Date(System.currentTimeMillis()));

                    String txt = "Bienvenue sur social pro ceci est votre tache ";

                    msg.setText(txt);
                    SMTPTransport st = (SMTPTransport) session.getTransport("smtps");
                    st.connect("smtp.gmail.com", "mohamedali.masmoudi@esprit.tn", "dali123");
                    st.sendMessage(msg, msg.getAllRecipients());

                    System.out.println("ServerResponse : " + st.getLastServerResponse());

                } catch (MessagingException ex) {
                }

            }
        });

    }
}
