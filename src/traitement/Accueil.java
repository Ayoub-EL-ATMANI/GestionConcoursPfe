package traitement;

import Model.UserModel;
import dao.AcceuilDao;

public class Accueil {



    private AcceuilDao acc;
    public Accueil(){

         acc = new AcceuilDao();
    }

    public boolean isUser(UserModel userModel){
        return acc.isUser(userModel);
    }

    public String type(String user){

        return acc.getType(user);
    }
}
