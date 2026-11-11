package fr.univartois.butinfo.sae.cabinet.controleur;

import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class PatientController {

    @FXML
    private ListView<Patient> patientListView;

    @FXML
    private Label nomLabel;

    @FXML
    private Label prenomLabel;

    @FXML
    private Label telephoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label adresseLabel;

    @FXML
    private Label naissanceLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private Label secuLabel;

    @FXML
    private Label tauxLabel;

    private Stage stage;

    private Scene scene;

    @FXML
    private void initialize() {
        patientListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Patient patient, boolean empty) {
                super.updateItem(patient, empty);
                if (empty || patient == null) {
                    setText(null);
                } else {
                    setText(patient.getNomComplet());
                }
            }
        });

        patientListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, ancien, nouveau) -> afficherDetails(nouveau));
    }

    public void setPatients(ObservableList<Patient> patients) {
        patientListView.setItems(patients);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @FXML
    private void supprimerPatient() {
        Patient selectionne = patientListView.getSelectionModel().getSelectedItem();
        if (selectionne != null) {
            patientListView.getItems().remove(selectionne);
        }
    }

    @FXML
    private void ajouterPatient() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/PatientEditView.fxml"));
            Parent root = loader.load();

            PatientEditController editController = loader.getController();
            editController.setPatients(patientListView.getItems());
            editController.setStage(stage);
            editController.setSceneRetour(scene);

            stage.setScene(new Scene(root));
    }

    @FXML
    private void retourMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/GlobalView.fxml"));
            Parent root = loader.load();

            GlobalController controller = loader.getController();
            Scene menuScene = new Scene(root);
            controller.setStage(stage);
            controller.setScene(menuScene);

            stage.setScene(menuScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void afficherDetails(Patient patient) {
        if (patient == null) {
            nomLabel.setText("");
            prenomLabel.setText("");
            telephoneLabel.setText("");
            emailLabel.setText("");
            adresseLabel.setText("");
            naissanceLabel.setText("");
            ageLabel.setText("");
            secuLabel.setText("");
            tauxLabel.setText("");
            return;
        }
        nomLabel.setText(patient.getNom());
        prenomLabel.setText(patient.getPrenom());
        telephoneLabel.setText(patient.getTelephone());
        emailLabel.setText(patient.getEmail());
        adresseLabel.setText(patient.getAdresse());
        naissanceLabel.setText(String.valueOf(patient.getDateNaissance()));
        ageLabel.setText(patient.getAge() + " ans");
        secuLabel.setText(patient.getNumeroSecu());
        tauxLabel.setText((int) (patient.getTauxRemboursement() * 100) + " %");
    }
}
