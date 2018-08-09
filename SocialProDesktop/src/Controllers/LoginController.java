/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import IServices.IUserService;
import Models.NavigatorData;
import Models.User;
import Services.UserService;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author Mortadhafff
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField user;
    @FXML
    private JFXPasswordField password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void Login(ActionEvent event) {
         String CIN = user.getText();
        String pass = password.getText();
        IUserService u = new UserService();
        User lo = u.findByCIN(CIN);
        
        if (CIN.equals("") && pass.equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("please Enter all feilds");
            alert.showAndWait();               
        } else {
            if (lo == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("login or password incorect");
                alert.showAndWait();
            } else {
                NavigatorData.getInstance().setUserlogedIn(u.findByCIN(CIN));                 
            }
        }
    }

}
