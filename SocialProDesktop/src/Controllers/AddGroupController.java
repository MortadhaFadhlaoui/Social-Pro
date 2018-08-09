/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import IServices.IGroupeService;
import IServices.IUserService;
import Models.CopyFile;
import Models.Groupe;
import Models.NavigatorData;
import Models.User;
import Services.GroupeService;
import Services.UserService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationSupport;

/**
 * FXML Controller class
 *
 * @author Mortadhafff
 */
public class AddGroupController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField nomgroup;
    @FXML
    private JFXTextArea descriptiongroup;
    @FXML
    private ListSelectionView<GridPane> slectedlistview;
    @FXML
    private JFXButton savebuttonGroup;
    @FXML
    private JFXButton cancelbuttonGroup;
    @FXML
    private JFXTextField searchmember;
    private ObservableList<User> Users = FXCollections.observableArrayList();
    List list = new ArrayList();
    IUserService users = new UserService();
    @FXML
    private JFXButton addpicture;
    @FXML
    private ImageView imagegroupe;
    File file;
    IGroupeService GG = new GroupeService();    
    IUserService ui = new UserService();
    ValidationSupport validationSupport = new ValidationSupport();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chargescene();
    }

    private void chargescene() {
        NavigatorData.getInstance().getUserlogedIn();
        NavigatorData.getInstance().setUserlogedIn(NavigatorData.getInstance().getUserlogedIn());
        savebuttonGroup.getStyleClass().add("bt");
        cancelbuttonGroup.getStyleClass().add("bt");
        addpicture.getStyleClass().add("button-raised");
        Label Members = new Label("Members");
        Label Invited = new Label("Invited");
        slectedlistview.sourceHeaderProperty().setValue(Members);
        slectedlistview.targetHeaderProperty().setValue(Invited);
        list = users.getAll();
        Users.addAll(list);       
        for (User User1 : Users) {
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(4);
            grid.setPadding(new Insets(0, 10, 0, 10));
            //image profil
            ImageView Person = new ImageView("/Utils/jihn-highnoon-splash-final.jpg");
            Person.setFitWidth(50);
            Person.setFitHeight(50);
            grid.add(Person, 0, 0, 1, 2);
            //label nom et prenom
            Label nom = new Label(String.valueOf(User1.getNom()));
            nom.setStyle("-fx-text-fill: #C8C81E;-fx-font-weight: bold;-fx-font-family: \"Arial\";");
            grid.add(nom, 1, 0);
            //id
            Label id = new Label(String.valueOf(User1.getId()));
            id.setVisible(false);
            grid.add(id, 1, 1);
            slectedlistview.getSourceItems().addAll(grid);
        }

    }

    @FXML
    private void SaveGroup(ActionEvent event) {
        String n = nomgroup.getText();
        String d = descriptiongroup.getText();
        if (n.isEmpty() && d.isEmpty()) {
            validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(nomgroup, "required name"));
            validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(descriptiongroup, "required description"));
        } else if (n.isEmpty()) {
            validationSupport.getValidationDecorator().removeDecorations(descriptiongroup);
            validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(nomgroup, "required description"));
        } else if (d.isEmpty()) {
            validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(descriptiongroup, "required description"));
            validationSupport.getValidationDecorator().removeDecorations(nomgroup);
        } else if (file.getName() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Ajouter un Image");
            alert.showAndWait();
        }else{
               validationSupport.getValidationDecorator().removeDecorations(descriptiongroup);
            validationSupport.getValidationDecorator().removeDecorations(nomgroup);
            try {             
                File dest = new File("C:\\wamp64\\www\\Image\\" + file.getName());
                CopyFile.copyFileUsingStream(file, dest);
            } catch (IOException ex) {
                Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
            }

            Groupe Groupee = new Groupe(n, d, file.getName(), NavigatorData.getInstance().getUserlogedIn());
            GG.add(Groupee);
            Groupe Groupenom = GG.findbyTitre(n);      
            GG.addUserGroupe(NavigatorData.getInstance().getUserlogedIn().getId(), Groupenom.getIdGroupe());
            for (GridPane grid : slectedlistview.getTargetItems()) {
                Label l = (Label) grid.getChildren().get(2);
                User uuu = ui.findById(Integer.parseInt(l.getText()));
                if (NavigatorData.getInstance().getUserlogedIn().getId() != uuu.getId()) {
                    GG.addUserGroupe(uuu.getId(), Groupenom.getIdGroupe());
                }
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Succes");
            alert.showAndWait();
            Navigator.LoadScene(Navigator.GroupsYouManage);
        }
    }

    @FXML
    private void CancelGroup(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure");
        alert.showAndWait();
        Navigator.LoadScene(Navigator.GroupsYouManage);
    }

    @FXML
    private void recherche(KeyEvent event) {
        String s = searchmember.getText();
        slectedlistview.getSourceItems().removeAll(slectedlistview.getSourceItems());
        for (User User1 : users.getUsersbyNom(s)) {
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(4);
            grid.setPadding(new Insets(0, 10, 0, 10));
            //image profil
            ImageView Person = new ImageView("/Utils/jihn-highnoon-splash-final.jpg");
            Person.setFitWidth(50);
            Person.setFitHeight(50);
            grid.add(Person, 0, 0, 1, 2);
            //label nom
            Label nom = new Label(String.valueOf(User1.getNom()));
            nom.setStyle("-fx-text-fill: #C8C81E;-fx-font-weight: bold;-fx-font-family: \"Arial\";");
            grid.add(nom, 1, 0);
            slectedlistview.getSourceItems().addAll(grid);
        }

    }

    @FXML
    private void addpictureAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        FileChooser.ExtensionFilter extFilterALL = new FileChooser.ExtensionFilter("All Images", "*.*");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG, extFilterALL);

        try {
            file = fileChooser.showOpenDialog(null);
            if (file != null) {
                BufferedImage bufferedImage;
                bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imagegroupe.setImage(image);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("You must choose picture");
                alert.showAndWait();
            }

        } catch (IOException ex) {
            Logger.getLogger(AddQuestionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
