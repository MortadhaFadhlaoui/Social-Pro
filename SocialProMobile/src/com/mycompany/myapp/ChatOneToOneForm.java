/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import Entity.Message;
import utils.NavigatorData;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mortadhafff
 */
public class ChatOneToOneForm extends BaseForm {

    Resources r = null;
    private volatile boolean exit = false;
    private Image roundedMeImage;
    private URLImage imgURLL;
    Container chatArea = null;
//    private int count = 0;
//    private int nb = 0;

   @Override
    protected boolean isCurrentChatForm() {
        return true;
    }

    public ChatOneToOneForm(com.codename1.ui.util.Resources resourceObjectInstance) {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException ex) {
//                        Log.getReportingLevel();
//                    }
//                    Display.getInstance().callSerially(() -> {
//                        chatArea.removeAll();
        initGuiBuilderComponents(resourceObjectInstance);
//                        chatArea.revalidate();
//                    });
//                }
//            }
//        });
//        thread.start();        

    }

    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        r = resourceObjectInstance;
        setLayout(new BorderLayout());
        setTitle("Chat");
        setName("ChatOneToOneForm");
        Toolbar tb = new Toolbar();
        chatArea = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        chatArea.setScrollableY(true);
        chatArea.setName("ChatArea");
        setToolbar(tb);
        EncodedImage encImg = EncodedImage.createFromImage(resourceObjectInstance.getImage("contact-a.png"), false);
        final URLImage imgURL;
        if (NavigatorData.getInstance().getUserSelected().getImageName() == null) {
            imgURL = URLImage.createToStorage(encImg, "ooo", "http://localhost/MobilePHP/contact-a.png");
            imgURL.fetch();
        } else {
            imgURL = URLImage.createToStorage(encImg, NavigatorData.getInstance().getUserSelected().getEmail(), "http://localhost/Social_Pro/web/img/authors/" + NavigatorData.getInstance().getUserSelected().getImageName());
            imgURL.fetch();
        }

        if (NavigatorData.getInstance().getUserlogedIn().getImageName() == null) {
            imgURLL = URLImage.createToStorage(encImg, "oooo", "http://localhost/MobilePHP/contact-a.png");
            imgURLL.fetch();
        } else {
            imgURLL = URLImage.createToStorage(encImg, NavigatorData.getInstance().getUserlogedIn().getEmail(), "http://localhost/Social_Pro/web/img/authors/" + NavigatorData.getInstance().getUserlogedIn().getImageName());
            imgURLL.fetch();
        }

        Command himOrHerCommand = new Command("", imgURL);
        tb.addCommandToRightBar(himOrHerCommand);

        tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> {
            new MessageForm(resourceObjectInstance).showBack();
        });
        tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_SETTINGS, e -> {

        });
        tb.addCommandToLeftBar(NavigatorData.getInstance().getUserSelected().getNom(), null, null);
//         ConnectionRequest connn = new ConnectionRequest();
//        connn.setUrl("http://localhost/pidev2017/selectcount.php?author_id=" + NavigatorData.getInstance().getUserlogedIn().getId() + "&addresse_id=" + NavigatorData.getInstance().getUserSelected().getId()+"");
//        connn.addResponseListener(new ActionListener<NetworkEvent>() {
//
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                String response = new String(connn.getResponseData());
//                try {
//                    nb = getcount(response);
//                } catch (ParseException ex) {
//                    Log.getReportingLevel();
//                }                
//            }
//            
//        });
//         NetworkManager.getInstance().addToQueueAndWait(connn);
        ConnectionRequest con = new ConnectionRequest();
        //"&count="+(nb-count)+
        con.setUrl("http://localhost/pidev2017/selectMessage.php?author_id=" + NavigatorData.getInstance().getUserlogedIn().getId() + "&addresse_id=" + NavigatorData.getInstance().getUserSelected().getId() + "");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                String response = new String(con.getResponseData());
