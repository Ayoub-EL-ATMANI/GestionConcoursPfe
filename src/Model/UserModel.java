package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class UserModel extends RecursiveTreeObject<UserModel> {
	
	private int id_us;
	private String user,password,codeSecret,type;
	
	
	public UserModel(){
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getId_us() {
		return id_us;
	}
	public void setId_us(int id_us) {
		this.id_us = id_us;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCodeSecret() {
		return codeSecret;
	}
	public void setCodeSecret(String codeSecret) {
		this.codeSecret = codeSecret;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

}
