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
public class GroupsYouManageController implements Initializable {

    @FXML
    private JFXListView<HBox> MyList;
    @FXML
    private JFXButton add;
    @FXML
    private JFXTextField search;
    IGroupeService G = new GroupeService(); 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chargescene();
    }    
    private void chargescene()
    {
        NavigatorData.getInstance().getUserlogedIn();
        NavigatorData.getInstance().setUserlogedIn(NavigatorData.getInstance().getUserlogedIn());
        
     add.getStyleClass().add("button-raised");                   
//         listyours.setItems(Groups);
for (Groupe Grou : G.FindbyOwner(NavigatorData.getInstance().getUserlogedIn().getId())) {
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
            MyList.getItems().add(h);
            MyList.setExpanded(true);
            MyList.depthProperty().set(1);   
            MyList.setCellFactory(GroupListView -> new JFXListCell());                 
        }           
    }
    @FXML
    private void Addgroup(ActionEvent event) {
        Navigator.LoadScene(Navigator.AddGroup);        
    }

    @FXML
    private void searchgroup(KeyEvent event) {
         String searched = search.getText();
        MyList.getItems().removeAll(MyList.getItems());
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
            MyList.getItems().add(h);
            MyList.setExpanded(true);
            MyList.depthProperty().set(1);   
            MyList.setCellFactory(GroupListView -> new JFXListCell());                 
        }
        MyList.refresh();
    }

    @FXML
    private void getGroupeMange(MouseEvent event) {
         GridPane g = ((GridPane) MyList.getSelectionModel().getSelectedItem().getChildren().get(0));
        Label l = (Label) g.getChildren().get(2);           
        NavigatorData.getInstance().setSelectedGroupe(G.findById(Integer.parseInt(l.getText())));
        Navigator.LoadScene(Navigator.Groupe);
    }
    
}
