package fr.univartois.butinfo.sae.cabinet.modele;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.when;

import fr.univartois.butinfo.sae.cabinet.enumeration.StatutRendezVous;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Classe de tests unitaires pour la classe {@link RendezVous}.
 *
 * <p>La classe {@code RendezVous} dépend de {@link Medecin} et {@link Patient}
 * (notamment pour le calcul du prix de la consultation) : ces collaborateurs
 * sont donc simulés à l'aide de mocks Mockito afin que chaque test reste
 * indépendant et autonome.</p>
 */
@ExtendWith(MockitoExtension.class)
class RendezVousTest {

    @Mock
    private Medecin medecin;

    @Mock
    private Patient patient;

    @Test
    void testConstructeurComplet() {
        LocalDateTime dateRdv = LocalDateTime.of(2025, 6, 10, 14, 0);

        RendezVous rdv = new RendezVous(patient, medecin, dateRdv, 45, "Consultation");

        assertThat(rdv.getPatient()).isEqualTo(patient);
        assertThat(rdv.getMedecin()).isEqualTo(medecin);
        assertThat(rdv.getDateHeure()).isEqualTo(dateRdv);
        assertThat(rdv.getDureeMinutes()).isEqualTo(45);
        assertThat(rdv.getMotif()).isEqualTo("Consultation");
        // À la création, le statut doit être PLANIFIE.
        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.PLANIFIE);
    }

    @Test
    void testConstructeurDureeParDefaut() {
        LocalDateTime dateRdv = LocalDateTime.of(2025, 6, 10, 9, 0);

        RendezVous rdv = new RendezVous(patient, medecin, dateRdv, "Suivi");

        assertThat(rdv.getDureeMinutes()).isEqualTo(30);
        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.PLANIFIE);
    }

    @Test
    void testPrixConsultationAvecMocks() {
        when(medecin.getTarifHoraire()).thenReturn(60.0);
        when(patient.getTauxRemboursement()).thenReturn(0.70);

        LocalDateTime dateRdv = LocalDateTime.of(2025, 6, 10, 14, 0);
        RendezVous rdv = new RendezVous(patient, medecin, dateRdv, 30, "Consultation test");

        double prix = rdv.prixConsultation();

        assertThat(prix).isCloseTo(9.0, within(0.01));
    }

    @Test
    void testConfirmer() {
        RendezVous rdv = creerRendezVous();

        rdv.confirmer();

        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.CONFIRME);
    }

    @Test
    void testConfirmerIgnoreSiNonPlanifie() {
        RendezVous rdv = creerRendezVous();
        rdv.annuler();

        rdv.confirmer();

        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.ANNULE);
    }

    @Test
    void testAnnuler() {
        RendezVous rdv = creerRendezVous();

        rdv.annuler();

        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.ANNULE);
    }

    @Test
    void testAnnulerSansEffetSiTermine() {
        RendezVous rdv = creerRendezVous();
        rdv.terminer();

        rdv.annuler();

        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.TERMINE);
    }

    @Test
    void testTerminer() {
        RendezVous rdv = creerRendezVous();

        rdv.terminer();

        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.TERMINE);
    }

    @Test
    void testSetters() {
        RendezVous rdv = creerRendezVous();
        LocalDateTime nouvelleDate = LocalDateTime.of(2025, 7, 1, 11, 30);

        rdv.setDateHeure(nouvelleDate);
        rdv.setDureeMinutes(60);
        rdv.setMotif("Contrôle annuel");
        rdv.setStatut(StatutRendezVous.CONFIRME);

        assertThat(rdv.getDateHeure()).isEqualTo(nouvelleDate);
        assertThat(rdv.getDureeMinutes()).isEqualTo(60);
        assertThat(rdv.getMotif()).isEqualTo("Contrôle annuel");
        assertThat(rdv.getStatut()).isEqualTo(StatutRendezVous.CONFIRME);
    }

    @Test
    void testEquals() {
        LocalDateTime dateRdv = LocalDateTime.of(2025, 6, 10, 14, 0);
        RendezVous rdv1 = new RendezVous(patient, medecin, dateRdv, 30, "RDV 1");

        // Réflexivité
        assertThat(rdv1).isEqualTo(rdv1);

        // Même patient, même médecin et même date/heure => égaux
        RendezVous rdv2 = new RendezVous(patient, medecin, dateRdv, 45, "RDV 2");
        assertThat(rdv1).isEqualTo(rdv2);

        // Date/heure différente => non égaux
        RendezVous rdv3 = new RendezVous(patient, medecin,
                LocalDateTime.of(2025, 6, 10, 15, 0), 30, "RDV 3");
        assertThat(rdv1).isNotEqualTo(rdv3);

        // Non égalité avec null ou un autre type
        assertThat(rdv1).isNotEqualTo(null);
        assertThat(rdv1).isNotEqualTo("Pas un rendez-vous");
    }

    @Test
    void testHashCode() {
        LocalDateTime dateRdv = LocalDateTime.of(2025, 6, 10, 14, 0);
        RendezVous rdv1 = new RendezVous(patient, medecin, dateRdv, 30, "RDV 1");
        RendezVous rdv2 = new RendezVous(patient, medecin, dateRdv, 45, "RDV 2");

        // Deux rendez-vous égaux doivent avoir le même hashCode.
        assertThat(rdv1.hashCode()).isEqualTo(rdv2.hashCode());
    }

    @Test
    void testToString() {
        when(medecin.getTarifHoraire()).thenReturn(60.0);
        when(medecin.getNomComplet()).thenReturn("Jean Dupont");
        when(patient.getTauxRemboursement()).thenReturn(0.70);
        when(patient.getNomComplet()).thenReturn("Marie Curie");

        LocalDateTime dateRdv = LocalDateTime.of(2025, 6, 10, 14, 0);
        RendezVous rdv = new RendezVous(patient, medecin, dateRdv, 30, "Consultation");

        String result = rdv.toString();

        assertThat(result).contains("Jean Dupont");
        assertThat(result).contains("Marie Curie");
        assertThat(result).contains("Consultation");
        assertThat(result).contains("Planifié");
    }

    private RendezVous creerRendezVous() {
        LocalDateTime dateRdv = LocalDateTime.of(2025, 6, 10, 14, 0);
        return new RendezVous(patient, medecin, dateRdv, 30, "Consultation");
    }
}
