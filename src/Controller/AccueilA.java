package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import traitement.Accueil;
import Model.UserModel;
import Model.encadrants;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AccueilA implements Initializable {

        @FXML
        private AnchorPane rootPane;
        @FXML
        private JFXButton login;
        @FXML
        private JFXButton consulter;
        @FXML
        private JFXTextField txtUser;
        @FXML
        private JFXPasswordField txtPwd;
        @FXML
        private Label lblError;

        public static Object CurrentConnected;//soit etudiants soit encadrants
        public static encadrants CurrentEncadrant;
        public  static String userA="";
        private UserDao ud = new UserDao();
        private Accueil accueil = new Accueil();
        private Frame frame = new Frame();
        public static Stage stage = null;

        //click pour se connecter
    public void loginClick(ActionEvent ev) {
        //accueil = new Accueil();
        String pwd  =  txtPwd.getText();
        String user = txtUser.getText();
        UserModel userModel = ud.getUser(user,pwd);
        System.out.println("here"+user+" "+pwd);
        try {
            //get l'objet connecte: OBJECT et on test avec le type s'il est chef ou etudiant et candidat
            CurrentConnected = ud.getUserInfo(txtUser.getText(), txtPwd.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }
            //verifier l'utilisateur s'il est éxiste ou non
        if (accueil.isUser(userModel)) {

            userA = user;
            System.out.println("user OK");
            String type = accueil.type(user);
             stage = new Stage();
            final Node source = (Node) ev.getSource();
            final Stage s = (Stage) source.getScene().getWindow();
            s.close();
            Parent pane = null;
            System.out.println(type);
            boolean isChef=false;
            switch (type){
                case "chef":
                    CurrentEncadrant=(encadrants) CurrentConnected;
                    System.out.println("chef "+CurrentEncadrant.getDepartement()+" "+CurrentEncadrant.getFiliere());
                    try {
                       isChef=true;
                       pane = FXMLLoader.load(getClass().getResource("/vue/ChefDepartementWelcome.fxml"));

                    } catch (Exception e) {
                        System.out.println("error loadind FXML file");
                        e.printStackTrace();
                    }

                break;
                case  "Etudiant":
                    try {
                    	isChef=false;
                        pane = FXMLLoader.load(getClass().getResource("/vue/Etudiants.fxml"));

                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    break;
                case  "encadrant":
                	isChef=false;
                    break;
                case "candidat":
                	isChef=false;
                    if (userModel.getCodeSecret()== null){

                        try{

                            pane = FXMLLoader.load(getClass().getResource("/vue/PremiereAuth.fxml"));

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else {
                        try{

                            pane = FXMLLoader.load(getClass().getResource("/vue/CandidatEspace.fxml"));

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    break;
            }
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();


        }
        else {
            lblError.setText("Login ou mot de passe incorrecte");
            System.out.println("error");
        }

            //JOptionPane.showMessageDialog(frame,"Login ou mot de passe invalide","Incorrecte",JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //Consulter la liste des concours
    public void consulterClick(ActionEvent ev) {


        System.out.println("Consulter concours");
        rootPane.setVisible(false);
        Stage stage = new Stage();
        try {
           // FXMLLoader fxmlLoader  = FXMLLoader.load(getClass().getResource("vue/accueilA.fxml"));
            final Node source = (Node) ev.getSource();
            final Stage s = (Stage) source.getScene().getWindow();
            s.close();

            Parent parent = FXMLLoader.load(getClass().getResource("/vue/listConcours.fxml"));
            
          //Parent parent = FXMLLoader.load(getClass().getResource("/vue/FormEtudiant.fxml"));

            Scene sc = new Scene(parent);
           String css  = this.getClass().getResource("/vue/conc.css").toExternalForm();
           sc.getStylesheets().add(css);
            stage.setScene(sc);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Formulaire  Inscription d'un etudiaant
    public void etudiantclick(ActionEvent event) {

        Stage stage = new Stage();
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/vue/FormEtudiant.fxml"));
            Scene sc = new Scene(parent);
            stage.setScene(sc);
            stage.setResizable(false);
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
        final Node source = (Node) event.getSource();
        final Stage stage1  = (Stage) source.getScene().getWindow();
        stage1.close();

    }
}
