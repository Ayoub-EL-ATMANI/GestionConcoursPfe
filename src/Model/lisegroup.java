package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class lisegroup extends RecursiveTreeObject<lisegroup> {
	
	private int nbgroup;
	private String etudiant;
	private String encadrant;
	public int getNbgroup() {
		return nbgroup;
	}
	public void setNbgroup(int nbgroup) {
		this.nbgroup = nbgroup;
	}
	public String getEtudiant() {
		return etudiant;
	}
	public void setEtudiant(String etudiant) {
		this.etudiant = etudiant;
	}
	public String getEncadrant() {
		return encadrant;
	}
	public void setEncadrant(String encadrant) {
		this.encadrant = encadrant;
	}
	
	

}
