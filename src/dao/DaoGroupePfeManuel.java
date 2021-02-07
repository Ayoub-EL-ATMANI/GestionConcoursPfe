package dao;

import Controller.AccueilA;
import Controller.GroupePfe;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class DaoGroupePfeManuel {

	private Connection cnx;
	private Statement stm;
	private ResultSet res;
	private ResultSet res1;
	
	public DaoGroupePfeManuel() {
		// TODO Auto-generated constructor stub
		stm= db_connection.getStatment();
	}
	

	public void insert(GroupePfe o) {
		// TODO Auto-generated method stub
		
	}

	public void update(GroupePfe o) {
		// TODO Auto-generated method stub
		
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	public Vector<GroupePfe> getData() {
		// TODO Auto-generated method stub&
		return null;
	}

	
	
	
	
	
	public int insertG() {
		// TODO Auto-generated method stub
		int nbrGroupe=0;
		try {			
			stm.executeUpdate("insert into listegroupes (departement,filiere,isMannuel) values ('"+ AccueilA.CurrentEncadrant.getDepartement()+"','"+AccueilA.CurrentEncadrant.getFiliere()+"',1) ");
			res=stm.executeQuery("select nbrGroupe from listegroupes order by nbrGroupe desc limit 1");
			System.out.println("add Groupe");
			while(res.next()) {
				nbrGroupe=res.getInt(1);				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nbrGroupe;
	}
	
	public void deleteGroupe(int nbrGroupe) throws SQLException {
		stm.executeUpdate("UPDATE etudiants SET nbrGroupe=null,status=0 WHERE nbrGroupe="+nbrGroupe);
		stm.executeUpdate("DELETE FROM listegroupes WHERE nbrGroupe="+nbrGroupe);
	}
	
	public void addEtudiant(int idEtudiant, int nbrGroupe) {
		try {
			stm.executeUpdate("UPDATE etudiants SET nbrGroupe="+nbrGroupe+" ,status=1 WHERE id="+idEtudiant);
			System.out.println("add etudiant");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addEncadrant(int idEncadrant, int nbrGroupe) {
		try {
			stm.executeUpdate("update listegroupes set id_encadrant="+idEncadrant+" where nbrGroupe="+nbrGroupe);
			System.out.println("add Encadrant");
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
		
	public static void main(String[] args) {
		DaoGroupePfeManuel dao=new DaoGroupePfeManuel();
		int idEtudiant=98;
		int idEncadrant=9;
		int nbrGroupe=dao.insertG();		
		dao.addEtudiant(idEtudiant, nbrGroupe);
		dao.addEncadrant(idEncadrant, nbrGroupe);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
