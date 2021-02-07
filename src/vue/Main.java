package vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage st) throws Exception {


        try {
          //  Parent root  = FXMLLoader.load(getClass().getResource("ConcoursVue_B.fxml"));
            //Parent root  = FXMLLoader.load(getClass().getResource("CondidatRegister.fxml"));
            Parent root  = FXMLLoader.load(getClass().getResource("accueilA.fxml"));
           //Parent root  = FXMLLoader.load(getClass().getResource("ChefDepartementWelcome.fxml"));

            Scene scene  = new Scene(root);
            //scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
            st.setTitle("Gestion de Concours et PFE | Authentification");
            st.setScene(scene);
            st.setResizable(false);
            st.show();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Eror "+e.getMessage());
        }
    }

    public static void main(String[] args){

        launch(args);
    }
}