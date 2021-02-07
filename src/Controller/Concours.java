package Controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.ConcoursDao;
import dao.DocumentDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.DateStringConverter;
import Model.ConcoursModel;
import traitement.ConcoursTraitement;
import Model.DocumentModel;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Vector;

public class Concours implements Initializable {

    @FXML
    private JFXButton addConocurs;
    @FXML
    private AnchorPane AnchPane;
    @FXML
    private JFXTextField txtTitre;
    @FXML
    private JFXDatePicker dateDebut;
    @FXML
    private JFXDatePicker dateFin;
    @FXML
    private JFXTextField alias;
    @FXML
    private JFXTextField lieu;
    @FXML
    private JFXButton btnValider;
    @FXML
    private AnchorPane anchorPaneTable;
    @FXML
    private HBox hboxManager;
    @FXML
    private VBox vboxManager;
    @FXML
    private JFXButton testbtn;
    @FXML
    private StackPane stackPaneModifier;

    private DocumentModel documentModel = new DocumentModel();
    private DocumentDao documentDao = new DocumentDao();
    private  ConcoursModel concoursModel = new ConcoursModel();
    private ConcoursTraitement ct;
    public static int cmp = 0;
    private AccueilA accueilA;
    private ConcoursDao conDao;
    private   JFXTreeTableView<ConcoursModel> treetable ;
    private  static TreeTableView.TreeTableViewSelectionModel<ConcoursModel> treeTableSelect;
    private TextArea textArea = new TextArea();
    private static JFXTextField txtsearch;
    private static Label label;
    private Frame frame = new Frame();


    //Afficher le panel por ajouter un concours
    public void addConcours(ActionEvent actionEvent) {
        AnchPane.setVisible(true);
        anchorPaneTable.setVisible(false);
        acctualiser();

    }

    //Ajouter un nouveau Concours
    public void validerClick(ActionEvent actionEvent) {
        ct = new ConcoursTraitement();
        accueilA = new AccueilA();
        String titre = txtTitre.getText();
        Date dDebut = Date.valueOf(dateDebut.getValue());
        Date dFin = Date.valueOf(dateFin.getValue());
        String aliass = alias.getText();
        String chef = accueilA.userA;
        String Lieu = lieu.getText();

        if (txtTitre.getText().equals("") || alias.getText().equals("") || lieu.getText().equals("")) {
            System.out.println("Champs vide");
            JOptionPane.showMessageDialog(frame,"Veuillez remplir tous les champs","Champs vides !",JOptionPane.ERROR_MESSAGE);
        } else {
            try {

                System.out.println("champs plein " + txtTitre.getText() + " " + dateDebut.getValue() + " " + dateFin.getValue() + " " + alias.getText());
                concoursModel.setTitreConcours(titre);
                concoursModel.setDateDebut(dDebut);
                concoursModel.setDateFin(dFin);
                concoursModel.setAlias(aliass);
                concoursModel.setNomChef(chef);
                concoursModel.setLieu(Lieu);

                ct.addConcours(concoursModel);

                documentModel.setId_concours(ConcoursDao.getLastConcours());
                documentModel.setValeur(txtTitre.getText());
                documentModel.setStatut(0);
                documentModel.setChef(AccueilA.userA);

                documentDao.addDocument(documentModel);
                acctualiser();
            }catch (Exception e){
                JOptionPane.showMessageDialog(frame,"Veuillez Verfiier les dates","Verifier dates !",JOptionPane.ERROR_MESSAGE);

            }
        }

    }

    public void acctualiser() {

        txtTitre.setText("");
        alias.setText("");
        dateFin.setValue(LocalDate.now());
        dateDebut.setValue(LocalDate.now());
        lieu.setText("" +
                "");
    }
    //afficher les concours et les boutons pour la géstion
    public void ManagerClick(ActionEvent actionEvent) {
        cmp = 0;
        ChefDepartementWelcome ch = new ChefDepartementWelcome();
        cmp++;

        AnchPane.setVisible(false);
        anchorPaneTable.setVisible(true);

        //get value from Vector
        ConcoursDao conDao0 = new ConcoursDao();
        Vector<ConcoursModel> vect = conDao0.getAllConcours();
        System.out.println(vect.size());

        implTable(vect);
    }


