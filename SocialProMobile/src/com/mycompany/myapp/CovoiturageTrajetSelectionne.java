/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.maps.Coord;
import com.codename1.maps.MapComponent;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.SideMenuBar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.codename1.util.BigDecimal;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.mycompany.utils.MapContainer;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.util.converter.BigDecimalStringConverter;
import Entity.Trajet;
import utils.Context;

/**
 *
 * @author Choura
 */
public class CovoiturageTrajetSelectionne extends Form {

    private final Geocoder geocoder = new Geocoder();
    public static final String ACCOUNT_SID = "AC12c6946636670c568c97c0d46decfe90";
    public static final String AUTH_TOKEN = "1fb476981b6cc49913d0768acced6766";

    public CovoiturageTrajetSelectionne(Resources resourceObjectInstance, Trajet t) {

        getToolbar().setTitleComponent(
                FlowLayout.encloseCenterMiddle(
                        new Label("Réserver le Trajet", "Title")
                )
        );

        getToolbar().setBackCommand("back", new ActionListener<ActionEvent>() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new CovoiturageForm(resourceObjectInstance).showBack();
            }
        });

        setLayout(new BorderLayout());
        Style s = new Style();
        s.setFgColor(0xffffff);
        s.setBgTransparency(0);
        Button take = new Button("Prendre", FontImage.createMaterial(FontImage.MATERIAL_CHECK, s, 5));
        take.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ConnectionRequest con = new ConnectionRequest();
                con.setUrl("http://localhost/MobilePHP/addReservation.php"
                        + "?id_trajet=" + t.getIdTrajet()
                        + "&id_user=" + Context.getInstance().getUser().getId());
                con.addResponseListener(new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt) {

                        ConnectionRequest con1 = new ConnectionRequest();
                        con1.setUrl("http://localhost/MobilePHP/updateTrajet.php"
                                + "?id_trajet=" + t.getIdTrajet());
                        con1.addResponseListener(new ActionListener<NetworkEvent>() {
                            @Override
                            public void actionPerformed(NetworkEvent evt) {
                                new CovoiturageForm(resourceObjectInstance).show();
                                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

                                Message message = Message.creator(new PhoneNumber("+21658950157"),
                                        new PhoneNumber("+13344580504"),
                                        "L'utilisateur : " + Context.getInstance().getUser().getNom()
                                        + " à pris votre trajet de " + t.getDepart()
                                        + " à " + t.getArrive()).create();

                            }
                        });
                        NetworkManager.getInstance().addToQueue(con1);

                    }
                });
                NetworkManager.getInstance().addToQueue(con);
            }
        }
        );
        add(BorderLayout.SOUTH, take);
        add(BorderLayout.NORTH, FlowLayout.encloseCenterMiddle(new Label(t.getDepart().substring(0, t.getDepart().indexOf(",")) + " - " + t.getArrive().substring(0, t.getArrive().indexOf(",")))));

        UIManager.getInstance()
                .setThemeProps(resourceObjectInstance.getTheme(resourceObjectInstance.getThemeResourceNames()[0]));
        Display.getInstance()
                .setCommandBehavior(Display.COMMAND_BEHAVIOR_SIDE_NAVIGATION);
        UIManager.getInstance()
                .getLookAndFeel().setMenuBarClass(SideMenuBar.class
                );
        UIBuilder.registerCustomComponent(
                "MapComponent", MapComponent.class
        );

        final MapContainer cnt = new MapContainer("AIzaSyAqspLkLXoXLLHg0TopKhjsPZKJMkP7q-Q");

        String[] marker_1 = getLatLng(t.getDepart());
        String[] marker_2 = getLatLng(t.getArrive());

        Coord coordMarker_1 = new Coord(Float.parseFloat(marker_1[0]), Float.parseFloat(marker_1[1]));
        Coord coordMarker_2 = new Coord(Float.parseFloat(marker_2[0]), Float.parseFloat(marker_2[1]));

        Double centerLat, centerLng;
        if ((Double.valueOf(marker_1[0]) + Double.valueOf(marker_2[0])) / 2 > 0) {
            centerLat = (Double.valueOf(marker_1[0]) + Double.valueOf(marker_2[0])) / 2 - 0.141443160695405 - 0.55;
        } else {
            centerLat = (Double.valueOf(marker_1[0]) + Double.valueOf(marker_2[0])) / 2 + 0.141443160695405 + 0.55;
        }

        if ((Double.valueOf(marker_1[1]) + Double.valueOf(marker_2[1])) / 2 > 0) {
            centerLng = (Double.valueOf(marker_1[1]) + Double.valueOf(marker_2[1])) / 2 - 0.32958984376 - 1.15;
        } else {
            centerLng = (Double.valueOf(marker_1[1]) + Double.valueOf(marker_2[1])) / 2 + 0.32958984376 + 1.15;
        }

        Coord mapCenter = new Coord(centerLat, centerLng);

        s.setFgColor(
                0xff0000);
        s.setBgTransparency(
                0);
        FontImage markerImg = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, 3);

        cnt.addPath(coordMarker_1, coordMarker_2);

        cnt.addMarker(EncodedImage.createFromImage(markerImg, false),
                coordMarker_1,
                t.getDepart().substring(0, t.getDepart().indexOf(",")),
                t.getDepart(),
                null);

        cnt.addMarker(EncodedImage.createFromImage(markerImg, false),
                coordMarker_2,
                t.getArrive().substring(0, t.getDepart().indexOf(",")),
                t.getArrive(),
                null);

        Button b = new Button("ge");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println(cnt.getCameraPosition());
            }
        });

        cnt.setCameraPosition(mapCenter);
        add(BorderLayout.CENTER, cnt);
    }

    private String[] getLatLng(String address) {

        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(address).setLanguage("en").getGeocoderRequest();
        GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
        String latLng = geocoderResponse.getResults().get(0).getGeometry().getLocation().toString();
        String lat = latLng.substring(latLng.indexOf('=') + 1, latLng.indexOf(','));
        String partLng = latLng.substring(latLng.indexOf(',') + 2);
        String lng = partLng.substring(partLng.indexOf('=') + 1, partLng.indexOf('}'));

        return new String[]{lat, lng};
    }

}
