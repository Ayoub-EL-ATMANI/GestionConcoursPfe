package Controller;

import dao.DaoEncadrant;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

//c'est la page mère de tous
//il ya un dashboard à gauche
//lors de click sur un lien on fait changer le center
public class ChefDepartementWelcome implements Initializable {

	@FXML
	private BorderPane borderpane;
	@FXML
	private VBox ContainerLink ;
	@FXML
	private Hyperlink groupe_pfe;



	public void groupe_de_pfe() {

		AccueilA.stage.setMaximized(true);
		AccueilA.stage.setResizable(true);

	}
	
	public void Concours() {


		LoadUI("/vue/Concours.fxml");
		Concours.cmp = 0;
		AccueilA.stage.setMaximized(false);
		AccueilA.stage.setResizable(false);


	}



	//accepte en parametre une interface et l'insere en center
	public void LoadUI(String ui) {
		Parent root=null;
		try {
			root=FXMLLoader.load(getClass().getResource(ui));		
		} catch (Exception e) {
			e.printStackTrace();
		}
		borderpane.setCenter(root);
	}


	public void ConcoursClick(ActionEvent event) {

		LoadUI("/vue/Documents.fxml");

		AccueilA.stage.setMaximized(false);
		AccueilA.stage.setResizable(false);




	}

	public void logout(ActionEvent event) {

		AccueilA.userA = null;
		final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/vue/accueilA.fxml"));
			Scene scene = new Scene(parent);
			Stage stage1 = new Stage();
			stage1.setScene(scene);
			stage1.setResizable(false);
			stage1.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Vector<String> filieres= DaoEncadrant.DepartementFilieres(AccueilA.CurrentEncadrant.getDepartement());
		if (AccueilA.CurrentEncadrant != null) {
			System.out.println("Not Null");
			System.out.println(AccueilA.CurrentEncadrant.getDepartement()+"   "+AccueilA.CurrentEncadrant.getFiliere());
			for (String fil : filieres) {
				Hyperlink filiere=new Hyperlink(fil);filiere.setId(fil);filiere.setTextFill(Paint.valueOf("white"));	filiere.setPrefWidth(107);	filiere.setAlignment(Pos.CENTER);
				filiere.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						AccueilA.CurrentEncadrant.setFiliere(fil);
						LoadUI("/vue/GroupePfe.fxml");
					}
				});
				ContainerLink.getChildren().add(filiere);
			}

		}

	}

	public void userClick(ActionEvent event) {
		Utilisateurs.cmp = 0;
		LoadUI("/vue/Utilisateurs.fxml");

		AccueilA.stage.setMaximized(false);
		AccueilA.stage.setResizable(false);



	}
}
