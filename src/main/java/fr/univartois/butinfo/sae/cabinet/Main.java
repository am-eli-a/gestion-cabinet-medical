package fr.univartois.butinfo.sae.cabinet;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import fr.univartois.butinfo.sae.cabinet.modele.Ordonnance;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.modele.RendezVous;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedicaments;
import fr.univartois.butinfo.sae.cabinet.service.GestionOrdonnances;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import fr.univartois.butinfo.sae.cabinet.service.GestionRendezVous;
import fr.univartois.butinfo.sae.cabinet.utils.Recherche;
import fr.univartois.butinfo.sae.cabinet.utils.Statistiques;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe principale de démonstration du système de gestion de cabinet médical.
 * Exécutez cette classe pour tester toutes les fonctionnalités.
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        logger.info("╔═══════════════════════════════════════════╗");
        logger.info("║   SYSTÈME DE GESTION DE CABINET MÉDICAL   ║");
        logger.info("╚═══════════════════════════════════════════╝\n");

        // ============================================================
        // 1. Initialisation des services
        // ============================================================
        GestionPatients gestionPatients       = new GestionPatients();
        GestionMedecins gestionMedecins       = new GestionMedecins();
        GestionMedicaments gestionMedicaments = new GestionMedicaments();
        GestionRendezVous gestionRdv          = new GestionRendezVous();
        GestionOrdonnances gestionOrdonnances = new GestionOrdonnances();
        Recherche recherche                   = new Recherche(gestionPatients, gestionMedecins);
        Statistiques stats                    = new Statistiques(
                gestionPatients, gestionMedecins, gestionMedicaments, gestionRdv, gestionOrdonnances);

        // ============================================================
        // 2. Création de médecins
        // ============================================================
        logger.info("=== AJOUT DES MÉDECINS ===");
        Medecin dr1 = new Medecin("Dupont", "Jean", "0312345678", "j.dupont@cabinet.fr",
                                   "ORD-001", Specialite.GENERALISTE, 30.0);
        Medecin dr2 = new Medecin("Martin", "Sophie", "0398765432", "s.martin@cabinet.fr",
                                   "ORD-002", Specialite.CARDIOLOGUE, 50.0);
        Medecin dr3 = new Medecin("Bernard", "Lucie", "0367891234", "l.bernard@cabinet.fr",
                                   "ORD-003", Specialite.PEDIATRE, 40.0);
        gestionMedecins.ajouterMedecin(dr1);
        gestionMedecins.ajouterMedecin(dr2);
        gestionMedecins.ajouterMedecin(dr3);

        // ============================================================
        // 3. Création de patients
        // ============================================================
        logger.info("\n=== AJOUT DES PATIENTS ===");
        Patient p1 = new Patient("Leblanc", "Paul", "0612345678", "p.leblanc@mail.fr",
                                  "12 rue des Lilas, Arras",
                                  LocalDate.of(1985, 3, 22), "185036562012345", .50);
        Patient p2 = new Patient("Moreau", "Claire", "0687654321", "c.moreau@mail.fr",
                                  "8 avenue Foch, Lens",
                                  LocalDate.of(1995, 7, 15), "295076210054321");
        Patient p3 = new Patient("Petit", "Antoine", "0655443322", "a.petit@mail.fr",
                                  "3 impasse du Moulin, Douai",
                                  LocalDate.of(2010, 11, 5), "210116210011122", .6);
        gestionPatients.ajouterPatient(p1);
        gestionPatients.ajouterPatient(p2);
        gestionPatients.ajouterPatient(p3);

        // ============================================================
        // 4. Création de médicaments
        // ============================================================
        logger.info("\n=== AJOUT DES MÉDICAMENTS ===");
        Medicament med1 = new Medicament("Doliprane 1000mg",
                                          "Antalgique et antipyrétique", 5.5);
        Medicament med2 = new Medicament("Amoxicilline 500mg",
                                          "Antibiotique à large spectre", 13.25);
        Medicament med3 = new Medicament("Ibuprofène 400mg",
                                          "Anti-inflammatoire non stéroïdien", 43.12);
        Medicament med4 = new Medicament("Lisinopril 10mg",
                                          "Inhibiteur de l'enzyme de conversion", 24.56);
        gestionMedicaments.ajouterMedicament(med1);
        gestionMedicaments.ajouterMedicament(med2);
        gestionMedicaments.ajouterMedicament(med3);
        gestionMedicaments.ajouterMedicament(med4);

        // ============================================================
        // 5. Prise de rendez-vous
        // ============================================================
        logger.info("\n=== PRISE DE RENDEZ-VOUS ===");
        LocalDateTime rdv1Date = LocalDateTime.of(2025, 9, 10, 9, 0);
        LocalDateTime rdv2Date = LocalDateTime.of(2025, 9, 10, 10, 30);
        LocalDateTime rdv3Date = LocalDateTime.of(2025, 9, 11, 14, 0);

        RendezVous rdv1 = gestionRdv.prendreRendezVous(p1, dr1, rdv1Date, 30, "Consultation de routine");
        RendezVous rdv2 = gestionRdv.prendreRendezVous(p2, dr2, rdv2Date, 45, "Bilan cardiaque");
        RendezVous rdv3 = gestionRdv.prendreRendezVous(p3, dr3, rdv3Date, 20, "Vaccination enfant");

        // Confirmation du premier RDV
        if (rdv1 != null) gestionRdv.confirmerRendezVous(rdv1.getId());

        // Annulation du troisième RDV
        if (rdv3 != null) gestionRdv.annulerRendezVous(rdv3.getId());

        // ============================================================
        // 6. Création d'ordonnances
        // ============================================================
        logger.info("\n=== CRÉATION D'ORDONNANCES ===");

        Ordonnance ordo1 = gestionOrdonnances.creerOrdonnance(
                dr1, p1, LocalDate.now().plusMonths(3), "Renouveler si nécessaire");
        gestionOrdonnances.ajouterPrescription(
                ordo1.getId(), med1, 4,"2 comprimés par jour", 7, "À prendre avec de l'eau");
        gestionOrdonnances.ajouterPrescription(
                ordo1.getId(), med2, 3,"1 comprimé 3 fois/jour", 10, "Ne pas dépasser 3g/jour");

        Ordonnance ordo2 = gestionOrdonnances.creerOrdonnance(
                dr2, p2, LocalDate.now().plusMonths(6), "Contrôle dans 1 mois");
        gestionOrdonnances.ajouterPrescription(
                ordo2.getId(), med4, 1,"1 comprimé le matin", 30, "Surveillance tension artérielle");

        logger.info("\n--- Détail des ordonnances ---");
        logger.info(String.valueOf(ordo1));
        logger.info(String.valueOf(ordo2));

        // ============================================================
        // 7. Module de recherche
        // ============================================================
        logger.info("\n=== MODULE DE RECHERCHE ===");

        logger.info("\n>> Recherche patient par nom 'leblanc':");
        recherche.rechercherPatientParNom("leblanc");

        logger.info("\n>> Recherche médecin par spécialité CARDIOLOGUE:");
        recherche.rechercherMedecinParSpecialite(Specialite.CARDIOLOGUE);

        logger.info("\n>> Recherche médecin par nom 'martin':");
        recherche.rechercherMedecinParNom("martin");

        logger.info("\n>> Recherche patient par n° sécu:");
        recherche.rechercherPatientParSecu("185036562012345");

        // ============================================================
        // 8. Module de statistiques
        // ============================================================
        logger.info("\n=== MODULE DE STATISTIQUES ===");
        logger.info(stats.getStatistiques());

        Medecin actif = stats.getMedecinLePlusActif();
        if (actif != null) {
            logger.info("Médecin le plus actif : Dr " + actif.getNomComplet());
        } else {
            logger.info("Aucun médecin actif trouvé.");
        }

        // ============================================================
        // 9. Mise à jour et suppression (CRUD)
        // ============================================================
        logger.info("\n=== MISE À JOUR ET SUPPRESSION ===");

        // Mise à jour d'un médecin
        gestionMedecins.mettreAJourMedecin(dr1.getId(),
                "Dupont", "Jean", 33.45, "0312345678",
            "j.dupont@cabinet.fr",  Specialite.GENERALISTE);

        // Mise à jour d'un patient
        gestionPatients.mettreAJourPatient(p1.getId(),
                "Leblanc", "Paul-Henri", "0699887766", "p.leblanc@newmail.fr",
                "20 rue des Roses, Arras", .80);

        // Suppression d'un patient
        gestionPatients.supprimerPatient(p3.getId());

        // Affichage final
        logger.info("\n=== ÉTAT FINAL ===");
        List<Patient> patients = gestionPatients.listerTousLesPatients();
        if (patients.isEmpty()) {
            logger.info("Aucun patient enregistré.");
            return;
        }
        logger.info("=== LISTE DES PATIENTS (" + patients.size() + ") ===");
        for (Patient p : patients) {
            logger.info(String.valueOf(p));
        }
        List<Medecin> medecins = gestionMedecins.listerTousLesMedecins();
        if (medecins.isEmpty()) {
            logger.info("Aucun médecin enregistré.");
            return;
        }
        logger.info("=== LISTE DES MÉDECINS (" + medecins.size() + ") ===");
        for (Medecin m : medecins) {
            logger.info(String.valueOf(m));
        }
        List<Medicament> medicaments = gestionMedicaments.listerTousLesMedicaments();
        if (medicaments.isEmpty()) {
            logger.info("Aucun médicament enregistré.");
            return;
        }
        logger.info("=== LISTE DES MÉDICAMENTS (" + medicaments.size() + ") ===");
        for (Medicament m : medicaments) {
            logger.info(String.valueOf(m));
        }
        List<RendezVous> rendezVous = gestionRdv.listerTousLesRendezVous();
        if (rendezVous.isEmpty()) {
            logger.info("Aucun rendez-vous enregistré.");
            return;
        }
        logger.info("=== LISTE DES RENDEZ-VOUS (" + rendezVous.size() + ") ===");
        for (RendezVous rdv : rendezVous) {
            logger.info(String.valueOf(rdv));
        }
    }
}
