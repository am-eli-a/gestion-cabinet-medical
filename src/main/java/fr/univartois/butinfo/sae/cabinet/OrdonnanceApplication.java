package fr.univartois.butinfo.sae.cabinet;

import fr.univartois.butinfo.sae.cabinet.controleur.OrdonnanceController;
import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import fr.univartois.butinfo.sae.cabinet.modele.Ordonnance;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.service.GestionOrdonnances;
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
        Medecin medecin = new Medecin("Dupont", "Jean", "0312345678", "j.dupont@cabinet.fr",
                "ORD-001", Specialite.GENERALISTE);
        Patient patient = new Patient("Curie", "Marie", "0102030405", "m.curie@cabinet.fr",
                "12 rue des Sciences", LocalDate.of(1990, 5, 12), "190057512345678");
        Medicament medicament = new Medicament("Paracétamol", "Antidouleur", 2.50);

        GestionOrdonnances gestionOrdonnances = new GestionOrdonnances();
        Ordonnance ordo = gestionOrdonnances.creerOrdonnance(medecin, patient,
                LocalDate.now().plusMonths(3), "Repos conseillé");
        gestionOrdonnances.ajouterPrescription(ordo.getId(), medicament, 4,
                "par voie orale", 7, "2 fois par jour");

        ObservableList<Ordonnance> ordonnances =
                FXCollections.observableArrayList(gestionOrdonnances.listerToutesLesOrdonnances());
        ObservableList<Medecin> medecins = FXCollections.observableArrayList(medecin);
        ObservableList<Patient> patients = FXCollections.observableArrayList(patient);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/OrdonnanceView.fxml"));
        Scene scene = new Scene(loader.load());

        OrdonnanceController controller = loader.getController();
        controller.setOrdonnances(ordonnances);
        controller.setMedecins(medecins);
        controller.setPatients(patients);
        controller.setStage(stage);
        controller.setScene(scene);

        stage.setTitle("Cabinet médical — Liste des ordonnances");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