    // Remplir la table (treetableview ) par vector des concours
    public JFXTreeTableView<ConcoursModel> getTable(Vector<ConcoursModel> vector){

        JFXTreeTableView<ConcoursModel> treetableN = new JFXTreeTableView<>();

        treetableN.setEditable(true );
        treetableN.getSelectionModel().selectFirst();
        treeTableSelect = treetableN.getSelectionModel();
        treeTableSelect.setSelectionMode(SelectionMode.MULTIPLE);
        treetableN.setShowRoot(false);

        TreeTableColumn<ConcoursModel, Integer> id_concours = new TreeTableColumn<>("ID Concours");
        TreeTableColumn<ConcoursModel, String> titre_con = new TreeTableColumn<>("Titre");
        TreeTableColumn<ConcoursModel, java.util.Date> dateDebu =null;
        TreeTableColumn<ConcoursModel, java.util.Date> dateFin=null;
        try {
           dateDebu = new TreeTableColumn<>("Date de debut");
            dateFin= new TreeTableColumn<>("Date Final");
       }catch (InputMismatchException e){System.out.println("first error");}
        TreeTableColumn<ConcoursModel, String> alias = new TreeTableColumn<>("alias de concours");
        TreeTableColumn<ConcoursModel, String> lieu = new TreeTableColumn<>("Lieu");
        TreeTableColumn<ConcoursModel, String> nom_chef = new TreeTableColumn<>("Nom de Chef");

        titre_con.setOnEditCommit(event -> {
            TreeItem<ConcoursModel> currCon = treetable.getTreeItem(event.getTreeTablePosition().getRow());
            ConcoursModel cm = currCon.getValue();
            cm.setTitreConcours(event.getNewValue());
            System.out.println(cm.getTitreConcours()+" new value");

        });
        alias.setOnEditCommit(event -> {
            TreeItem<ConcoursModel> currCon = treetable.getTreeItem(event.getTreeTablePosition().getRow());
            ConcoursModel cm = currCon.getValue();
            cm.setAlias(event.getNewValue());
            System.out.println(cm.getAlias()+" new value");
        });
        lieu.setOnEditCommit(event -> {

                TreeItem<ConcoursModel> currCon = treetable.getTreeItem(event.getTreeTablePosition().getRow());
                ConcoursModel cm = currCon.getValue();
                cm.setLieu(event.getNewValue());
                System.out.println(cm.getLieu()+" new value");


        });
        dateDebu.setOnEditCommit(event -> {
            try {    TreeItem<ConcoursModel> currCon = treetable.getTreeItem(event.getTreeTablePosition().getRow());
                ConcoursModel cm = currCon.getValue();
                Date date = new Date(event.getNewValue().getTime());
                cm.setDateDebut(date);
                System.out.println(cm.getDateDebut()+" new value");
            }catch (InputMismatchException e){
                System.out.println("Date error");
                JOptionPane.showMessageDialog(frame,"Date error","Date error",JOptionPane.ERROR_MESSAGE);
            }
        });
        dateFin.setOnEditCommit(event -> {
            TreeItem<ConcoursModel> currCon = treetable.getTreeItem(event.getTreeTablePosition().getRow());
            ConcoursModel cm = currCon.getValue();
            Date date = new Date(event.getNewValue().getTime());
            cm.setDateFin(date);
            System.out.println(cm.getDateFin()+" new value");
        });

        id_concours.setCellValueFactory(new TreeItemPropertyValueFactory<>("id_concours"));
        titre_con.setCellValueFactory(new TreeItemPropertyValueFactory<>("titreConcours"));
        titre_con.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
       try {
           dateDebu.setCellValueFactory(new TreeItemPropertyValueFactory<>("dateDebut"));
           DateStringConverter converter = new DateStringConverter();
           dateDebu.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(converter));
           dateFin.setCellValueFactory(new TreeItemPropertyValueFactory<>("dateFin"));
           dateFin.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(converter));

       }catch (InputMismatchException e){System.out.println("Error CellValue");}
        alias.setCellValueFactory(new TreeItemPropertyValueFactory<>("alias"));
        alias.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        lieu.setCellValueFactory(new TreeItemPropertyValueFactory<>("lieu"));
        lieu.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        nom_chef.setCellValueFactory(new TreeItemPropertyValueFactory<>("nomChef"));
        if (cmp == 1){
            //add columns to treetable
            treetableN.getColumns().addAll(id_concours, titre_con, dateDebu, dateFin, alias,lieu, nom_chef);
        }

        ObservableList<ConcoursModel> concoursModels = FXCollections.observableArrayList();

        for (ConcoursModel c : vector) {
            concoursModels.add(c);
        }

        final TreeItem<ConcoursModel> root = new RecursiveTreeItem<ConcoursModel>(concoursModels, RecursiveTreeObject::getChildren);
        root.setExpanded(true);
        treetableN.setRoot(root);
        treetableN.setPrefWidth(678);

        this.treetable = treetableN;

        return treetableN;
    }

    //pour afficher la table dans le vbox
    public void implTable(Vector<ConcoursModel> cm){
         hboxManager = this.getButtons();
         label = new Label("Veuillez selectionner une ligne pour Supprimer ou modifier");

        if (cmp == 1) {
            vboxManager.getChildren().add(label);
            vboxManager.getChildren().add(getTable(cm));
            vboxManager.getChildren().add(hboxManager);
        }
    }

    // pour ajouter les buttons dans le hbox
    private HBox getButtons()
    {
        // Create the Buttons
        JFXButton addButton = new JFXButton("Add");
        JFXButton ModifierButton = new JFXButton("Modifier");
        JFXButton deleteButton = new JFXButton("Delete");
        JFXButton RechercherButton = new JFXButton("Rechercher");

        Label lbl = new Label("Rechercher: ");
        txtsearch = new JFXTextField();
        txtsearch.setStyle(" -fx-background-color: transparent, white, transparent, white;\n" +
                "    -fx-background-radius: 10, 10, 10, 10;\n" +
                "    -fx-padding: 0.166667em;");
        deleteButton.setStyle("-fx-background-color: linear-gradient(#98a8bd 0%, #8195af 25%, #6d86a4 100%)");
        RechercherButton.setStyle("-fx-background-color: linear-gradient(#98a8bd 0%, #8195af 25%, #6d86a4 100%)");
        ModifierButton.setStyle("-fx-background-color: linear-gradient(#98a8bd 0%, #8195af 25%, #6d86a4 100%)");

        // Create EventHandler for the Buttons
        addButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                addRow();
            }

        });

        deleteButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                deleteRow();
                ConcoursTraitement ct = new ConcoursTraitement();
                ConcoursModel concoursModel = treeTableSelect.getSelectedItem().getValue();
                ct.delConcours(concoursModel.getId_concours());
            }
        });

        RechercherButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ConcoursTraitement ct = new ConcoursTraitement();
                vboxManager.getChildren().setAll(label,getTable(ct.getConcoursKey(txtsearch.getText())),getButtons());
            }
        });
        ModifierButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ConcoursModel cppp = treeTableSelect.getSelectedItem().getValue();
                System.out.println(cppp.getTitreConcours()+"titre");
                ConcoursTraitement ctt = new ConcoursTraitement();
                ctt.modifierConcours(cppp.getId_concours(),cppp);
                System.out.println("updating");
            }
        });

        // Create and return the HBox
        return new HBox(10, deleteButton,ModifierButton,RechercherButton,txtsearch);
    }

