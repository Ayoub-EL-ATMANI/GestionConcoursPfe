package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.DaoGroupePfe;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import Model.encadrants;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;

/****************************************************************/

class groupe extends RecursiveTreeObject<groupe> {

    StringProperty numG;
    StringProperty etudiants;
    StringProperty encadrant;

    public groupe(String numG, String etudiants,String encadrant){
        this.encadrant = new SimpleStringProperty(encadrant);
        this.numG = new SimpleStringProperty(numG);
        this.etudiants = new SimpleStringProperty(etudiants);
    }
    
}

/****************************************************************/

public class GroupePfe implements Initializable {

    @FXML
    private FlowPane main;
    @FXML
    private JFXTreeTableView<groupe> treeView;        
        
    @FXML
    private ChoiceBox select=new ChoiceBox();    


    @FXML
    private JFXButton manuelID;
    
    @FXML
    private JFXButton CodeCompteID;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	DaoGroupePfe daoG=new DaoGroupePfe();
		encadrants enc=AccueilA.CurrentEncadrant;
	    select.setItems(FXCollections.observableArrayList(	    		
	            "nombre Etudiant/Groupe", 
	            new Separator(), 2, 3)
	        );		
	    select.getSelectionModel().select(0);	    
	    // selectionner un nombre
	    select.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				daoG.affecter(newValue.intValue());				
				loadtreeTableView();
			}
		});//end selectionner un nombre
	    	  
	    
//tree table view par default
	        loadtreeTableView();	    
	    
    }//EndInitialize
    
    /****************************************************************/

    @FXML
    private void filter(ActionEvent event) {
    }


    public void loadtreeTableView() {
    	DaoGroupePfe daoG=new DaoGroupePfe();
        JFXTreeTableColumn<groupe, String> numGCOL = new JFXTreeTableColumn<>("Num Groupe");
        numGCOL.setPrefWidth(235);
        numGCOL.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<groupe, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<groupe, String> param) {
                return param.getValue().getValue().numG;
            }
        });        
        
        JFXTreeTableColumn<groupe, String> etudiantsCol = new JFXTreeTableColumn<>("etudiants");
        etudiantsCol.setPrefWidth(235);
        etudiantsCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<groupe, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<groupe, String> param) {
                return param.getValue().getValue().etudiants;
            }
        });

    	//define column    	    	
        JFXTreeTableColumn<groupe, String> encadrantCOL = new JFXTreeTableColumn<>("encadrant");
        encadrantCOL.setPrefWidth(235);
        encadrantCOL.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<groupe, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<groupe, String> param) {
                return param.getValue().getValue().encadrant;
            }
        });
        
        //associate the tree column with the tree table view 
        
        ObservableList<groupe> groupes = FXCollections.observableArrayList();
        
        
        try {
			Vector<Model.groupe> vG=daoG.ListeGroupe();
			for (Model.groupe groupe : vG) {
				groupes.add(new groupe(groupe.getNumG()+"", groupe.getEtudiants(), groupe.getEncadrant()));
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //code for create a tree item
        //RecursiveTreeObject::getChildren  for getting children simply 
        final TreeItem<groupe> root = new RecursiveTreeItem<groupe>(groupes, RecursiveTreeObject::getChildren);        
        treeView.getColumns().setAll(numGCOL, etudiantsCol, encadrantCOL);
        treeView.setRoot(root);
        treeView.setShowRoot(false);    	
    }


    //redirection vers la création manuelle
    @FXML
    public void manuelFunction(ActionEvent event) throws IOException {
    	FXMLLoader loader=new FXMLLoader(getClass().getResource("/vue/ChefDepartementWelcome.fxml"));
    	Parent root=loader.load();
    	ChefDepartementWelcome chefD=loader.getController();
    	chefD.LoadUI("/vue/GroupePfeManuellement.fxml");
    	Stage stage=new Stage();
    	stage.setScene(new Scene(root));
    	stage.setMaximized(true);
    	stage.setResizable(false);
    	stage.show();    	
    	final Node source=(Node)event.getSource();
    	final Stage stage1=(Stage) source.getScene().getWindow();
    	stage1.close();    	
    }
        
    //redirection vers la liste des code de compte des étudiants
    public void CodeCompteFunction(ActionEvent event) throws IOException {
    	FXMLLoader loader=new FXMLLoader(getClass().getResource("/vue/ChefDepartementWelcome.fxml"));
    	Parent root=loader.load();
    	ChefDepartementWelcome chefD=loader.getController();
    	chefD.LoadUI("/vue/GeneratedCode.fxml");
    	Stage stage=new Stage();
    	stage.setScene(new Scene(root));
    	stage.setMaximized(true);
    	stage.setResizable(false);
    	stage.show();    	
    	final Node source=(Node)event.getSource();
    	final Stage stage1=(Stage) source.getScene().getWindow();
    	stage1.close();    	    	
    }
}
