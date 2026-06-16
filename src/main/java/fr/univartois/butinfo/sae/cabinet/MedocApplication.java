package fr.univartois.butinfo.sae.cabinet;

import fr.univartois.butinfo.sae.cabinet.controleur.MedocController;
import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedicaments;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MedocApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        GestionMedicaments gestionMedocs = new GestionMedicaments();
        gestionMedocs.ajouterMedicament(new Medicament("Sertraline", "Anti-dépresseur", 5));
        gestionMedocs.ajouterMedicament(new Medicament("Aspirine", "Anti-douleur", 2));
        gestionMedocs.ajouterMedicament(new Medicament("Paracétamole", "Anti-douleur", 1.8));

        ObservableList<Medicament> medocs = FXCollections.observableArrayList(gestionMedocs.listerTousLesMedicaments());

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/vue/MedocView.fxml"));
        Scene scene = new Scene(loader.load());

        MedocController controller = loader.getController();
        controller.setMedocs(medocs);

        stage.setTitle("Cabinet médical — Liste des médicaments");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
