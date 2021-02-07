package Controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import dao.DocumentDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import Model.CandidatT;
import Model.ConcoursModel;
import traitement.ConcoursTraitement;
import Model.DocumentModel;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class Documents implements Initializable {

    @FXML
    private JFXToggleButton tglConcours;
    @FXML
    private JFXToggleButton tglPfe;
    @FXML
    private JFXTextField nbrCandidat;
    @FXML
    private ComboBox cmbConcours = new ComboBox();


    private CandidatT ct = new CandidatT();
    private ConcoursTraitement cont = new ConcoursTraitement();
    private DocumentDao documentDao = new DocumentDao();
    public static boolean isDisplayResults = false;
    public static int nmbrCandidat;
    private Frame frame = new Frame();
    private static  DocumentModel documentModel = new DocumentModel();


    //Pour activer le résultat de concours et de pfe pour qu'il soit visible au étudiant et au candidat
    public void concoursClick(ActionEvent event) {

        System.out.println(tglConcours.isSelected());
        isDisplayResults = tglConcours.isSelected();

        try {
            nmbrCandidat = Integer.parseInt(nbrCandidat.getText());
            if(isDisplayResults){
                documentModel.setStatut(1);
                documentModel.setNbrCandidat(nmbrCandidat);
                ConcoursModel cm1 = cont.getConcoursByAlias(cmbConcours.getValue().toString());
                documentModel.setId_concours(cm1.getId_concours());
                System.out.println(cmbConcours.getValue()+" "+cm1.getId_concours());
                documentDao.updateDocument(documentModel,documentModel.getId_concours());
            }

        }catch (Exception e){
            JOptionPane.showMessageDialog(frame,"Veuillez entrer un nombre","Verifier nombre des candidats",JOptionPane.ERROR_MESSAGE);
        }
        if (isDisplayResults==false){
            documentModel.setStatut(0);
            documentModel.setNbrCandidat(0);
            ConcoursModel cm1 = cont.getConcoursByAlias(cmbConcours.getValue().toString());
            documentModel.setId_concours(cm1.getId_concours());
            System.out.println(cmbConcours.getValue()+" "+cm1.getId_concours());
            documentDao.updateDocument(documentModel,documentModel.getId_concours());
        }
    }


    //
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Vector<ConcoursModel> vector = cont.getConcoursKey(AccueilA.userA);
        Vector<String> titres = new Vector<>();
        Vector<String> alias = documentDao.getActifDocument();
        System.out.println("taille de vector d'alias"+alias.size());

        for (ConcoursModel cm: vector) {
            titres.add(cm.getAlias());
        }
        for (String s:alias) System.out.println(s);

        cmbConcours.setItems(FXCollections.observableArrayList(titres));
        cmbConcours.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                try {
                        System.out.println(oldValue.toString()+" "+newValue.toString());
                        boolean b = false;
                        for (String s:alias) {
                        if (newValue.toString().equalsIgnoreCase(s)){

                            System.out.println(newValue.toString().equalsIgnoreCase(s));
                            b = true;
                        }
                    }
                    if (b)     tglConcours.setSelected(true);
                    else      tglConcours.setSelected(false);

                }catch (Exception e){}
            }
        });
    }


}
