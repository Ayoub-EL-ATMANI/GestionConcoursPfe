package Model;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class jfxGroupe extends RecursiveTreeObject<jfxGroupe>{

    String numG;
    String etudiants;
    String encadrant;
    JFXButton action;    
    
    public jfxGroupe() {
		// TODO Auto-generated constructor stub
	}
    
	public jfxGroupe(String numG, String etudiants, String encadrant, JFXButton action) {
		super();
		this.numG = numG;
		this.etudiants = etudiants;
		this.encadrant = encadrant;
		this.action = action;
	}
	public String getNumG() {
		return numG;
	}
	public void setNumG(String numG) {
		this.numG = numG;
	}
	public String getEtudiants() {
		return etudiants;
	}
	public void setEtudiants(String etudiants) {
		this.etudiants = etudiants;
	}
	public String getEncadrant() {
		return encadrant;
	}
	public void setEncadrant(String encadrant) {
		this.encadrant = encadrant;
	}
	public JFXButton getAction() {
		return action;
	}
	public void setAction(JFXButton action) {
		this.action = action;
	}	
	
}
