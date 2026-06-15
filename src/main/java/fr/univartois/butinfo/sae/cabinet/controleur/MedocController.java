package fr.univartois.butinfo.sae.cabinet.controleur;

import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class MedocController {

    @FXML
    private ListView<Medicament> medocListView;

    @FXML
    private Label nomLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label prixLabel;

    @FXML
    private void initialize() {
        medocListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Medicament medoc, boolean empty) {
                super.updateItem(medoc, empty);
                if (empty || medoc == null) {
                    setText(null);
                } else {
                    setText(medoc.getNom());
                }
            }
        });

        medocListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, ancien, nouveau) -> afficherDetails(nouveau));
    }

    public void setMedocs(ObservableList<Medicament> medocs) {
        medocListView.setItems(medocs);
    }

    private void afficherDetails(Medicament medoc) {
        if (medoc == null) {
            nomLabel.setText("");
            descriptionLabel.setText("");
            prixLabel.setText("");
            return;
        }
        nomLabel.setText(medoc.getNom());
        descriptionLabel.setText(medoc.getDescription());
        prixLabel.setText(medoc.getPrix() + " €");
    }

    @FXML
    private void ajouter(ActionEvent actionEvent) throws IOException {
    }

    public void supprMedoc(ActionEvent actionEvent) {
    }
}
