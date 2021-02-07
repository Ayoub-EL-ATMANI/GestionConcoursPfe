package dao;


import Controller.AccueilA;
import Model.GroupePfe;
import Model.encadrants;
import Model.etudiants;
import Model.groupe;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class DaoGroupePfe {

	private Connection cnx;
	private Statement stm;
	private ResultSet res;
	private ResultSet res1;
	
	public DaoGroupePfe() {
		stm=db_connection.getStatment();
	}
	
	
	
	public int insertG(GroupePfe groupe) {
		// TODO Auto-generated method stub
		int nbrGroupe=0;
		System.out.println(groupe.getId_encadrant());
		System.out.println(groupe.getDepartement());
		System.out.println(groupe.getFiliere());
		try {
			stm.executeUpdate("insert into listegroupes (id_encadrant,departement,filiere) values ("+groupe.getId_encadrant()+",'"+groupe.getDepartement()+"','"+groupe.getFiliere()+"')");
			res=stm.executeQuery("select nbrGroupe from listegroupes order by nbrGroupe desc limit 1");
			while(res.next()) {
				nbrGroupe=res.getInt(1);				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nbrGroupe;
	}
/*	
	
	public void update(GroupePfe o) {
		// TODO Auto-generated method stub
		try {
			stm.executeUpdate("update listegroupes set NomEncadrant='"+o.getEncadrant()+"', departement='"+o.getDepartement()+"' , filiere = '"+o.getFiliere()+"' where id="+o.getNumGrp());
			stm.execute("commit");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/

	public void EtudiantExchange(String etudiant , int numGroupeDe , int numGroupeA) {
			String etudiants=new String();
		try {
			res=stm.executeQuery("select NomEtudiants from  listegroupes where nbrGroupe="+numGroupeA);
			while(res.next()) {
				etudiants=res.getString(1);
			}
			etudiants+="/"+etudiant;
			stm.executeUpdate("update listegroupes set NomEtudiants='"+etudiants+"' where nbrGroupe="+numGroupeA);
			stm.execute("commit");			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void delete(int id) {
		// TODO Auto-generated method stub
		try {
			stm.executeUpdate("delete from listegroupes where id="+id);
			stm.execute("commit");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public Vector<GroupePfe> getData() {
		// TODO Auto-generated method stub
		Vector<etudiants> vEtudiants=new Vector<>();
		
		try {
			res=stm.executeQuery("select * from listegroupes");
			while (res.next()) {
				etudiants etudiant = new etudiants();
				etudiant.setId(res.getInt(1));
				etudiant.setNom(res.getString(2));
				etudiant.setPrenom(res.getString(3));
				etudiant.setCne(res.getString(4));
				etudiant.setMail(res.getString(5));
				etudiant.setVille(res.getString(6));
				etudiant.setAddresse(res.getString(7));
				etudiant.setTel(res.getString(8));
				etudiant.setDepartement(res.getString(9));
				etudiant.setFiliere(res.getString(10));
				vEtudiants.add(etudiant);
			}
	} catch (Exception e) {
			e.printStackTrace();
		}				
	return null;
	}
	
	
	public void insert(GroupePfe o) {
		// TODO Auto-generated method stub
		
	}



	
	public void update(GroupePfe o) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	// liste ge groupes
	public Vector<Vector<Integer>> getGroupes(int nbrEtudiantParGroupe ) { //vector des id des utilisateur
		etudiantDao daoE=new etudiantDao();
		Vector<etudiants> veRandom=daoE.getEtudiantRandom();
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
	
	// affectation au encadrant
	public void affecter(int nbrEtudiantParGroupe) {
		
			try {																																											//requete imbriqu� 
				stm.executeUpdate("UPDATE etudiants SET nbrGroupe=null where nbrGroupe IN(select nbrGroupe from listegroupes where isMannuel=0) and departement ='"+AccueilA.CurrentEncadrant.getDepartement()+"' and filiere='"+AccueilA.CurrentEncadrant.getFiliere()+"'");
				stm.executeUpdate("DELETE FROM listegroupes where departement ='"+ AccueilA.CurrentEncadrant.getDepartement()+"' and filiere='"+AccueilA.CurrentEncadrant.getFiliere()+"' and isMannuel=0");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		
			etudiantDao daoEtudiant=new etudiantDao();
			DaoEncadrant daoEncadrant=new DaoEncadrant();
			DaoGroupePfe daoGroupe=new DaoGroupePfe();
			GroupePfe groupe=new GroupePfe();
			
			Vector<Vector<Integer>> vGroupe=daoEtudiant.getGroupes(nbrEtudiantParGroupe);
			Vector<encadrants> vEncadrant=daoEncadrant.getData();
			int sizeEncadrant=vEncadrant.size();
			
			int indiceEncadrant=1;
			for (Vector<Integer> v : vGroupe) {//vector de tout les groupes;
//					groupe.setId_encadrant(indiceEncadrant);
					groupe.setId_encadrant(vEncadrant.get(indiceEncadrant).getId());
					groupe.setDepartement(AccueilA.CurrentEncadrant.getDepartement());
					groupe.setFiliere(AccueilA.CurrentEncadrant.getFiliere());
					//inserer un groupe et get nbrGroupe for it pour l'inserer dans �tudiant
					int nbrGroupe=daoGroupe.insertG(groupe);
					groupe=new GroupePfe();	
					
						for (Integer idEtudiant : v) {//vector d'un groupe
							daoEtudiant.insertIdGroupe(nbrGroupe, idEtudiant.intValue());
						}
						indiceEncadrant++;
						if (indiceEncadrant == sizeEncadrant) {
							indiceEncadrant=1;
						}
			}		
	   }
	
	public Vector<groupe> ListeGroupe() throws SQLException{
		//nbrGroupe + nom Encadrant
		Vector<groupe> vG=new Vector<>();
//		encadrants enc=login.CurrentEncadrant;		
//		user.setText(enc.getId()+"  "+enc.getNom()+"  "+enc.getPrenom()+"  "+enc.getDepartement()+"  "+enc.getFiliere()+"  "+enc.getUser_id());		
		
		res=stm.executeQuery("SELECT listegroupes.nbrGroupe,encadrants.nom FROM listegroupes,encadrants WHERE listegroupes.id_encadrant=encadrants.id and listegroupes.departement='"+AccueilA.CurrentEncadrant.getDepartement()+"' and listegroupes.filiere='"+AccueilA.CurrentEncadrant.getFiliere()+"' and isMannuel=0");
		while(res.next()) {				
			groupe grp=new groupe();
			grp.setNumG(res.getInt(1));
			grp.setEncadrant(res.getString(2));			
			vG.add(grp);
		}

		//add etudiants to groups  
		for (groupe groupe : vG) {		
			res1=stm.executeQuery("SELECT nom FROM etudiants WHERE nbrGroupe="+groupe.getNumG()+"");
			String nomEtudiant="";
			while(res1.next()) {
				nomEtudiant+=res1.getString(1)+" | ";				
			}
			groupe.setEtudiants(nomEtudiant);
		}	
		return vG;
	}
	
	public static Vector<groupe> listeVide(){		
		return null; 
	}
	
	public Vector<groupe> ListeGroupeMannuel() throws SQLException{
		//nbrGroupe + nom Encadrant
		Vector<groupe> vG=new Vector<>();	
//		encadrants enc=login.CurrentEncadrant;		
//		user.setText(enc.getId()+"  "+enc.getNom()+"  "+enc.getPrenom()+"  "+enc.getDepartement()+"  "+enc.getFiliere()+"  "+enc.getUser_id());		
		
		res=stm.executeQuery("SELECT listegroupes.nbrGroupe,encadrants.nom FROM listegroupes,encadrants WHERE listegroupes.id_encadrant=encadrants.id and listegroupes.departement='"+AccueilA.CurrentEncadrant.getDepartement()+"' and listegroupes.filiere='"+AccueilA.CurrentEncadrant.getFiliere()+"' and isMannuel=1");
		while(res.next()) {				
			groupe grp=new groupe();
			grp.setNumG(res.getInt(1));

				grp.setEncadrant(res.getString(2));

			vG.add(grp);
		}

		//add etudiants to groups  
		for (groupe groupe : vG) {		
			res1=stm.executeQuery("SELECT nom FROM etudiants WHERE nbrGroupe="+groupe.getNumG()+"");
			String nomEtudiant="";
			while(res1.next()) {
					nomEtudiant+=res1.getString(1)+" | ";					
			}
			groupe.setEtudiants(nomEtudiant);
		}
		return vG;
	}	

	
}