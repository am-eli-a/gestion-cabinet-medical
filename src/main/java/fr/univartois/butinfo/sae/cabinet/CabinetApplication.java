package fr.univartois.butinfo.sae.cabinet;

/**
 * Ce logiciel est distribué à des fins éducatives.
 *
 * Il est fourni "tel quel", sans garantie d’aucune sorte, explicite
 * ou implicite, notamment sans garantie de qualité marchande, d’adéquation
 * à un usage particulier et d’absence de contrefaçon.
 * En aucun cas, les auteurs ou titulaires du droit d’auteur ne seront
 * responsables de tout dommage, réclamation ou autre responsabilité, que ce
 * soit dans le cadre d’un contrat, d’un délit ou autre, en provenance de,
 * consécutif à ou en relation avec le logiciel ou son utilisation, ou avec
 * d’autres éléments du logiciel.
 *
 * (c) 2022 Romain Wallon - Université d'Artois.
 * Tous droits réservés.
 */

import java.io.IOException;

import fr.univartois.butinfo.sae.cabinet.controleur.GlobalController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class CabinetApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/GlobalView.fxml"));
        Parent viewContent = fxmlLoader.load();
        Scene scene = new Scene(viewContent, 800, 600);
        stage.setScene(scene);

        stage.setTitle("cabinet FX");

        GlobalController controller = fxmlLoader.getController();
        controller.setStage(stage);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
