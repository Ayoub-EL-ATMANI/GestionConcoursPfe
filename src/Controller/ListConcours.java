package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.ConcoursDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Model.ConcoursModel;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class ListConcours implements Initializable {

    @FXML
    private VBox vbox;
    @FXML
    private JFXTextField txtNom;
    @FXML
    private TitledPane pane;
    private Vector<ConcoursModel> concoursModels = new Vector<>();
    private ConcoursDao cd;
    private static int ind = 0;
    public static ConcoursModel cmo;
    private ScrollPane sp;

    public void enter(MouseEvent mouseEvent) {


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sp = new ScrollPane();
        vbox.setSpacing(10);

            cd = new ConcoursDao();
            concoursModels = cd.getAllConcours();

            for (ConcoursModel c:concoursModels){
                JFXButton btn = new JFXButton(c.getTitreConcours()+" ,date d'inscription: "+c.getDateDebut()+" ,dernier delai: "+c.getDateFin()+" à "+c.getLieu());

                btn.getStyleClass().add("dark-blue");
                btn.setMinWidth(vbox.getPrefWidth());
                System.out.println(c.getTitreConcours());
                vbox.getChildren().add(btn);
                Stage st = new Stage();
                btn.setOnAction(e -> {
                    cmo = c;
                    final Node source = (Node) e.getSource();
                    final Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                    try {
                        Parent parent = FXMLLoader.load(getClass().getResource("/vue/candidature.fxml"));
                        Scene scene = new Scene(parent);
                        String css  = this.getClass().getResource("/style/candidature.css").toExternalForm();
                        scene.getStylesheets().add(css);
                        st.setScene(scene);
                        st.setResizable(false);
                        st.show();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                });
            }

    }
}
