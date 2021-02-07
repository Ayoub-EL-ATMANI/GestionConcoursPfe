package traitement;

import Model.etudiants;
import dao.etudiantDao;
import dao.grouppfedao;

import java.sql.SQLException;
import java.util.Vector;

public class etudiantTrait {
	
	private etudiantDao etudb = new etudiantDao();
	private grouppfedao gppfe;
	public void addEtudiantA(etudiants e) {
		
		etudb.addEtudiantA(e);
		
	}

	//Tester si un étudiant Existe
	 public boolean checkEtudiant(String cne){

	        Vector<String> vector = etudb.getAllCne();
	        for (String s:vector) {
	            if (s.equals(cne)) return true;
	        }
	        return false;
	    }

	    //L'inscription d'un étudiant se fait par un code Donné par le chef de département
		//Ici on test si le code est parmi les codes donné par le chef Dep
	 public boolean checkcode(String code) {
		 try {
			gppfe = new grouppfedao();
			Vector<String> vec = gppfe.getAllCode();
			 for (String c : vec) {
				if(c.equals(code)) return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return false;
	 }
}
