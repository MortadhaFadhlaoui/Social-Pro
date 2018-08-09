/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.FocusListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import java.util.ArrayList;
import java.util.List;
import utils.Context;

/**
 *
 * @author Choura
 */
public class CovoiturageAjoutForm extends Form {

    private final TextField departTextField = new TextField();
    private final TextField arriveTextField = new TextField();
    private final Picker dateDepartPicker = new Picker();
    private final String dateNow;
    private final TextField nbPlacesTextField = new TextField();
    private final Picker heureDepartPicker = new Picker();

    public CovoiturageAjoutForm() {
        this(Resources.getGlobalResources());
    }

    public CovoiturageAjoutForm(Resources resourceObjectInstance) {

        getToolbar().setTitleComponent(
                FlowLayout.encloseCenterMiddle(
                        new Label("Ajout Trajet", "Title")
                )
        );

        getToolbar().setBackCommand("back", new ActionListener<ActionEvent>() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new CovoiturageForm(resourceObjectInstance).showBack();
            }
        });

        Style s = new Style();
        s.setFgColor(0xffffff);
        s.setBgTransparency(0);
        Button ajouterTrajetButton = new Button("Ajouter", FontImage.createMaterial(FontImage.MATERIAL_ADD, s, 5));
        ajouterTrajetButton.setUIID("SideCommandNumber");
        setLayout(new BorderLayout());
        setTitle("Ajout Trajet");
        setName("AjoutTrajetForm");
        addAddressListener(departTextField);
        addAddressListener(arriveTextField);
        departTextField.setHint("Départ");
        arriveTextField.setHint("Arrivée");
        dateNow = dateDepartPicker.getText();
        dateDepartPicker.setText("Date");
        heureDepartPicker.setType(Display.PICKER_TYPE_TIME);
        heureDepartPicker.setText("Heure");
        nbPlacesTextField.setHint("Nombre de Places");
        add(BorderLayout.NORTH, new Label(" "));
        add(BorderLayout.CENTER,
                FlowLayout.encloseCenterMiddle(
                        BoxLayout.encloseY(
                                departTextField,
                                arriveTextField,
                                dateDepartPicker,
                                heureDepartPicker,
                                nbPlacesTextField
                        )
                )
        );
        add(BorderLayout.SOUTH,
                BoxLayout.encloseY(
                        ajouterTrajetButton,
                        new Label(" ")
                )
        );

        ajouterTrajetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                if (departTextField.getText().equals("")
                        || arriveTextField.getText().equals("")
                        || dateDepartPicker.getText().equals("Date")
                        || heureDepartPicker.getText().equals("Heure")
                        || nbPlacesTextField.getText().equals("")) {
                    Dialog.show("Problème", "Il Faut Remplir tout les Champs", "Ok", null);
                    return;
                }

                String a = dateDepartPicker.getText();
                if (splitDate(dateNow).get(2) > splitDate(a).get(2)) {
                    Dialog.show("Problème", "Date de Départ doit être supérieure à la Date d'Ajourd'hui", "Ok", null);
                    return;
                } else if (splitDate(dateNow).get(1) > splitDate(a).get(1) && splitDate(dateNow).get(2) == splitDate(a).get(2)) {
                    Dialog.show("Problème", "Date de Départ doit être supérieure à la Date d'Ajourd'hui", "Ok", null);
                    return;
                } else if (splitDate(dateNow).get(0) > splitDate(a).get(0) && splitDate(dateNow).get(1) == splitDate(a).get(1) && (splitDate(dateNow).get(2) == splitDate(a).get(2))) {
                    Dialog.show("Problème", "Date de Départ doit être supérieure à la Date d'Ajourd'hui", "Ok", null);
                    return;
                } else if (splitTime(heureDepartPicker.getText()).get(0) < splitTime(dateDepartPicker.getDate().toString()).get(0) && splitDate(dateNow).get(0) == splitDate(a).get(0) && (splitDate(dateNow).get(1) == splitDate(a).get(1)) && (splitDate(dateNow).get(2) == splitDate(a).get(2))) {
                    Dialog.show("Problème", "Heure de Départ doit être supérieure à l'Heure Actuelle", "Ok", null);
                    return;
                } else if (splitTime(heureDepartPicker.getText()).get(1) < splitTime(dateDepartPicker.getDate().toString()).get(1) && (splitTime(heureDepartPicker.getText()).get(0) == splitTime(dateDepartPicker.getDate().toString()).get(0)) && (splitDate(dateNow).get(0) == splitDate(a).get(0)) && (splitDate(dateNow).get(1) == splitDate(a).get(1)) && (splitDate(dateNow).get(2) == splitDate(a).get(2))) {
                    Dialog.show("Problème", "Heure de Départ doit être supérieure à l'Heure Actuelle", "Ok", null);
                    return;
                }                

                try {
                    Integer.parseInt(nbPlacesTextField.getText());
                } catch (Exception e) {
                    Dialog.show("Problème", "Le Nombre de Places doit être un Entier", "Ok", null);
                    return;
                }

                if (Integer.parseInt(nbPlacesTextField.getText()) > 4 || Integer.parseInt(nbPlacesTextField.getText()) < 1) {
                    Dialog.show("Problème", "Le Nombre de Places doit être Entre 1 et 4", "Ok", null);
                    return;
                }
                

                String month = a.substring(a.indexOf(a.charAt(0)), a.indexOf('/'));
                String day = a.substring(a.indexOf('/') + 1, a.indexOf('/', a.indexOf('/') + 1));
                String year = a.substring(a.indexOf('/', a.indexOf('/') + 1) + 1, a.length());
                String fulldate = "20" + year + "-" + month + "-" + day;

                ConnectionRequest con = new ConnectionRequest();
                con.setUrl("http://localhost/MobilePHP/addTrajet.php"
                        + "?id_user_trajet=" + Context.getInstance().getUser().getId()
                        + "&depart=" + departTextField.getText()
                        + "&arrive=" + arriveTextField.getText()
                        + "&date=" + fulldate
                        + "&heure=" + heureDepartPicker.getText()
                        + "&nombre_place=" + nbPlacesTextField.getText());
                con.addResponseListener(new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        String response = new String(con.getResponseData());
                        if (response.equals("success")) {
                            Context.getInstance().visited = true;
                            new CovoiturageForm(resourceObjectInstance).show();
                        } else {
                            System.out.println(response);
                        }
                    }
                });
                NetworkManager.getInstance().addToQueue(con);
            }
        });

        //getToolbar().addCommandToRightBar("", resourceObjectInstance.getImage("toolbar-profile-pic.png"), e -> {});
    }

    private void addAddressListener(TextField t) {
        t.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(Component cmp) {
            }

            @Override
            public void focusLost(Component cmp) {
                if (!t.getText().equals("")) {
                    final Geocoder geocoder = new Geocoder();
                    GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(t.getText()).setLanguage("en").getGeocoderRequest();
                    GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
                    if (geocoderResponse.getStatus().OK == geocoderResponse.getStatus()) {
                        String formattedAddress = geocoderResponse.getResults().get(0).getFormattedAddress();
                        if (Dialog.show("Adresse", "Do You Mean : " + formattedAddress, "Yes", "No")) {
                            t.setText(formattedAddress);
                        } else {
                            t.setText("");
                        }
                    } else {
                        Dialog.show("Adresse", "Adresse Introuvable", "Yes", null);
                        t.setText("");
                    }
                }
            }
        });
    }

    private List<Integer> splitDate(String date) {
        ArrayList<Integer> list = new ArrayList<Integer>();

        String day = date.substring(date.indexOf('/') + 1, date.indexOf('/', date.indexOf('/') + 1));
        list.add(Integer.parseInt(day));

        String month = date.substring(date.indexOf(date.charAt(0)), date.indexOf('/'));
        list.add(Integer.parseInt(month));

        String year = date.substring(date.indexOf('/', date.indexOf('/') + 1) + 1, date.length());
        list.add(Integer.parseInt(year));

        return list;
    }
    
    private List<Integer> splitTime(String time) {
        ArrayList<Integer> list = new ArrayList<Integer>();

        String hour = time.substring(time.indexOf(':')-2, time.indexOf(':'));
        list.add(Integer.parseInt(hour));

        String minute = time.substring(time.indexOf(':')+1, time.indexOf(':')+3);
        list.add(Integer.parseInt(minute));

        return list;
    }
}
