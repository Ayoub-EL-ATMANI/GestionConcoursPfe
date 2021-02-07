package dao;

import Controller.AccueilA;
import Model.encadrants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class DaoEncadrant {

	private Connection cnx;
	private Statement stm;
	private ResultSet res;

	public DaoEncadrant() {
		stm = db_connection.getStatment();
	}


	public void insert(encadrants o) {
		// TODO Auto-generated method stub
		try {
			stm.executeUpdate("insert into encadrants (nom,prenom,departement,filiere) values ('" + o.getNom() + "','"
					+ o.getPrenom() + "','" + o.getDepartement() + "','" + o.getFiliere() + "')");
			stm.execute("commit");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void update(encadrants o) {
		// TODO Auto-generated method stub
		try {
			stm.executeUpdate(
					"update encadrants set nom='" + o.getNom() + "', prenom='" + o.getPrenom() + "', departement='"
							+ o.getDepartement() + "' , filiere = '" + o.getFiliere() + "' where id=" + o.getId());
			stm.execute("commit");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void delete(int id) {
		// TODO Auto-generated method stub
		try {
			stm.executeUpdate("delete from encadrants where id=" + id);
			stm.execute("commit");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public Vector<encadrants> getData() {
		// TODO Auto-generated method stub
		Vector<encadrants> vEncadrants = new Vector<>();

		try {			
			res = stm.executeQuery("select * from encadrants where departement ='"+ AccueilA.CurrentEncadrant.getDepartement()+"' and filiere='"+AccueilA.CurrentEncadrant.getFiliere()+"'");
			while (res.next()) {
				encadrants encadrant = new encadrants();
				encadrant.setId(res.getInt(1));
				encadrant.setNom(res.getString(2));
				encadrant.setPrenom(res.getString(3));
				encadrant.setDepartement(res.getString(4));
				encadrant.setFiliere(res.getString(5));
				vEncadrants.add(encadrant);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return vEncadrants;
	}
	
	public static Vector<String> DepartementFilieres(String departement){		
		Vector<String> filieres=new Vector<>();		
		try {			
			Statement stm=db_connection.get_Connection().createStatement();			
			ResultSet res = stm.executeQuery("select filiere from departement_filiere where departement='"+departement+"'");
			while (res.next()) {
				filieres.add(res.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return filieres;
	}

	public static void main(String[] args) {

		DaoEncadrant dao=new DaoEncadrant();
		for(int i=1;i<=10;i++) {
			encadrants e=new encadrants();
			e.setNom("tsiEncadrantNom"+i);e.setPrenom("tsiEncadrantPrenom"+i);e.setDepartement("gi");e.setFiliere("tsi");
			dao.insert(e);
		}				
	}

}
