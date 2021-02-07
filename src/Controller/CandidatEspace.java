package Controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dao.ConcoursDao;
import dao.DocumentDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Model.Candidat;
import Model.CandidatT;
import Model.ConcoursModel;
import Model.DocumentModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

public class CandidatEspace implements Initializable {

    @FXML
    private JFXTextField txtNom;
    @FXML
    private JFXTextField txtPrenom;
    @FXML
    private JFXTextField txtMail;
    @FXML
    private JFXDatePicker txtDate;
    @FXML
    private JFXTextField txtCne;
    @FXML
    private JFXTextField txtTel;
    @FXML
    private JFXTextField txtAddresse;
    @FXML
    private JFXTextField txtNote;
    @FXML
    private JFXTextField txtDiplome;
    @FXML
    private JFXTextField txtEta;

    private Frame frame = new Frame();
    private CandidatT ct = new CandidatT();
    private ConcoursDao cd = new ConcoursDao();
    private DocumentDao documentDao = new DocumentDao();
    private static int cmp = 0;
    private static final String msgConfirmer = "Vous etes entrain de modifiez votre information, cliquer sur Yes pour Confirmer";
    private static final String TitleConfirmer = "Modification";
    private static final String msgResultat = "Les resultats ne sont pas encore traitees";
    private static final String TitleResultat = "Résultats";



    //Modifier Un Candidat
    public void modifierClick(ActionEvent event) {

            Candidat candidat = new Candidat();
            candidat.setNom(txtNom.getText());
            candidat.setPrenom(txtPrenom.getText());
            candidat.setDate_n(java.sql.Date.valueOf(txtDate.getValue()));
            candidat.setMail(txtMail.getText());
            candidat.setAddresse(txtAddresse.getText());
            candidat.setTel(txtTel.getText());
            candidat.setDiplome(txtDiplome.getText());
            candidat.setEta(txtEta.getText());

            ct  = new CandidatT();
        int a = JOptionPane.showConfirmDialog(frame,msgConfirmer+"",TitleConfirmer+"",JOptionPane.YES_NO_OPTION);
        if(a==JOptionPane.YES_OPTION)ct.updateCandidat(candidat,AccueilA.userA,txtCne.getText());

    }

    public void logout(ActionEvent event) {
        cmp = 0;
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("/vue/accueilA.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setResizable(false);
        stage1.show();


    }

    //Afficher  La liste des Candidats admit
    public void ResultatClick(ActionEvent event) {

        Candidat candidat = ct.getCandidat(AccueilA.userA);
        DocumentModel documentModel = documentDao.getNbrEtStatut(candidat.getId_concours());
        String path = null;
        if (documentModel.getStatut() ==1){


          try {
              File f = new File("");
              path = f.getAbsolutePath()+"\\outputPDF\\resultas.pdf";
              Document document = new Document();
              PdfWriter.getInstance(document,new FileOutputStream(path));
              document.open();
              Paragraph paragraph = new Paragraph("Liste des etudiants admis \n\n");
              document.add(paragraph);

              PdfPTable table = new PdfPTable(5);
              PdfPCell cneC = new PdfPCell(new Paragraph("CNE"));
              PdfPCell nomC = new PdfPCell(new Paragraph("NOM"));
              PdfPCell prenomC = new PdfPCell(new Paragraph("PRENOM"));
              PdfPCell filiereC = new PdfPCell(new Paragraph("FILIERE"));
              PdfPCell noteC = new PdfPCell(new Paragraph("NOTE DE DOSSIER"));


              table.addCell(cneC);
              table.addCell(nomC);
              table.addCell(prenomC);
              table.addCell(filiereC);
              table.addCell(noteC);

              Vector<Candidat> vector = ct.getSelectedCandidat(documentModel.getNbrCandidat(),candidat.getId_concours());
              for (Candidat c:vector) {
                  System.out.println(c);
                  PdfPCell cne = new PdfPCell(new Paragraph(c.getCne()+""));
                  PdfPCell nom = new PdfPCell(new Paragraph(c.getNom()+""));
                  PdfPCell prenom = new PdfPCell(new Paragraph(c.getPrenom()+""));
                  PdfPCell filiere = new PdfPCell(new Paragraph(c.getFiliere()+""));
                  PdfPCell note = new PdfPCell(new Paragraph(c.getNoteDossier()+""));


                  table.addCell(cne);
                  table.addCell(nom);
                  table.addCell(prenom);
                  table.addCell(filiere);
                  table.addCell(note);



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
              }catch (Exception e){}
          }
      }
      else{
            JOptionPane.showMessageDialog(frame,msgResultat+"",TitleResultat+"",JOptionPane.INFORMATION_MESSAGE);
      }
    }

    //Methode pour  Remplir les données de condidat  au démarrage de la vue
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Candidat candidat = ct.getCandidat(AccueilA.userA);
        System.out.println(candidat+" "+candidat.getId_concours() );
        ConcoursModel cm = cd.getConcoursById(candidat.getId_concours());
        //Date datefin = cm.getDateFin();

        java.util.Date dateFin = new java.util.Date(cm.getDateFin().getTime());
        System.out.println(cm +" "+candidat.getId_concours() );
        if (!dateFin.before(new Date())){
            txtNom.setEditable(false);
            txtPrenom.setEditable(false);
            txtDate.setEditable(false);
            txtAddresse.setEditable(false);
            txtDiplome.setEditable(false);
            txtEta.setEditable(false);
            txtTel.setEditable(false);
            txtMail.setEditable(false);
            txtNote.setEditable(false);
            txtCne.setEditable(false);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(new Date());
        Date date = null;
        try {
            date = format.parse(cm.getDateFin().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txtCne.setEditable(false);
        txtNote.setEditable(false);
        txtNom.setText(candidat.getNom());
        txtPrenom.setText(candidat.getPrenom());
        txtDate.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        txtAddresse.setText(candidat.getAddresse());
        txtDiplome.setText(candidat.getDiplome());
        txtEta.setText(candidat.getEta());
        txtTel.setText(candidat.getTel());
        txtMail.setText(candidat.getMail());
        txtNote.setText(candidat.getNoteDossier()+"");
        txtCne.setText(candidat.getCne());

    }
}
