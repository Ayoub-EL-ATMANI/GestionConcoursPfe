package Controller;

import Model.*;
import Model.groupe;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.DaoEncadrant;
import dao.DaoGroupePfe;
import dao.DaoGroupePfeManuel;
import dao.etudiantDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;

public class GroupePfeManuellement implements Initializable{

	@FXML
	private HBox hbox;
	
	@FXML
	private VBox vbox1;

	@FXML
	private VBox vbox2;
	
	@FXML
	private VBox vbox3;	
	@FXML
	private JFXTreeTableView<jfxEtudiant> ListeEtudiant;

	@FXML
	private JFXTreeTableView<jfxEncadrant> ListeEncadrant;
	
	@FXML
	private JFXTreeTableView<jfxGroupe> ListeGroupe;
	
	@FXML
	private JFXButton addGroupeID;
	
	@FXML
	private JFXButton pdf;
	
	private int nbrGroupe;
//	public static int nbrGroupe;
	public static int test=0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
	    	DaoGroupePfe daoG=new DaoGroupePfe();
	    	Vector<groupe> vG=daoG.ListeGroupeMannuel();
	    	refresh();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}	
	
	//Ajouter un nouveau groupe
	public void addGroupeFunction(ActionEvent event) throws SQLException {				
		DaoGroupePfeManuel dao=new DaoGroupePfeManuel();
		nbrGroupe=dao.insertG();
		System.out.println(nbrGroupe);
		test=1;
		refresh();
	}
	
	

