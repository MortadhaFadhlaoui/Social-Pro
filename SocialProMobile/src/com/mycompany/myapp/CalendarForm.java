/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.mycompany.myapp;

import Entity.Evenement;
import Entity.User;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * GUI builder created Form
 *
 * @author shai
 */
public class CalendarForm extends BaseForm {

    public CalendarForm(com.codename1.ui.util.Resources resourceObjectInstance) {
        
//            Resources res3 = Resources.open("/theme2.res");
//            UIManager.getInstance().setThemeProps(res3.getTheme("Theme"));
//            refreshTheme();
            initGuiBuilderComponents(resourceObjectInstance);
            setLayout(BoxLayout.y());
            setScrollableY(true);
            getContentPane().setScrollVisible(false);
            getToolbar().setUIID("Container");
            Button b = new Button(" ");
            b.setUIID("Container");
            getToolbar().setTitleComponent(b);
            getTitleArea().setUIID("Container");
            installSidemenu(resourceObjectInstance);
            gui_Calendar_1.setTwoDigitMode(true);
            
            Picker p = new Picker();
            b.addActionListener(e -> {
                p.pressed();
                p.released();
            });
            p.addActionListener(e -> {
                gui_Calendar_1.setCurrentDate(p.getDate());
                gui_Calendar_1.setSelectedDate(p.getDate());
                gui_Calendar_1.setDate(p.getDate());
            });
            p.setFormatter(new SimpleDateFormat("MMMM"));
            p.setDate(new Date());
            p.setUIID("CalendarDateTitle");
            Container cnt = BoxLayout.encloseY(
                    p,
                    new Label(resourceObjectInstance.getImage("calendar-separator.png"), "CenterLabel")
            );
            
            BorderLayout bl = (BorderLayout) gui_Calendar_1.getLayout();
            Component combos = bl.getNorth();
            gui_Calendar_1.replace(combos, cnt, null);
        

    }

    private Container createEntry(Resources res, boolean selected, String startTime, String endTime, String location, String title, String attendance, String images, Image img) {
        Component time = new Label(startTime, "CalendarHourUnselected");
        if (selected) {
            time.setUIID("CalendarHourSelected");
        }

        Container circleBox = BoxLayout.encloseY(new Label(res.getImage("label_round-selected.png")),
                new Label("-", "OrangeLine"),
                new Label("-", "OrangeLine")
        );

        Container cnt = new Container(BoxLayout.x());

        cnt.add(res.getImage(images));
        ///  cnt.add(img);
        Container mainContent = BoxLayout.encloseY(
                BoxLayout.encloseX(
                        new Label(title, "SmallLabel"),
                        new Label("-", "SmallThinLabel"),
                        new Label(startTime, "SmallThinLabel"),
                        new Label("-", "SmallThinLabel"),
                        new Label(endTime, "SmallThinLabel")),
                new Label(attendance, "TinyThinLabel"),
                cnt
        );

        Label redLabel = new Label("", "RedLabelRight");
        FontImage.setMaterialIcon(redLabel, FontImage.MATERIAL_LOCATION_ON);
        Container loc = BoxLayout.encloseY(
                redLabel,
                new Label("Location:", "TinyThinLabelRight"),
                new Label(location, "TinyBoldLabel")
        );

        mainContent = BorderLayout.center(mainContent).
                add(BorderLayout.WEST, circleBox);

        return BorderLayout.center(mainContent).
                add(BorderLayout.WEST, FlowLayout.encloseCenter(time)).
                add(BorderLayout.EAST, loc);
    }

    private com.codename1.ui.Calendar gui_Calendar_1 = new com.codename1.ui.Calendar();
    private Container c = new Container();
                     
    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        setLayout(new com.codename1.ui.layouts.GridLayout(2, 1));
        setTitle("");
        setName("CalendarForm");
        addComponent(gui_Calendar_1);
        add(c);
        gui_Calendar_1.setName("Calendar_1");

        gui_Calendar_1.addActionListener(e -> {
            Date d = new Date(gui_Calendar_1.getSelectedDay());
            String DATE_FORMAT_NOW = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
            String stringDate = sdf.format(d);
            ConnectionRequest req = new ConnectionRequest();

            req.setUrl("http://localhost/codename/selectEvent.php?date='" + stringDate + "'");
            req.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt2) {
                    c.removeAll();
                    try {
                        for (Evenement ev : getListE(new String(req.getResponseData()))) {
                            EncodedImage enc = EncodedImage.createFromImage(resourceObjectInstance.getImage("contact-a.png"), false);
                            URLImage url = URLImage.createToStorage(enc, ev.getUser().getImageName(), "http://localhost/" + ev.getUser().getImageName());

                            url.fetch();
                            c.add(createEntry(resourceObjectInstance, true, "9:00", "12:00", ev.getLieu(), ev.getNom(), ev.getUser().getUsername(), ev.getUser().getImageName(), url));
                        }
                    } catch (IOException ex) {

                    } catch (ParseException ex) {

                    }

                }
            });
            NetworkManager.getInstance().addToQueueAndWait((req));

        });

    }

    public ArrayList<Evenement> getListE(String json) throws IOException, ParseException {

        ArrayList<Evenement> listE = new ArrayList<>();

        JSONParser j = new JSONParser();

        Map<String, Object> events = j.parseJSON(new CharArrayReader(json.toCharArray()));

        try {

            List<Map<String, Object>> list = (List<Map<String, Object>>) events.get("event");
            for (Map<String, Object> obj : list) {
                Evenement e = new Evenement();
                e.setNom(obj.get("nomEvent").toString());
                String da = obj.get("date").toString();
                String DATE_FORMAT_NOW = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
                Date d = sdf.parse(da);
                e.setDate(d);
                e.setUser(new User(obj.get("username").toString(), obj.get("image_name").toString()));
                e.setLieu(obj.get("lieu").toString());

                listE.add(e);
            }
        } catch (ClassCastException ex) {
            Map<String, Object> list = (Map<String, Object>) events.get("event");

            Evenement e = new Evenement();
            e.setNom(list.get("nomEvent").toString());
            String da = list.get("date").toString();
            String DATE_FORMAT_NOW = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
            Date d = sdf.parse(da);
            e.setDate(d);
            e.setUser(new User(list.get("username").toString(), list.get("image_name").toString()));
            e.setLieu(list.get("lieu").toString());

            listE.add(e);

        } catch (NullPointerException nex) {

        }
        return listE;

    }

//-- DON'T EDIT ABOVE THIS LINE!!!
    protected boolean isCurrentCalendar() {
        return true;
    }

    @Override
    protected void initGlobalToolbar() {
        setToolbar(new Toolbar(true));
    }

}
