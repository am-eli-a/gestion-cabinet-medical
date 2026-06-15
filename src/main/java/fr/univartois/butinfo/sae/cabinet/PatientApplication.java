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

/**
 * Classe principale JavaFX intermédiaire permettant de tester la vue
 * Master-Detail des patients de façon isolée.
 *
 * <p>Elle remplit une {@link GestionPatients} avec quelques patients d'exemple,
 * charge la vue {@code PatientView.fxml} et associe au contrôleur la liste
 * observable des patients à afficher.</p>
 */
public class PatientApplication extends Application {

    /**
     * Point d'entrée JavaFX : construit et affiche la fenêtre.
     *
     * @param stage La fenêtre principale fournie par JavaFX.
     * @throws IOException si le fichier FXML ne peut pas être chargé.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // 1. Données d'exemple.
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

        // 2. Construction de la liste observable attendue par la ListView.
        ObservableList<Patient> patients = FXCollections.observableArrayList(gestionPatients.listerTousLesPatients());

        // 3. Chargement de la vue et récupération du contrôleur.
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fr/univartois/butinfo/sae/cabinet/vue/PatientView.fxml"));
        Scene scene = new Scene(loader.load());

        // 4. Association de la liste au contrôleur.
        PatientController controller = loader.getController();
        controller.setPatients(patients);

        // 5. Affichage de la fenêtre.
        stage.setTitle("Cabinet médical — Liste des patients");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Lance l'application JavaFX.
     *
     * @param args Les arguments de la ligne de commande (non utilisés).
     */
    public static void main(String[] args) {
        launch(args);
    }
}