/*    public void edit(TableColumn.CellEditEvent<ConcoursModel,?> event){

        Object newValue = event.getNewValue();
        // other data that might be helpful:
        TablePosition<?,?> position = event.getTablePosition();
        int row = position.getRow();
        System.out.println(row+" "+newValue);
    }
*/

    //ajouter une ligne dans la table
    private void addRow()
    {
        if (treetable.getExpandedItemCount() == 0 )
        {
            // There is no row in the TreeTableView
            addNewRootItem();
        }
        else if (treetable.getSelectionModel().isEmpty())
        {
            logging("Select a row to add.");
            return;
        }
        else
        {
            // Add Child
            addNewChildItem();
        }
    }


    private void addNewRootItem()
    {
        // Add a root Item
        TreeItem<ConcoursModel> item = new TreeItem<>(new ConcoursModel(1,"new",null,null,"new",""));
        treetable.setRoot(item);

        // Edit the item
        this.editItem(item);
        System.out.println("addnew row");
    }

    //
    private void addNewChildItem()
    {
        // Prepare a new TreeItem with a new Person object

        TreeItem<ConcoursModel> item = new TreeItem<>(new ConcoursModel(8,"titre",null,null,"",""));

        // Get the selection model
        TreeTableView.TreeTableViewSelectionModel<ConcoursModel> sm = treetable.getSelectionModel();

        // Get the selected row index
        int rowIndex = sm.getSelectedIndex();

        // Get the selected TreeItem
        TreeItem<ConcoursModel> selectedItem = sm.getModelItem(rowIndex);

        // Add the new item as children to the selected item
        selectedItem.getChildren().add(item);

        // Make sure the new item is visible
        selectedItem.setExpanded(true);

        // Edit the item
        this.editItem(item);
    }

    private void editItem(TreeItem<ConcoursModel> item)
    {
        // Scroll to the new item
        int newRowIndex = treetable.getRow(item);

        treetable.scrollTo(newRowIndex);

        // Put the first column in editing mode

        TreeTableColumn<ConcoursModel, ?> firstCol = treetable.getColumns().get(0);

        treetable.getSelectionModel().select(item);

        treetable.getFocusModel().focus(newRowIndex, firstCol);

        treetable.edit(newRowIndex, firstCol);

    }



    private void deleteRow()

    {

        // Get the selection model

        TreeTableView.TreeTableViewSelectionModel<ConcoursModel> sm = treetable.getSelectionModel();

        if (sm.isEmpty())

        {

            logging("Select a row to delete.");

            return;
        }



        // Get the selected Entry

        int rowIndex = sm.getSelectedIndex();

        TreeItem<ConcoursModel> selectedItem = sm.getModelItem(rowIndex);

        TreeItem<ConcoursModel> parent = selectedItem.getParent();



        if (parent != null)

        {

            // Remove the Item

            parent.getChildren().remove(selectedItem);

        }

        else

        {

            // Must be deleting the Root Item
            treetable.setRoot(null);

        }

    }



    private void logging(String message)

    {

        this.textArea.appendText(message + "\n");

    }

    public void testClick(ActionEvent actionEvent) {

    ConcoursModel concoursModel = treeTableSelect.getSelectedItem().getValue();
    System.out.println(concoursModel.getId_concours());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dateDebut.setValue(LocalDate.now());
        dateFin.setValue(LocalDate.now());

    }
}



