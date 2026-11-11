package fr.univartois.butinfo.sae.cabinet.service;

import fr.univartois.butinfo.sae.cabinet.enumeration.StatutRendezVous;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.modele.RendezVous;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service de gestion des rendez-vous médicaux.
 */
public class GestionRendezVous {

    private final List<RendezVous> rendezVous;

    /**
     * Constructeur : initialise la liste vide.
     */
    public GestionRendezVous() {
        this.rendezVous = new ArrayList<>();
    }

    // -------------------------
    // Gestion des RDV
    // -------------------------

    /**
     * Prend un rendez-vous si le créneau du médecin est libre.
     * @param patient,medecin,dateheure,dureeMinutes,motif
     * @return le rendez-vous créé, ou null si le créneau est pris
     */
    public RendezVous prendreRendezVous(Patient patient, Medecin medecin,
                                         LocalDateTime dateHeure, int dureeMinutes, String motif) {
        // Vérification de disponibilité du médecin
        if (!estDisponible(medecin, dateHeure)) {
            System.out.println("ERREUR : Le Dr " + medecin.getNomComplet() +
                               " n'est pas disponible à ce créneau.");
            return null;
        }
        RendezVous rdv = new RendezVous(patient, medecin, dateHeure, dureeMinutes, motif);
        rendezVous.add(rdv);
        System.out.println("Rendez-vous créé : " + rdv);
        return rdv;
    }

    /**
     * Annule un rendez-vous par son ID.
     * @param id su rendez-vous
     * @return si le rendez-vous est bien annulé ou introuvable
     */
    public void annulerRendezVous(int id) {
        RendezVous rdv = rechercherParId(id);
        if (rdv == null) {
            System.out.println("ERREUR : Rendez-vous ID=" + id + " introuvable.");
            return;
        }
        rdv.annuler();
        System.out.println("Rendez-vous annulé : ID=" + id);
    }

    /**
     * Confirme un rendez-vous par son ID.
     * @param id du rendez-vous
     * @return si le rend"ez-vous est confirmé ou introuvable
     */
    public void confirmerRendezVous(int id) {
        RendezVous rdv = rechercherParId(id);
        if (rdv == null) {
            System.out.println("ERREUR : Rendez-vous ID=" + id + " introuvable.");
            return;
        }
        rdv.confirmer();
        System.out.println("Rendez-vous confirmé : ID=" + id);
    }

    /**
     * Marque un rendez-vous comme terminé.
     * @param id du rendez-vous
     * @return true or false si le rendez-vous est terminé ou introuvable
     */
    public boolean terminerRendezVous(int id) {
        RendezVous rdv = rechercherParId(id);
        if (rdv == null) {
            System.out.println("ERREUR : Rendez-vous ID=" + id + " introuvable.");
            return false;
        }
        rdv.terminer();
        System.out.println("Rendez-vous terminé : ID=" + id);
        return true;
    }

    // -------------------------
    // Recherche
    // -------------------------

    /**
     * Recherche un rendez-vous par son ID.
     * @param id rendez-vous
     * @return l'id du rendez-vous
     */
    public RendezVous rechercherParId(int id) {
        for (RendezVous rdv : rendezVous) {
            if (rdv.getId() == id) return rdv;
        }
        return null;
    }

    /**
     * Retourne tous les rendez-vous d'un patient donné.
     * @param patient
     * @return resultats, les rendez-vosu du patient
     */
    public List<RendezVous> getRendezVousPatient(Patient patient) {
        List<RendezVous> resultats = new ArrayList<>();
        for (RendezVous rdv : rendezVous) {
            if (rdv.getPatient().equals(patient)) resultats.add(rdv);
        }
        return resultats;
    }

    /**
     * Retourne tous les rendez-vous d'un médecin donné.
     * @param medecin
     * @return resultats, les rendez-vous des médecins
     */
    public List<RendezVous> getRendezVousMedecin(Medecin medecin) {
        List<RendezVous> resultats = new ArrayList<>();
        for (RendezVous rdv : rendezVous) {
            if (rdv.getMedecin().equals(medecin)) resultats.add(rdv);
        }
        return resultats;
    }

    /**
     * Retourne les rendez-vous filtrés par statut.
     * @param statut
     * @return resultats, le statut d'un rendez-vosu en fonction de l'enumération
     */
    public List<RendezVous> getRendezVousParStatut(StatutRendezVous statut) {
        List<RendezVous> resultats = new ArrayList<>();
        for (RendezVous rdv : rendezVous) {
            if (rdv.getStatut() == statut) resultats.add(rdv);
        }
        return resultats;
    }

    /**
     * Vérifie si un médecin est disponible à une date/heure donnée.
     * Un médecin est indisponible si un RDV non annulé existe à la même heure.
     * @param medecin,dateHeure
     * @return true or false si le médecin est disponible ou non
     */
    public boolean estDisponible(Medecin medecin, LocalDateTime dateHeure) {
        for (RendezVous rdv : rendezVous) {
            if (rdv.getMedecin().equals(medecin) &&
                rdv.getDateHeure().equals(dateHeure) &&
                rdv.getStatut() != StatutRendezVous.ANNULE) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retourne la liste complète des rendez-vous.
     */
    public List<RendezVous> listerTousLesRendezVous() {
        return new ArrayList<>(rendezVous);
    }

    /**
     * Retourne le nombre total de rendez-vous.
     */
    public int getNombreRendezVous() {
        return rendezVous.size();
    }

}
