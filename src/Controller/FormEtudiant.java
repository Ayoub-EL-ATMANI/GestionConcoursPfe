package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import traitement.User;
import Model.UserModel;
import traitement.etudiantTrait;
import Model.etudiants;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FormEtudiant implements Initializable {
	
	@FXML
	private JFXTextField txtnom;
	@FXML
	private JFXTextField txtprenom;
	@FXML
	private JFXTextField txtcne;
	@FXML
	private JFXTextField txtmail;
	@FXML
	private JFXTextField txtville;
	@FXML
	private JFXTextField txtdepart;
	@FXML
	private JFXTextField txtfilie;
	@FXML
	private JFXTextField txttele;
	@FXML
	private JFXTextArea txtaddress;
	@FXML
	private JFXDatePicker txtdate;
	@FXML
	private JFXTextField txtcode;
	@FXML
	private JFXButton bttback;
	@FXML
	private JFXButton bttval;
	@FXML
	private GridPane gridpane;
	@FXML
	private StackPane stackpane;
	private User user = new User();
	private UserModel userModel ;
	private Frame frame = new Frame();
	private static final String type = "Etudiant";
	private etudiants e;
	//private etudiantDao ee;
	private etudiantTrait ett;
	public void addclick(ActionEvent a) {
		// UserDao us = new UserDao();
		 e = new etudiants();
		 ett = new etudiantTrait();
		 e.setNom(txtnom.getText());e.setPrenom(txtprenom.getText());
		 e.setCne(txtcne.getText());e.setMail(txtmail.getText());
		 e.setVille(txtville.getText());e.setAddresse(txtaddress.getText());
		 e.setTel(txttele.getText());e.setDepartement(txtdepart.getText());
		// e.setDate_n();
		 e.setFiliere(txtfilie.getText());e.setDate_n(Date.valueOf(txtdate.getValue()));
		 e.setIsCandidat(0);
		 e.setCodeinsc(txtcode.getText());
		// e.setId_user(UserDao.getLastUser()+1);
		 System.out.println(" methode");
		//stackpane = new StackPane();
		 if(e.getCodeinsc().equals("") || e.getNom().equals("") || e.getPrenom().equals("") || e.getCne().equals("") || e.getMail().equals("")
			|| e.getVille().equals("") || e.getAddresse().equals("") || e.getTel().equals("") || 
			e.getDepartement().equals("") || e.getFiliere().equals("") || e.getDate_n().equals(null))
		 {


	            frame.setTitle("error");
	            JOptionPane.showMessageDialog(frame,"Champs vide","Veuillez remplir tout les champs",JOptionPane.ERROR_MESSAGE);
	            frame.setLocationRelativeTo(null);
	        }
		 if (ett.checkEtudiant(txtcne.getText())){

	            JOptionPane.showMessageDialog(frame,"CNE erreur","CNE deja inscrit",JOptionPane.ERROR_MESSAGE);
	            frame.setLocationRelativeTo(null);
	            }
		  if(!ett.checkcode(txtcode.getText())) {
			 JOptionPane.showMessageDialog(frame,"Code erreur","Code de confirmation  deja inscrit",JOptionPane.ERROR_MESSAGE);
	            frame.setLocationRelativeTo(null);
		 }
		 
		 else {
			 userModel = new UserModel();
			 StringBuffer username = new StringBuffer();
			 username.append(e.getNom()+"@"+e.getPrenom());
			 String pwd = e.getNom()+username.hashCode();
			 System.out.println("------------------------->"+user+" "+pwd);
			 userModel.setUser(username.toString());
	            userModel.setPassword(pwd);
	            userModel.setType(type);
	            if (user.addUsers(userModel)){

					ett.addEtudiantA(e);
					JOptionPane.showMessageDialog(frame,"vous avez  ajouter avec succes," +
							" veuillez noter les information pour la prochaine connection \n login:      "+
							username+" \n password: "+pwd,"Candidature reussite",JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(frame,"Erreur d'ajout","Candidature Echouee",JOptionPane.INFORMATION_MESSAGE);
				}
	         frame.setLocationRelativeTo(null);
			 
			 }
		 
	}
	
	public void backclick(ActionEvent a) {
		  final Node source = (Node) a.getSource();
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
		// TODO Auto-generated method stub
		
		txtdate.setValue(LocalDate.now());
	}
}
