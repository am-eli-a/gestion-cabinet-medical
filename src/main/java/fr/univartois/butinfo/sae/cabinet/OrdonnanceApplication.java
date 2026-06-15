package fr.univartois.butinfo.sae.cabinet;

import fr.univartois.butinfo.sae.cabinet.controleur.OrdonnanceController;
import fr.univartois.butinfo.sae.cabinet.controleur.PatientController;
import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import fr.univartois.butinfo.sae.cabinet.modele.Ordonnance;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.service.GestionOrdonnances;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import java.io.IOException;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OrdonnanceApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Medicament medicament = new Medicament("Paracétamol", "test description1 et test description2", 2.50);

        GestionOrdonnances gestionOrdonnances = new GestionOrdonnances();
        gestionOrdonnances.ajouterPrescription(new Ordonnance(1, medicament, 4, "par voie orale", 7, "2 fois par jours"));


        ObservableList<Ordonnance> ordonnances = FXCollections.observableArrayList(gestionOrdonnances.listerToutesLesOrdonnances());

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/vue/OrdonnanceView.fxml"));
        Scene scene = new Scene(loader.load());

        OrdonnanceController controller = loader.getController();
        controller.setOrdonnances(ordonnances);

        stage.setTitle("Cabinet médical — Liste des ordonnances");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
