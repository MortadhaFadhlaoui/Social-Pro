/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.IOException;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author Mortadhafff
 */
public class Navigator {

    public static final String AddGroup = "/GUI/AddGroup.fxml";    
    public static final String Main = "/GUI/MainFXMLController.fxml";
    public static final String ProposerQuestion = "/GUI/AddQuestion.fxml";
    public static final String Forum = "/GUI/ListQuestion.fxml";
    public static final String Question = "/GUI/Question.fxml";
    public static final String Group = "/GUI/Groups.fxml";
    public static final String login = "/GUI/login.fxml";
    public static final String MesQuestions = "/GUI/MesQuestions.fxml";
    public static final String GroupsYouManage="/GUI/GroupsYouManage.fxml";
    public static final String Groupe="/GUI/Groupe.fxml";
    private static MainFXMLController mainControlleur;

    public static void setMainController(MainFXMLController mainControlleur) {
        Navigator.mainControlleur = mainControlleur;
    }

    public static void LoadScene(String fxml) {
        try {
            mainControlleur.setAPance(FXMLLoader.load(Navigator.class.getResource(fxml)
            )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
