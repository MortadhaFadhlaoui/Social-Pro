/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import IServices.IGroupeService;
import Models.Groupe;
import Models.NavigatorData;
import Services.GroupeService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Mortadhafff
 */
public class GroupsController implements Initializable {

    @FXML
    private JFXButton add;
    @FXML
    private JFXTextField search;
    @FXML
    private JFXListView<HBox> listyours;  
    IGroupeService G = new GroupeService(); 
    @FXML
    private JFXButton groupsyoumanage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       leadscene();
    }    
    private void leadscene()
    {                 
        NavigatorData.getInstance().getUserlogedIn();
        NavigatorData.getInstance().setUserlogedIn(NavigatorData.getInstance().getUserlogedIn());
        for (Groupe Grou : G.getAll()) {    
        for (Groupe test : G.FindbyUser(NavigatorData.getInstance().getUserlogedIn().getId())) {
        if (test.getIdGroupe()==Grou.getIdGroupe()) {                           
            //hbox
            HBox h = new HBox();
            h.setSpacing(50);
            //grid1
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(4);
            grid.setPadding(new Insets(0, 10, 0, 10));
             //grid2
            GridPane gridq = new GridPane();
            gridq.setHgap(10);
            gridq.setVgap(4);
            gridq.setPadding(new Insets(0, 10, 0, 10));                     
            //image profil            
            Image image = new Image("file:///c:/wamp64/www/Image/" + Grou.getImage());            
            ImageView groupimage = new ImageView();            
            groupimage.setImage(image);
            groupimage.setFitWidth(50);
            groupimage.setFitHeight(50);
            grid.add(groupimage, 0, 0, 1, 2);
            //label nom et prenom
            Label nom = new Label(String.valueOf(Grou.getNom()));
            nom.getStyleClass().add("title");
            grid.add(nom, 0, 2);
            //idQuestion   
            Label id = new Label(String.valueOf(Grou.getIdGroupe()));
            grid.add(id, 1, 1);
            id.setVisible(false);
            //label titre
            Label Description = new Label(Grou.getDescription());
            Description.getStyleClass().add("title");
            gridq.add(Description, 1, 0);
            Description.setId("tt");            
            h.getChildren().addAll(grid, gridq);
            listyours.getItems().add(h);
            listyours.setExpanded(true);
            listyours.depthProperty().set(1);   
            listyours.setCellFactory(GroupListView -> new JFXListCell());                 
        }
}
 }
        add.getStyleClass().add("button-raised");
        groupsyoumanage.getStyleClass().add("button-raised");    
    }

    @FXML
    private void Addgroup(ActionEvent event) {
        Navigator.LoadScene(Navigator.AddGroup);        
    }

    @FXML
    private void NavigatetoManage(ActionEvent event) {
        Navigator.LoadScene(Navigator.GroupsYouManage);
    }

    @FXML
    private void searchgroup(KeyEvent event) {
        String searched = search.getText();
        listyours.getItems().removeAll(listyours.getItems());   
        NavigatorData.getInstance().getUserlogedIn();
        for (Groupe Grou : G.FindbyName(searched)) {           
            //hbox
            HBox h = new HBox();
            h.setSpacing(50);
            //grid1
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(4);
            grid.setPadding(new Insets(0, 10, 0, 10));
             //grid2
            GridPane gridq = new GridPane();
            gridq.setHgap(10);
            gridq.setVgap(4);
            gridq.setPadding(new Insets(0, 10, 0, 10));                     
            //image profil
             Image image = new Image("file:///c:/wamp64/www/Image/" + Grou.getImage());            
            ImageView groupimage = new ImageView();  
            groupimage.setImage(image);
            groupimage.setFitWidth(50);
            groupimage.setFitHeight(50);
            grid.add(groupimage, 0, 0, 1, 2);
            //label nom et prenom
            Label nom = new Label(String.valueOf(Grou.getNom()));
            nom.getStyleClass().add("title");
            grid.add(nom, 0, 2);
            //idQuestion   
            Label id = new Label(String.valueOf(Grou.getIdGroupe()));
            grid.add(id, 1, 1);
            id.setVisible(false);
            //label titre
            Label Description = new Label(Grou.getDescription());
            Description.getStyleClass().add("title");
            gridq.add(Description, 1, 0);
            Description.setId("tt");            
            h.getChildren().addAll(grid, gridq);
            listyours.getItems().add(h);                                    
        }
    }
    @FXML
    private void getGroupe(MouseEvent event) {
        if (listyours.getSelectionModel().getSelectedIndex() != -1) {
             GridPane g = ((GridPane) listyours.getSelectionModel().getSelectedItem().getChildren().get(0));
        Label l = (Label) g.getChildren().get(2);             
        NavigatorData.getInstance().setSelectedGroupe(G.findById(Integer.parseInt(l.getText())));
        Navigator.LoadScene(Navigator.Groupe);
        }       
    }
    
}
