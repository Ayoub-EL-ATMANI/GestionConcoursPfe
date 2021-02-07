package Controller;


import Model.Candidat;
import Model.CandidatT;
import Model.ConcoursModel;
import Model.UserModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import traitement.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Candidature implements Initializable {

    @FXML
    private JFXTextField txtTitre;
    @FXML
    private JFXTextField txtNom;

    @FXML
    private JFXTextField txtPrenom;

    @FXML
    private JFXDatePicker txtDate;

    @FXML
    private JFXTextField txtCne;

    @FXML
    private JFXTextField txtMail;

    @FXML
    private JFXTextField txtAdress;

    @FXML
    private JFXTextField txtTel;

    @FXML
    private JFXTextField txtDip;

    @FXML
    private JFXTextField txtEta;
    @FXML
    private JFXTextField txtNoteBac;
    @FXML
    private JFXTextField txtNoteDip;

    @FXML
    private JFXButton btnValider;

    private Frame frame = new Frame();
    private CandidatT ct = new CandidatT();
    private User user = new User();
    private UserModel userModel ;
    private static final String type = "candidat";

    //Ajouter un condidat
    public void validerClick(ActionEvent actionEvent) {


        Integer noteBac = 0;
        Integer noteDip = 0;
        if (!txtNoteBac.getText().equals("") && !txtNoteDip.getText().equals("")){
            try {
                noteBac = Integer.parseInt(txtNoteBac.getText());
                noteDip = Integer.parseInt(txtNoteDip.getText());
            } catch (NumberFormatException e){
                System.out.println("pas float");

            }
        }

        Candidat candidat = new Candidat();
        candidat.setNom(txtNom.getText());
        candidat.setPrenom(txtPrenom.getText());
        candidat.setCne(txtCne.getText());
        candidat.setMail(txtMail.getText());
        candidat.setAddresse(txtAdress.getText());
        candidat.setTel(txtTel.getText());
        candidat.setDiplome(txtDip.getText());
        candidat.setEta(txtEta.getText());
        candidat.setNoteDossier(((noteBac*0.25F)+(noteDip*0.75F)));
        candidat.setIsCandidat(1);
        candidat.setId_concours(ListConcours.cmo.getId_concours());
        System.out.println("valider "+candidat.getNom());

        if (candidat.getNom().equals("") || candidat.getPrenom().equals("") || candidat.getDiplome().equals("")
                || candidat.getEta().equals("") || candidat.getAddresse().equals("") || txtCne.getText().length()!=10
                || candidat.getCne().equals("") || candidat.getMail().equals("")
                || candidat.getTel().equals("") || candidat.getMail().equals("")
                || txtNoteDip.getText().equals("") || txtNoteBac.getText().equals("") || txtDate.getValue().equals(null)
        ){


            frame.setTitle("error");
            JOptionPane.showMessageDialog(frame,"Champs vide","Veuillez remplir tout les champs",JOptionPane.ERROR_MESSAGE);
            frame.setLocationRelativeTo(null);
        }
        else if (ct.checkCandidat(txtCne.getText())){

            JOptionPane.showMessageDialog(frame,"CNE erreur","CNE deja inscrit",JOptionPane.ERROR_MESSAGE);
            frame.setLocationRelativeTo(null);
        }
        else {
            ConcoursModel cm = ListConcours.cmo;
            candidat.setDate_n(Date.valueOf(txtDate.getValue()));
            userModel = new UserModel();

            StringBuffer username = new StringBuffer();
            username.append(candidat.getNom()+"@"+candidat.getPrenom());
            String pass = cm.getAlias()+username.hashCode();
            System.out.println("------------------------->"+user+" "+pass);

            userModel.setUser(username.toString());
            userModel.setPassword(pass);
            userModel.setType(type);
           try{

               if (user.addUsers(userModel)){
                   ct.addCandidat(candidat);
                   JOptionPane.showMessageDialog(frame,"Candidat ajouter avec succes," +
                           " veuillez noter les information pour la prochaine connection \n login:      "+
                           username+" \n password: "+pass,"Candidature reussite",JOptionPane.INFORMATION_MESSAGE);
                   frame.setLocationRelativeTo(null);
                   System.out.println(candidat);
                   actualiser();
               }
               else {

                   JOptionPane.showMessageDialog(frame,"Veuillez verifier vos information","Candidature Echouee",JOptionPane.INFORMATION_MESSAGE);
               }


           }catch (Exception e){
               JOptionPane.showMessageDialog(frame,"Verifier votre information","Error",JOptionPane.ERROR_MESSAGE);
           }
        }
    }


    public void actualiser(){

        txtNom.setText("");
        txtPrenom.setText("");
        txtMail.setText("");
        txtDate.setValue(LocalDate.now());
        txtAdress.setText("");
        txtCne.setText("");
        txtTel.setText("");
        txtDip.setText("");
        txtEta.setText("");
        txtNoteBac.setText("");
        txtNoteDip.setText("");

    }

    public void retourClick(ActionEvent event) {


        final Node source = (Node) event.getSource();
        final Stage s = (Stage) source.getScene().getWindow();
        s.close();
        Parent pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("/vue/accueilA.fxml"));
            Scene scene = new Scene(pane);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtTitre.setText(ListConcours.cmo.getTitreConcours());
    }
}
