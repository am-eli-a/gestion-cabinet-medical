package fr.univartois.butinfo.sae.cabinet.modele;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Classe de tests unitaires pour la classe {@link Medicament}.
 *
 * <p>{@code Medicament} n'ayant pas de collaborateurs injectables,
 * aucun mock n'est nécessaire : les instances sont créées directement
 * dans chaque test ou via la méthode utilitaire {@link #creerMedicament()}.</p>
 */
class MedicamentTest {

    @Test
    void testConstructeurComplet() {
        Medicament med = new Medicament("Paracétamol", "test description1 et test description2", 2.50);

        assertThat(med.getNom()).isEqualTo("Paracétamol");
        assertThat(med.getDescription()).isEqualTo("test description1 et test description2");
        assertThat(med.getPrix()).isEqualTo(2.50);
        assertThat(med.getId()).isPositive();
    }

    @Test
    void testIdsUniques() {
        Medicament med1 = new Medicament("Sertraline", "Anti-dépresseur", 3.00);
        Medicament med2 = new Medicament("Aspirine", "test description", 1.50);

        assertThat(med1.getId()).isNotEqualTo(med2.getId());
    }

    @Test
    void testSetters() {
        Medicament med = creerMedicament();

        med.setNom("Doliprane");
        med.setDescription("Paracétamol 1000mg");
        med.setPrix(4.99);

        assertThat(med.getNom()).isEqualTo("Doliprane");
        assertThat(med.getDescription()).isEqualTo("Paracétamol 1000mg");
        assertThat(med.getPrix()).isEqualTo(4.99);
    }

    @Test
    void testEquals() {
        Medicament med1 = new Medicament("Paracétamol", "test description1", 2.50);

        assertThat(med1).isEqualTo(med1);

        Medicament med2 = new Medicament("PARACÉTAMOL", "Description diff", 2.50);
        assertThat(med1).isEqualTo(med2);

        Medicament med3 = new Medicament("Paracétamol", "test description1", 3.00);
        assertThat(med1).isNotEqualTo(med3);

        Medicament med4 = new Medicament("Ibuprofène", "test description1", 2.50);
        assertThat(med1).isNotEqualTo(med4);

        assertThat(med1).isNotEqualTo(null);
        assertThat(med1).isNotEqualTo("Pas un médicament");
    }

    @Test
    void testEqualsCasseInsensible() {
        Medicament med1 = new Medicament("aspirine", "test description1", 1.50);
        Medicament med2 = new Medicament("ASPIRINE", "Anti-fièvre", 1.50);

        assertThat(med1).isEqualTo(med2);
    }

    @Test
    void testHashCode() {
        Medicament med1 = new Medicament("Paracétamol", "test description1", 2.50);
        Medicament med2 = new Medicament("PARACÉTAMOL", "Description diff", 2.50);

        assertThat(med1.hashCode()).isEqualTo(med2.hashCode());
    }

    @Test
    void testToString() {
        Medicament med = new Medicament("Paracétamol", "test description1 et test description2", 2.50);

        String result = med.toString();

        assertThat(result).contains("Nom médoc");
        assertThat(result).contains("2.5");
        assertThat(result).contains("test description1 et test description2");
        assertThat(result).contains("ID=");
    }

    private Medicament creerMedicament() {
        return new Medicament("Paracétamol", "test description1 et test description2", 2.50);
    }
}