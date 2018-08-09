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

import Entity.User;
import Entity.Vote;
import Entity.t_avis;
import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComponentGroup;
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
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * GUI builder created Form
 *
 * @author Shai Almog
 */
public class SignInForm extends com.codename1.ui.Form {

    private Container mainContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    private Label label1 = new Label();
    private ComponentGroup groupComponent = new ComponentGroup();
    private TextField usernameTextField = new TextField();
    private TextField passwordTextField = new TextField(null, "Password", 20, TextField.PASSWORD);
    private Button loginButton = new Button();
    private Button forgetPasswordButton = new Button();

    public SignInForm() {
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }

    public SignInForm(Resources resourceObjectInstance) {
        initGuiBuilderComponents(resourceObjectInstance);
        getTitleArea().setUIID("Container");
        getToolbar().setUIID("Container");
        getToolbar().getTitleComponent().setUIID("SigninTitle");
        FontImage mat = FontImage.createMaterial(FontImage.MATERIAL_CLOSE, "SigninTitle", 3.5f);
        getToolbar().addCommandToLeftBar("", mat, e -> new SplashForm().show());
        getContentPane().setUIID("SignInForm");
    }

    private void initGuiBuilderComponents(Resources resourceObjectInstance) {

        setLayout(new BorderLayout());
        setTitle("Sign In");
        setName("SignInForm");
        addComponent(BorderLayout.CENTER, mainContainer);
        mainContainer.setScrollableY(true);
        mainContainer.addComponent(label1);
        mainContainer.add(new Label(" "));
        mainContainer.addComponent(usernameTextField);
        mainContainer.addComponent(passwordTextField);
        usernameTextField.setHint("Username");
        passwordTextField.setHint("Password");
        mainContainer.add(new Label(" "));
        mainContainer.addComponent(loginButton);
        mainContainer.addComponent(forgetPasswordButton);
        label1.setUIID("CenterLabel");
        EncodedImage encImg = EncodedImage.createFromImage(resourceObjectInstance.getImage("profile_image.png"), false);

        URLImage imgUrl = URLImage.createToStorage(encImg, "qazzz", "http://localhost/MobilePHP/man.png");
        imgUrl.fetch();
        
        label1.setIcon(imgUrl);
        groupComponent.setName("Component_Group_1");
        loginButton.setText("Sign In");
        forgetPasswordButton.setText("Forgot Your Password");
        forgetPasswordButton.setUIID("CenterLabelSmall");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new ParsingJSON().getAccess(usernameTextField.getText(), passwordTextField.getText());
            }
        });
    }
    
    public static ArrayList<Vote> getVotes(String json) {
        ArrayList<Vote> listV = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();
            Map<String, Object> votes = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = new ArrayList<>();

            try {
                list = (List<Map<String, Object>>) votes.get("vote");
                for (Map<String, Object> obj : list) {
                    Vote v = new Vote();
                    v.setUser(new User(Integer.parseInt(obj.get("id_membre").toString())));

                    v.setAvis(t_avis.valueOf(obj.get("avis").toString()));

                    listV.add(v);
                }
            } catch (ClassCastException c) {
                Map<String, Object> Mapprofil = (Map<String, Object>) votes.get("vote");
                Vote v = new Vote();
                v.setUser(new User(Integer.parseInt(Mapprofil.get("id_membre").toString())));
                v.setAvis(t_avis.valueOf(Mapprofil.get("avis").toString()));
                listV.add(v);
            } catch (NullPointerException c) {

            }

        } catch (IOException ex) {
        }
        return listV;

    }
    
    public static User getUser(String json) throws ParseException {
        User user = new User();
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> users = j.parseJSON(new CharArrayReader(json.toCharArray()));
            Map<String, Object> Mapprofil = (Map<String, Object>) users.get("user");
            System.out.println(Mapprofil);
            if (Mapprofil != null) {
                user.setPassword(Mapprofil.get("pwd").toString());
                user.setId(Integer.parseInt(Mapprofil.get("id").toString()));
                user.setUsername(Mapprofil.get("username").toString());
                user.setImageName(Mapprofil.get("image_name").toString());
                user.setEmail(Mapprofil.get("email").toString());
                String dateStr = (Mapprofil.get("last_login").toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateenvoyer = sdf.parse(dateStr);

                user.setLastLogout(dateenvoyer);
            }
        } catch (IOException ex) {
        }
        return user;

    }
}
