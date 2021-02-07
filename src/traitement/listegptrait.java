package traitement;

import Model.lisegroup;
import dao.EspaceEtudiantDao;

import java.sql.SQLException;
import java.util.Vector;

public class listegptrait {
	
	private EspaceEtudiantDao dbesEt;
	
	public listegptrait() {
		// TODO Auto-generated constructor stub
		dbesEt = new EspaceEtudiantDao();
	}
	
	public Vector<lisegroup> getAllistPfe() throws SQLException{
		return dbesEt.getAllistePfe();
	}

}
