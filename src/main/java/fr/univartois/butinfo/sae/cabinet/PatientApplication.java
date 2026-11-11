package fr.univartois.butinfo.sae.cabinet;

import fr.univartois.butinfo.sae.cabinet.controleur.PatientController;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import java.io.IOException;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PatientApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        GestionPatients gestionPatients = new GestionPatients();
        gestionPatients.ajouterPatient(new Patient("Curie", "Marie", "0102030405",
                "m.curie@cabinet.fr", "12 rue des Sciences", LocalDate.of(1990, 5, 12),
                "190057512345678", 0.75));
        gestionPatients.ajouterPatient(new Patient("Pasteur", "Louis", "0203040506",
                "l.pasteur@cabinet.fr", "5 avenue de la Recherche", LocalDate.of(1985, 11, 3),
                "185117598765432", 0.80));
        gestionPatients.ajouterPatient(new Patient("Dupond", "Alice", "0304050607",
                "a.dupond@cabinet.fr", "8 boulevard Central", LocalDate.of(2001, 2, 27),
                "201027511223344"));

        ObservableList<Patient> patients = FXCollections.observableArrayList(gestionPatients.listerTousLesPatients());

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/vue/PatientView.fxml"));
        Scene scene = new Scene(loader.load());

        PatientController controller = loader.getController();
        controller.setPatients(patients);
        controller.setStage(stage);
        controller.setScene(scene);

        stage.setTitle("Cabinet médical — Liste des patients");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
