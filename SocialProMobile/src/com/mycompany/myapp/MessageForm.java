/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import Entity.Profil;
import Entity.User;
import utils.NavigatorData;
import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mortadhafff
 */
public class MessageForm extends BaseForm {

    private volatile boolean exit = false;
    Resources r = null, r2 = null;

    public MessageForm() throws InterruptedException {
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }

    @Override
    protected boolean isCurrentChatForm() {
        return true;
    }

    public MessageForm(com.codename1.ui.util.Resources resourceObjectInstance) {

        initGuiBuilderComponents(resourceObjectInstance);
        installSidemenu(resourceObjectInstance);

        ScaleImageLabel sl = new ScaleImageLabel(resourceObjectInstance.getImage("skate-park.jpg"));
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        sl = new ScaleImageLabel(resourceObjectInstance.getImage("bridge.jpg"));
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
    }

    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        r = resourceObjectInstance;
        r2 = resourceObjectInstance;
        setLayout(new BorderLayout());
        setTitle("Message");
        setName("MessageForm");
        Toolbar tb = new Toolbar();
        setToolbar(tb);

        Server myServer = new Server();
        Thread t1 = new Thread(myServer, "T1");

        tb.addSearchCommand(e -> {

            myServer.stop(); //Let's wait to see server thread stopped 

            System.out.println(currentThread().getName() + " is finished now");

            exit = true;
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                System.out.println("null text");
                InitListeUsers(resourceObjectInstance);
                // clear search
                for (Component cmp : getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                getContentPane().animateLayout(150);
            } else {
                System.out.println(currentThread().isAlive());
                text = text.toLowerCase();
                System.out.println(text);
                InitListeUsersSearched(resourceObjectInstance, text);
                getContentPane().animateLayout(150);
            }

            System.out.println("fin");
        }, 4);
        InitListeUsers(resourceObjectInstance);

