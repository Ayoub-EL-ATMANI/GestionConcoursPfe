package Model;

public class Candidat extends etudiants {


    private String numCandidature;
    private int idEtud;
    private String Diplome;
    private String Eta;
    private float noteDossier;
    private float noteConcours;
    private float noteoral;
    private int isSeleted;
    private int isSeletedOral;
    private int id_concours;
    private int id_etudiant;

    public int getId_etudiant() {
        return id_etudiant;
    }

    public void setId_etudiant(int id_etudiant) {
        this.id_etudiant = id_etudiant;
    }

    public int getId_concours() {
        return id_concours;
    }

    public void setId_concours(int id_concours) {
        this.id_concours = id_concours;
    }

    public Candidat() {
        super();
    }

    public String getNumCandidature() {
        return numCandidature;
    }

    public void setNumCandidature(String numCandidature) {
        this.numCandidature = numCandidature;
    }

    public int getIdEtud() {
        return idEtud;
    }

    public void setIdEtud(int idEtud) {
        this.idEtud = idEtud;
    }

    public String getDiplome() {
        return Diplome;
    }

    public void setDiplome(String diplome) {
        Diplome = diplome;
    }

    public String getEta() {
        return Eta;
    }

    public void setEta(String eta) {
        Eta = eta;
    }

    public float getNoteDossier() {
        return noteDossier;
    }

    public void setNoteDossier(float noteDossier) {
        this.noteDossier = noteDossier;
    }

    public float getNoteConcours() {
        return noteConcours;
    }

    public void setNoteConcours(float noteConcours) {
        this.noteConcours = noteConcours;
    }

    public float getNoteoral() {
        return noteoral;
    }

    public void setNoteoral(float noteoral) {
        this.noteoral = noteoral;
    }

    public int getIsSeleted() {
        return isSeleted;
    }

    public void setIsSeleted(int isSeleted) {
        this.isSeleted = isSeleted;
    }

    public int getIsSeletedOral() {
        return isSeletedOral;
    }

    public void setIsSeletedOral(int isSeletedOral) {
        this.isSeletedOral = isSeletedOral;
    }

    @Override
    public String toString() {
        return "Candidat{" +
                "numCandidature='" + numCandidature + '\'' +
                ", Diplome='" + Diplome + '\'' +
                ", Eta='" + Eta + '\'' +
                ", noteDossier=" + noteDossier +
                ", noteConcours=" + noteConcours +
                ", noteoral=" + noteoral +
                ", isSeleted=" + isSeleted +
                ", isSeletedOral=" + isSeletedOral +
                ", id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", cne='" + cne + '\'' +
                ", mail='" + mail + '\'' +
                ", ville='" + ville + '\'' +
                ", addresse='" + addresse + '\'' +
                ", tel='" + tel + '\'' +
                ", departement='" + departement + '\'' +
                ", filiere='" + filiere + '\'' +
                "id_concours='"+id_concours+ '\''+
                '}';
    }
}
