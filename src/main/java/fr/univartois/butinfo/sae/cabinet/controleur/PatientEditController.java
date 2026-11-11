package fr.univartois.butinfo.sae.cabinet.controleur;

import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PatientEditController {

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField telephoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField adresseField;

    @FXML
    private DatePicker naissancePicker;

    @FXML
    private TextField secuField;

    @FXML
    private TextField tauxField;

    private ObservableList<Patient> patients;

    private Stage stage;

    private Scene sceneRetour;

    public void setPatients(ObservableList<Patient> patients) {
        this.patients = patients;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setSceneRetour(Scene sceneRetour) {
        this.sceneRetour = sceneRetour;
    }

    @FXML
    private void valider() {
        double taux = 0.75;
        if (!tauxField.getText().isBlank()) {
            try {
                taux = Double.parseDouble(tauxField.getText().replace(",", "."));
            } catch (NumberFormatException e) {
                taux = 0.75;
            }
        }

        Patient patient = new Patient(
                nomField.getText(),
                prenomField.getText(),
                telephoneField.getText(),
                emailField.getText(),
                adresseField.getText(),
                naissancePicker.getValue(),
                secuField.getText(),
                taux);

        patients.add(patient);
        stage.setScene(sceneRetour);
    }

    @FXML
    private void annuler() {
        stage.setScene(sceneRetour);
    }
}