        t1.start();
        System.out.println(currentThread().getName() + " is stopping Server thread");

    }

    public ArrayList<User> getListUserss(String json) throws ParseException {
        ArrayList<User> listUsers = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));
            try {

                List<Map<String, Object>> list = (List<Map<String, Object>>) etudiants.get("users");

                for (Map<String, Object> obj : list) {
                    User e = new User();
                    e.setId(Integer.parseInt(obj.get("id").toString()));
                    e.setNom(obj.get("nom").toString());
                    e.setPrenom(obj.get("prenom").toString());
                    e.setEmail(obj.get("email").toString());
                    String dateStr = obj.get("last_login").toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date dateenvoyer = sdf.parse(dateStr);
                    e.setLastLogin(dateenvoyer);
                    String dateeStr = obj.get("lastActivity").toString();
                    SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date dateenvoyerr = sdff.parse(dateeStr);
                    e.setLastLogout(dateenvoyerr);
                    listUsers.add(e);
                }
            } catch (ClassCastException e) {
                Map<String, Object> list = (Map<String, Object>) etudiants.get("users");

                User ee = new User();
                ee.setId(Integer.parseInt(list.get("id").toString()));
                ee.setNom(list.get("nom").toString());
                ee.setPrenom(list.get("prenom").toString());
                ee.setEmail(list.get("email").toString());
                String dateStr = list.get("last_login").toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateenvoyer = sdf.parse(dateStr);
                ee.setLastLogin(dateenvoyer);
                String dateeStr = list.get("lastActivity").toString();
                SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateenvoyerr = sdff.parse(dateeStr);
                ee.setLastLogout(dateenvoyerr);
                listUsers.add(ee);

            }
        } catch (IOException ex) {
        }
        return listUsers;

    }

    public Profil getListUsers(String json) {
        Profil profil = new Profil();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));

            Map<String, Object> Mapprofil = (Map<String, Object>) etudiants.get("profils");
            if (Mapprofil != null) {
                profil.setIdProfil(Integer.parseInt(Mapprofil.get("idProfil").toString()));
                profil.setIdUser(Integer.parseInt(Mapprofil.get("idUser").toString()));
                profil.setImageName(Mapprofil.get("image_name").toString());
            }
        } catch (IOException ex) {
        }
        return profil;

    }

    public void InitListeUsers(com.codename1.ui.util.Resources resourceObjectInstance) {
        Container C1 = new Container(BoxLayout.y());
        C1.setScrollableY(true);
        C1.setName("C1");
        C1.removeAll();
        add(BorderLayout.CENTER, C1);
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev2017/selectUsers.php?id=" + NavigatorData.getInstance().getUserlogedIn().getId());
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    String response = new String(con.getResponseData());
                    if (!response.equals("NO{}")) {
                        for (User u : getListUserss(new String(con.getResponseData()))) {
                            ConnectionRequest conn = new ConnectionRequest();
                            String url = "http://localhost/pidev2017/select.php?idUser=" + u.getId();
                            conn.setUrl(url);
                            conn.addResponseListener(new ActionListener<NetworkEvent>() {
                                @Override
                                public void actionPerformed(NetworkEvent evt) {
                                    Profil p = getListUsers(new String(conn.getResponseData()));
                                    u.setProfil(p);
                                    Container c = new Container(new BorderLayout());
                                    MultiButton m = new MultiButton();
                                    Label on = new Label();
                                    C1.add(c);
                                    c.setName("c");
                                    c.add(com.codename1.ui.layouts.BorderLayout.CENTER, m);

                                    Date datesys = new Date();
//                                    System.out.println(u.getLastLogout().getTime() + u.getNom());
//                                    System.out.println(u.getLastLogin().getTime() + u.getNom());
//                                    System.out.println((int) (u.getLastLogin().getTime() - u.getLastLogout().getTime()) + u.getNom());
                                    if ((int) (u.getLastLogin().getTime() - u.getLastLogout().getTime()) < 0) {
                                        FontImage.setMaterialIcon(on, FontImage.MATERIAL_CHAT_BUBBLE_OUTLINE);
                                    } else {
                                        FontImage.setMaterialIcon(on, FontImage.MATERIAL_CHAT_BUBBLE);
                                    }

                                    c.add(com.codename1.ui.layouts.BorderLayout.EAST, on);
                                    m.setUIID("Label");
                                    m.setName("Multi_Button_1");
                                    EncodedImage encImg = EncodedImage.createFromImage(resourceObjectInstance.getImage("contact-a.png"), false);
                                    URLImage imgURL = null;
                                    if (p.getImageName() == null) {
                                        imgURL = URLImage.createToStorage(encImg, "ooo", "http://localhost/MobilePHP/contact-a.png");
                                        imgURL.fetch();
                                    } else {
                                        imgURL = URLImage.createToStorage(encImg, u.getEmail(), "http://localhost/Social_Pro/web/img/authors/" + p.getImageName());
                                        imgURL.fetch();
                                    }
                                    m.setIcon(imgURL);
                                    m.setPropertyValue("line1", u.getNom());
                                    m.setPropertyValue("line2", u.getEmail());
                                    m.setPropertyValue("uiid1", "Label");
                                    m.setPropertyValue("uiid2", "RedLabel");
                                    Label l = new Label();
                                    C1.add(l);
                                    c.setName("c");

                                    l.setUIID("Separator");
                                    l.setName("separator1");
                                    l.setShowEvenIfBlank(true);
                                    m.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent evt) {

                                            u.setImageName(p.getImageName());
                                            NavigatorData.getInstance().setUserSelected(u);
                                            new ChatOneToOneForm(resourceObjectInstance).show();
                                        }
                                    });
                                    C1.revalidate();
                                }
                            });
                            NetworkManager.getInstance().addToQueue(conn);
                        }
                    }
                } catch (ParseException ex) {
                    Log.getReportingLevel();
                }

            }
        });
        NetworkManager.getInstance().addToQueue(con);
    }

    public void InitListeUsersSearched(com.codename1.ui.util.Resources resourceObjectInstance, String text) {
        Container C1 = new Container(BoxLayout.y());
        C1.setScrollableY(true);
        C1.setName("C1");
        C1.removeAll();
        add(BorderLayout.CENTER, C1);
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev2017/selectUse.php?username=" + text);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    String response = new String(con.getResponseData());
                    if (!response.equals("NO{}")) {
                        for (User u : getListUserss(new String(con.getResponseData()))) {
                            ConnectionRequest conn = new ConnectionRequest();
                            String url = "http://localhost/pidev2017/select.php?idUser=" + u.getId();
                            conn.setUrl(url);
                            conn.addResponseListener(new ActionListener<NetworkEvent>() {
                                @Override
                                public void actionPerformed(NetworkEvent evt) {
                                    Profil p = getListUsers(new String(conn.getResponseData()));
                                    u.setProfil(p);
                                    Container c = new Container(new BorderLayout());
                                    MultiButton m = new MultiButton();
                                    Label on = new Label();
                                    C1.add(c);
                                    c.setName("c");
                                    c.add(com.codename1.ui.layouts.BorderLayout.CENTER, m);

                                    Date datesys = new Date();
//                            if(datesys.compareTo(datesys) == 1) {
//                                System.out.println(datesys+"jjjj");
//                                
//}
                                    if ((int) (u.getLastLogin().getTime() - u.getLastLogout().getTime()) < 0) {
                                        FontImage.setMaterialIcon(on, FontImage.MATERIAL_CHAT_BUBBLE_OUTLINE);
                                    } else {
                                        FontImage.setMaterialIcon(on, FontImage.MATERIAL_CHAT_BUBBLE);
                                    }

                                    c.add(com.codename1.ui.layouts.BorderLayout.EAST, on);
                                    m.setUIID("Label");
                                    m.setName("Multi_Button_1");
                                    EncodedImage encImg = EncodedImage.createFromImage(resourceObjectInstance.getImage("user-avatar-placeholder.png"), false);
                                    URLImage imgURL = null;
                                    if (p.getImageName() == null) {
                                        imgURL = URLImage.createToStorage(encImg, "ooo", "http://localhost/Social_Pro/web/img/authors/user-avatar-placeholder.png");
                                        imgURL.fetch();
                                    } else {
                                        imgURL = URLImage.createToStorage(encImg, u.getEmail(), "http://localhost/Social_Pro/web/img/authors/" + p.getImageName());
                                        imgURL.fetch();
                                    }
                                    m.setIcon(imgURL);
                                    m.setPropertyValue("line1", u.getNom());
                                    m.setPropertyValue("line2", u.getEmail());
                                    m.setPropertyValue("uiid1", "Label");
                                    m.setPropertyValue("uiid2", "RedLabel");
                                    Label l = new Label();
                                    C1.add(l);
                                    c.setName("c");

                                    l.setUIID("Separator");
                                    l.setName("separator1");
                                    l.setShowEvenIfBlank(true);
                                    m.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent evt) {

                                            u.setImageName(p.getImageName());
                                            NavigatorData.getInstance().setUserSelected(u);
                                            new ChatOneToOneForm(resourceObjectInstance).show();
                                        }
                                    });
                                    C1.revalidate();
                                }
                            });
                            NetworkManager.getInstance().addToQueue(conn);
                        }
                    }
                } catch (ParseException ex) {
                    Log.getReportingLevel();
                }

            }
        });
        NetworkManager.getInstance().addToQueue(con);

    }

    class Server implements Runnable {
//        Resources resourceObjectInstance;

        private volatile boolean exit = false;

        public void run() {
            while (!exit) {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException ex) {
                    Log.getReportingLevel();
                }
                InitListeUsers(r);
                System.out.println("Server is running.....");
            }
            System.out.println("Server is stopped....");
        }

        public void stop() {
            exit = true;
        }
    }
//       class Serverr implements Runnable{
////        Resources resourceObjectInstance;
//        private volatile boolean exit = false;
//      
//        public void run() {
//            while(!exit){ 
//                try {
//                    Thread.sleep(40000);
//                } catch (InterruptedException ex) {
//                        Log.getReportingLevel();
//                }
//                InitListeUsers(r);
//                System.out.println("Server is running....."); 
//            } 
//            System.out.println("Server is stopped...."); } 
//        public void stop(){ exit = true; } }
}
