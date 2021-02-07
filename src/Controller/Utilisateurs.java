package Controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.UserDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.control.TreeTableView.TreeTableViewSelectionModel;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import traitement.User;
import Model.UserModel;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class Utilisateurs implements Initializable {
	
	//private static final EventHandler MouseEventl ;

	ObservableList<String> maintypelist = FXCollections.observableArrayList("chef","Etudiant");

	private Frame frame = new Frame();
	 private User u = new User();
	@FXML
	private AnchorPane anchpaneAdd;
	@FXML
	private JFXTextField txtuser;
	@FXML
	private JFXTextField txtpwd;
	@FXML
	private JFXTextField txtcode;
//	@FXML
//	private JFXTextField txttype;
	@FXML
	private ComboBox combotype;
	@FXML
	private StackPane stackPane ;
	@FXML
	private HBox hboxtrait = new HBox();
	@FXML
	private AnchorPane paneaffche;
	// user dao 
	@FXML
    private VBox vboxmanager;
	private UserDao userdb ;
	private UserModel usmodel;
	@FXML
	private JFXButton suppBtt ;
	@FXML
	private JFXButton modufyBtt ;
	@FXML
	private JFXButton searchbtt ;
	@FXML
	private JFXTextField txtrech;
	public static int cmp = 0;
	 private JFXTreeTableView<UserModel> treeTable;
	 private  static TreeTableViewSelectionModel<UserModel> select;

	public void inisialse() {
		combotype.setValue("Etudiant");

		combotype.setItems(maintypelist);
	}

	public void useraddClick(ActionEvent a) {

		paneaffche.setVisible(false);
		anchpaneAdd.setVisible(true);
		inisialse();
		cmp=0;
		maintypelist.set(0, "chef");
		maintypelist.set(1, "Etudiant");

	}
	public void addUsr(ActionEvent a) {

		UserModel us = new UserModel();
		us.setUser(txtuser.getText());
		us.setPassword(txtpwd.getText());
		us.setCodeSecret(txtcode.getText());
		us.setType((String) combotype.getValue());
		//stackPane  = new StackPane();
		System.out.println("ok");
         if( txtuser.getText().equals("") || txtpwd.getText().equals("")) {

		         JOptionPane.showMessageDialog(frame,"Veuillez remplir tout les champs","Champs vide",JOptionPane.ERROR_MESSAGE);
	            frame.setLocationRelativeTo(null);
        System.out.println("champs vide");
         }
         if(u.checkuser(txtuser.getText()))
         {
         	JOptionPane.showMessageDialog(frame,"username deja existe","Username erreur",JOptionPane.ERROR_MESSAGE);
 	            frame.setLocationRelativeTo(null);
         }
         if(!txtuser.getText().equals("") && !txtpwd.getText().equals("")){
        	 JOptionPane.showMessageDialog(frame,"vous avez  ajouter avec succes," +
	                    "  "+
	                    " ","ce user est le droit de connecter",JOptionPane.INFORMATION_MESSAGE);
        	 u.addUsers(us);}
    }

	public  void userafficheclick(ActionEvent a) {
		cmp=0;
		u = new User();
		cmp++;

		anchpaneAdd.setVisible(false);
		paneaffche.setVisible(true);
		UserDao dbus = new UserDao();
		Vector<UserModel> vc = dbus.getAllUsers();

		impTable(vc);
		vboxmanager.getChildren().setAll(getTable(vc));

		vboxmanager.getChildren().add(getButtons());

	}

	public JFXTreeTableView<UserModel> getTable(Vector<UserModel> vector){


		JFXTreeTableView<UserModel> treeTableUS = new JFXTreeTableView<UserModel>();
		 treeTableUS.getSelectionModel().selectFirst();
		 select = treeTableUS.getSelectionModel();
		 select.setSelectionMode(SelectionMode.MULTIPLE);
	        treeTableUS.setShowRoot(false);


		JFXTreeTableColumn<UserModel, Integer> id_us = new JFXTreeTableColumn<>("ID Utilisateur");
		JFXTreeTableColumn<UserModel, String> user = new JFXTreeTableColumn<>("Utilisateur");
		JFXTreeTableColumn<UserModel, String> password = new JFXTreeTableColumn<>("Password");
		JFXTreeTableColumn<UserModel, String> codeSecret = new JFXTreeTableColumn<>("Code_secret");
		JFXTreeTableColumn<UserModel, String> type = new JFXTreeTableColumn<>("Type");

		// return champs a modifi�

		user.setOnEditCommit(new EventHandler<CellEditEvent<UserModel,String>>() {

			@Override
			public void handle(CellEditEvent<UserModel, String> event) {
				// TODO Auto-generated method stub
				TreeItem<UserModel> currCon = treeTableUS.getTreeItem(event.getTreeTablePosition().getRow());
				UserModel um = new UserModel();
				um=currCon.getValue();
				um.setUser(event.getNewValue());
				System.out.println(um.getUser()+" new val ");
			}
		});
         password.setOnEditCommit(new EventHandler<CellEditEvent<UserModel,String>>() {

			@Override
			public void handle(CellEditEvent<UserModel, String> event) {
				// TODO Auto-generated method stub
				TreeItem<UserModel> currCon = treeTableUS.getTreeItem(event.getTreeTablePosition().getRow());
				UserModel um = new UserModel();
				um=currCon.getValue();
				um.setPassword(event.getNewValue());
				System.out.println(um.getPassword()+" new val ");
			}
		});

         codeSecret.setOnEditCommit(new EventHandler<CellEditEvent<UserModel,String>>() {

 			@Override
 			public void handle(CellEditEvent<UserModel, String> event) {
 				// TODO Auto-generated method stub
 				TreeItem<UserModel> currCon = treeTableUS.getTreeItem(event.getTreeTablePosition().getRow());
 				UserModel um = new UserModel();
 				um=currCon.getValue();
 				um.setCodeSecret(event.getNewValue());
 				System.out.println(um.getCodeSecret()+" new val ");
 			}
 		});
         type.setOnEditCommit(new EventHandler<CellEditEvent<UserModel,String>>() {

  			@Override
  			public void handle(CellEditEvent<UserModel, String> event) {
  				// TODO Auto-generated method stub
  				TreeItem<UserModel> currCon = treeTableUS.getTreeItem(event.getTreeTablePosition().getRow());
  				UserModel um = new UserModel();
  				um=currCon.getValue();
  				um.setType(event.getNewValue());
  				System.out.println(um.getType()+" new val ");
  			}
  		});




		id_us.setCellValueFactory(new TreeItemPropertyValueFactory<UserModel,Integer>("id_us"));
		user.setCellValueFactory(new TreeItemPropertyValueFactory<UserModel,String>("user"));
	    user.setCellFactory(TextFieldTreeTableCell.<UserModel>forTreeTableColumn());
		password.setCellValueFactory(new TreeItemPropertyValueFactory<UserModel,String>("password"));
		password.setCellFactory(TextFieldTreeTableCell.<UserModel>forTreeTableColumn());
		codeSecret.setCellValueFactory(new TreeItemPropertyValueFactory<UserModel,String>("codeSecret"));
		codeSecret.setCellFactory(TextFieldTreeTableCell.<UserModel>forTreeTableColumn());
		type.setCellValueFactory(new TreeItemPropertyValueFactory<UserModel,String>("type"));
		type.setCellFactory(TextFieldTreeTableCell.<UserModel>forTreeTableColumn());

		/// ajouter coulum au treetable
		if(cmp == 1) {
		treeTableUS.getColumns().addAll(id_us,user,password,codeSecret,type);
		}
		//item
		ObservableList<UserModel> userModel = FXCollections.observableArrayList();



		for(UserModel u : vector) {
			userModel.add(u);
		}


		final TreeItem<UserModel> root = new RecursiveTreeItem<UserModel>(userModel, RecursiveTreeObject::getChildren);
		root.setExpanded(true);

		select.setCellSelectionEnabled(true);

		treeTableUS.setEditable(true);

		treeTableUS.setRoot(root);
		treeTableUS.setPrefSize(650,490);


	      this.treeTable = treeTableUS;

	      return treeTableUS;
	}
	  public void impTable(Vector<UserModel> us) {
		    hboxtrait = this.getButtons();
			if(cmp == 1) {
		    vboxmanager.getChildren().add(getTable(us));
			vboxmanager.getChildren().add(hboxtrait);
			}
	  }

	     private HBox getButtons() {


	    	  Label lbl = new Label("Rechercher: ");

	    	  JFXTextField txtrec = new JFXTextField();
	    	 suppBtt.setButtonType(JFXButton.ButtonType.RAISED);
	    	 modufyBtt.setButtonType(JFXButton.ButtonType.RAISED);
	    	 searchbtt.setButtonType(JFXButton.ButtonType.RAISED);

	    	 suppBtt.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub

					 User us = new User();
			    	 UserModel id = select.getSelectedItem().getValue();
			    	 System.out.println("id "+id.getId_us());
			    	 int iduser = id.getId_us();
			    	 us.deleUser(iduser);
			    	 deleteRow();
				}
			});
	    	 modufyBtt.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					UserModel us = select.getSelectedItem().getValue();
					System.out.println(us.getUser()+" user");
					System.out.println(us.getPassword()+" password");
					System.out.println(us.getCodeSecret()+" codeSecret");
					System.out.println(us.getType());
					User usmet = new User();
                    if(usmet.checkuser(us.getUser())){
						JOptionPane.showMessageDialog(frame,"username deja existe","Username erreur",JOptionPane.ERROR_MESSAGE);
						frame.setLocationRelativeTo(null);
					}

					else
					usmet.ModifierUserAyoub(us, us.getId_us());

					//System.out.println(" modification ");


				}
			});
	    	 searchbtt.setOnAction(new EventHandler<ActionEvent>() {



				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					User us = new User();

					vboxmanager.getChildren().setAll(getTable(us.recherche(txtrech.getText())),getButtons());

				}


			});
	    	 return new HBox(20,suppBtt,modufyBtt,searchbtt,txtrech);
	     }


	     private void deleteRow()  {
	         // Get the selection model
	         TreeTableViewSelectionModel<UserModel> sm = treeTable.getSelectionModel();
	         if (sm.isEmpty())
	         {
               return;
	         }
	         // Get the selected Entry
	         int rowIndex = sm.getSelectedIndex();
	         TreeItem<UserModel> selectedItem = sm.getModelItem(rowIndex);
	         TreeItem<UserModel> parent = selectedItem.getParent();
	         if (parent != null)
	         {
	             // Remove the Item
	             parent.getChildren().remove(selectedItem);
	         }
	         else
	         {    // Must be deleting the Root Item
	        	 treeTable.setRoot(null);
	         }

	     }
	 /*    private void addRow()
	     {
	         if (treeTable.getExpandedItemCount() == 0 )
	         {
	             // There is no row in the TreeTableView
	             addNewRootItem();
	         }
	         else if (treeTable.getSelectionModel().isEmpty())
	         {

	             return;
	         }
	         else
	         {
	             // Add Child
	             addNewChildItem();
	         }
	     }
	     private void addNewRootItem(){

	    	 TreeItem<UserModel> item = new TreeItem<>(new UserModel());
	    	 treeTable.setRoot(item);

	    	 // Edit the item
	         this.editItem(item);
	         System.out.println("addnew row");

	     }

	     private void addNewChildItem()
	     {
	         // Prepare a new TreeItem with a new Person object

	         TreeItem<UserModel> item = new TreeItem<>(new UserModel());

	         // Get the selection model
	         TreeTableViewSelectionModel<UserModel> sm = treeTable.getSelectionModel();

	         // Get the selected row index
	         int rowIndex = sm.getSelectedIndex();

	         // Get the selected TreeItem
	         TreeItem<UserModel> selectedItem = sm.getModelItem(rowIndex);

	         // Add the new item as children to the selected item
	         selectedItem.getChildren().add(item);

	         // Make sure the new item is visible
	         selectedItem.setExpanded(true);

	         // Edit the item
	         this.editItem(item);
	     }
	     
	     
	     private void editItem(TreeItem<UserModel> item) {
	    	 
	    	// Scroll to the new item
	         int newRowIndex = treeTable.getRow(item);
	         treeTable.scrollTo(newRowIndex);
	         
	         TreeTableColumn<UserModel, ?> firstCol = treeTable.getColumns().get(0);
	         
	         treeTable.getSelectionModel().select(item);

	         treeTable.getFocusModel().focus(newRowIndex, firstCol);

	         treeTable.edit(newRowIndex, firstCol);
	     } */


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		txtuser.setText("");
		txtpwd.setText("");
		txtcode.setText("");

	}
}
