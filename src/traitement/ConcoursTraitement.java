package traitement;

import Model.ConcoursModel;
import dao.ConcoursDao;

import java.sql.Date;
import java.util.Vector;


public class ConcoursTraitement {

    private ConcoursDao concoursDao;

    public ConcoursTraitement(){

        concoursDao = new ConcoursDao();
    }

    public void addConcours(ConcoursModel concoursModel){

        concoursDao.addConcours(concoursModel);
    }

    public Vector<ConcoursModel> getAllConcours(){
        return concoursDao.getAllConcours();
    }

    public Vector<ConcoursModel> getConcoursKey(String key){
        return concoursDao.getConcourKey(key);
    }

    public void delConcours(int id){
        concoursDao.delConcours(id);
    }

    public void modifierConcours(int id,ConcoursModel concoursModel){

        concoursDao.ModiferConcours(id,concoursModel);
    }

    //La méthode renvoi un concours en donnant son alias
    public ConcoursModel getConcoursByAlias(String alias){
         ConcoursModel cm1  = concoursDao.getConcoursByAlias(alias);
         return cm1;
    }
}
