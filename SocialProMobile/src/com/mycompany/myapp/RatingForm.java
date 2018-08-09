/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.FloatingActionButton;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.DateSpinner;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import java.text.SimpleDateFormat;
import utils.Context;

/**
 *
 * @author ASUS
 */
public class RatingForm extends BaseForm {

    public RatingForm() {
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }
    
    @Override
    protected boolean isCurrentEvenement() {
        return true;
    }

    public RatingForm(com.codename1.ui.util.Resources resourceObjectInstance) {
        initGuiBuilderComponents(resourceObjectInstance);
        getToolbar().setTitleComponent(
                FlowLayout.encloseCenterMiddle(
                        new Label("Notez un Evenments", "Title")
                )
        );
        getToolbar().addCommandToOverflowMenu("Mes invitations", null, e -> new InvitationForm().show());
        getToolbar().addCommandToOverflowMenu("Mes Evenements", null, e -> new ShowEvenementForm().show());
        getToolbar().addCommandToOverflowMenu("Mes Participation", null, e -> new ParticipationForm().show());
        getToolbar().addCommandToOverflowMenu("Ajouter un evenement", null, e -> new EvenementForm().show());

        installSidemenu(resourceObjectInstance);

    }

    private void initStarRankStyle(Style s, Image star) {
        s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
        s.setBorder(Border.createEmpty());
        s.setBgImage(star);
        s.setBgTransparency(0);

    }

    private Slider createStarRankSlider() {
        Slider starRank = new Slider();
        starRank.setEditable(true);
        starRank.setMinValue(0);
        starRank.setMaxValue(5);

        Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
        Style s = new Style(0xffff33, 0, fnt, (byte) 0);
        Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        s.setOpacity(100);
        s.setFgColor(0);
        Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
        initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
        starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));

        starRank.addPointerPressedListener((evt) -> {

            evt.getSource().toString();
        });

        return starRank;

    }

    private void initGuiBuilderComponents(Resources resourceObjectInstance) {
        UIBuilder.registerCustomComponent("Picker", Picker.class);
        UIBuilder.registerCustomComponent("DateSpinner", DateSpinner.class);

        setLayout(new com.codename1.ui.layouts.BorderLayout());
        Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        setTitle("Notez l' Evenement");
        setName("EventForm");
        Slider gg = createStarRankSlider();
        c1.add(FlowLayout.encloseCenter(gg));
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        addComponent(BorderLayout.CENTER, c1);

        Button btnOk = new Button("ajouter un evnement");

        fab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                double n = (double) gg.getProgress();
                if (Dialog.show("Confirmation", "Vous voulez vraiment attribuer la note " + n + " à l' evenement", "Ok", "annuler")) {

                    ConnectionRequest req = new ConnectionRequest();

                    req.setUrl("http://localhost/SocialPro1/insertRating.php?id_event=" + ParticipationForm.idev + "&id_user=" + Context.getInstance().getUser().getId() + "&rating=" + n);
                    req.addResponseListener(new ActionListener<NetworkEvent>() {

                        @Override
                        public void actionPerformed(NetworkEvent evt) {
                            byte[] data = (byte[]) evt.getMetaData();
                            String s = new String(data);

                            if (s.equals("success")) {
                                Dialog.show("Confirmation", "vous avez noté l'evenement", "Ok", null);
                                new ParticipationForm().show();
                            }
                        }
                    });

                    NetworkManager.getInstance().addToQueue(req);
                }
            }
        });

        c1.add(fab);

    }

}
