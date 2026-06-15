package fr.univartois.butinfo.sae.cabinet.controleur;

import fr.univartois.butinfo.sae.cabinet.modele.LignePrescription;
import fr.univartois.butinfo.sae.cabinet.modele.Ordonnance;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.service.GestionOrdonnances;
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

public class OrdonnanceController {

    @FXML
    private ListView<Ordonnance> ordonnanceListView;

    @FXML
    private Label ordonnanceId;

    @FXML
    private Label medecinLabel;

    @FXML
    private Label patientLabel;

    @FXML
    private Label dateEmission;

    @FXML
    private Label dateValidite;

    @FXML
    private Label prescriptions;

    @FXML
    private Label remarques;

    private Stage stage;

    private Scene scene;

    private ObservableList<Medecin> medecins;

    private ObservableList<Patient> patients;


    public void setOrdonnances(ObservableList<Ordonnance> ordonnances){
        ordonnanceListView.setItems(ordonnances);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setMedecins(ObservableList<Medecin> medecins) {
        this.medecins = medecins;
    }

    public void setPatients(ObservableList<Patient> patients) {
        this.patients = patients;
    }

    @FXML
    private void supprimerOrdonnance() {
        Ordonnance selectionnee = ordonnanceListView.getSelectionModel().getSelectedItem();
        if (selectionnee != null) {
            ordonnanceListView.getItems().remove(selectionnee);
        }
    }

    @FXML
    private void ajouterOrdonnance() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/OrdonnanceEditView.fxml"));
            Parent root = loader.load();

            OrdonnanceEditController editController = loader.getController();
            editController.setOrdonnances(ordonnanceListView.getItems());
            editController.setMedecins(medecins);
            editController.setPatients(patients);
            editController.setStage(stage);
            editController.setSceneRetour(scene);

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void initialize() {
        ordonnanceListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Ordonnance ordonnances, boolean empty) {
                super.updateItem(ordonnances, empty);
                if (empty || ordonnances == null) {
                    setText(null);
                }
                else {
                    setText("Ordonnance n°" + ordonnances.getId()
                            + " — " + ordonnances.getPatient().getNomComplet()
                            + " (Dr " + ordonnances.getMedecin().getNomComplet() + ")");
                }
            }
        });

        ordonnanceListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, ancien, nouveau) -> afficherDetails(nouveau));
    }

    public void afficherDetails(Ordonnance ordonnance){
        if(ordonnance==null){
            ordonnanceId.setText("");
            medecinLabel.setText("");
            patientLabel.setText("");
            dateEmission.setText("");
            dateValidite.setText("");
            prescriptions.setText("");
            remarques.setText("");
            return;
        }
        String Id = Integer.toString(ordonnance.getId());
        String dateE = String.valueOf(ordonnance.getDateEmission());
        String dateV = String.valueOf(ordonnance.getDateValidite());
        ordonnanceId.setText(Id);
        medecinLabel.setText(ordonnance.getMedecin().getNom());
        patientLabel.setText(ordonnance.getPatient().getNom());
        dateEmission.setText(dateE);
        dateValidite.setText(dateV);

        StringBuilder sb = new StringBuilder();
        for (LignePrescription ligne : ordonnance.getPrescriptions()) {
            sb.append(ligne.getMedicament().getNom())
                    .append(" x").append(ligne.getQuantite())
                    .append(" — ").append(ligne.getPosologie())
                    .append(" (").append(ligne.getDureeTraitement()).append(" jours)")
                    .append("\n");
        }

        prescriptions.setText(sb.toString());
        remarques.setText(ordonnance.getRemarques());
    }
}
