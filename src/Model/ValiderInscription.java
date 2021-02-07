package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class ValiderInscription extends RecursiveTreeObject<ValiderInscription>{

	private String code;
	private String departement;
	private String filiere;	
	
	public ValiderInscription() {
		// TODO Auto-generated constructor stub
	}
	
	public ValiderInscription(String code, String departement, String filiere) {
		super();
		this.code = code;
		this.departement = departement;
		this.filiere = filiere;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDepartement() {
		return departement;
	}
	public void setDepartement(String departement) {
		this.departement = departement;
	}
	public String getFiliere() {
		return filiere;
	}
	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}
	
	
	
}
