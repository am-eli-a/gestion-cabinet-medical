package fr.univartois.butinfo.sae.cabinet.controleur;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

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
    void addMedecin() throws IOException {
        // Il faut d'abord récupérer la description de la nouvelle vue (au format FXML).
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/NewMedecinView.fxml"));
        Parent viewContent = fxmlLoader.load();

        // Ensuite, on la place dans une nouvelle Scene...
        Scene addPokemonScene = new Scene(viewContent, 800, 600);
        // que l'on place elle-même dans la fenêtre pour remplacer la scène courante.
        stage.setScene(addPokemonScene);

        // On lie le modèle au nouveau contrôleur.
        NewMedecinController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setMainScene(scene);
        controller.bind(medecins);
    }

    @FXML
    void removeMedecin(ActionEvent event) {
        medecins.supprimerMedecin(medecinList.getSelectionModel().getSelectedItem().getId());
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
