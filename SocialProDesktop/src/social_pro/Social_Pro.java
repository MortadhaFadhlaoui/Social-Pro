/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package social_pro;

import Controllers.MainFXMLController;
import Controllers.Navigator;
import Technique.MultiThreadedServer;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Mortadhafff
 */
public class Social_Pro extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(new Image("/Utils/app.png"));
        stage.setScene(
                createScene(
                        loadMainPane()
                )
        );

        stage.show();
    }

    private Pane loadMainPane() throws IOException {
        
        FXMLLoader loader = new FXMLLoader();           
        Pane mainPane = (Pane) loader.load(
                getClass().getResourceAsStream(
                        Navigator.Main
                )
        );
        MainFXMLController mainController = loader.getController();

        Navigator.setMainController(mainController);
        Navigator.LoadScene(Navigator.Forum);
        

        return mainPane;
    }

    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(
                mainPane
        );
        return scene;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        launch(args);
        MultiThreadedServer server = new MultiThreadedServer(9000);
new Thread(server).start();

try {
    Thread.sleep(20 * 1000);
} catch (InterruptedException e) {
    e.printStackTrace();
}
System.out.println("Stopping Server");
server.stop();
//        IUserService ui = new UserService();
//        User u = new User(11,"fff", "Achref", "Adala", "achref.adala@esprit.tn", "wwwww", Role.user, Statut.libre, Etat.actif);
//        ui.add(u);
//        IGroupeService group = new GroupeService();
//        Groupe g = new Groupe(100,"Group1", "hello world", "im sure i will do it", u);
//        group.add(g);

    }
    
}
