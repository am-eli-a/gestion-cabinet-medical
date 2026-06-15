package fr.univartois.butinfo.sae.cabinet.controleur;

import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 * Contrôleur de la vue Master-Detail des patients ({@code PatientView.fxml}).
 *
 * <p>Il affiche la liste des patients dans une {@link ListView} et, lorsqu'un
 * patient est sélectionné, présente le détail de ses informations dans la partie
 * droite de la vue.</p>
 */
public class PatientController {

    /** Liste affichant l'ensemble des patients (partie « Master »). */
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

    @FXML
    private void initialize() {
        // Par défaut, la ListView utilise toString() : on n'affiche ici que le nom complet.
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

        // À chaque sélection, on met à jour le panneau de détail.
        patientListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, ancien, nouveau) -> afficherDetails(nouveau));
    }

    /**
     * Associe à la vue la liste observable des patients à afficher.
     *
     * @param patients La liste observable des patients.
     */
    public void setPatients(ObservableList<Patient> patients) {
        patientListView.setItems(patients);
    }

    /**
     * Met à jour le panneau de détail avec les informations du patient sélectionné.
     *
     * @param patient Le patient sélectionné, ou {@code null} si la sélection est vide.
     */
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
