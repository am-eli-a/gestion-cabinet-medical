package fr.univartois.butinfo.sae.cabinet.modele;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Classe de tests unitaires pour la classe Patient.
 * Conforme aux exigences de la SAÉ S2.01 (JUnit 5 + AssertJ).
 */
class PatientTest {

    private Patient patientTest;
    private LocalDate dateNaissance;

    @BeforeEach
    void setUp() {
        // Initialisation d'un patient de test avant chaque méthode de test
        dateNaissance = LocalDate.now().minusYears(20); // Le patient a exactement 20 ans aujourd'hui
        patientTest = new Patient(
                "Leblanc",
                "Paul",
                "0612345678",
                "p.leblanc@mail.fr",
                "12 rue des Lilas, Arras",
                dateNaissance,
                "185036562012345",
                0.50
        );
    }

    @Test
    void testConstructeurCompletEtGetters() {
        // Vérification du bon stockage des données par le constructeur complet
        assertThat(patientTest.getNom()).isEqualTo("Leblanc");
        assertThat(patientTest.getPrenom()).isEqualTo("Paul");
        assertThat(patientTest.getTelephone()).isEqualTo("0612345678");
        assertThat(patientTest.getEmail()).isEqualTo("p.leblanc@mail.fr");
        assertThat(patientTest.getAdresse()).isEqualTo("12 rue des Lilas, Arras");
        assertThat(patientTest.getDateNaissance()).isEqualTo(dateNaissance);
        assertThat(patientTest.getNumeroSecu()).isEqualTo("185036562012345");
        assertThat(patientTest.getTauxRemboursement()).isEqualTo(0.50);
    }

    @Test
    void testConstructeurSimplifie() {
        // Vérification du constructeur simplifié (le taux par défaut doit être à 75% -> 0.75)
        Patient patientSimplifie = new Patient(
                "Moreau",
                "Claire",
                "0687654321",
                "c.moreau@mail.fr",
                "8 avenue Foch, Lens",
                LocalDate.of(1995, 7, 15),
                "295076210054321"
        );

        assertThat(patientSimplifie.getTauxRemboursement()).isEqualTo(0.75);
    }

    @Test
    void testSetters() {
        // Modification des attributs modifiables
        patientTest.setAdresse("20 rue des Roses, Arras");
        patientTest.setTauxRemboursement(0.80);
        LocalDate nouvelleDate = LocalDate.of(1985, 3, 22);
        patientTest.setDateNaissance(nouvelleDate);

        // Validation des modifications
        assertThat(patientTest.getAdresse()).isEqualTo("20 rue des Roses, Arras");
        assertThat(patientTest.getTauxRemboursement()).isEqualTo(0.80);
        assertThat(patientTest.getDateNaissance()).isEqualTo(nouvelleDate);
    }

    @Test
    void testGetAge() {
        // Le patient ayant été initialisé à l'année courante moins 20 ans, il doit avoir 20 ans
        assertThat(patientTest.getAge()).isEqualTo(20);
    }

    @Test
    void testEqualsIdentiques() {
        // Deux instances différentes représentant le même patient (même numéro de sécu)
        Patient memePatient = new Patient(
                "Leblanc",
                "Paul",
                "0612345678",
                "p.leblanc@mail.fr",
                "12 rue des Lilas, Arras",
                dateNaissance,
                "185036562012345",
                0.50
        );

        assertThat(patientTest).isEqualTo(memePatient);
        assertThat(patientTest.hashCode()).isEqualTo(memePatient.hashCode());
    }

    @Test
    void testEqualsDifferents() {
        // Un patient différent (autre numéro de sécurité sociale)
        Patient autrePatient = new Patient(
                "Leblanc",
                "Paul",
                "0612345678",
                "p.leblanc@mail.fr",
                "12 rue des Lilas, Arras",
                dateNaissance,
                "999999999999999",
                0.50
        );

        assertThat(patientTest).isNotEqualTo(autrePatient);
    }

    @Test
    void testToString() {
        String chaine = patientTest.toString();
        // Vérifie que les informations critiques apparaissent bien dans la chaîne textuelle
        assertThat(chaine)
                .contains("Leblanc")
                .contains("Paul")
                .contains("185036562012345")
                .contains("0.5");
    }
}