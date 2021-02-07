package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.sql.Date;

public class ConcoursModel extends RecursiveTreeObject<ConcoursModel> {

    private int id_concours;
    private String titreConcours;
    private Date dateDebut;
    private Date dateFin;
    private String alias;
    private String NomChef;
    private String Lieu;

    public String getLieu() {
        return Lieu;
    }

    public void setLieu(String lieu) {
        Lieu = lieu;
    }

    public ConcoursModel() {
        super();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public ConcoursModel(int id_concours, String titreConcours, Date dateDebut, Date dateFin, String alias, String nomChef) {
        this.id_concours = id_concours;
        this.titreConcours = titreConcours;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.NomChef = nomChef;
        this.alias = alias;
    }


    public int getId_concours() {
        return id_concours;
    }

    public void setId_concours(int id_concours) {
        this.id_concours = id_concours;
    }

    public String getTitreConcours() {
        return titreConcours;
    }

    public void setTitreConcours(String titreConcours) {
        this.titreConcours = titreConcours;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getNomChef() {
        return NomChef;
    }

    @Override
    public String toString() {
        return "ConcoursModel{" +
                "id_concours=" + id_concours +
                ", titreConcours='" + titreConcours + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", alias='" + alias + '\'' +
                ", NomChef='" + NomChef + '\'' +
                '}';
    }

    public void setNomChef(String nomChef) {
        NomChef = nomChef;
    }
}