//                System.out.println(response.equals("NO{}"));
                try {
                    if (!response.equals("NO{}")) {
                        // count = nb;
                        if (getListMessage(new String(con.getResponseData())) != null) {
                            for (Message m : getListMessage(new String(con.getResponseData()))) {
                                if (m.getEmetteur() == NavigatorData.getInstance().getUserSelected().getId()) {
                                    respondNoLayout(chatArea, m.getContenu(), m.getDateEnvoie(), imgURL);
                                    chatArea.refreshTheme();
                                    chatArea.revalidate();
                                } else {
                                    sayNoLayout(chatArea, m.getContenu(), m.getDateEnvoie());
                                    chatArea.refreshTheme();
                                    chatArea.revalidate();
                                }
                            }
                        }
                    }
                } catch (ParseException ex) {
                    Log.getReportingLevel();
                } catch (IOException ex) {
                    Log.getReportingLevel();
                }
            }
        });
        NetworkManager.getInstance().addToQueue(con);

        TextField write = new TextField(30);
        write.setHint("Write to " + NavigatorData.getInstance().getUserSelected().getNom());
        addComponent(BorderLayout.CENTER, chatArea);
        addComponent(BorderLayout.SOUTH, write);
        write.addActionListener((e) -> {
            String text = write.getText();
            if (!"".equals(text)) {
                write.setText("");
                ConnectionRequest messcon = new ConnectionRequest();
                messcon.setUrl("http://localhost/pidev2017/InsertMessage.php?content=" + text + "&author_id=" + NavigatorData.getInstance().getUserlogedIn().getId() + "&addresse_id=" + NavigatorData.getInstance().getUserSelected().getId() + "");
                messcon.addResponseListener(new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt) {
//                        Display.getInstance().callSerially(() -> {
//                            removeAll();
//                            initGuiBuilderComponents(resourceObjectInstance);
//                            revalidate();
//                        });                                   
                        byte[] data = (byte[]) evt.getMetaData();
                        String s = new String(data);
                        System.out.println(s);
                        if (s.equals("Error")) {
//                            chatArea.removeComponent(t);
                            chatArea.revalidate();
                            Dialog.show("Error", "Connection error message wasn't sent", "OK", null);
                            chatArea.refreshTheme();
                            chatArea.revalidate();
                        } else {
                            System.out.println(NavigatorData.getInstance().getUserSelected());
                            ConnectionRequest mail = new ConnectionRequest();
                            mail.setUrl("http://localhost/pidev2017/mail.php?message=" + text + "&subject=" + NavigatorData.getInstance().getUserSelected().getNom() + "&email=" + NavigatorData.getInstance().getUserSelected().getEmail() + "");
                            NetworkManager.getInstance().addToQueue(mail);

//                            t.getUnselectedStyle().setOpacity(255);
                            chatArea.refreshTheme();
                            chatArea.revalidate();
                        }
                    }
                });
                NetworkManager.getInstance().addToQueue(messcon);
            }
        });
        Server myServer = new Server();
        Thread t1 = new Thread(myServer, "T1");
        t1.start();
        Serverr myServerr = new Serverr();
        Thread t2 = new Thread(myServerr, "T1");
        t2.start();
    }// </editor-fold>

    public void UpdateMessage(Resources resourceObjectInstance) {
        EncodedImage encImg = EncodedImage.createFromImage(resourceObjectInstance.getImage("contact-a.png"), false);
        final URLImage imgURL;
        if (NavigatorData.getInstance().getUserSelected().getImageName() == null) {
            imgURL = URLImage.createToStorage(encImg, "ooo", "http://localhost/MobilePHP/contact-a.png");
            imgURL.fetch();
        } else {
            imgURL = URLImage.createToStorage(encImg, NavigatorData.getInstance().getUserSelected().getEmail(), "http://localhost/MobilePHP/" + NavigatorData.getInstance().getUserSelected().getImageName());
            imgURL.fetch();
        }

        if (NavigatorData.getInstance().getUserlogedIn().getImageName() == null) {
            imgURLL = URLImage.createToStorage(encImg, "oooo", "http://localhost/MobilePHP/contact-a.png");
            imgURLL.fetch();
        } else {
            imgURLL = URLImage.createToStorage(encImg, NavigatorData.getInstance().getUserlogedIn().getEmail(), "http://localhost/MobilePHP/" + NavigatorData.getInstance().getUserlogedIn().getImageName());
            imgURLL.fetch();
        }
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev2017/updatemes.php?author_id=" + NavigatorData.getInstance().getUserlogedIn().getId() + "&addresse_id=" + NavigatorData.getInstance().getUserSelected().getId() + "");
        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                String response = new String(con.getResponseData());
//                System.out.println(response.equals("NO{}"));
                try {
                    if (!response.equals("NO{}")) {

                        // count = nb;
                        if (getListMessage(new String(con.getResponseData())) != null) {
                            for (Message m : getListMessage(new String(con.getResponseData()))) {
                                if (m.getEmetteur() == NavigatorData.getInstance().getUserSelected().getId()) {
//                                    respond(chatArea, m.getContenu(),imgURL ,m.getDateEnvoie());
                                    respond(chatArea, m.getContenu(), imgURL, m.getDateEnvoie());
//                                     say(chatArea, m.getContenu(), m.getDateEnvoie());
                                    chatArea.refreshTheme();
                                    chatArea.revalidate();
                                } else {
                                    say(chatArea, m.getContenu(), m.getDateEnvoie());
//                                   final Component t = say(chatArea, m.getContenu(), m.getDateEnvoie());
                                    chatArea.refreshTheme();
                                    chatArea.revalidate();
                                }
                            }
                        }
                    }
                } catch (ParseException ex) {
                    Log.getReportingLevel();
                } catch (IOException ex) {
                    Log.getReportingLevel();
                }
//                 Date d = new Date();
//                final Component t = say(chatArea, text, d);
////             we make outgoing messages translucent to indicate that they weren't received yet
//                t.getUnselectedStyle().setOpacity(120);
            }

        });
        NetworkManager.getInstance().addToQueue(con);
    }

    private Component say(Container chatArea, String text, Date date) {
        Component t = sayNoLayout(chatArea, text, date);
        t.setY(chatArea.getHeight());
        t.setWidth(chatArea.getWidth());
        t.setHeight(40);
        chatArea.animateLayoutAndWait(300);

        chatArea.scrollComponentToVisible(t);

        return t;
    }

    private Component sayNoLayout(Container chatArea, String text, Date date) {
        SpanLabel t = new SpanLabel(text);
        String datee = date.toString().substring(8, date.toString().length() - 13);
        String dateee = date.toString().substring(0, date.toString().length() - 26);
        Label l = new Label();
        l.setText(dateee + " " + datee);
        l.setUIID("CalendarHourSelected");
        t.setIcon(imgURLL);
        t.setTextBlockAlign(Component.LEFT);
        t.setTextUIID("BubbleMe");
        chatArea.addComponent(t);
        chatArea.addComponent(l);
        return t;
    }

    private Container getChatArea(Container cnt) {
        String n = cnt.getName();
        if (n != null && n.equals("ChatArea")) {
            return cnt;
        }

        for (Component cmp : cnt) {
            if (cmp instanceof Container) {
                Container cur = getChatArea((Container) cmp);
                if (cur != null) {
                    return cur;
                }
            }
        }
        return null;
    }

    /**
     * Stores the given message into the permanent storage
     */