/**
 * @throws SQLException **************************************************************/	
	
	public void refresh() throws SQLException {
		hbox.setSpacing(9);		
		loadListeEtudiant();
		loadListeEncadrant();
		loadListeGroupe();
	}

	//Rempir le jfxtreetableview des groupes
    public void loadListeGroupe() throws SQLException {
    	    		
			//declaration column     		
	        TreeTableColumn<jfxGroupe,Integer> nbgp = new TreeTableColumn<>("Nombre de GroupeP");	        	
	        TreeTableColumn<jfxGroupe,String> etudiants = new TreeTableColumn<>("Etudiants");
	        TreeTableColumn<jfxGroupe,String> encadrant = new TreeTableColumn<>("Encadrant");
	        TreeTableColumn<jfxGroupe,JFXButton> action = new TreeTableColumn<>("Action");
	        //associer au champs de model
	        nbgp.setCellValueFactory(new TreeItemPropertyValueFactory<>("numG"));
	        etudiants.setCellValueFactory(new TreeItemPropertyValueFactory<>("etudiants"));
	        encadrant.setCellValueFactory(new TreeItemPropertyValueFactory<>("encadrant"));
	        action.setCellValueFactory(new TreeItemPropertyValueFactory<>("action"));	        
	        nbgp.setPrefWidth(141);
	        etudiants.setPrefWidth(141);
	        encadrant.setPrefWidth(141);
	        action.setPrefWidth(141);
	        ListeGroupe.getColumns().setAll(nbgp,etudiants,encadrant,action);
	        
	        //remplir
	        ObservableList<jfxGroupe> listegroup = FXCollections.observableArrayList();
	    	DaoGroupePfe daoG=new DaoGroupePfe();
	    	Vector<groupe> vG=daoG.ListeGroupeMannuel();
	    	for (Model.groupe groupe : vG) {
	        	jfxGroupe GroupeP=new jfxGroupe();
	        	JFXButton btn=new JFXButton("Delete"); btn.setStyle("-fx-background-color:#8bb2ba");btn.setId(groupe.getNumG()+"");
		        	btn.setOnAction(new EventHandler<ActionEvent>() {					
						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
							System.out.println(btn.getId());
							DaoGroupePfeManuel dao=new DaoGroupePfeManuel();
							try {
								dao.deleteGroupe(Integer.parseInt(btn.getId()));
								refresh();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	
						}
					});
		        	GroupeP.setNumG(groupe.getNumG()+"");		        	
		        	GroupeP.setEtudiants(groupe.getEtudiants());
		        	GroupeP.setEncadrant(groupe.getEncadrant());
		        	GroupeP.setAction(btn);
	        	listegroup.add(GroupeP);				
			}
	    	if(test==1) {
	    	jfxGroupe jfx=new jfxGroupe();jfx.setNumG(nbrGroupe+"");
	    	listegroup.add(jfx);
	    	test=0;
	    	}
		        
	        final TreeItem<jfxGroupe> root = new RecursiveTreeItem<jfxGroupe>(listegroup, RecursiveTreeObject::getChildren);
	        root.setExpanded(true);
	        ListeGroupe.setRoot(root);
	        ListeGroupe.setPrefWidth(600);
	        ListeGroupe.setEditable(true );
	        ListeGroupe.getSelectionModel().selectFirst();
	        ListeGroupe.setShowRoot(false);    	
    }	
	
	/*-----------------------------------------------------------------*/


	//Rempir le jfxtreetableview des étudiant
    public void loadListeEtudiant() {
    	etudiantDao daoE=new etudiantDao();
    	Vector<etudiants> ve=daoE.getData();
    	//declaration column
        TreeTableColumn<jfxEtudiant,String> etudiant = new TreeTableColumn<>("Etudiant");
        TreeTableColumn<jfxEtudiant,Label> status = new TreeTableColumn<>("Status");
        TreeTableColumn<jfxEtudiant,JFXButton> action = new TreeTableColumn<>("Action");        
        
        //associer au champs de model
        etudiant.setCellValueFactory(new TreeItemPropertyValueFactory<>("etudiants"));
        status.setCellValueFactory(new TreeItemPropertyValueFactory<>("status"));
        action.setCellValueFactory(new TreeItemPropertyValueFactory<>("action"));        
        ListeEtudiant.getColumns().setAll(etudiant,status,action);
        
        //remplir
        ObservableList<jfxEtudiant> etudiants = FXCollections.observableArrayList();

        for (etudiants etud : ve) {        	
        	JFXButton btn=new JFXButton("+"); btn.setStyle("-fx-background-color:#8bb2ba");btn.setId(etud.getId()+"");
	        	btn.setOnAction(new EventHandler<ActionEvent>() {					
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						System.out.println(btn.getId());
						DaoGroupePfeManuel dao=new DaoGroupePfeManuel();
						dao.addEtudiant(Integer.parseInt(btn.getId()), nbrGroupe);
						try {
					    	DaoGroupePfe daoG=new DaoGroupePfe();
					    	Vector<groupe> vG=daoG.ListeGroupeMannuel();
//					    	loadListeGroupe(DaoGroupePfe.listeVide());
							refresh();							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	        		        	    		
	        	if (etud.getStatus() == 1) {
	        		Label statusP=new Label("  No  ");	        		
	        		statusP.setStyle("-fx-background-color:#ff6060");
	        		statusP.setTextFill(Paint.valueOf("#ffffff"));
	        		etudiants.add(new jfxEtudiant(etud.getNom()+"  "+etud.getPrenom(),btn,statusP));
				}else {
					Label statusN=new Label("");					
					etudiants.add(new jfxEtudiant(etud.getNom()+"  "+etud.getPrenom(),btn,statusN));
				}
		}
        
        final TreeItem<jfxEtudiant> root = new RecursiveTreeItem<jfxEtudiant>(etudiants, RecursiveTreeObject::getChildren);
        root.setExpanded(true);	        
        ListeEtudiant.setRoot(root);
        ListeEtudiant.setPrefWidth(600);
        ListeEtudiant.setEditable(true );
        ListeEtudiant.getSelectionModel().selectFirst();
        ListeEtudiant.setShowRoot(false);
    	
    }    
	
	
    /*-----------------------------------------------------------------*/
	//Rempir le jfxtreetableview des encadrant
    public void loadListeEncadrant() {
    	DaoEncadrant daoE=new DaoEncadrant();
    	Vector<encadrants> ve=daoE.getData();

		//declaration column 
        TreeTableColumn<jfxEncadrant,String> encadrant = new TreeTableColumn<>("Encadrant");
        TreeTableColumn<jfxEncadrant,JFXButton> action = new TreeTableColumn<>("Action");
        
        //associer au champs de model
        encadrant.setCellValueFactory(new TreeItemPropertyValueFactory<>("encadrant"));
        action.setCellValueFactory(new TreeItemPropertyValueFactory<>("action"));	                
        ListeEncadrant.getColumns().setAll(encadrant,action);
        
        //remplir
        ObservableList<jfxEncadrant> listeEncadrant = FXCollections.observableArrayList();
    	for (encadrants enc : ve) {			        
        	JFXButton btn=new JFXButton("+"); btn.setStyle("-fx-background-color:#8bb2ba");btn.setId(enc.getId()+"");
	        	btn.setOnAction(new EventHandler<ActionEvent>() {					
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						System.out.println(btn.getId());
						DaoGroupePfeManuel dao=new DaoGroupePfeManuel();
						dao.addEncadrant(Integer.parseInt(btn.getId()), nbrGroupe);	
						try {
					    	DaoGroupePfe daoG=new DaoGroupePfe();
					    	Vector<groupe> vG=daoG.ListeGroupeMannuel();
//					    	loadListeGroupe(DaoGroupePfe.listeVide());
							loadListeGroupe();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	        	listeEncadrant.add(new jfxEncadrant(enc.getNom()+" "+enc.getPrenom(), btn));
		}

        
        final TreeItem<jfxEncadrant> root = new RecursiveTreeItem<jfxEncadrant>(listeEncadrant, RecursiveTreeObject::getChildren);
        root.setExpanded(true);	        
        ListeEncadrant.setRoot(root);
        ListeEncadrant.setPrefWidth(600);
        ListeEncadrant.setEditable(true );
        ListeEncadrant.getSelectionModel().selectFirst();
        ListeEncadrant.setShowRoot(false);    	
    }
    
    
    
    public void generatePdf(ActionEvent event) {       
    		
    		DaoGroupePfe daoG=new DaoGroupePfe();
    		Vector<groupe> vG=new Vector<>();
    		String path=new String();
			try {
				vG = daoG.ListeGroupeMannuel();
			} catch (SQLException e1) {e1.printStackTrace();}
    	                        
              try {
            	  File f = new File("");
            	  f.mkdir();
            	  System.out.println(f.isDirectory());
                  path = f.getAbsolutePath()+"\\outputPDF\\ListePfe.pdf";
                  Document document = new Document();
                  PdfWriter.getInstance(document,new FileOutputStream(path));
                  document.open();
                  Paragraph paragraph = new Paragraph("Liste des etudiants admis \n\n");
                  document.add(paragraph);

                  PdfPTable table = new PdfPTable(3);
                  PdfPCell numG = new PdfPCell(new Paragraph("NumG"));
                  PdfPCell etudiants = new PdfPCell(new Paragraph("etudiants"));
                  PdfPCell encadrant = new PdfPCell(new Paragraph("encadrant"));

                  table.addCell(numG);
                  table.addCell(etudiants);
                  table.addCell(encadrant);
                  
                  for (groupe c:vG) {
                      System.out.println(c);
                      PdfPCell NumG = new PdfPCell(new Paragraph(c.getNumG()+""));
                      PdfPCell etud = new PdfPCell(new Paragraph(c.getEtudiants()+""));
                      PdfPCell enc = new PdfPCell(new Paragraph(c.getEncadrant()+""));

                      table.addCell(NumG);
                      table.addCell(etud);
                      table.addCell(enc);

                  }

                  document.add(table);
                  document.close();



              } catch (DocumentException e) {
                  e.printStackTrace();
              } catch (FileNotFoundException e) {
                  e.printStackTrace();
              }

              if(Desktop.isDesktopSupported()){
                  try {
                      File file = new File(path);
                      Desktop.getDesktop().open(file);
                  }catch (Exception e){e.printStackTrace();}
              }
    }
}