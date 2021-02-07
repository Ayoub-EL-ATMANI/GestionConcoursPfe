package traitement;

import java.util.Vector;

import Model.UserModel;
import dao.UserDao;

public class User {

	private UserDao usdao;
	
	public User() {
		usdao = new UserDao();
	}
	public boolean addUsers(UserModel user) {
		
	return 	usdao.addUser(user);
	}
	public void ModifierUser(UserModel user,String userId) {
		
		usdao.ModifierUser(user,userId);
	}
	
	public void deleUser(int id) {
		usdao.deletUser(id);
	}
	public Vector<UserModel> recherche(String mat){
		return usdao.getAllMatricule(mat);
	}
	public boolean checkuser(String user) {
		try {

			Vector<String> vec = usdao.getAllUser();
			for (String c : vec) {
				if(c.equals(user)) return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	//modifier les utilisateurs
	public void ModifierUserAyoub(UserModel u, int usid) {
		usdao = new UserDao();
		usdao.ModifierUserAyoub(u, usid);    //ModifierUser(u);
	}
}
