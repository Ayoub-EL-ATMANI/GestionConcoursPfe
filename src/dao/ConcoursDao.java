package dao;

import Model.ConcoursModel;

import java.sql.*;
import java.util.Vector;

public class ConcoursDao {


    private static Connection con;

    public ConcoursDao(){

        con = db_connection.get_Connection();
    }

    public void addConcours(ConcoursModel concoursModel){

        String req = "Insert into ListConcours(Concours,DateDebut,DateFin,alias,nom_chef,Lieu) values(?,?,?,?,?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setString(1,concoursModel.getTitreConcours());
            ps.setDate(2,concoursModel.getDateDebut());
            ps.setDate(3,concoursModel.getDateFin());
            ps.setString(4,concoursModel.getAlias());
            ps.setString(5,concoursModel.getNomChef());
            ps.setString(6,concoursModel.getLieu());
            ps.execute();
            System.out.println("values adding");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("values not adding");
        }
    }

    public Vector<ConcoursModel> getAllConcours(){

        String req =  "select * from ListConcours";
        Vector<ConcoursModel> vect = new Vector<>();
        ConcoursModel cm ;

        try {
            PreparedStatement ps = con.prepareStatement(req);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                cm = new ConcoursModel();
                cm.setId_concours(rs.getInt(1));
                cm.setTitreConcours(rs.getString(2));
                cm.setDateDebut(rs.getDate(3));
                cm.setDateFin(rs.getDate(4));
                cm.setAlias(rs.getString(5));
                cm.setNomChef(rs.getString(6));
                cm.setLieu(rs.getString(7));

                vect.add(cm);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return vect;
    }

    public ConcoursModel getConcoursById(int id){

        String req =  "select * from ListConcours where id like ?";
        ConcoursModel cm = null;

        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                cm = new ConcoursModel();
                cm.setId_concours(rs.getInt(1));
                cm.setTitreConcours(rs.getString(2));
                cm.setDateDebut(rs.getDate(3));
                cm.setDateFin(rs.getDate(4));
                cm.setAlias(rs.getString(5));
                cm.setNomChef(rs.getString(6));
                cm.setLieu(rs.getString(7));
                return cm;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cm;
    }

    public ConcoursModel getConcoursByAlias(String alias){

        String req =  "select * from ListConcours where alias like ?";
        ConcoursModel cm = null;

        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setString(1,alias);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                cm = new ConcoursModel();
                cm.setId_concours(rs.getInt(1));
                cm.setTitreConcours(rs.getString(2));
                cm.setDateDebut(rs.getDate(3));
                cm.setDateFin(rs.getDate(4));
                cm.setAlias(rs.getString(5));
                cm.setNomChef(rs.getString(6));
                cm.setLieu(rs.getString(7));
                return cm;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cm;
    }


    public void ModiferConcours(int id,ConcoursModel concoursModel){

        String req = "update ListConcours set Concours = ? , dateDebut = ? ,dateFin = ? , alias = ? , nom_chef = ? , lieu = ? where id = ? ";
        try {
            PreparedStatement ps = con.prepareStatement(req);

            ps.setString(1,concoursModel.getTitreConcours());
            ps.setDate(2,concoursModel.getDateDebut());
            ps.setDate(3,concoursModel.getDateFin());
            ps.setString(4,concoursModel.getAlias());
            ps.setString(5,concoursModel.getNomChef());
            ps.setString(6,concoursModel.getLieu());
            ps.setInt(7,id);
            ps.execute();
            System.out.println("colonne modifié ");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error modification");

        }
    }

    public Vector<ConcoursModel> getConcourKey(String key){
        Vector<ConcoursModel> cm = new Vector<>();
        String motCle = "%"+key+"%";

        String req = "select * from ListConcours where Concours like ? or alias like ? or nom_chef like ? or lieu like ? or id like ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setString(1,motCle);
            ps.setString(2,motCle);
            ps.setString(3,motCle);
            ps.setString(4,motCle);
            ps.setString(5,motCle);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                ConcoursModel concours = new ConcoursModel();
                concours.setId_concours(rs.getInt(1));
                concours.setTitreConcours(rs.getString(2));
                concours.setDateDebut(rs.getDate(3));
                concours.setDateFin(rs.getDate(4));
                concours.setAlias(rs.getString(5));
                concours.setNomChef(rs.getString(6));
                concours.setLieu(rs.getString(7));
                cm.add(concours);
            }

            System.out.println("selection effectue");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("probleme de selection par mot cle");
        }


        return cm;
    }

    public  void delConcours(int id){

        String req = "DELETE FROM ListConcours WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1,id);
            ps.execute();
            System.out.println("row "+id+" deleted");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("problem deleting");

        }
    }

    public static int getLastConcours(){
        int ind = 0;
        String req = "select id from ListConcours order by id DESC limit 1";
        try {
            PreparedStatement ps= con.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next())  ind = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ind;
    }
 }
