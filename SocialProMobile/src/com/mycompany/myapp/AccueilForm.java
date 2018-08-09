/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Slider;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Choura
 */
public class AccueilForm extends BaseForm {

    public AccueilForm() {
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }

    @Override
    protected boolean isCurrentAccueil() {
        return true;
    }

    public AccueilForm(com.codename1.ui.util.Resources resourceObjectInstance) {

        getToolbar().setTitleComponent(
                FlowLayout.encloseCenterMiddle(
                        new Label("Accueil", "Title")
                )
                
                
        );
        
        initGuiBuilderComponents(resourceObjectInstance);
       
        installSidemenu(resourceObjectInstance);
        
        //getToolbar().addCommandToRightBar("", resourceObjectInstance.getImage("toolbar-profile-pic.png"), e -> {});
        
    }

      
    
    
    private void initGuiBuilderComponents(Resources resourceObjectInstance) {

    setLayout(new com.codename1.ui.layouts.BorderLayout());
        Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        setTitle("Acceuil");
        setName("AccueilFrom");
        
        Label l1 = new Label("Bienvenu");
        c1.add(l1);
       // TextField tfId_tache = new TextField("", "Id Tache");
        TextField tfContenu = new TextField("", "Exprimez vous");
        addComponent(BorderLayout.CENTER,c1);
        //FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
       // fab.addActionListener(e -> ToastBar.showErrorMessage("Not implemented yet..."));
       // fab.bindFabToContainer(getContentPane());
       // f.add(tfId_tache);
        c1.add(tfContenu);
       

        Button btnOk = new Button("Ajouter");

        c1.add(btnOk);
        
        
        
//        
//      
        btnOk.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                ConnectionRequest req = new ConnectionRequest();
                req.setUrl("http://localhost/mobile/insert.php?contenu_publication=" + tfContenu.getText() +"");

                req.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        byte[] data = (byte[]) evt.getMetaData();
                        String s = new String(data);

                        if (s.equals("success")) {
                            Dialog.show("Confirmation", "ajout ok", "Ok", null);
                        }
                    }
                });
                
                NetworkManager.getInstance().addToQueue(req);
                
            tfContenu.clear();

            }
            
            
            
        });
        
    }
      
}
