package fr.univartois.butinfo.sae.cabinet.controleur;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class MedecinController {

    private Stage stage;

    private Scene scene;

    @FXML
    private Label email;

    @FXML
    private ListView<Medecin> medecinList;

    @FXML
    private Label nom;

    @FXML
    private Label ordre;

    @FXML
    private Label prenom;

    @FXML
    private Label specialite;

    @FXML
    private Label tarif;

    @FXML
    private Label telephone;

    private GestionMedecins medecins;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @FXML
    void addMedecin(ActionEvent event) {

    }

    @FXML
    void removeMedecin(ActionEvent event) {

    }

    public void bind(GestionMedecins medecins) {
        this.medecins = medecins;


        medecinList.setItems(medecins.getMedecins());

        medecinList.getSelectionModel().selectedItemProperty().addListener((o, p, n) -> {
            nom.setText("nom : "+n.getNom());
            prenom.setText("prenom : "+n.getPrenom());
            email.setText("email : "+n.getEmail());
            telephone.setText("telephone : "+n.getTelephone());
            ordre.setText("ordre : "+n.getNumeroOrdre());
            specialite.setText("specialite : "+n.getSpecialite());
            tarif.setText("tarif horaire : "+n.getTarifHoraire());
        });
    }

}
