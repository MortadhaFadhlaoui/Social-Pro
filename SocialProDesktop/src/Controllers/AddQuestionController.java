/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import IServices.ICompetenceService;
import IServices.IQuestionService;
import IServices.IUserService;
import Models.Competence;
import Models.CopetenceConverter;
import Models.Etat;
import Models.NavigatorData;
import Models.Question;
import Models.Role;
import Models.StatuQuestion;
import Models.Statut;
import Models.User;
import Services.CompetenceService;
import Services.QuestionService;
import Services.UserService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationSupport;

/**
 * FXML Controller class
 *
 * @author Mortadhafff
 */
public class AddQuestionController implements Initializable {

    File f;
    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextArea content;
    @FXML
    private JFXComboBox<String> tag;
    @FXML
    private JFXButton savebutton;
    @FXML
    private JFXButton cancel;
    CopetenceConverter c = new CopetenceConverter();
    @FXML
    private AnchorPane root;
    /**
     * Initializes the controller class.
     */
//    String contentupdate = NavigatorData.getInstance().getSelectedQusetion().getSujet();
//    String titleupdate = NavigatorData.getInstance().getSelectedQusetion().getTitre();
    ICompetenceService competences;
    ValidationSupport validationSupport = new ValidationSupport();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chargeScene();
    }

    private void chargeScene() {
        NavigatorData.getInstance().getUserlogedIn();
        NavigatorData.getInstance().setUserlogedIn(NavigatorData.getInstance().getUserlogedIn());
        cancel.getStyleClass().add("bt");
        savebutton.getStyleClass().add("bt");
        competences = new CompetenceService();
        if (NavigatorData.getInstance().getSelectedQusetion() != null) {
            title.setText(NavigatorData.getInstance().getSelectedQusetion().getTitre());
            content.setText(NavigatorData.getInstance().getSelectedQusetion().getSujet());
//        ObservableList<String> competence = FXCollections.observableArrayList();
//        List<String> a = new ArrayList<>();
//        for (Competence competence1 : competences.getAll()) {            
//            a.add(c.toString(competence1));
//        }
//        competence.addAll(a);
//        tag.setItems(competence);
        }
        ObservableList<String> competence = FXCollections.observableArrayList();
        List<String> a = new ArrayList<>();
        for (Competence competence1 : competences.getAll()) {
            a.add(c.toString(competence1));
        }
        competence.addAll(a);
        tag.setItems(competence);

    }

    @FXML
    private void SaveQuestion(ActionEvent event) {
        IUserService u = new UserService();
        String t = title.getText();
        String cc = content.getText();
        String tagg = tag.getSelectionModel().getSelectedItem();
        if (t.isEmpty() && cc.isEmpty() && tag.getSelectionModel().isEmpty()) {
            validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(title, "required name"));
            validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(content, "required description"));
            validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(tag, "required description"));
        } else if (t.isEmpty() && cc.isEmpty()) {
            validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(title, "required name"));
            validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(content, "required description"));
            validationSupport.getValidationDecorator().removeDecorations(tag);
        } else if (t.isEmpty() && tag.getSelectionModel().isEmpty()) {
            validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(title, "required name"));
            validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(tag, "required description"));
            validationSupport.getValidationDecorator().removeDecorations(content);
        } else if (cc.isEmpty() && tag.getSelectionModel().isEmpty()) {
            validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(tag, "required description"));
            validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(tag, "required description"));
            validationSupport.getValidationDecorator().removeDecorations(title);
        } else if (t.isEmpty()) {
            validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(title, "required name"));
            validationSupport.getValidationDecorator().removeDecorations(tag);
            validationSupport.getValidationDecorator().removeDecorations(content);
        } else if (cc.isEmpty()) {
            validationSupport.getValidationDecorator().removeDecorations(title);
            validationSupport.getValidationDecorator().removeDecorations(tag);
            validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(content, "required name"));
        } else if (tag.getSelectionModel().isEmpty()) {
            validationSupport.getValidationDecorator().applyValidationDecoration(ValidationMessage.error(tag, "required description"));
            validationSupport.getValidationDecorator().removeDecorations(title);
            validationSupport.getValidationDecorator().removeDecorations(content);
        } else {
            if (NavigatorData.getInstance().getSelectedQusetion() != null) {
                ICompetenceService co = new CompetenceService();
                IQuestionService Q = new QuestionService();
                Competence n = co.findbynom(tagg);
                Question question = new Question(t, cc, StatuQuestion.nonresolut, NavigatorData.getInstance().getUserlogedIn());
                Question m = NavigatorData.getInstance().getSelectedQusetion();
                Q.update(m.getIdQuestion(), m);
                Q.UpdateQuestionCompetence(m.getIdQuestion(), n.getIdCompetence());
            } else {
                ICompetenceService co = new CompetenceService();
                Competence n = co.findbynom(tagg);
                Question question = new Question(t, cc, StatuQuestion.nonresolut, NavigatorData.getInstance().getUserlogedIn());
                IQuestionService Q = new QuestionService();
                Q.add(question);
                Question m = Q.findbyTitre(t);
                Q.addQuestionCompetence(m.getIdQuestion(), n.getIdCompetence());
            }        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Succes");
        alert.showAndWait();
        Navigator.LoadScene(Navigator.MesQuestions);
        }
    }

    @FXML
    private void CancelImage(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure");
        alert.showAndWait();
        Navigator.LoadScene(Navigator.MesQuestions);
    }

    @FXML
    private void search(KeyEvent event) {
        String var = tag.getValue();
        tag.getItems().removeAll(tag.getItems());
        for (Competence c : competences.getsearch(var)) {
            tag.getItems().add(c.getNom());
        }

    }

}
