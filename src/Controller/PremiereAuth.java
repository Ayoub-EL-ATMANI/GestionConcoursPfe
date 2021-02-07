package Controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import traitement.User;
import Model.UserModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PremiereAuth {

    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXTextField txtCode;
    @FXML
    private JFXPasswordField txtPwd;
    @FXML
    private JFXPasswordField txtPwdConfirme;

    private User userT = new User();
    private UserModel userModel;
    private Frame frame  = new Frame();
    private static final String messageEmpty = "Veuillez remplir tous les champs";
    private static final String TitleEmpty = "Champs vide";
    private static final String messagePwd = "les motes de passes ne sont pas identiques";
    private static final String TitlePwd = "les motes de passes ne sont pas identiques";
    private static final String messageUpdate = "Vous venez de modifiez vos informations de connection ,Cliquer sur Ok pour Confirmer";
    private static final String titleUpdate = "Confiramtion";


    public void ConfirmerClick(ActionEvent event) {

        String codeSecret = txtCode.getText();
        String pwd = txtPwd.getText();
        String pwdConfirme = txtPwdConfirme.getText();

        if (codeSecret.equals("") || pwd.equals("") || pwdConfirme.equals("")){

            JOptionPane.showMessageDialog(frame,messageEmpty+"",TitleEmpty+"",JOptionPane.ERROR_MESSAGE);
            frame.setLocationRelativeTo(null);
        }
        else if (!pwd.equals(pwdConfirme)){
            JOptionPane.showMessageDialog(frame,messagePwd+"",TitlePwd+"",JOptionPane.ERROR_MESSAGE);
            frame.setLocationRelativeTo(null);
        }

        else{
                userModel = new UserModel();
                userModel.setUser(txtUsername.getText());
                userModel.setPassword(txtPwd.getText());
                userModel.setCodeSecret(txtCode.getText());
                userModel.setType("candidat");

              int a =  JOptionPane.showConfirmDialog(frame,messageUpdate+"",titleUpdate+"",JOptionPane.YES_NO_OPTION);
                if (a == JOptionPane.YES_OPTION){
                    userT.ModifierUser(userModel,AccueilA.userA);

                    final Node source =  (Node) event.getSource();
                    final Stage s= (Stage) source.getScene().getWindow();
                    s.close();

                    try {
                        Stage stage = new Stage();
                        Parent pane = FXMLLoader.load(getClass().getResource("/vue/accueilA.fxml"));
                        Scene scene = new Scene(pane);
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
        }

    }
}
