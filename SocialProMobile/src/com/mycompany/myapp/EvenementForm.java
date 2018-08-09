/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Calendar;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
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
import com.codename1.ui.spinner.DateSpinner;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.DatePicker;
import utils.Context;

/**
 *
 * @author ASUS
 */
public class EvenementForm extends BaseForm {

    Picker cld;

    public EvenementForm() {
       this(com.codename1.ui.util.Resources.getGlobalResources());

    }

    @Override
    protected boolean isCurrentEvenement() {
        return true;
    }

    public EvenementForm(com.codename1.ui.util.Resources resourceObjectInstance) {
        initGuiBuilderComponents(resourceObjectInstance);
        getToolbar().setTitleComponent(
                FlowLayout.encloseCenterMiddle(
                        new Label("Ajouter un  Evenments", "Title")
                )
        );

        getToolbar().addCommandToOverflowMenu("Mes invitations", null, e -> new InvitationForm().show());
        getToolbar().addCommandToOverflowMenu("Mes Evenements", null, e -> new ShowEvenementForm().show());
        getToolbar().addCommandToOverflowMenu("Mes Participation", null, e -> new ParticipationForm().show());

        installSidemenu(resourceObjectInstance);

    }

    private void initGuiBuilderComponents(Resources resourceObjectInstance) {
        UIBuilder.registerCustomComponent("Picker", Picker.class);
        UIBuilder.registerCustomComponent("DateSpinner", DateSpinner.class);

        setLayout(new com.codename1.ui.layouts.BorderLayout());
        Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        setTitle("Evenement");
        setName("EventForm");

        TextField tfNom = new TextField("", "Nom de l'evenement");
        TextField tflieu = new TextField("", "Lieu de l'evenement");
        DateSpinner dateDepart = new DateSpinner();
        Picker tftype = new Picker();
        tftype.setType(Display.PICKER_TYPE_STRINGS);
        tftype.setSelectedString("type evenement");
        tftype.setStrings("loisir", "Professionnel");
        TextField tfdescription = new TextField("", "Description");
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        addComponent(BorderLayout.CENTER, c1);

        c1.add(tfNom);
        c1.add(tflieu);
        c1.add(dateDepart);

        c1.add(tftype);

        c1.add(tfdescription);

        fab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ConnectionRequest req = new ConnectionRequest();
                req.setUrl("http://localhost/SocialPro1/insertEvent.php?nom=" + tfNom.getText() + "&dayDepart=" + dateDepart.getCurrentDay() + "&monthDepart=" + dateDepart.getCurrentMonth() + "&yearDepart=" + dateDepart.getCurrentYear() + "&lieu=" + tflieu.getText() + "&confidentialite=" + "Public" + "&type=" + tftype.getSelectedString() + "&id_organisateur=" + Context.getInstance().getUser().getId() + "&description=" + tfdescription.getText());

                req.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        byte[] data = (byte[]) evt.getMetaData();
                        String s = new String(data);

                        if (s.equals("success")) {
                            Dialog.show("Confirmation", "evenement ajouter avec succée", "Ok", null);

                            new ShowEvenementForm(resourceObjectInstance).show();
                        }
                    }
                });

                NetworkManager.getInstance().addToQueue(req);
            }
        });

        c1.add(fab);

    }

}
