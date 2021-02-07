package Model;

import Controller.ListConcours;
import dao.CandidatDao;
import dao.etudiantDao;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class CandidatT {

    private CandidatDao cd = new CandidatDao();
    private etudiantDao ed = new etudiantDao();
    public static StringBuffer user;
    public static String pass;
    private Frame frame = new Frame();

    public void addCandidat(Candidat candidat){
        Byte b = 1;
        ConcoursModel cm = ListConcours.cmo;
        candidat.setNumCandidature(cm.getAlias()+""+(etudiantDao.getLastEtudiant()+1));
        candidat.setIdEtud(etudiantDao.getLastEtudiant());
        candidat.setIsSeleted(b);
        try {
    ed.addEtudiant(candidat);
    cd.addCandidat(candidat);

            }catch (Exception e){
            JOptionPane.showMessageDialog(frame,"Verifier votre information","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean checkCandidat(String cne){

        Vector<String> vector = cd.getAllCne();
        for (String s:vector) {
            if (s.equals(cne)) return true;
        }
        return false;
    }

    public Candidat getCandidat(String userId){
        Candidat candidat = new Candidat();
        return candidat = cd.getCandidatById(userId);
    }

    public void updateCandidat(Candidat candidat,String user,String cne){

        cd.updateCandidat(candidat,user);
        ed.updateEtudaint(candidat,cne);
    }

    public Vector<Candidat> getSelectedCandidat(int nbrCandidat,int idConcours){
        return cd.getSelectedCandidat(nbrCandidat,idConcours);

    }
}
