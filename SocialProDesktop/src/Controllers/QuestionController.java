/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;
import IServices.IQuestionService;
import IServices.IReponseService;
import Models.NavigatorData;
import Models.Question;
import Models.Reponse;
import Models.User;
import Models.Utilite;
import Services.QuestionService;
import Services.ReponseService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Mortadhafff
 */
public class QuestionController implements Initializable {

    @FXML
    private Label title;
    @FXML
    private Label content;
    @FXML
    private JFXTextArea Answer;
    @FXML
    private JFXListView<Reponse> LisyReponse;
    @FXML
    private JFXButton AnswerButton;
    private ObservableList<Reponse> Reponses;
    List list = new ArrayList();
    String c = NavigatorData.getInstance().getSelectedQusetion().getSujet();
    String t = NavigatorData.getInstance().getSelectedQusetion().getTitre();
    @FXML
    private AnchorPane but;
    @FXML
    private JFXButton delete;
    @FXML
    private JFXButton update;
    IReponseService re = new ReponseService();
    IQuestionService questionService = new QuestionService();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadscen();

    }
    private void loadscen() {
        
        NavigatorData.getInstance().getUserlogedIn();
        NavigatorData.getInstance().setUserlogedIn(NavigatorData.getInstance().getUserlogedIn());       
        if (questionService.TestIsUser(NavigatorData.getInstance().getUserlogedIn().getId(),NavigatorData.getInstance().getSelectedQusetion().getIdQuestion()) == null) {
            delete.setVisible(false);
            update.setVisible(false);    
        }
        delete.getStyleClass().add("button-raised");
        update.getStyleClass().add("button-raised");
        AnswerButton.getStyleClass().add("button-raised");        
        title.setText(t);
        content.setText(c);
        Answer.getStyleClass().add("bt");
        IReponseService re = new ReponseService();
        list = re.getReponseByQustion(NavigatorData.getInstance().getSelectedQusetion());      
        Reponses = FXCollections.observableArrayList();
        Reponses.addAll(list);
        LisyReponse.setItems(Reponses);
        LisyReponse.setCellFactory(new Callback<ListView<Reponse>, ListCell<Reponse>>() {
            @Override
            public ListCell<Reponse> call(ListView<Reponse> ListViewReponses) {
                ListCell<Reponse> cell = new ListCell<Reponse>() {
                    @Override
                    protected void updateItem(Reponse r, boolean bln) {
                        super.updateItem(r, bln);
                        if (r != null) {
                            VBox vb1 = new VBox();
                            VBox vb2 = new VBox(20);
                            HBox hbC = new HBox(20);
                            vb2.setPrefWidth(400);
                            vb1.setAlignment(Pos.CENTER);
                            vb2.setAlignment(Pos.CENTER_LEFT);
//                            hbC.setStyle("-fx-border-color: #C8C8C8 ;");
                            Text contenu = new Text();
                            String contenuString = r.getContenu();
                          
                            contenu.setText(contenuString);
                            Label userNameL = new Label();
                            userNameL.setStyle("-fx-text-fill: #C8C81E;-fx-font-weight: bold;-fx-font-family: \"Arial\";");
                            String userName = r.getRepondeur().getNom();
                        
                            userNameL.setText(userName);
                            //image profile
                            ImageView Person = new ImageView("/Utils/jihn-highnoon-splash-final.jpg");
                            Person.setFitWidth(50);
                            Person.setFitHeight(50);
                            Person.getStyleClass().add("imageProfil");
                            vb1.getChildren().addAll(Person, userNameL);
                            vb2.getChildren().addAll(contenu);
                            hbC.getChildren().addAll(vb1, vb2);
                            setGraphic(hbC);                            
                        }
                    }

                };

                return cell;
            }
        });
        
    }
    @FXML
    private void AnswerAction(ActionEvent event) {
        String contenu = Answer.getText();        
        if (!contenu.equals("")) {            
            Question quu = questionService.findbyTitre(t);
            Reponse r = new Reponse(contenu, Utilite.utile, quu, NavigatorData.getInstance().getUserlogedIn());
            re.add(r);  
            list = re.getReponseByQustion(NavigatorData.getInstance().getSelectedQusetion());
            Reponses = FXCollections.observableArrayList();
            Reponses.addAll(list);
            LisyReponse.setItems(Reponses);
            LisyReponse.setCellFactory(new Callback<ListView<Reponse>, ListCell<Reponse>>() {
                @Override
                public ListCell<Reponse> call(ListView<Reponse> ListViewReponses) {
                    ListCell<Reponse> cell = new ListCell<Reponse>() {
                        @Override
                        protected void updateItem(Reponse r, boolean bln) {
                            super.updateItem(r, bln);
                            if (r != null) {
                                VBox vb1 = new VBox();
                                VBox vb2 = new VBox(20);
                                HBox hbC = new HBox(20);
                                vb2.setPrefWidth(400);
                                vb1.setAlignment(Pos.CENTER);
                                vb2.setAlignment(Pos.CENTER_LEFT);
//                            hbC.setStyle("-fx-border-color: #C8C8C8 ;");
                                Text contenu = new Text();

                                String contenuString = r.getContenu();
                                contenu.setText(contenuString);
                                Label userNameL = new Label();
                                userNameL.setStyle("-fx-text-fill: #C8C81E;-fx-font-weight: bold;-fx-font-family: \"Arial\";");
                                String userName = r.getRepondeur().getNom();
                                userNameL.setText(userName);
                                //image profile
                                ImageView Person = new ImageView("/Utils/jihn-highnoon-splash-final.jpg");
                                Person.setFitWidth(50);
                                Person.setFitHeight(50);
                                Person.getStyleClass().add("imageProfil");
                                vb1.getChildren().addAll(Person, userNameL);
                                vb2.getChildren().addAll(contenu);
                                hbC.getChildren().addAll(vb1, vb2);
                                setGraphic(hbC);
                            }
                        }

                    };
                    return cell;
                }
            });
            Answer.clear();
        }
    }
    @FXML
    private void deletequestion(ActionEvent event) {
        IQuestionService questionService = new QuestionService();
        Question quu = questionService.findbyTitre(t);
        IReponseService r = new ReponseService();
        List<Reponse> reponses = quu.getReponses();
        for (Reponse reponse : reponses) {
            r.delete(reponse.getIdReponse());
        }
        questionService.deleteQuestionCompetence(quu.getIdQuestion());
        questionService.delete(quu.getIdQuestion());
        Navigator.LoadScene(Navigator.MesQuestions);
    }

    @FXML
    private void updatequestion(ActionEvent event) {
        IQuestionService questionService = new QuestionService();
        Question quu = questionService.findbyTitre(t);
        NavigatorData.getInstance().setSelectedQusetion(quu);
        Navigator.LoadScene(Navigator.ProposerQuestion);
    }

}
