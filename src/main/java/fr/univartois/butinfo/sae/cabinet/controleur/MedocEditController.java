package fr.univartois.butinfo.sae.cabinet.controleur;

import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MedocEditController {
    @FXML
    private TextField nomField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField prixField;

    private ObservableList<Medicament> medocs;

    private Stage stage;

    private Scene sceneRetour;

    public void setMedocs(ObservableList<Medicament> medocs) {
        this.medocs = medocs;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setSceneRetour(Scene sceneRetour) {
        this.sceneRetour = sceneRetour;
    }

    @FXML
    private void valider() {
        double prix = 0.0;
        if (!prixField.getText().isBlank()) {
            try {
                prix = Double.parseDouble(prixField.getText().replace(",", "."));
            } catch (NumberFormatException e) {
                prix = 0.0;
            }
        }

        Medicament medoc = new Medicament(
                nomField.getText(),
                descriptionField.getText(),
                prix);

        medocs.add(medoc);
        stage.setScene(sceneRetour);
    }

    @FXML
    private void annuler() {
        stage.setScene(sceneRetour);
    }
}
