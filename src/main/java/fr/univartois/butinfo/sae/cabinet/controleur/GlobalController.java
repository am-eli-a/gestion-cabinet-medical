package fr.univartois.butinfo.sae.cabinet.controleur;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import fr.univartois.butinfo.sae.cabinet.modele.Ordonnance;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedicaments;
import fr.univartois.butinfo.sae.cabinet.service.GestionOrdonnances;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class GlobalController {

    private Stage stage;
    private Scene scene;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @FXML
    private Button listeMedecin;

    @FXML
    private Button listeMedicament;

    @FXML
    private Button listeOrdonnace;

    @FXML
    private Button listePatient;

    // ============================================================
    // INSTANCIATIONS GLOBALES (Services et Objets de Test)
    // ============================================================
    private final GestionMedicaments gestionMedocs = new GestionMedicaments();
    private final GestionMedecins gestionMedecins = new GestionMedecins();
    private final GestionPatients gestionPatients = new GestionPatients();
    private final GestionOrdonnances gestionOrdonnances = new GestionOrdonnances();

    // Déclaration des objets de test globaux
    private Medecin medecin1;
    private Medecin medecin2;
    private Patient patient1;
    private Patient patient2;
    private Patient patient3;
    private Medicament medicament1;

    @FXML
    void initialize() {
        // 1. Initialisation et ajout des médicaments de test
        medicament1 = new Medicament("Paracétamol", "test description1 et test description2", 2.50);
        gestionMedocs.ajouterMedicament(medicament1);
        gestionMedocs.ajouterMedicament(new Medicament("Ibuprofène", "Anti-inflammatoire", 4.10));
        gestionMedocs.ajouterMedicament(new Medicament("Amoxicilline", "Antibiotique", 6.50));

        // 2. Initialisation et ajout des médecins de test
        medecin1 = new Medecin("Dupont", "Jean", "0312345678", "j.dupont@cabinet.fr", "ORD-001", Specialite.GENERALISTE, 30.0);
        medecin2 = new Medecin("Martin", "Sophie", "0398765432", "s.martin@cabinet.fr", "ORD-002", Specialite.PEDIATRE, 45.0);
        gestionMedecins.ajouterMedecin(medecin1);
        gestionMedecins.ajouterMedecin(medecin2);

        // 3. Initialisation et ajout des patients de test
        patient1 = new Patient("Curie", "Marie", "0102030405", "m.curie@cabinet.fr", "12 rue des Sciences", LocalDate.of(1990, 5, 12), "190057512345678", 0.75);
        patient2 = new Patient("Pasteur", "Louis", "0203040506", "l.pasteur@cabinet.fr", "5 avenue de la Recherche", LocalDate.of(1985, 11, 3), "185117598765432", 0.80);
        patient3 = new Patient("Dupond", "Alice", "0304050607", "a.dupond@cabinet.fr", "8 boulevard Central", LocalDate.of(2001, 2, 27), "201027511223344");
        gestionPatients.ajouterPatient(patient1);
        gestionPatients.ajouterPatient(patient2);
        gestionPatients.ajouterPatient(patient3);

        // ============================================================
        // AJOUT DE L'ORDONNANCE VIA LES VRAIES MÉTHODES DE TON SERVICE
        // ============================================================
        // A. On utilise creerOrdonnance() de GestionOrdonnances qui fabrique et enregistre l'ordonnance d'un coup
        Ordonnance ordonnanceTest = gestionOrdonnances.creerOrdonnance(
                medecin1,
                patient1,
                LocalDate.now().plusMonths(3),
                "2 fois par jour"
        );

        // B. On prépare la ligne de prescription avec ton médicament
        fr.univartois.butinfo.sae.cabinet.modele.LignePrescription prescriptionTest =
                new fr.univartois.butinfo.sae.cabinet.modele.LignePrescription(medicament1, 4, "par voie orale", 7, "Prendre pendant les repas");

        // C. On l'ajoute sur l'ordonnance créée
        ordonnanceTest.ajouterPrescription(prescriptionTest);
    }

    // ============================================================
    // MÉTHODES DE NAVIGATION DES BOUTONS
    // ============================================================

    @FXML
    void onMedecin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/MedecinView.fxml"));
        Parent viewContent = fxmlLoader.load();

        Scene scene = new Scene(viewContent, 800, 600);

        MedecinController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setScene(scene);
        controller.bind(gestionMedecins);

        stage.setTitle("Cabinet médical — Liste des médecins");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onMedicament(ActionEvent event) throws IOException {
        ObservableList<Medicament> medocs = FXCollections.observableArrayList(gestionMedocs.listerTousLesMedicaments());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/MedocView.fxml"));
        Scene scene = new Scene(loader.load());

        MedocController controller = loader.getController();
        controller.setMedocs(medocs);

        stage.setTitle("Cabinet médical — Liste des médicaments");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onOrdonnance(ActionEvent event) throws IOException {
        // Récupère proprement la liste depuis le service global
        ObservableList<Ordonnance> ordonnances = FXCollections.observableArrayList(gestionOrdonnances.listerToutesLesOrdonnances());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/OrdonnanceView.fxml"));
        Scene scene = new Scene(loader.load());

        OrdonnanceController controller = loader.getController();
        controller.setOrdonnances(ordonnances);

        stage.setTitle("Cabinet médical — Liste des ordonnances");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onPatient(ActionEvent event) throws IOException {
        ObservableList<Patient> patients = FXCollections.observableArrayList(gestionPatients.listerTousLesPatients());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/PatientView.fxml"));
        Scene scene = new Scene(loader.load());

        PatientController controller = loader.getController();
        controller.setPatients(patients);
        controller.setStage(stage);
        controller.setScene(scene);

        stage.setTitle("Cabinet médical — Liste des patients");
        stage.setScene(scene);
        stage.show();
    }
}