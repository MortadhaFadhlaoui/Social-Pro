/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import IServices.IQuestionService;
import Models.NavigatorData;
import Models.Question;
import Services.QuestionService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
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
public class ListQuestionController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private JFXListView<HBox> list;
    private ObservableList<Question> question = FXCollections.observableArrayList();
//    FilteredList<Question> filtredQuestions = new FilteredList(question, predicate -> true);
    JFXButton b = new JFXButton();
    IQuestionService q = new QuestionService();   
    @FXML
    private JFXButton AskQuestion;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chargelistview();
    
    }

    private void chargelistview() {     
        NavigatorData.getInstance().getUserlogedIn();
        NavigatorData.getInstance().setUserlogedIn(NavigatorData.getInstance().getUserlogedIn());
        for (Question que : q.getAll()) {
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
            ImageView Person = new ImageView("/Utils/jihn-highnoon-splash-final.jpg");
            Person.setFitWidth(50);
            Person.setFitHeight(50);
            grid.add(Person, 0, 0, 1, 2);
            //label nom et prenom
            Label nom = new Label(String.valueOf(que.getProposeur().getNom()) + " " + String.valueOf(que.getProposeur().getPrenom()));
            nom.getStyleClass().add("title");
            grid.add(nom, 0, 2);
            //idQuestion   
            Label id = new Label(String.valueOf(que.getIdQuestion()));
            grid.add(id, 1, 1);
            id.setVisible(false);
            //label titre
            Label title = new Label(que.getTitre());
            title.getStyleClass().add("title");
            gridq.add(title, 1, 0);
            title.setId("tt");
            //label date              
            Label date = new Label(String.valueOf(que.getDateQuestion()));
            date.getStyleClass().add("time");
            gridq.add(date, 1, 1);       
            h.getChildren().addAll(grid, gridq);
            list.getItems().add(h);
            list.setExpanded(true);
            list.depthProperty().set(1);   
            list.setCellFactory(QuestionListView -> new JFXListCell());     
            
        }
        AskQuestion.getStyleClass().add("button-raised"); 
    }

    @FXML
    private void recherche(KeyEvent event) {
        String s = search.getText();
        list.getItems().removeAll(list.getItems());
        for (Question qu : q.FindbyTitre(s)) {
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
            ImageView Person = new ImageView("/Utils/jihn-highnoon-splash-final.jpg");
            Person.getStyleClass().add("image");
            Person.setFitWidth(50);
            Person.setFitHeight(50);
            grid.add(Person, 0, 0, 1, 2);
            //label nom et prenom
            Label nom = new Label(qu.getProposeur().getNom() + " " + qu.getProposeur().getPrenom());
            nom.getStyleClass().add("title");
            grid.add(nom, 0, 2);
            //label titre
            Label title = new Label(qu.getTitre());
            title.getStyleClass().add("title");
            gridq.add(title, 1, 0);
            //label date              
            Label date = new Label(qu.getDateQuestion().toString());
            date.getStyleClass().add("time");
            gridq.add(date, 1, 1);
            h.getChildren().addAll(grid, gridq);
            list.getItems().add(h);
        }
        list.refresh();
    }
    @FXML
    private void getQuestion(MouseEvent event) {
         GridPane g = ((GridPane) list.getSelectionModel().getSelectedItem().getChildren().get(1));
        Label l = (Label) g.getChildren().get(0);      
        NavigatorData.getInstance().setSelectedQusetion(q.findbyTitre(l.getText()));
        Navigator.LoadScene(Navigator.Question);
    }   
    @FXML
    private void askquestion(ActionEvent event) {
        Navigator.LoadScene(Navigator.ProposerQuestion);
    }       

}
