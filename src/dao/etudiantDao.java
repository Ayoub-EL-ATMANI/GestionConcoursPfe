package dao;

import Controller.AccueilA;
import Model.ValiderInscription;
import Model.etudiants;
import traitement.generate;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class etudiantDao {

    private static Connection con;
    private Frame frame = new Frame();
    private ResultSet res;

    public etudiantDao() {
        con = db_connection.get_Connection();
    }

    public void addEtudiant(etudiants etudiant){

        String req = "insert into etudiants (nom,prenom,date_n,cne,mail,ville,addresse,tel,departement,filiere,isCandidat,id_user) values(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setString(1,etudiant.getNom());
            ps.setString(2,etudiant.getPrenom());
            ps.setDate(3,etudiant.getDate_n());
            ps.setString(4,etudiant.getCne());
            ps.setString(5,etudiant.getMail());
            ps.setString(6,etudiant.getVille());
            ps.setString(7,etudiant.getAddresse());
            ps.setString(8,etudiant.getTel());
            ps.setString(9,etudiant.getDepartement());
            ps.setString(10,etudiant.getFiliere());
            //ps.setByte(11,(byte)1);//etudiant.getIsSelected()
            ps.setInt(11,etudiant.getIsCandidat());
            if (etudiant.getIsCandidat()== 1 ){

                ps.setInt(12,UserDao.getLastUser()) ;
            }
            else {
                ps.setInt(12,UserDao.getLastUser()+1);
            }

            ps.execute();
            System.out.println("adding etudiant");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("problem adding etudiant");
            JOptionPane.showMessageDialog(frame,"Veuillez Verfiier les dates","Verifier dates !",JOptionPane.ERROR_MESSAGE);

        }
    }

    public void addEtudiantA(etudiants etudiant){

        String req = "insert into etudiants (nom,prenom,date_n,cne,mail,ville,addresse,tel,departement,filiere,isCandidat,id_user,codeinsc) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
//      int id = UserDao.getLastUser();
        if(true) {

            try {
                PreparedStatement ps = con.prepareStatement(req);
                ps.setString(1,etudiant.getNom());
                ps.setString(2,etudiant.getPrenom());
                ps.setDate(3,etudiant.getDate_n());
                ps.setString(4,etudiant.getCne());
                ps.setString(5,etudiant.getMail());
                ps.setString(6,etudiant.getVille());
                ps.setString(7,etudiant.getAddresse());
                ps.setString(8,etudiant.getTel());
                ps.setString(9,etudiant.getDepartement());
                ps.setString(10,etudiant.getFiliere());
                ps.setInt(11,etudiant.getIsCandidat());//etudiant.getIsSelected()
                ps.setInt(12,UserDao.getLastUser());
                ps.setString(13, etudiant.getCodeinsc());
                ps.execute();
                System.out.println("adding etudiant");

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("problem adding etudiant");
            }

        }    }

    public static int getLastEtudiant(){

        int ind=-1;
        String req = "select id from etudiants where isCandidat=1 order by id DESC limit 1";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) ind = rs.getInt(1);
            System.out.println("last candidat "+ind);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ind;
    }

    public void updateEtudaint(etudiants etud,String cne){
        String req = "update etudiants set nom = ?,prenom = ?,date_n = ?,mail = ?,addresse = ?,tel = ? where cne = ?";

        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setString(1,etud.getNom());
            ps.setString(2,etud.getPrenom());
            ps.setDate(3,etud.getDate_n());
            ps.setString(4,etud.getMail());
            ps.setString(5,etud.getAddresse());
            ps.setString(6,etud.getTel());
            ps.setString(7,cne);
            ps.execute();
            System.out.println("etudiant updated");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("etudaint error updating ");

        }
    }

    public Vector<etudiants> getData() {
        Vector<etudiants> vEtudiants=new Vector<>();

        try {
            res=db_connection.getStatment().executeQuery("select * from etudiants where departement ='"+ AccueilA.CurrentEncadrant.getDepartement()+"' and filiere='"+AccueilA.CurrentEncadrant.getFiliere()+"'");
            while (res.next()) {
                etudiants etudiant = new etudiants();
                etudiant.setId(res.getInt(1));
                etudiant.setNom(res.getString(3));
                etudiant.setPrenom(res.getString(4));
                etudiant.setCne(res.getString(5));
                etudiant.setMail(res.getString(6));
                etudiant.setVille(res.getString(7));
                etudiant.setAddresse(res.getString(8));
                etudiant.setTel(res.getString(9));
                etudiant.setDepartement(res.getString(10));
                etudiant.setFiliere(res.getString(11));
                etudiant.setStatus(res.getInt(16));
                vEtudiants.add(etudiant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vEtudiants;
    }

    public Vector<etudiants> getEtudiantNonGroupe() {
        Vector<etudiants> vEtudiants=new Vector<>();

        try {
            res=db_connection.getStatment().executeQuery("SELECT * FROM etudiants WHERE nbrGroupe IS null and departement ='"+AccueilA.CurrentEncadrant.getDepartement()+"' and filiere='"+AccueilA.CurrentEncadrant.getFiliere()+"'");
            while (res.next()) {
                etudiants etudiant = new etudiants();
                etudiant.setId(res.getInt(1));
                etudiant.setNom(res.getString(3));
                etudiant.setPrenom(res.getString(4));
                etudiant.setCne(res.getString(5));
                etudiant.setMail(res.getString(6));
                etudiant.setVille(res.getString(7));
                etudiant.setAddresse(res.getString(8));
                etudiant.setTel(res.getString(9));
                etudiant.setDepartement(res.getString(10));
                etudiant.setFiliere(res.getString(11));
                vEtudiants.add(etudiant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vEtudiants;
    }

    public void insertIdGroupe(int nbrGroupe,int idEtudiant) {
        try {
            db_connection.getStatment().executeUpdate("update etudiants set nbrGroupe="+nbrGroupe+" where id="+idEtudiant);
            db_connection.getStatment().execute("commit");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Vector<etudiants> getEtudiantRandom(){

        Vector<etudiants> ve=getEtudiantNonGroupe();
        Vector<etudiants> veRandom = new Vector<>();
        Vector<Integer> vRandom =new Vector<>();

        int executeSeuleFois=0;//remplir la premier valeur de vRandom
        int randomExiste = 0;

        for(int i=0;i<ve.size();i++) {
            int random=(int)(Math.random()*ve.size());

            if (executeSeuleFois==0) { //premiere fois
                vRandom.add(random);
                veRandom.add(ve.get(random));
                executeSeuleFois=1;
            } else {	// > deuxieme fois
                randomExiste=0;
                for (Integer integer : vRandom) {//parcours Vrandom pour savoir s'il existe ou non
                    if(random == integer.intValue()) { //random existe
                        i--;
                        randomExiste=1;
                    }
                }


                if (randomExiste==0) {
                    vRandom.add(random);
                    veRandom.add(ve.get(random));
                }

            }
        }
        return veRandom;
    }

    public Vector<Vector<Integer>> getGroupes(int nbrEtudiantParGroupe ) { //vector des id des utilisateur

        Vector<etudiants> veRandom=getEtudiantRandom();
        Vector<Integer> vgroupe=new Vector<>();
        Vector<Vector<Integer>> vgroupes=new Vector<>();

        int ne=veRandom.size();
        int ng=ne/nbrEtudiantParGroupe;
        int reste=ne%nbrEtudiantParGroupe;
        int compteur=0;
        int indiceEtudiantRestant=ne-reste-1;// 11-2-1 : donc debut=8;
        for(int i=0;i<ng;i++) {//nombre_groupe

            vgroupe=new Vector<>();
            for(int j=0;j<nbrEtudiantParGroupe;j++) {//nombre_etudiantpour chaque_groupe
                vgroupe.add(veRandom.get(compteur).getId());
                compteur++;
            }

            vgroupes.add(vgroupe);
        }

        //traiter les etudiants restant
        for(int i=0;i<reste;i++) {
            Vector<Integer> vg=vgroupes.get(i);
            vg.add(veRandom.get(indiceEtudiantRestant).getId());
            vgroupes.setElementAt(vg, i);
            indiceEtudiantRestant++;
        }

        return vgroupes;
    }


    public void InsertCode(int nbrEtudiant) throws SQLException  {
        for(int i=0;i<nbrEtudiant;i++) {
            String Code = generate.makePassword(9);
            if(i==0) {
                db_connection.getStatment().executeUpdate("delete from ValiderInscription where departement='"+AccueilA.CurrentEncadrant.getDepartement()+"' and filiere='"+AccueilA.CurrentEncadrant.getFiliere()+"'");
            }
            db_connection.getStatment().executeUpdate("insert into ValiderInscription values ('"+Code+"','"+AccueilA.CurrentEncadrant.getDepartement()+"','"+AccueilA.CurrentEncadrant.getFiliere()+"')");
        }
    }


    public Vector<ValiderInscription> getCode(){
        Vector<ValiderInscription> vv=new Vector<>();
        try {System.out.println("getCode");
            System.out.println(AccueilA.CurrentEncadrant.getDepartement()+"  "+AccueilA.CurrentEncadrant.getFiliere());
            res=db_connection.getStatment().executeQuery("select * from ValiderInscription where departement='"+AccueilA.CurrentEncadrant.getDepartement()+"' and filiere='"+AccueilA.CurrentEncadrant.getFiliere()+"'");
            while(res.next()) {
                ValiderInscription v=new ValiderInscription();
                v.setCode(res.getString(1));
                v.setDepartement(res.getString(2));
                v.setFiliere(res.getString(3));
                vv.add(v);
            }} catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return vv;
    }

    public Vector<String> getAllCne(){
        Vector<String> vector = new Vector<>();
        String req = "SELECT cne FROM etudiants WHERE isCandidat <> 1 ";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String cne = rs.getString(1);
                vector.add(cne);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vector;
    }

}
