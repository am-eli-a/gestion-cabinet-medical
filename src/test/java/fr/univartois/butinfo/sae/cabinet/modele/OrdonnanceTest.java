package fr.univartois.butinfo.sae.cabinet.modele;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;

/**
 * Classe de tests unitaires pour la classe {@link Ordonnance}.
 * Conforme aux consignes : aucun mock, tests indépendants, couverture par méthode.
 */
class OrdonnanceTest {

    private Medecin medecin;
    private Patient patient;

    @BeforeEach
    void setUp() {
        medecin = new Medecin("Dupont", "Jean", "0312345678", "j.dupont@cabinet.fr", "ORD-001", Specialite.GENERALISTE, 35.50);

        patient = new Patient("Martin", "Alice", "0612345678", "a.martin@outlook.fr",
                "12 Rue de l'Université, 62300 Lens", LocalDate.of(1995, 5, 15), "295056212345678", 0.60);
    }

    @Test
    void testConstructeurComplet() {
        LocalDate dateValidite = LocalDate.now().plusMonths(1);
        String remarques = "Prendre au milieu des repas.";

        Ordonnance ordonnance = new Ordonnance(medecin, patient, dateValidite, remarques);

        assertThat(ordonnance.getId()).isPositive();
        assertThat(ordonnance.getMedecin()).isEqualTo(medecin);
        assertThat(ordonnance.getPatient()).isEqualTo(patient);
        assertThat(ordonnance.getDateEmission()).isEqualTo(LocalDate.now());
        assertThat(ordonnance.getDateValidite()).isEqualTo(dateValidite);
        assertThat(ordonnance.getRemarques()).isEqualTo(remarques);
        assertThat(ordonnance.getPrescriptions()).isEmpty();
    }

    @Test
    void testConstructeurSimplifie() {
        Ordonnance ordonnance = new Ordonnance(medecin, patient);

        assertThat(ordonnance.getId()).isPositive();
        assertThat(ordonnance.getDateValidite()).isEqualTo(LocalDate.now().plusMonths(3));
        assertThat(ordonnance.getRemarques()).isEmpty();
    }

    @Test
    void testSetDateValidite() {
        Ordonnance ordonnance = new Ordonnance(medecin, patient);
        LocalDate nouvelleDate = LocalDate.now().plusDays(15);

        ordonnance.setDateValidite(nouvelleDate);
        assertThat(ordonnance.getDateValidite()).isEqualTo(nouvelleDate);
    }

    @Test
    void testSetRemarques() {
        Ordonnance ordonnance = new Ordonnance(medecin, patient);
        String nouvelleRemarque = "Attention aux effets de somnolence.";

        ordonnance.setRemarques(nouvelleRemarque);
        assertThat(ordonnance.getRemarques()).isEqualTo(nouvelleRemarque);
    }

    @Test
    void testAjouterPrescription() {
        Ordonnance ordonnance = new Ordonnance(medecin, patient);
        Medicament medicament = new Medicament("Doliprane 1000", "Paracétamol antalgique", 2.50);
        LignePrescription ligne = new LignePrescription(medicament, 2, "1 comprimé toutes les 6 heures", 5, "À prendre avec un grand verre d'eau");

        ordonnance.ajouterPrescription(ligne);

        assertThat(ordonnance.getPrescriptions()).hasSize(1).contains(ligne);
    }

    @Test
    void testSupprimerPrescription() {
        Ordonnance ordonnance = new Ordonnance(medecin, patient);
        Medicament med1 = new Medicament("Doliprane", "Paracétamol", 2.50);
        Medicament med2 = new Medicament("Ibuprofène", "Anti-inflammatoire", 4.00);

        LignePrescription ligne1 = new LignePrescription(med1, 2, "1 comprimé matin et soir", 5, "Pendant le repas");
        ordonnance.ajouterPrescription(ligne1);

        boolean supprInexistant = ordonnance.supprimerPrescription(med2);
        assertThat(supprInexistant).isFalse();
        assertThat(ordonnance.getPrescriptions()).hasSize(1);

        boolean supprExistant = ordonnance.supprimerPrescription(med1);
        assertThat(supprExistant).isTrue();
        assertThat(ordonnance.getPrescriptions()).isEmpty();
    }

    @Test
    void testGetPrescriptions() {
        Ordonnance ordonnance = new Ordonnance(medecin, patient);
        Medicament med = new Medicament("Doliprane", "Paracétamol", 2.50);
        LignePrescription ligne = new LignePrescription(med, "1 comprimé au coucher", 7);
        ordonnance.ajouterPrescription(ligne);

        List<LignePrescription> listeRetournee = ordonnance.getPrescriptions();

        listeRetournee.clear();
        assertThat(ordonnance.getPrescriptions()).hasSize(1).contains(ligne);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 10}) // Aujourd'hui, demain, dans 10 jours
    void testEstValideCasVrais(long joursAjoutes) {
        LocalDate dateValidite = LocalDate.now().plusDays(joursAjoutes);
        Ordonnance ordonnance = new Ordonnance(medecin, patient, dateValidite, "");

        assertThat(ordonnance.estValide()).isTrue();
    }

    @Test
    void testEstValideCasFaux() {
        LocalDate dateValidite = LocalDate.now().minusDays(1);
        Ordonnance ordonnance = new Ordonnance(medecin, patient, dateValidite, "");

        assertThat(ordonnance.estValide()).isFalse();
    }

    @Test
    void testGetPrixTotal() {
        Patient patientRembourse = new Patient("Martin", "Alice", "0612345678", "a.martin@outlook.fr",
                "12 Rue de l'Université, 62300 Lens", LocalDate.of(1995, 5, 15), "295056212345678", 0.60);
        Ordonnance ordonnance = new Ordonnance(medecin, patientRembourse);

        Medicament med1 = new Medicament("Doliprane", "Boite de 8", 10.00);
        Medicament med2 = new Medicament("Sirop", "Flacon 150ml", 5.00);

        ordonnance.ajouterPrescription(new LignePrescription(med1, 2, "1 comprimé matin et soir", 4, "Aucune"));
        ordonnance.ajouterPrescription(new LignePrescription(med2, 3, "1 cuillère au repas", 5, "Aucune"));

        assertThat(ordonnance.getPrixTotal()).isCloseTo(14.00, within(0.001));
    }

    @Test
    void testEqualsEtHashCode() {
        Ordonnance ordonnance1 = new Ordonnance(medecin, patient);
        Ordonnance ordonnance2 = new Ordonnance(medecin, patient);

        assertThat(ordonnance1).isNotEqualTo(ordonnance2);
        assertThat(ordonnance1.hashCode()).isNotEqualTo(ordonnance2.hashCode());
        assertThat(ordonnance1).isEqualTo(ordonnance1);
        assertThat(ordonnance1.hashCode()).isEqualTo(ordonnance1.hashCode());
        assertThat(ordonnance1).isNotEqualTo(null);
        assertThat(ordonnance1).isNotEqualTo("Une Chaine");
    }

    @Test
    void testToString() {
        Ordonnance ordonnance = new Ordonnance(medecin, patient, LocalDate.now(), "Prendre le soir");
        String résultat = ordonnance.toString();

        assertThat(résultat)
                .contains("=== ORDONNANCE N°")
                .contains("Dr Dupont Jean")
                .contains("Martin Alice")
                .contains("Prendre le soir");
    }
}