//    private void addMessage(Message m) {
////        String personId;
////        
////        // if this is a message to me then store based on sender otherwise store based on recepient
////        if(m.getRecepientId().equals(tokenPrefix + uniqueId)) {
////            personId = m.getSenderId();
////        } else {
////            personId = m.getRecepientId();
////        }
////        java.util.List messages = (java.util.List)Storage.getInstance().readObject(personId);
////        if(messages == null) {
////            messages = new ArrayList();
////        }
////        messages.add(m);
////        Storage.getInstance().writeObject(personId, messages);
//    }
//    private void respond(Message m) {
//        String clientId = (String)Display.getInstance().getCurrent().getClientProperty("cid");
//        EncodedImage rounded = getRoundedFriendImage(m.getSenderId(), m.getPicture());
//        if(clientId == null || !clientId.equals(m.getSenderId())) {
//            // show toast, we aren't in the chat form...
//            InteractionDialog toast = new InteractionDialog();
//            toast.setUIID("Container");
//            toast.setLayout(new BorderLayout());
//
//            SpanButton messageButton = new SpanButton(m.getMessage());
//            messageButton.setIcon(rounded);
//
//            toast.addComponent(BorderLayout.CENTER, messageButton);
//            int h = toast.getPreferredH();
//            toast.show(Display.getInstance().getDisplayHeight() - h - 10, 10, 10, 10);
//            UITimer uit = new UITimer(() -> {
//                toast.dispose();
//            });
//            uit.schedule(3000, false, Display.getInstance().getCurrent());
//
//            messageButton.addActionListener((e) -> {
//                uit.cancel();
//                toast.dispose();
//                showChatForm(getContactById(m.getSenderId()), Display.getInstance().getCurrent());
//            });
//        } else {
//            Container chatArea = getChatArea(Display.getInstance().getCurrent().getContentPane());
//            respond(chatArea, m.getMessage(), rounded);
//        }
//    }
    private void respond(Container chatArea, String text, Image roundedHimOrHerImage, Date date) {
        Component answer = respondNoLayout(chatArea, text, date, roundedHimOrHerImage);
        answer.setX(chatArea.getWidth());
        answer.setWidth(chatArea.getWidth());
        answer.setHeight(40);
        chatArea.animateLayoutAndWait(300);
        chatArea.scrollComponentToVisible(answer);
    }

    private Component respondNoLayout(Container chatArea, String text, Date date, Image roundedHimOrHerImage) {
        SpanLabel answer = new SpanLabel(text);
        MultiButton m = new MultiButton();
        String datee = date.toString().substring(8, date.toString().length() - 13);
        String dateee = date.toString().substring(0, date.toString().length() - 26);
        Label l = new Label();
        l.setText(dateee + " " + datee);
        l.setUIID("CalendarHourSelected");
        answer.setIcon(roundedHimOrHerImage);
        answer.setIconPosition(BorderLayout.EAST);
        answer.setTextUIID("BubbleThem");
        answer.setTextBlockAlign(Component.RIGHT);
        chatArea.addComponent(answer);
        chatArea.addComponent(l);
        return answer;
    }

    public ArrayList<Message> getListMessage(String json) throws ParseException, IOException {
        ArrayList<Message> listMessage = new ArrayList<>();

        JSONParser j = new JSONParser();

        Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));

        try {
            List<Map<String, Object>> list = (List<Map<String, Object>>) etudiants.get("messages");

            for (Map<String, Object> obj : list) {
                Message e = new Message();
                e.setIdMessage(Integer.parseInt(obj.get("id").toString()));
                e.setContenu(obj.get("messageText").toString());
                e.setEmetteur(Integer.parseInt(obj.get("author_id").toString()));
                e.setRecepteur(Integer.parseInt(obj.get("addressee_id").toString()));
                e.setReding(Integer.parseInt(obj.get("reading").toString()));
                e.setVu(obj.get("vu").toString());
                String dateStr = obj.get("date_envoyer").toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateenvoyer = sdf.parse(dateStr);
                e.setDateEnvoie(dateenvoyer);
                listMessage.add(e);

            }
        } catch (ClassCastException e) {
            Map<String, Object> list = (Map<String, Object>) etudiants.get("messages");
            Message ee = new Message();
            ee.setIdMessage(Integer.parseInt(list.get("id").toString()));
            ee.setContenu(list.get("messageText").toString());
            ee.setEmetteur(Integer.parseInt(list.get("author_id").toString()));
            ee.setRecepteur(Integer.parseInt(list.get("addressee_id").toString()));
            ee.setReding(Integer.parseInt(list.get("reading").toString()));
            ee.setVu(list.get("vu").toString());
            String dateStr = list.get("date_envoyer").toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateenvoyer = sdf.parse(dateStr);
            ee.setDateEnvoie(dateenvoyer);
            listMessage.add(ee);
        }
        return listMessage;

    }

    class Server implements Runnable {
//        Resources resourceObjectInstance;

        private volatile boolean exit = false;

        public void run() {
            while (!exit) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    Log.getReportingLevel();
                }
                UpdateMessage(r);
                System.out.println("Server is running.....");
            }
            System.out.println("Server is stopped....");
        }

        public void stop() {
            exit = true;
        }
    }

    class Serverr implements Runnable {
//        Resources resourceObjectInstance;

        private volatile boolean exit = false;

        public void run() {
            while (!exit) {
                try {
                    Thread.sleep(11000);
                } catch (InterruptedException ex) {
                    Log.getReportingLevel();
                }
                ConnectionRequest conn = new ConnectionRequest();
                conn.setUrl("http://localhost/pidev2017/updateMessage.php?author_id=" + NavigatorData.getInstance().getUserlogedIn().getId() + "&addresse_id=" + NavigatorData.getInstance().getUserSelected().getId() + "");
                conn.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        String response = new String(conn.getResponseData());
                        System.out.println(response);
                    }

                });
                NetworkManager.getInstance().addToQueue(conn);
                System.out.println("Serverr is running.....");
            }
            System.out.println("Serverr is stopped....");
        }

        public void stop() {
            exit = true;
        }
    }
// public int getcount(String json) throws ParseException {
//       int countt = 0;
//        try {
//
//            JSONParser j = new JSONParser();
//
//            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));
//            Map<String, Object> list = (Map<String, Object>) etudiants.get("messages");
//
//            
//                countt =Integer.parseInt(list.get("count").toString());
//      
//
//            
//
//        } catch (IOException ex) {
//        }
//        return countt;
//
//    }
}
