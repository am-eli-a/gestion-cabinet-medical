package fr.univartois.butinfo.sae.cabinet.utils;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.enumeration.StatutRendezVous;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedicaments;
import fr.univartois.butinfo.sae.cabinet.service.GestionOrdonnances;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import fr.univartois.butinfo.sae.cabinet.service.GestionRendezVous;

/**
 * Module de statistiques du cabinet médical.
 * Agrège des informations issues des différents services pour produire un tableau de bord.
 */
public class Statistiques {

    private final GestionPatients gestionPatients;
    private final GestionMedecins gestionMedecins;
    private final GestionMedicaments gestionMedicaments;
    private final GestionRendezVous gestionRendezVous;
    private final GestionOrdonnances gestionOrdonnances;

    /**
     * Constructeur : injecte tous les services nécessaires.
     */
    public Statistiques(GestionPatients gestionPatients,
                        GestionMedecins gestionMedecins,
                        GestionMedicaments gestionMedicaments,
                        GestionRendezVous gestionRendezVous,
                        GestionOrdonnances gestionOrdonnances) {
        this.gestionPatients = gestionPatients;
        this.gestionMedecins = gestionMedecins;
        this.gestionMedicaments = gestionMedicaments;
        this.gestionRendezVous = gestionRendezVous;
        this.gestionOrdonnances = gestionOrdonnances;
    }

    /**
     * Affiche un résumé complet des statistiques du cabinet.
     */
    public String getStatistiques() {
        StringBuilder texte = new StringBuilder();

        texte.append("\n╔══════════════════════════════════════════╗");
        texte.append("║        TABLEAU DE BORD - CABINET MÉDICAL  ║");
        texte.append("╚══════════════════════════════════════════╝");

        // -- Chiffres globaux --
        texte.append("\n📋 CHIFFRES GLOBAUX :");
        texte.append("  • Patients enregistrés   : ").append(gestionPatients.getNombrePatients());
        texte.append("  • Médecins enregistrés   : ").append(gestionMedecins.getNombreMedecins());
        texte.append("  • Médicaments référencés : ").append(gestionMedicaments.getNombreMedicaments());
        texte.append("  • Rendez-vous total      : ").append(gestionRendezVous.getNombreRendezVous());
        texte.append("  • Ordonnances émises     : ").append(gestionOrdonnances.getNombreOrdonnances());

        // -- Détail des rendez-vous par statut --
        texte.append("\n📅 RENDEZ-VOUS PAR STATUT :");
        for (StatutRendezVous statut : StatutRendezVous.values()) {
            int nb = gestionRendezVous.getRendezVousParStatut(statut).size();
            texte.append("  • ").append(statut.getLibelle()).append(" : ").append(nb);
        }

        // -- Médecins par spécialité --
        texte.append("\n🩺 MÉDECINS PAR SPÉCIALITÉ :");
        for (Specialite sp : Specialite.values()) {
            int nb = gestionMedecins.rechercherParSpecialite(sp).size();
            if (nb > 0) {
                texte.append("  • ").append(sp.getLibelle()).append(" : ").append(nb);
            }
        }

        // -- Top médecins (par nombre de RDV) --
        texte.append("\n🏆 MÉDECINS PAR NOMBRE DE RENDEZ-VOUS :");
        for (Medecin m : gestionMedecins.listerTousLesMedecins()) {
            int nbRdv = gestionRendezVous.getRendezVousMedecin(m).size();
            int nbOrdo = gestionOrdonnances.getOrdonnancesMedecin(m).size();
            texte.append("  • Dr ").append(m.getNomComplet()).append(" (").append(m.getSpecialite().getLibelle()).append(")").append(" → ").append(nbRdv).append(" RDV, ").append(nbOrdo).append(" ordonnances");
        }

        // -- Ordonnances valides --
        texte.append("\n💊 ORDONNANCES VALIDES (en cours) : ").append(gestionOrdonnances.getOrdonnancesValides().size());

        texte.append("\n══════════════════════════════════════════════\n");

        return texte.toString();
    }

    // -------------------------
    // Méthodes utilitaires
    // -------------------------

    /** Retourne le nombre de rendez-vous pour un statut donné. */
    public int getNombreRendezVousParStatut(StatutRendezVous statut) {
        return gestionRendezVous.getRendezVousParStatut(statut).size();
    }

    /** Retourne le médecin ayant le plus de rendez-vous (ou null si aucun). */
    public Medecin getMedecinLePlusActif() {
        Medecin medecinMax = null;
        int max = 0;
        for (Medecin m : gestionMedecins.listerTousLesMedecins()) {
            int nb = gestionRendezVous.getRendezVousMedecin(m).size();
            if (nb > max) {
                max = nb;
                medecinMax = m;
            }
        }
        return medecinMax;
    }

    @Override
    public String toString() {
        return "Statistiques { " +
               "patients=" + gestionPatients.getNombrePatients() +
               ", médecins=" + gestionMedecins.getNombreMedecins() +
               ", médicaments=" + gestionMedicaments.getNombreMedicaments() +
               ", RDV=" + gestionRendezVous.getNombreRendezVous() +
               ", ordonnances=" + gestionOrdonnances.getNombreOrdonnances() + " }";
    }
}
