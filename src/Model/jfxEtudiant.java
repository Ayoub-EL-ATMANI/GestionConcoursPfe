package Model;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.scene.control.Label;


public class jfxEtudiant extends RecursiveTreeObject<jfxEtudiant> {

	    String etudiants;
	    JFXButton action;	
	    Label status;
	    
	    public jfxEtudiant() {
			// TODO Auto-generated constructor stub
		}
	    

			public jfxEtudiant(String etudiants, JFXButton action, Label status) {
			super();
			this.etudiants = etudiants;
			this.action = action;
			this.status = status;
		}



		public Label getStatus() {
			return status;
		}
		public void setStatus(Label status) {
			this.status = status;
		}

		public String getEtudiants() {
			return etudiants;
		}
		public void setEtudiants(String etudiants) {
			this.etudiants = etudiants;
		}
		public JFXButton getAction() {
			return action;
		}
		public void setAction(JFXButton action) {
			this.action = action;
		}
	
}
