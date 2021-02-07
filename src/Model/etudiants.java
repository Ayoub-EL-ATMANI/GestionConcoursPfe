package Model;

import java.sql.Date;

public class etudiants {

	protected int id;
	protected String nom;
	protected String prenom;
	protected Date date_n;
	protected String cne;
	protected String mail;
	protected String ville;
	protected String addresse;
	protected String tel;
	protected String departement;
	protected String filiere;
	protected int isCandidat;
	protected int id_user;
	protected  int status;
	private String codeinsc;

	public String getCodeinsc() {
		return codeinsc;
	}

	public void setCodeinsc(String codeinsc) {
		this.codeinsc = codeinsc;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public etudiants() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Date getDate_n() {
		return date_n;
	}
	public void setDate_n(Date date_n) {
		this.date_n = date_n;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getCne() {
		return cne;
	}
	public void setCne(String cne) {
		this.cne = cne;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getAddresse() {
		return addresse;
	}
	public void setAddresse(String addresse) {
		this.addresse = addresse;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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

	public int getIsCandidat() {
		return isCandidat;
	}

	public void setIsCandidat(int isSelected) {
		this.isCandidat = isSelected;
	}

	@Override
	public String toString() {
		return "etudiants{" +
				"id=" + id +
				", nom='" + nom + '\'' +
				", prenom='" + prenom + '\'' +
				", date_n=" + date_n +
				", cne='" + cne + '\'' +
				", mail='" + mail + '\'' +
				", ville='" + ville + '\'' +
				", addresse='" + addresse + '\'' +
				", tel='" + tel + '\'' +
				", departement='" + departement + '\'' +
				", filiere='" + filiere + '\'' +
				", isSelected=" + isCandidat +
				'}';
	}
}
