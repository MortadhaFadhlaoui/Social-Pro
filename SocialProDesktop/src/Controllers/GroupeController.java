/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import IServices.IGroupeService;
import IServices.IMessageService;
import IServices.IUserService;
import Models.CopyFile;
import Models.Message;
import Models.NavigatorData;
import Models.User;
import Services.GroupeService;
import Services.MessageService;
import Services.UserService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Mortadhafff
 */
public class GroupeController implements Initializable {

    @FXML
    private Label groupename;
    @FXML
    private ImageView imagegroupe;
    @FXML
    private JFXListView<HBox> listuser;
    @FXML
    private JFXTextArea send;
    @FXML
    private JFXButton Upload;
    @FXML
    private VBox VBoxOnwer;
    @FXML
    private JFXButton Delete;
    @FXML
    private JFXButton Update;
    IGroupeService GG = new GroupeService();
    IUserService uu = new UserService();
    @FXML
    private TextField memeberinvit;
    List<String> UsersName = new ArrayList<>();
    File file;
    private ObservableList<Message> Messages;
    List list = new ArrayList();
    IMessageService mess = new MessageService();
    @FXML
    private JFXListView<Message> ListMessage;
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilterAll = new FileChooser.ExtensionFilter("All Files (*.*)", "*");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chargeScene();
    }

    private void chargeScene() {
        NavigatorData.getInstance().getUserlogedIn();
        NavigatorData.getInstance().setUserlogedIn(NavigatorData.getInstance().getUserlogedIn());
        NavigatorData.getInstance().getSelectedGroupe();
        if (GG.ChechGroupeOwner(NavigatorData.getInstance().getSelectedGroupe().getIdGroupe(), NavigatorData.getInstance().getUserlogedIn().getId()) == null) {
            Delete.setVisible(false);
            Update.setVisible(false);
            memeberinvit.setVisible(false);
        } else {
            Update.getStyleClass().add("button-raised");
            Delete.getStyleClass().add("button-raised");
        }
        for (User user : uu.getAll()) {
            if (user.getId() != NavigatorData.getInstance().getSelectedGroupe().getOwner().getId()) {
                UsersName.add(user.getNom() + " " + user.getPrenom());
            }
        }
        TextFields.bindAutoCompletion(memeberinvit, UsersName);
        for (User us : GG.getUsersByGroup(NavigatorData.getInstance().getSelectedGroupe().getIdGroupe())) {
            //hbox
            HBox h = new HBox();
            h.setSpacing(50);
            //grid1
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
            Label nom = new Label(String.valueOf(us.getNom()) + " " + String.valueOf(us.getPrenom()));
            nom.getStyleClass().add("title");
            grid.add(nom, 0, 2);
            h.getChildren().addAll(grid);
            listuser.getItems().add(h);
            listuser.setExpanded(true);
            listuser.depthProperty().set(1);
            listuser.setCellFactory(QuestionListView -> new JFXListCell());
        }

        list = mess.getUserMessageGroupe(NavigatorData.getInstance().getUserlogedIn().getId(), NavigatorData.getInstance().getSelectedGroupe().getIdGroupe());
        Messages = FXCollections.observableArrayList();
        Messages.addAll(list);
        ListMessage.setItems(Messages);
        ListMessage.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            @Override
            public ListCell<Message> call(ListView<Message> ListViewReponses) {
                ListCell<Message> cell = new ListCell<Message>() {
                    @Override
                    protected void updateItem(Message m, boolean bln) {
                        super.updateItem(m, bln);
                        if (m != null) {
                            VBox vb1 = new VBox();
                            VBox vb2 = new VBox(20);
                            HBox hbC = new HBox(20);
                            vb2.setPrefWidth(400);
                            vb1.setAlignment(Pos.CENTER);
                            vb2.setAlignment(Pos.CENTER_LEFT);
                            Text contenu = new Text();
                            String contenuString = m.getContenu();
                            contenu.setText(contenuString);
                            ImageView icon = new ImageView("/Utils/save-file.png");
                            icon.setFitWidth(10);
                            icon.setFitHeight(10);
                            if (m.getContenu().indexOf(".") == -1) {
                                icon.setVisible(false);
                            } else {
                                icon.setVisible(true);
                            }
                            Label userNameL = new Label();
                            userNameL.setStyle("-fx-text-fill: #C8C81E;-fx-font-weight: bold;-fx-font-family: \"Arial\";");
                            String userName = m.getEmetteur().getNom();
                            userNameL.setText(userName);
                            //image profile
                            ImageView Person = new ImageView("/Utils/jihn-highnoon-splash-final.jpg");
                            Person.setFitWidth(50);
                            Person.setFitHeight(50);
                            Person.getStyleClass().add("imageProfil");
                            vb1.getChildren().addAll(Person, userNameL);
                            vb2.getChildren().addAll(contenu, icon);
                            hbC.getChildren().addAll(vb1, vb2);
                            setGraphic(hbC);
                        }
                    }

                };

                return cell;
            }
        });
        groupename.setText(NavigatorData.getInstance().getSelectedGroupe().getNom());
        Image image = new Image("file:///c:/wamp64/www/Image/" + NavigatorData.getInstance().getSelectedGroupe().getImage());
        imagegroupe.setImage(image);
        Upload.getStyleClass().add("button-raised");
    }

    @FXML
    private void sendmessage(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            String contenu = send.getText();
            if (!contenu.equals("")) {
                Message r = new Message(contenu, NavigatorData.getInstance().getUserlogedIn(), NavigatorData.getInstance().getSelectedGroupe());
                mess.add(r);
                list = mess.getUserMessageGroupe(NavigatorData.getInstance().getUserlogedIn().getId(), NavigatorData.getInstance().getSelectedGroupe().getIdGroupe());
                Messages = FXCollections.observableArrayList();
                Messages.addAll(list);
                ListMessage.setItems(Messages);
                ListMessage.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
                    @Override
                    public ListCell<Message> call(ListView<Message> ListViewReponses) {
                        ListCell<Message> cell = new ListCell<Message>() {
                            @Override
                            protected void updateItem(Message m, boolean bln) {
                                super.updateItem(m, bln);
                                if (m != null) {
                                    VBox vb1 = new VBox();
                                    VBox vb2 = new VBox(20);
                                    HBox hbC = new HBox(20);
                                    vb2.setPrefWidth(400);
                                    vb1.setAlignment(Pos.CENTER);
                                    vb2.setAlignment(Pos.CENTER_LEFT);
                                    Text contenu = new Text();
                                    String contenuString = m.getContenu();
                                    contenu.setText(contenuString);
                                    ImageView icon = new ImageView("/Utils/save-file.png");
                                    icon.setFitWidth(10);
                                    icon.setFitHeight(10);
                                    if (m.getContenu().indexOf(".") == -1) {
                                        icon.setVisible(false);
                                    } else {
                                        icon.setVisible(true);
                                    }
                                    Label userNameL = new Label();
                                    userNameL.setStyle("-fx-text-fill: #C8C81E;-fx-font-weight: bold;-fx-font-family: \"Arial\";");
                                    String userName = m.getEmetteur().getNom();
                                    userNameL.setText(userName);
                                    //image profile
                                    ImageView Person = new ImageView("/Utils/jihn-highnoon-splash-final.jpg");
                                    Person.setFitWidth(50);
                                    Person.setFitHeight(50);
                                    Person.getStyleClass().add("imageProfil");
                                    vb1.getChildren().addAll(Person, userNameL);
                                    vb2.getChildren().addAll(contenu, icon);
                                    hbC.getChildren().addAll(vb1, vb2);
                                    setGraphic(hbC);
                                }
                            }

                        };

                        return cell;
                    }
                });
                send.clear();
            }
        }

    }

    @FXML
    private void Uploadfile(ActionEvent event) throws IOException {
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            File dest = new File("C:\\wamp64\\www\\file\\" + file.getName());
            CopyFile.copyFileUsingStream(file, dest);
            Message r = new Message(file.getName(), NavigatorData.getInstance().getUserlogedIn(), NavigatorData.getInstance().getSelectedGroupe());
            mess.add(r);
            list = mess.getUserMessageGroupe(NavigatorData.getInstance().getUserlogedIn().getId(), NavigatorData.getInstance().getSelectedGroupe().getIdGroupe());
            Messages = FXCollections.observableArrayList();
            Messages.addAll(list);
            ListMessage.setItems(Messages);
            ListMessage.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
                @Override
                public ListCell<Message> call(ListView<Message> ListViewReponses) {
                    ListCell<Message> cell = new ListCell<Message>() {
                        @Override
                        protected void updateItem(Message m, boolean bln) {
                            super.updateItem(m, bln);
                            if (m != null) {
                                VBox vb1 = new VBox();
                                VBox vb2 = new VBox(20);
                                HBox hbC = new HBox(20);
                                vb2.setPrefWidth(400);
                                vb1.setAlignment(Pos.CENTER);
                                vb2.setAlignment(Pos.CENTER_LEFT);
                                Label filename = new Label();
                                filename.setText(m.getContenu());
                                ImageView icon = new ImageView("/Utils/save-file.png");
                                icon.setFitWidth(10);
                                icon.setFitHeight(10);
                                if (m.getContenu().indexOf(".") == -1) {
                                    icon.setVisible(false);
                                } else {
                                    icon.setVisible(true);
                                }
                                Label userNameL = new Label();
                                userNameL.setStyle("-fx-text-fill: #C8C81E;-fx-font-weight: bold;-fx-font-family: \"Arial\";");
                                String userName = m.getEmetteur().getNom();
                                userNameL.setText(userName);
                                //image profile
                                ImageView Person = new ImageView("/Utils/jihn-highnoon-splash-final.jpg");
                                Person.setFitWidth(50);
                                Person.setFitHeight(50);
                                Person.getStyleClass().add("imageProfil");
                                vb1.getChildren().addAll(Person, userNameL);
                                vb2.getChildren().addAll(filename, icon);
                                hbC.getChildren().addAll(vb1, vb2);
                                setGraphic(hbC);
                            }
                        }

                    };

                    return cell;
                }
            });
            Notifications notification = Notifications.create()
                    .title("Download Complete")
                    .text("File Saved")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_LEFT);
            notification.darkStyle();
            notification.showConfirm();
        }
    }

    @FXML
    private void addmember(ActionEvent event) {
//        System.out.println(NavigatorData.getInstance().getSelectedGroupe().getIdGroupe());
//        
//         User user= uu.findUserByName(memeberinvit.getText());
//         GG.addUserGroupe(user.getId(), NavigatorData.getInstance().getSelectedGroupe().getIdGroupe());
//         Notifications notification = Notifications.create()
//                    .title(memeberinvit.getText()+" Added to the groupe")
//                    .text("Enjoyer chat")
//                    .graphic(null)
//                    .hideAfter(Duration.seconds(5))
//                    .position(Pos.TOP_LEFT);
//                    notification.darkStyle();
//                     notification.showConfirm();
        memeberinvit.clear();

    }

    @FXML
    private void deletegroup(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure");
        alert.showAndWait();
        GG.deleteUserGroupe(NavigatorData.getInstance().getUserlogedIn().getId(), NavigatorData.getInstance().getSelectedGroupe().getIdGroupe());
        for (Message me : mess.getMessagebyGroup(NavigatorData.getInstance().getSelectedGroupe().getIdGroupe())) {
            mess.delete(me.getIdMessage());
        }
        GG.delete(NavigatorData.getInstance().getSelectedGroupe().getIdGroupe());
        Navigator.LoadScene(Navigator.GroupsYouManage);
    }

    @FXML
    private void updategroupe(ActionEvent event) {
    }

    @FXML
    private void download(MouseEvent event) throws IOException {
        String contenu = ListMessage.getSelectionModel().getSelectedItem().getContenu();
        if (!contenu.isEmpty()) {
            if (contenu.contains(".")) {
                fileChooser.setTitle("Save");
                File file = fileChooser.showSaveDialog(null);
                if (file != null) {
                    SaveFile(contenu, file);
                }
            }
        }
    }

    private void SaveFile(String content, File file) throws IOException {
        FileWriter fileWriter = null;
        fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();
        Notifications notification = Notifications.create()
                .title(memeberinvit.getText() + " Added to the groupe")
                .text("Download finished")
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_LEFT);
        notification.darkStyle();
        notification.showConfirm();
    }
}
