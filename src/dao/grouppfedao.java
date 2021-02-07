package dao;

import Model.ValiderInscription;
import traitement.generate;

import java.sql.*;
import java.util.Vector;

public class grouppfedao {

	private Connection cnx;
	private Statement stm;
	private ResultSet res;
	
	public grouppfedao() throws SQLException {
		// TODO Auto-generated constructor stub
		cnx= db_connection.get_Connection();
		stm=cnx.createStatement();
	}
	
	public Vector<String> add(int nbrEtudiant)  {
		  Vector<String> v = new Vector<>();
			try {
				for(int i=0;i<nbrEtudiant;i++) {
					String pass = generate.makePassword(9);
					stm.executeUpdate("insert into ValiderInscription values ('"+pass+"','gi','gi')");
					v.add(pass);
				}
				
				System.out.println("generation des codes succes");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("probleme de generation des codes");
			}
			return v;					
	}
	
	public Vector<ValiderInscription> getCode(){
		Vector<ValiderInscription> vv=new Vector<>();
		try {
			res=stm.executeQuery("select * from ValiderInscription where departement='gi' and filiere='gi'");
			//res=stm.executeQuery("select * from ValiderInscription where departement='gi' and filiere='gi'");

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
	public Vector<String> getAllCode(){
		String req ="select code from ValiderInscription where code not in (select codeinsc from etudiants WHERE codeinsc is not null)";
		Vector<String> vec =  new Vector<>();
		try {
			  PreparedStatement ps = cnx.prepareStatement(req);
			  ResultSet rs = ps.executeQuery();
			  while(rs.next()) {
				  vec.add(rs.getString(1));
			  }
			  System.out.println("ok"+vec.size());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		return vec;
	}
	
	
	
	
	
}
