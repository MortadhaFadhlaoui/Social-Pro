/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import Entity.Trajet;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.TextArea;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.FocusListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import Entity.User;
import utils.Context;

/**
 *
 * @author Choura
 */
public class CovoiturageForm extends BaseForm {

    FloatingActionButton fab;
    Boolean b = true;

    public CovoiturageForm() {
        this(Resources.getGlobalResources());
    }

    @Override
    protected boolean isCurrentCovoiturage() {
        return true;
    }

    public CovoiturageForm(Resources resourceObjectInstance) {
        initGuiBuilderComponents(resourceObjectInstance);
        Context.getInstance().place = -1;
        getToolbar().setTitleComponent(
                FlowLayout.encloseCenterMiddle(
                        new Label("Covoiturage", "Title")
                )
        );

        installSidemenu(resourceObjectInstance);

        EncodedImage encImg = EncodedImage.createFromImage(resourceObjectInstance.getImage("contact-a.png"), false);

        URLImage imgUrl = URLImage.createToStorage(encImg, Context.getInstance().getProfil().getImageName(), "http://localhost/MobilePHP/" + Context.getInstance().getProfil().getImageName());
        imgUrl.fetch();

//        getToolbar().addCommandToRightBar("", imgUrl, e -> {
//        });
        fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new CovoiturageAjoutForm(resourceObjectInstance).show();
            }
        });
        RoundBorder rb = (RoundBorder) fab.getUnselectedStyle().getBorder();
        rb.uiid(true);
        fab.bindFabToContainer(getContentPane());
        Context.getInstance().fab = fab;

        if (Context.getInstance().visited) {
            ToastBar.showMessage("Trajet Ajouté avec Succès", FontImage.MATERIAL_INFO);
            Context.getInstance().visited = false;
        }

        getData(resourceObjectInstance, 1);

        getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            Context.getInstance().i = 1;
            removeAll();
            fill(resourceObjectInstance);
            refreshTheme();
            if (text == null || text.length() == 0) {
                b = false;
                getData(resourceObjectInstance, Context.getInstance().selected);
                new CovoiturageForm(resourceObjectInstance).show();

            } else {
                text = text.toLowerCase();
                getDataSearch(resourceObjectInstance, Context.getInstance().selected, text);
            }
        }, 4);

    }

    private void initGuiBuilderComponents(Resources resourceObjectInstance) {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        setTitle("covoiturageForm");
        setName("covoiturageForm");
        fill(resourceObjectInstance);
    }

    public ArrayList<Trajet> getResult(String json) {
        ArrayList<Trajet> one = new ArrayList<>();
        JSONParser j = new JSONParser();

        try {
            Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));

            if (user.get("trajet").toString().indexOf('[') != -1) {

                List<Map<String, Object>> list = (List<Map<String, Object>>) user.get("trajet");

                for (Map<String, Object> obj : list) {
                    Trajet t = new Trajet();
                    t.setIdTrajet(Integer.parseInt(obj.get("id_trajet").toString()));
                    t.setIdUserTrajet(Integer.parseInt(obj.get("id_user_trajet").toString()));
                    t.setDepart(obj.get("depart").toString());
                    t.setArrive(obj.get("arrive").toString());
                    t.setDate(obj.get("date").toString());
                    t.setHeure(obj.get("heure").toString());
                    t.setNombrePlace(Integer.parseInt(obj.get("nombre_place").toString()));
                    one.add(t);

                }
            } else {
                Trajet t = new Trajet();
                Map<String, Object> list = (Map<String, Object>) user.get("trajet");
                t.setIdTrajet(Integer.parseInt(list.get("id_trajet").toString()));
                t.setIdUserTrajet(Integer.parseInt(list.get("id_user_trajet").toString()));
                t.setDepart(list.get("depart").toString());
                t.setArrive(list.get("arrive").toString());
                t.setDate(list.get("date").toString());
                t.setHeure(list.get("heure").toString());
                t.setNombrePlace(Integer.parseInt(list.get("nombre_place").toString()));
                one.add(t);
            }

        } catch (IOException ex) {
        }
        return one;
    }

    public void fill(Resources resourceObjectInstance) {

        Button all = new Button("All");
        Button mine = new Button("Mine");
        Button reservations = new Button("Reservations");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, all, mine, reservations)
        ));

        all.setUIID("CalendarHourSelected");
        mine.setUIID("CalendarHourUnselected");
        reservations.setUIID("CalendarHourUnselected");

        all.addPointerPressedListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Context.getInstance().selected = 1;
                all.setUIID("CalendarHourSelected");
                mine.setUIID("CalendarHourUnselected");
                reservations.setUIID("CalendarHourUnselected");
            }
        });
        mine.addPointerPressedListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Context.getInstance().selected = 2;
                all.setUIID("CalendarHourUnselected");
                mine.setUIID("CalendarHourSelected");
                reservations.setUIID("CalendarHourUnselected");
            }
        });
        reservations.addPointerPressedListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Context.getInstance().selected = 3;
                all.setUIID("CalendarHourUnselected");
                mine.setUIID("CalendarHourUnselected");
                reservations.setUIID("CalendarHourSelected");
            }
        });

        all.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                clear();
                getData(resourceObjectInstance, 1);
                refreshTheme();
            }
        });
        mine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                clear();
                getData(resourceObjectInstance, 2);
                refreshTheme();
            }
        });
        reservations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Context.getInstance().suppression = 0;
                clear();
                getData(resourceObjectInstance, 3);
                refreshTheme();
            }
        });
    }

    private void getData(Resources resourceObjectInstance, int selected) {

        ConnectionRequest con = new ConnectionRequest();

        switch (selected) {
            case 1:
                if (b) {
                    clearFloatingButton();
                    fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
                    fab.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            new CovoiturageAjoutForm(resourceObjectInstance).show();
                        }
                    });
                    RoundBorder rb = (RoundBorder) fab.getUnselectedStyle().getBorder();
                    rb.uiid(true);
                    fab.bindFabToContainer(getContentPane());
                }

                con.setUrl("http://localhost/MobilePHP/getTrajets.php"
                        + "?id=" + Context.getInstance().getUser().getId());
                con.addResponseListener(new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        String response = new String(con.getResponseData());
                        if (response.equals("Nothing{}")) {
                            addRow(null, null, resourceObjectInstance, false);
                        } else {
                            for (Trajet trajet : getResult(response)) {
                                addRow(trajet, Context.getInstance().getUser(), resourceObjectInstance, true);
                            }
                            refreshTheme();
                        }
                    }
                });
                b = true;
                break;
            case 2:
                if (b) {
                    clearFloatingButton();
                    fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
                    fab.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            new CovoiturageAjoutForm(resourceObjectInstance).show();
                        }
                    });
                    RoundBorder rb1 = (RoundBorder) fab.getUnselectedStyle().getBorder();
                    rb1.uiid(true);
                    fab.bindFabToContainer(getContentPane());
                }

                con.setUrl("http://localhost/MobilePHP/getMesTrajets.php"
                        + "?id=" + Context.getInstance().getUser().getId());
                con.addResponseListener(new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        String response = new String(con.getResponseData());
                        if (response.equals("Nothing{}")) {
                            addRow(null, null, resourceObjectInstance, false);
                        } else {
                            for (Trajet trajet : getResult(response)) {
                                addMineRow(trajet, Context.getInstance().getUser(), resourceObjectInstance, true);
                            }
                            refreshTheme();
                        }
                    }
                });
                b = true;
                break;
            case 3:
                if (b) {
                    clearFloatingButton();
                    fab = FloatingActionButton.createFAB(FontImage.MATERIAL_DELETE);
                    Context.getInstance().fab = fab;
                    fab.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {

                            if (Context.getInstance().place != -1) {
                                ConnectionRequest con = new ConnectionRequest();
                                con.setUrl("http://localhost/MobilePHP/deleteReservation.php"
                                        + "?id_trajet=" + getIdTrajetFromContaniner(Context.getInstance().place)
                                        + "&id_user=" + Context.getInstance().getUser().getId()
                                );
                                clearDeletedReservation(Context.getInstance().place);
                                refreshTheme();
                                con.addResponseListener(new ActionListener<NetworkEvent>() {
                                    @Override
                                    public void actionPerformed(NetworkEvent evt) {
                                        ToastBar.showMessage("Réservation Annulée", FontImage.MATERIAL_INFO);
                                    }
                                });

                                NetworkManager.getInstance().addToQueue(con);
                            } else {
                                ToastBar.showMessage("Il faut Selectionner un Trajet pour pouvoir l'annuler.", FontImage.MATERIAL_INFO);
                            }

                        }
                    });
                    RoundBorder rb2 = (RoundBorder) fab.getUnselectedStyle().getBorder();
                    rb2.uiid(true);
                    fab.bindFabToContainer(getContentPane());
                }

                con.setUrl("http://localhost/MobilePHP/getMesReservations.php"
                        + "?id=" + Context.getInstance().getUser().getId());
                con.addResponseListener(new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        String response = new String(con.getResponseData());
                        if (response.equals("Nothing{}")) {
                            addRow(null, null, resourceObjectInstance, false);
                        } else {
                            int place = 1;
                            for (Trajet trajet : getResult(response)) {
                                addReservedRow(trajet, Context.getInstance().getUser(), resourceObjectInstance, true, place);
                                place++;
                            }
                            refreshTheme();
                        }
                    }
                });
                b = true;
                break;
            default:
                break;
        }
        NetworkManager.getInstance().addToQueue(con);
    }

    private void clear() {

        Container c = (Container) getComponentAt(Context.getInstance().i);
        Container c1 = (Container) c.getComponentAt(0);

        while (c1.getComponentCount() > 1) {
            Container a = (Container) c1.getComponentAt(1);
            c1.removeComponent(a);
        }
    }

    private void clearDeletedReservation(int index) {

        Container a;
        Container c = (Container) getComponentAt(1);
        Container c1 = (Container) c.getComponentAt(0);
        a = (Container) c1.getComponentAt(index);
        a.remove();
        for (int i = (Integer) a.getClientProperty("index"); i < c1.getComponentCount(); i++) {
            Container b = (Container) c1.getComponentAt(i);
            b.putClientProperty("index", i);
        }

        c1.removeComponent(a);
    }

    private Object getIdTrajetFromContaniner(int index) {

        Container c = (Container) getComponentAt(1);
        Container c1 = (Container) c.getComponentAt(0);

        Container a = (Container) c1.getComponentAt(index);
        return a.getClientProperty("id");
    }

    private void clearFloatingButton() {

        Container c = (Container) getComponentAt(1);
        Container c1 = (Container) c.getComponentAt(1);
        Container c2 = (Container) c1.getComponentAt(1);
        Component c3 = c2.getComponentAt(0);

        c3.remove();
    }

    private void addRow(Trajet trajet, User user, Resources resourceObjectInstance, Boolean state) {

        if (!state) {
            Dialog.show("Réponse", "Pas de Trajets", "Ok", null);
        } else {
            Container mainContainer = new Container(new BorderLayout());
            Container childContainer = new Container(new FlowLayout());
            Label label = new Label();
            Container childContainer0 = new Container(new FlowLayout());
            Label label0 = new Label();
            Container childContainer1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Label label1 = new Label();
            Label label2 = new Label();
            Label sep = new Label("", "Separator");
            sep.setShowEvenIfBlank(true);
            TextArea textArea = new TextArea();
            addComponent(mainContainer);
            mainContainer.setName("Container_1");
            mainContainer.addComponent(BorderLayout.EAST, childContainer);
            childContainer.setName("Container_2");
            childContainer.addComponent(label);
            label.setText(trajet.getHeure());
            label.setUIID("SmallFontLabel");
            label.setName("Label_1");
            mainContainer.addComponent(BorderLayout.WEST, childContainer0);
            childContainer0.setName("Container_4");
            ((FlowLayout) childContainer0.getLayout()).setAlign(Component.CENTER);
            childContainer0.addComponent(label0);
            label0.setUIID("Padding2");
            label0.setName("Label_4");
            label0.setIcon(resourceObjectInstance.getImage("label_round.png")); //.setIcon(resourceObjectInstance.getImage("label_round-selected.png"));
            mainContainer.addComponent(BorderLayout.CENTER, childContainer1);
            childContainer1.setName("Container_3");
            childContainer1.addComponent(label1);
            childContainer1.addComponent(label2);
            childContainer1.addComponent(textArea);
            childContainer1.add(sep);
            label1.setText(trajet.getDepart().substring(0, trajet.getDepart().indexOf(',')) + " - " + trajet.getArrive().substring(0, trajet.getArrive().indexOf(',')));
            label1.setName("Label_3");
            label2.setText(trajet.getDate());
            label2.setUIID("RedLabel");
            label2.setName("Label_2");
            textArea.setText("Créateur : " + trajet.getIdUserTrajet());
            textArea.setUIID("SmallFontLabel");
            textArea.setName("Text_Area_1");
            childContainer.setName("Container_2");
            childContainer0.setName("Container_4");
            ((FlowLayout) childContainer0.getLayout()).setAlign(Component.CENTER);
            childContainer1.setName("Container_3");

            mainContainer.setLeadComponent(label1);
            label1.addPointerPressedListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    new CovoiturageTrajetSelectionne(resourceObjectInstance, trajet).show();
                }
            });
        }
    }

    private void addReservedRow(Trajet trajet, User user, Resources resourceObjectInstance, Boolean state, int place) {

        if (!state) {
            Dialog.show("Réponse", "Pas de Trajets", "Ok", null);
        } else {
            Container mainContainer = new Container(new BorderLayout());
            Container childContainer = new Container(new FlowLayout());
            Label label = new Label();
            Container childContainer0 = new Container(new FlowLayout());
            Label label0 = new Label();
            Container childContainer1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Label label1 = new Label();
            Label label2 = new Label();
            Label sep = new Label("", "Separator");
            sep.setShowEvenIfBlank(true);
            TextArea textArea = new TextArea();
            addComponent(mainContainer);
            mainContainer.setName("Container_1");
            mainContainer.addComponent(BorderLayout.EAST, childContainer);
            childContainer.setName("Container_2");
            childContainer.addComponent(label);
            label.setText(trajet.getHeure());
            label.setUIID("SmallFontLabel");
            label.setName("Label_1");
            mainContainer.addComponent(BorderLayout.WEST, childContainer0);
            childContainer0.setName("Container_4");
            ((FlowLayout) childContainer0.getLayout()).setAlign(Component.CENTER);
            childContainer0.addComponent(label0);
            label0.setUIID("Padding2");
            label0.setName("Label_4");
            label0.setIcon(resourceObjectInstance.getImage("label_round.png")); //.setIcon(resourceObjectInstance.getImage("label_round-selected.png"));
            mainContainer.addComponent(BorderLayout.CENTER, childContainer1);
            childContainer1.setName("Container_3");
            childContainer1.addComponent(label1);
            childContainer1.addComponent(label2);
            childContainer1.addComponent(textArea);
            childContainer1.add(sep);
            label1.setText(trajet.getDepart().substring(0, trajet.getDepart().indexOf(',')) + " - " + trajet.getArrive().substring(0, trajet.getArrive().indexOf(',')));
            label1.setName("Label_3");
            label2.setText(trajet.getDate());
            label2.setUIID("RedLabel");
            label2.setName("Label_2");
            textArea.setText("Créateur : " + trajet.getIdUserTrajet());
            textArea.setUIID("SmallFontLabel");
            textArea.setName("Text_Area_1");
            childContainer.setName("Container_2");
            childContainer0.setName("Container_4");
            ((FlowLayout) childContainer0.getLayout()).setAlign(Component.CENTER);
            childContainer1.setName("Container_3");
            mainContainer.putClientProperty("id", trajet.getIdTrajet());
            mainContainer.putClientProperty("index", place);
            mainContainer.setLeadComponent(label1);
            label1.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(Component cmp) {
                    label0.setIcon(resourceObjectInstance.getImage("label_round-selected.png"));
                    refreshTheme();
                    Context.getInstance().place = (Integer) mainContainer.getClientProperty("index");
                }

                @Override
                public void focusLost(Component cmp) {
                    label0.setIcon(resourceObjectInstance.getImage("label_round.png"));
                }
            });
        }
    }

    private void addMineRow(Trajet trajet, User user, Resources resourceObjectInstance, Boolean state) {

        if (!state) {
            Dialog.show("Réponse", "Pas de Trajets", "Ok", null);
        } else {
            Container mainContainer = new Container(new BorderLayout());
            Container childContainer = new Container(new FlowLayout());
            Label label = new Label();
            Container childContainer0 = new Container(new FlowLayout());
            Label label0 = new Label();
            Container childContainer1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Label label1 = new Label();
            Label label2 = new Label();
            Label sep = new Label("", "Separator");
            sep.setShowEvenIfBlank(true);
            TextArea textArea = new TextArea();
            addComponent(mainContainer);
            mainContainer.setName("Container_1");
            mainContainer.addComponent(BorderLayout.EAST, childContainer);
            childContainer.setName("Container_2");
            childContainer.addComponent(label);
            label.setText(trajet.getHeure());
            label.setUIID("SmallFontLabel");
            label.setName("Label_1");
            mainContainer.addComponent(BorderLayout.WEST, childContainer0);
            childContainer0.setName("Container_4");
            ((FlowLayout) childContainer0.getLayout()).setAlign(Component.CENTER);
            childContainer0.addComponent(label0);
            label0.setUIID("Padding2");
            label0.setName("Label_4");
            label0.setIcon(resourceObjectInstance.getImage("label_round.png")); //.setIcon(resourceObjectInstance.getImage("label_round-selected.png"));
            mainContainer.addComponent(BorderLayout.CENTER, childContainer1);
            childContainer1.setName("Container_3");
            childContainer1.addComponent(label1);
            childContainer1.addComponent(label2);
            childContainer1.addComponent(textArea);
            childContainer1.add(sep);
            label1.setText(trajet.getDepart().substring(0, trajet.getDepart().indexOf(',')) + " - " + trajet.getArrive().substring(0, trajet.getArrive().indexOf(',')));
            label1.setName("Label_3");
            label2.setText(trajet.getDate());
            label2.setUIID("RedLabel");
            label2.setName("Label_2");
            textArea.setText("Créateur : " + trajet.getIdUserTrajet());
            textArea.setUIID("SmallFontLabel");
            textArea.setName("Text_Area_1");
            childContainer.setName("Container_2");
            childContainer0.setName("Container_4");
            ((FlowLayout) childContainer0.getLayout()).setAlign(Component.CENTER);
            childContainer1.setName("Container_3");

            mainContainer.setLeadComponent(label1);
            label1.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(Component cmp) {
                    label0.setIcon(resourceObjectInstance.getImage("label_round-selected.png"));
                    refreshTheme();
                }

                @Override
                public void focusLost(Component cmp) {
                    label0.setIcon(resourceObjectInstance.getImage("label_round.png"));
                }
            });
        }
    }

    private void getDataSearch(Resources resourceObjectInstance, int selected, String text) {

        ConnectionRequest con = new ConnectionRequest();

        switch (selected) {
            case 1:

                con.setUrl("http://localhost/MobilePHP/getTrajetsSearch.php"
                        + "?id=" + Context.getInstance().getUser().getId()
                        + "&text=" + text);
                con.addResponseListener(new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        String response = new String(con.getResponseData());
                        if (response.equals("Nothing{}")) {
                            ToastBar.showMessage("Pas de Trajets avec ce Nom", FontImage.MATERIAL_ART_TRACK);
                        } else {
                            for (Trajet trajet : getResult(response)) {
                                addRow(trajet, Context.getInstance().getUser(), resourceObjectInstance, true);
                            }
                            refreshTheme();
                        }
                    }
                });
                break;
            case 2:

                con.setUrl("http://localhost/MobilePHP/getMesTrajetsSearch.php"
                        + "?id=" + Context.getInstance().getUser().getId()
                        + "&text=" + text);
                con.addResponseListener(new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        String response = new String(con.getResponseData());
                        if (response.equals("Nothing{}")) {
                            ToastBar.showMessage("Pas de Trajets avec ce Nom", FontImage.MATERIAL_WARNING);
                        } else {
                            for (Trajet trajet : getResult(response)) {
                                addMineRow(trajet, Context.getInstance().getUser(), resourceObjectInstance, true);
                            }
                            refreshTheme();
                        }
                    }
                });
                break;
            case 3:

                con.setUrl("http://localhost/MobilePHP/getMesReservationsSearch.php"
                        + "?id=" + Context.getInstance().getUser().getId()
                        + "&text=" + text);
                con.addResponseListener(new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        String response = new String(con.getResponseData());
                        if (response.equals("Nothing{}")) {
                            ToastBar.showMessage("Pas de Trajets avec ce Nom", FontImage.MATERIAL_ERROR);
                        } else {
                            int place = 1;
                            for (Trajet trajet : getResult(response)) {
                                addReservedRow(trajet, Context.getInstance().getUser(), resourceObjectInstance, true, place);
                                place++;
                            }
                            refreshTheme();
                        }
                    }
                });
                break;
            default:
                break;
        }
        NetworkManager.getInstance().addToQueue(con);
    }
}
