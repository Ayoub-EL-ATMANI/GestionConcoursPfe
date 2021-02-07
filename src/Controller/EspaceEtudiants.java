package Controller;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.EspaceEtudiantDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView.TreeTableViewSelectionModel;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import Model.lisegroup;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

public class EspaceEtudiants {
	
	
	private JFXTreeTableView<lisegroup> treeTable;
	 private  static TreeTableViewSelectionModel<lisegroup> treeTableSelect;
	 
	 @FXML
	 private StackPane stackpanetudiants;
	 @FXML
	 private Hyperlink hyperDeconx;
	 @FXML
	 private AnchorPane panetotal;
	 public void affichetable(MouseEvent a) throws SQLException{
		 
			 EspaceEtudiantDao db = new EspaceEtudiantDao();
			 Vector<lisegroup> v = db.getAllistePfe();
			 //System.out.println();
			 stackpanetudiants.getChildren().add(getTable(v));
	 }
	 

	 public JFXTreeTableView<lisegroup> getTable(Vector<lisegroup> vector){
		
		 JFXTreeTableView<lisegroup> treetableN = new JFXTreeTableView<>();
		 
		 treetableN.setEditable(true );
	        treetableN.getSelectionModel().selectFirst();
	        treeTableSelect = treetableN.getSelectionModel();
	        treeTableSelect.setSelectionMode(SelectionMode.MULTIPLE);
	        treetableN.setShowRoot(false);
	        
	        TreeTableColumn<lisegroup,Integer> nbgp = new TreeTableColumn<>("Nombre De Groupe");
	        TreeTableColumn<lisegroup,String> etudiants = new TreeTableColumn<>("Etudiants");
	        TreeTableColumn<lisegroup,String> encadrant = new TreeTableColumn<>("Encadrant");
	        
	        nbgp.setCellValueFactory(new TreeItemPropertyValueFactory<>("nbgroup"));
	        etudiants.setCellValueFactory(new TreeItemPropertyValueFactory<>("etudiant"));
	        encadrant.setCellValueFactory(new TreeItemPropertyValueFactory<>("encadrant"));
	        
	        // add colum 
	        
	        treetableN.getColumns().setAll(nbgp,etudiants,encadrant);
	        
	        ObservableList<lisegroup> lisegroup = FXCollections.observableArrayList();
	        
	        for(lisegroup l : vector) {
	        	lisegroup.add(l);
	        }
	        
	        final TreeItem<lisegroup> root = new RecursiveTreeItem<lisegroup>(lisegroup, RecursiveTreeObject::getChildren);
	        root.setExpanded(true);
	        treetableN.setRoot(root);
	        treetableN.setPrefWidth(600);
	        this.treeTable = treetableN;
	        return treetableN;
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
}
