package dao;

import Model.Candidat;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class CandidatDao {

    private Frame frame = new Frame();
    private Connection con ;
    private db_connection conx = new db_connection();
    public CandidatDao() {

        con = conx.get_Connection();
    }

    public void addCandidat(Candidat candidat){

        String req1 = "insert into candidats (num_candidature,id_etudiant,diplome,etablissement,noteDossier,noteConcours,noteOrale,is_selectedCandidat ,is_selectedOraleCandidat,id_concours) values(?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps1 = con.prepareStatement(req1);
            ps1.setString(1,candidat.getNumCandidature());
            ps1.setInt(2,etudiantDao.getLastEtudiant());
            ps1.setString(3,candidat.getDiplome());
            ps1.setString(4,candidat.getEta());
            ps1.setFloat(5,candidat.getNoteDossier());
            ps1.setFloat(6,candidat.getNoteConcours());
            ps1.setFloat(7,candidat.getNoteoral());
            ps1.setInt(8,candidat.getIsSeleted());
            ps1.setInt(9,candidat.getIsSeletedOral());
            ps1.setInt(10,candidat.getId_concours());
            ps1.execute();
            System.out.println("Candidat ajouter"+candidat.getNumCandidature());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame,"Verifier votre information","Error",JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
            System.out.println("adding candidat problem");

        }

    }

    public Vector<String> getAllCne(){
        Vector<String> vector = new Vector<>();
        String req = "SELECT cne FROM etudiants WHERE isCandidat = 1 ";
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

    public Candidat getCandidatById(String userId){
        Candidat candidat = null;
        String req = "SELECT * from user u,candidats c,etudiants e WHERE u.id = e.id_user and e.id = c.id_etudiant and u.user like ? ";

        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setString(1,userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                candidat = new Candidat();
                candidat.setNom(rs.getString("nom"));
                candidat.setPrenom(rs.getString("prenom"));
                candidat.setCne(rs.getString("cne"));
                candidat.setDate_n(rs.getDate("date_n"));
                candidat.setMail(rs.getString("mail"));
                candidat.setTel(rs.getString("tel"));
                candidat.setEta(rs.getString("etablissement"));
                candidat.setNoteDossier(rs.getFloat("noteDossier"));
                candidat.setAddresse(rs.getString("addresse"));
                candidat.setDiplome(rs.getString("diplome"));
                candidat.setId_concours(rs.getInt("id_concours"));
                candidat.setId_etudiant(rs.getInt("id_etudiant"));
                return  candidat;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return candidat;
    }

    public void updateCandidat(Candidat candidat,String user){

        Candidat cand = getCandidatById(user);
        String req = "update candidats set diplome = ?, etablissement = ? where num_candidature = ?";

        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setString(1,candidat.getDiplome());
            ps.setString(2,candidat.getEta());
            ps.setString(3,cand.getNumCandidature());

            ps.execute();
            System.out.println("Candidat updated"+cand.getNumCandidature());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Candidat error updating");

        }
    }

    public Vector<Candidat> getSelectedCandidat(int nbrCandidat,int idConours){

        Vector<Candidat> vect = new Vector<>();
        String req ="select e.cne,e.nom,e.prenom,l.alias,c.noteDossier from etudiants e,candidats c,ListConcours l WHERE isCandidat = 1 AND e.id = c.id_etudiant and c.id_concours = l.id and l.id = ? ORDER by c.noteDossier DESC LIMIT ? ";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1,idConours);
            ps.setInt(2,nbrCandidat);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Candidat candidat  = new Candidat();
                candidat.setCne(rs.getString(1));
                candidat.setNom(rs.getString(2));
                candidat.setPrenom(rs.getString(3));
                candidat.setFiliere(rs.getString(4));
                candidat.setNoteDossier(rs.getFloat(5));
                vect.add(candidat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vect;
    }
}
