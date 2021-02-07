package Controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.etudiantDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView.TreeTableViewSelectionModel;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.VBox;
import Model.ValiderInscription;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;


public class GeneratedCode implements Initializable{

	 private JFXTreeTableView<ValiderInscription> treeTable;
	 private  static TreeTableViewSelectionModel<ValiderInscription> treeTableSelect;
	
	@FXML
	private JFXTextField txtnb;
	@FXML
	private VBox vboxmanager;
	
	@FXML
	private JFXTreeTableView<ValiderInscription> treeViewCode;
	private etudiantDao daoE ;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			loadListeGode();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}		
	
	
	
	
	public void generateclick(ActionEvent a) {
		
		try {
			daoE = new etudiantDao();
			daoE.InsertCode(Integer.parseInt(txtnb.getText()));
			loadListeGode();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	

    public void loadListeGode() throws SQLException {
		
    		etudiantDao dao=new etudiantDao();
    		Vector<ValiderInscription> data=dao.getCode();
			//declaration column 
	        TreeTableColumn<ValiderInscription,String> code = new TreeTableColumn<>("Code Inscription");
	        TreeTableColumn<ValiderInscription,String> departement = new TreeTableColumn<>("Departement");
	        TreeTableColumn<ValiderInscription,String> filiere = new TreeTableColumn<>("Filiere");

	        //associer au champs de model
	        code.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
	        departement.setCellValueFactory(new TreeItemPropertyValueFactory<>("departement"));
	        filiere.setCellValueFactory(new TreeItemPropertyValueFactory<>("filiere"));	                
	        treeViewCode.getColumns().setAll(code,departement,filiere);
	        
	        //remplir
	        ObservableList<ValiderInscription> listeCode= FXCollections.observableArrayList();
	        for (ValiderInscription validerInscription : data) {
				listeCode.add(validerInscription);
			}
		        
	        final TreeItem<ValiderInscription> root = new RecursiveTreeItem<ValiderInscription>(listeCode, RecursiveTreeObject::getChildren);
	        root.setExpanded(true);
	        treeViewCode.setRoot(root);
	        treeViewCode.setPrefWidth(600);
	        treeViewCode.setEditable(true );
	        treeViewCode.getSelectionModel().selectFirst();
	        treeViewCode.setShowRoot(false);    	
    }




	
	
}
