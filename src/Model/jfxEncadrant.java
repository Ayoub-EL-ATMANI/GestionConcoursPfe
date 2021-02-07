package Model;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class jfxEncadrant extends RecursiveTreeObject<jfxEncadrant>{

    String encadrant;
    JFXButton action;
    
    public jfxEncadrant() {
		// TODO Auto-generated constructor stub
	}
    
	public jfxEncadrant(String encadrant, JFXButton action) {
		super();
		this.encadrant = encadrant;
		this.action = action;
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
