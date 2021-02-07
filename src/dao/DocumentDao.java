package dao;

import Model.DocumentModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DocumentDao {

    private static Connection con;

    public DocumentDao() {
        con = db_connection.get_Connection();
    }

    public void addDocument(DocumentModel document){

        String req = "insert into documents(valeur,statut,nbrCandidat,chef,id_concours) values (?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setString(1,document.getValeur());
            ps.setInt(2,document.getStatut());
            ps.setInt(3,document.getNbrCandidat());
            ps.setString(4,document.getChef());
            ps.setInt(5,document.getId_concours());
            ps.execute();
        } catch (SQLException e) {
            System.out.println("error adding documents");
            e.printStackTrace();
        }
    }

    public void updateDocument(DocumentModel document,int idConcours){

        String req = "update documents set statut = ? ,nbrCandidat = ? where id_concours = ?" ;
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1,document.getStatut());
            ps.setInt(2,document.getNbrCandidat());
            ps.setInt(3,idConcours);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("error updating documents");
            e.printStackTrace();
        }


    }

    public DocumentModel getNbrEtStatut(int idConcours){
        DocumentModel documentModel = new DocumentModel();
        String req = "select statut,nbrCandidat from documents where id_concours = ?";
        try {
            PreparedStatement ps= con.prepareStatement(req);
            ps.setInt(1,idConcours);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                 documentModel.setStatut(rs.getInt(1));
                 documentModel.setNbrCandidat(rs.getInt(2));
                 return  documentModel;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documentModel;
    }

    public static Vector<String> getActifDocument(){
        Vector<String> vector = new Vector<>();
        String req = "select alias from documents d,ListConcours l where d.id_concours = l.id and d.statut=1";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                vector.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vector;
    }
}
