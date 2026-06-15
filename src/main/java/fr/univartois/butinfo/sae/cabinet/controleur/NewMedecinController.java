package fr.univartois.butinfo.sae.cabinet.controleur;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewMedecinController {

    private Stage stage;

    private Scene mainScene;

    private GestionMedecins medecins;

    @FXML
    private TextField email;

    @FXML
    private TextField nom;

    @FXML
    private TextField ordre;

    @FXML
    private TextField prenom;

    @FXML
    private ComboBox<Specialite> specialite;

    @FXML
    private TextField tarif;

    @FXML
    private TextField telephone;

    @FXML
    void initialize() {
        specialite.setItems(FXCollections.observableArrayList(Specialite.values()));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void bind(GestionMedecins medecins) {
        this.medecins = medecins;
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    @FXML
    void cancel() {
        stage.setScene(mainScene);
    }

    @FXML
    void validate() {
        Medecin medecin = new Medecin(nom.getText(),prenom.getText(),telephone.getText(),email.getText(),ordre.getText(),specialite.getValue(),Double.parseDouble(tarif.getText()));
        medecins.ajouterMedecin(medecin);
        stage.setScene(mainScene);
    }

}
