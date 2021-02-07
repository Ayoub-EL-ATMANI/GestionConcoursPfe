package Model;

public class DocumentModel {

    private String valeur;
    private int statut;
    private int nbrCandidat;
    private String chef;
    private int id_concours;

    public int getId_concours() {
        return id_concours;
    }

    public void setId_concours(int id_concours) {
        this.id_concours = id_concours;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public int getNbrCandidat() {
        return nbrCandidat;
    }

    public void setNbrCandidat(int nbrCandidat) {
        this.nbrCandidat = nbrCandidat;
    }

    public String getChef() {
        return chef;
    }

    public void setChef(String chef) {
        this.chef = chef;
    }
}
