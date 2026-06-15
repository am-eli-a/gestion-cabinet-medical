package fr.univartois.butinfo.sae.cabinet.modele;

import static org.assertj.core.api.Assertions.assertThat;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import org.junit.jupiter.api.Test;

/**
 * Classe de tests unitaires pour la classe {@link Medecin}.
 */
class MedecinTest {

    @Test
    void testConstructeurComplet() {
        Medecin medecin = new Medecin("Dupont", "Jean", "0312345678", "j.dupont@cabinet.fr",
                "ORD-001", Specialite.GENERALISTE, 35.50);

        assertThat(medecin.getNom()).isEqualTo("Dupont");
        assertThat(medecin.getPrenom()).isEqualTo("Jean");
        assertThat(medecin.getTelephone()).isEqualTo("0312345678");
        assertThat(medecin.getEmail()).isEqualTo("j.dupont@cabinet.fr");
        assertThat(medecin.getNumeroOrdre()).isEqualTo("ORD-001");
        assertThat(medecin.getSpecialite()).isEqualTo(Specialite.GENERALISTE);
        assertThat(medecin.getTarifHoraire()).isEqualTo(35.50);
    }

    @Test
    void testConstructeur() {
        Medecin medecin = new Medecin("Martin", "Sophie", "0398765432", "s.martin@cabinet.fr",
                "ORD-002", Specialite.CARDIOLOGUE);

        assertThat(medecin.getNom()).isEqualTo("Martin");
        assertThat(medecin.getPrenom()).isEqualTo("Sophie");
        assertThat(medecin.getTelephone()).isEqualTo("0398765432");
        assertThat(medecin.getEmail()).isEqualTo("s.martin@cabinet.fr");
        assertThat(medecin.getNumeroOrdre()).isEqualTo("ORD-002");
        assertThat(medecin.getSpecialite()).isEqualTo(Specialite.CARDIOLOGUE);

        assertThat(medecin.getTarifHoraire()).isEqualTo(30.00);
    }

    @Test
    void testSetters() {
        Medecin medecin = new Medecin("Dupont", "Jean", "0312345678", "j.dupont@cabinet.fr",
                "ORD-001", Specialite.GENERALISTE, 35.50);

        medecin.setSpecialite(Specialite.PEDIATRE);
        assertThat(medecin.getSpecialite()).isEqualTo(Specialite.PEDIATRE);

        medecin.setTarifHoraire(45.00);
        assertThat(medecin.getTarifHoraire()).isEqualTo(45.00);
    }

    @Test
    void testEqualsEtHashCode() {
        Medecin medecin1 = new Medecin("Dupont", "Jean", "0312345678", "j.dupont@cabinet.fr",
                "ORD-001", Specialite.GENERALISTE, 35.50);

        // Réflexivité
        assertThat(medecin1).isEqualTo(medecin1);
        assertThat(medecin1.hashCode()).isEqualTo(medecin1.hashCode());

        assertThat(medecin1).isNotEqualTo(null);
        assertThat(medecin1).isNotEqualTo("Pas un medecin");

        Medecin medecin2 = new Medecin("Dupont", "Jean", "0312345678", "j.dupont@cabinet.fr",
                "ORD-001", Specialite.GENERALISTE, 35.50);
        assertThat(medecin1).isNotEqualTo(medecin2);
    }

    @Test
    void testToString() {
        Medecin medecin = new Medecin("Dupont", "Jean", "0312345678", "j.dupont@cabinet.fr",
                "ORD-001", Specialite.GENERALISTE, 35.50);

        String result = medecin.toString();
        assertThat(result).contains("Dupont");
        assertThat(result).contains("Jean");
        assertThat(result).contains("ORD-001");
        assertThat(result).contains("Médecin généraliste");
        assertThat(result).contains("35.5");
    }
}
