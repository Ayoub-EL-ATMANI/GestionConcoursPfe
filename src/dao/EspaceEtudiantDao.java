package dao;

import Model.lisegroup;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class EspaceEtudiantDao {
	
	private db_connection conx = new db_connection();
    private Connection con;
    private Vector<lisegroup> vecliste;
    
    public EspaceEtudiantDao() {
		
    	con=conx.get_Connection();
	}
    
    public Vector<lisegroup> getAllistePfe() throws SQLException{
		Vector<lisegroup> vG=new Vector<>();	
//		encadrants enc=login.CurrentEncadrant;		
//		user.setText(enc.getId()+"  "+enc.getNom()+"  "+enc.getPrenom()+"  "+enc.getDepartement()+"  "+enc.getFiliere()+"  "+enc.getUser_id());		
		Statement stm= db_connection.get_Connection().createStatement();
		ResultSet res;
		res=stm.executeQuery("SELECT listegroupes.nbrGroupe,encadrants.nom FROM listegroupes,encadrants WHERE listegroupes.id_encadrant=encadrants.id and listegroupes.departement='gi' and listegroupes.filiere='gi' and isMannuel=0");		
		while(res.next()) {				
			lisegroup grp=new lisegroup();
			grp.setNbgroup(res.getInt(1));
			grp.setEncadrant(res.getString(2));			
			vG.add(grp);
		}

		//add etudiants to groups  
		for (lisegroup groupe : vG) {		
			res=stm.executeQuery("SELECT nom,prenom FROM etudiants WHERE nbrGroupe="+groupe.getNbgroup()+"");
			String nomEtudiant="";
			while(res.next()) {
				nomEtudiant+=res.getString(1)+" "+res.getString(2)+" \t ";				
			}
			groupe.setEtudiant(nomEtudiant);
		}	
		return vG;
    }
//    public static void main(String[] args) {
//		 EspaceEtudiantDao db = new EspaceEtudiantDao();
//		 Vector<lisegroup> v = db.getAllistePfe();
//		 for (lisegroup lisegroup : v) {
//			 System.out.println(lisegroup.getNbgroup());
//		}
//		 
//	}
}
