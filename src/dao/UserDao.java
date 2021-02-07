package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import Model.UserModel;
import Model.encadrants;

public class UserDao {
	
	private db_connection conx = new db_connection();
	private static Connection con ;
	private Vector<UserModel> vect ;
	private ResultSet res;



	public UserDao() {
		con = conx.get_Connection();
	}
	
	public boolean addUser(UserModel user) {
		
		String rest = "insert into user(user,pwd,codeSecret,type) values(?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(rest);
			//ps.setInt(1, id);
			ps.setString(1, user.getUser());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getCodeSecret());
			ps.setString(4, user.getType());
			ps.execute();
			System.out.println(" utilisateur ajouter");
			return true;
		} catch (Exception e) {
			System.out.println(" utilisateur non ajouter");
			return false;
		}

		
	}

	public Object getUserInfo(String login, String password) throws SQLException {
		Object CurrentConnected=new Object();
		UserModel CurrentUser=new UserModel();
		encadrants CurrentEncadrant= new encadrants();
		res=db_connection.getStatment().executeQuery("select * from user where user='"+login+"' and pwd='"+password+"'");
		while (res.next()) {
			CurrentUser.setId_us(res.getInt(1));
			CurrentUser.setUser(res.getString(2));
			CurrentUser.setPassword(res.getString(3));
			CurrentUser.setCodeSecret(res.getString(4));
			CurrentUser.setType(res.getString(5));
		}
		try {
			if(CurrentUser.getType().equals("chef")) {
				res=db_connection.getStatment().executeQuery("SELECT encadrants.id,encadrants.nom,encadrants.prenom,encadrants.departement,encadrants.filiere,encadrants.user_id FROM user,encadrants WHERE user.id=encadrants.user_id AND user.id="+CurrentUser.getId_us());
				while (res.next()) {
					CurrentEncadrant.setId(res.getInt(1));
					CurrentEncadrant.setNom(res.getString(2));
					CurrentEncadrant.setPrenom(res.getString(3));
					CurrentEncadrant.setDepartement(res.getString(4));
					CurrentEncadrant.setFiliere(res.getString(5));
					CurrentEncadrant.setUser_id(res.getInt(6));
				}
				CurrentConnected=CurrentEncadrant;

			}/*else if (CurrentUser.getType().equals("Etudiant")) {
			res=db_connection.getStatment().executeQuery("");
		}*/

		} catch (Exception e){
			System.out.println("error object");
		}
		return CurrentConnected;//o.getClass().getName()
	}


	public Vector<UserModel> getAllUsers(){
		 vect = new Vector<>();
		 String requ = "select * from user";
		 ResultSet res;
		 try {
			 PreparedStatement ps = con.prepareStatement(requ);
			 res = ps.executeQuery();
			 while(res.next()) {
				 UserModel us = new UserModel();
				 us.setId_us(res.getInt(1));
				 us.setUser(res.getString(2));
				 us.setPassword(res.getString(3));
				 us.setCodeSecret(res.getString(4));
				 us.setType(res.getString(5));
				 vect.add(us);		 
			 }	
			 System.out.println("affiche");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(" Error");
		}
		 return vect;
	 }
	 
	 public void deletUser(int id) {
		 
		  String req="delete from user where id = ?";
		 try {
			PreparedStatement ps = con.prepareStatement(req);
			ps.setInt(1, id);
			ps.execute();
			System.out.println(" l'utilisateur est supprimé ");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(" utilisateur non supprimé ");
		}
	 }
	  
	 public void ModifierUser(UserModel user,String userId) {
		 String req = "update user set user = ?, pwd = ? , codeSecret = ? , type = ? where user = ?";
		 try {
			 PreparedStatement ps = con.prepareStatement(req);
			 ps.setString(1, user.getUser());
			 ps.setString(2,user.getPassword());
			 ps.setString(3, user.getCodeSecret());
			 ps.setString(4, user.getType());
			 ps.setString(5, userId);
			 ps.execute();
			 System.out.println("l'utilisateur est modifié ");
		} catch (Exception e) {
			e.printStackTrace();
            System.out.println(" l'utilisateur n'a pas modifier ");		
		}
	 }
	 public Vector<UserModel> getAllMatricule(String mat){
		 Vector<UserModel> vecusm = new Vector<>();
		 String matricule = "%"+mat+"%";
		 String req = "select * from user where id like ? or user like ? or pwd like ? or codeSecret like ? or type like ?";
		  
		 try {
			 PreparedStatement ps = con.prepareStatement(req);
			 ps.setString(1, matricule);
			 ps.setString(2, matricule);
			 ps.setString(3, matricule);
			 ps.setString(4, matricule);
			 ps.setString(5, matricule);
			 ResultSet rs = ps.executeQuery();
			 while(rs.next()) {
				 UserModel usmodl = new UserModel();
				 usmodl.setId_us(rs.getInt(1));
				 usmodl.setUser(rs.getString(2));
				 usmodl.setPassword(rs.getString(3));
				 usmodl.setCodeSecret(rs.getString(4));
				 usmodl.setType(rs.getString(5));
				 vecusm.add(usmodl);
			 }
			 System.out.println(" recherche réussir");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(" here problem !!!!! ");
		}
		 return vecusm;
	 }

	 public UserModel getUser(String user,String pwd){
		UserModel userModel = new UserModel();
		String req = "select * from user where user = ? and pwd = ?";

		 try {
			 PreparedStatement ps = con.prepareStatement(req);
			 ps.setString(1,user);
			 ps.setString(2,pwd);
			 ResultSet rs = ps.executeQuery();
			 while (rs.next()){
			 	userModel.setUser(rs.getString(2));
			 	userModel.setPassword(rs.getString(3));
			 	userModel.setCodeSecret(rs.getString(4));
			 	userModel.setType(rs.getString(5));
			 }
		 } catch (SQLException e) {
			 e.printStackTrace();
		 }
		 return userModel;
	 }
	 public static int getLastUser(){

		String req = "select id from user order by id desc limit 1";
		int ind = 0;
		 try {
			 PreparedStatement ps = con.prepareStatement(req);
			 ResultSet rs = ps.executeQuery();
			 rs.next();
			 ind = rs.getInt(1);

		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 return ind;
	 }

	 public int getUserId(UserModel user){
		int ind = 0;
		String req = "select id from user where user like ?";

		 try {
			 PreparedStatement ps = con.prepareStatement(req);
			 ps.setString(1,user.getUser());
			 ResultSet rs = ps.executeQuery();
			 while (rs.next()) return ind = rs.getInt(1);

		 } catch (SQLException e) {
			 e.printStackTrace();
		 }

		 return ind;
	 }

	public Vector<UserModel> getAllUsersA(){
		vect = new Vector<>();
		String requ = "select * from user where id in(select id_user from etudiants,encadrants where etudiants.filiere = encadrants.filiere)";
		ResultSet res;
		try {
			PreparedStatement ps = con.prepareStatement(requ);
			res = ps.executeQuery();
			while(res.next()) {
				UserModel us = new UserModel();
				us.setId_us(res.getInt(1));
				us.setUser(res.getString(2));
				us.setPassword(res.getString(3));
				us.setCodeSecret(res.getString(4));
				us.setType(res.getString(5));
				vect.add(us);
			}
			System.out.println("affiche");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(" Error");
		}
		return vect;
	}


	public void ModifierUserAyoub(UserModel user,int userId) {
		String req = "update user set user = ?, pwd = ? , codeSecret = ? , type = ? where id = ?";
		try {
			PreparedStatement ps = con.prepareStatement(req);
			ps.setString(1, user.getUser());
			ps.setString(2,user.getPassword());
			ps.setString(3, user.getCodeSecret());
			ps.setString(4, user.getType());
			ps.setInt(5, userId);
			ps.execute();
			System.out.println("l'utilisateur est modifie ");


		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(" l'utilisateur n'a pas modifier ");
		}
	}


	public Vector<String> getAllUser(){
		String req ="select user from user";
		Vector<String> vec =  new Vector<>();
		try {
			PreparedStatement ps = con.prepareStatement(req);
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

