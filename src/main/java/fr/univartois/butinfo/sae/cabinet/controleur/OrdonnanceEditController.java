package fr.univartois.butinfo.sae.cabinet.controleur;

import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Ordonnance;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class OrdonnanceEditController {

    @FXML
    private ComboBox<Medecin> medecinCombo;

    @FXML
    private ComboBox<Patient> patientCombo;

    @FXML
    private DatePicker validitePicker;

    @FXML
    private TextField remarquesField;

    private ObservableList<Ordonnance> ordonnances;

    private Stage stage;

    private Scene sceneRetour;

    @FXML
    private void initialize() {
        medecinCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Medecin medecin) {
                return medecin == null ? "" : medecin.getNomComplet();
            }

            @Override
            public Medecin fromString(String texte) {
                return null;
            }
        });

        patientCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Patient patient) {
                return patient == null ? "" : patient.getNomComplet();
            }

            @Override
            public Patient fromString(String texte) {
                return null;
            }
        });
    }

    public void setOrdonnances(ObservableList<Ordonnance> ordonnances) {
        this.ordonnances = ordonnances;
    }

    public void setMedecins(ObservableList<Medecin> medecins) {
        medecinCombo.setItems(medecins);
    }

    public void setPatients(ObservableList<Patient> patients) {
        patientCombo.setItems(patients);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setSceneRetour(Scene sceneRetour) {
        this.sceneRetour = sceneRetour;
    }

    @FXML
    private void valider() {
        Medecin medecin = medecinCombo.getValue();
        Patient patient = patientCombo.getValue();
        if (medecin == null || patient == null) {
            return;
        }

        LocalDate validite = validitePicker.getValue();
        if (validite == null) {
            validite = LocalDate.now().plusMonths(3);
        }

        Ordonnance ordonnance = new Ordonnance(medecin, patient, validite, remarquesField.getText());
        ordonnances.add(ordonnance);
        stage.setScene(sceneRetour);
    }

    @FXML
    private void annuler() {
        stage.setScene(sceneRetour);
    }
}
