package fr.univartois.butinfo.sae.cabinet.service;

import fr.univartois.butinfo.sae.cabinet.modele.LignePrescription;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Medicament;
import fr.univartois.butinfo.sae.cabinet.modele.Ordonnance;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service de gestion des ordonnances médicales.
 */
public class GestionOrdonnances {

    /**
     * La liste des ordonnances gérées par ce service.
     */
    private final List<Ordonnance> ordonnances;

    /**
     * Constructeur : initialise une liste d'ordonnances vide.
     */
    public GestionOrdonnances() {
        this.ordonnances = new ArrayList<>();
    }

    // -------------------------
    // Gestion des ordonnances
    // -------------------------

    /**
     * Crée une nouvelle ordonnance vide (sans prescriptions) et l'enregistre dans le service.
     *
     * @param medecin      Le médecin émetteur de l'ordonnance.
     * @param patient      Le patient destinataire de l'ordonnance.
     * @param dateValidite La date limite de validité de l'ordonnance.
     * @param remarques    Les remarques ou consignes globales de l'ordonnance.
     * @return L'ordonnance créée et enregistrée.
     */
    public Ordonnance creerOrdonnance(Medecin medecin, Patient patient,
                                       LocalDate dateValidite, String remarques) {
        Ordonnance ordo = new Ordonnance(medecin, patient, dateValidite, remarques);
        ordonnances.add(ordo);
        System.out.println("Ordonnance créée (ID=" + ordo.getId() + ") pour " +
                           patient.getNomComplet() + " par Dr " + medecin.getNomComplet());
        return ordo;
    }

    /**
     * Ajoute une ligne de prescription à une ordonnance existante à partir de son identifiant.
     * Si l'ordonnance n'est pas trouvée, un message d'erreur est affiché et aucune prescription n'est ajoutée.
     *
     * @param ordonnanceId L'identifiant de l'ordonnance à modifier.
     * @param medicament   Le médicament à prescrire.
     * @param quantite     La quantité de médicament prescrite.
     * @param posologie    La posologie recommandée.
     * @param dureeJours   La durée du traitement en jours.
     * @param instructions Les consignes particulières pour la prise du médicament.
     */
    public void ajouterPrescription(int ordonnanceId, Medicament medicament, int quantite,
                                        String posologie, int dureeJours, String instructions) {
        Ordonnance ordo = rechercherParId(ordonnanceId);
        if (ordo == null) {
            System.out.println("ERREUR : Ordonnance ID=" + ordonnanceId + " introuvable.");
            return;
        }
        LignePrescription ligne = new LignePrescription(medicament, quantite, posologie, dureeJours, instructions);
        ordo.ajouterPrescription(ligne);
        System.out.println("Prescription ajoutée : " + medicament.getNom() +
                           " → ordonnance ID=" + ordonnanceId);
    }

    /**
     * Recherche une ordonnance par son identifiant unique.
     *
     * @param id L'identifiant unique de l'ordonnance recherchée.
     * @return L'ordonnance correspondante, ou null si aucune ordonnance ne possède cet ID.
     */
    public Ordonnance rechercherParId(int id) {
        for (Ordonnance o : ordonnances) {
            if (o.getId() == id) return o;
        }
        return null;
    }

    /**
     * Récupère toutes les ordonnances associées à un patient donné.
     *
     * @param patient Le patient concerné.
     * @return La liste des ordonnances du patient.
     */
    public List<Ordonnance> getOrdonnancesPatient(Patient patient) {
        List<Ordonnance> resultats = new ArrayList<>();
        for (Ordonnance o : ordonnances) {
            if (o.getPatient().equals(patient)) resultats.add(o);
        }
        return resultats;
    }

    /**
     * Récupère toutes les ordonnances rédigées par un médecin donné.
     *
     * @param medecin Le médecin concerné.
     * @return La liste des ordonnances émises par ce médecin.
     */
    public List<Ordonnance> getOrdonnancesMedecin(Medecin medecin) {
        List<Ordonnance> resultats = new ArrayList<>();
        for (Ordonnance o : ordonnances) {
            if (o.getMedecin().equals(medecin)) resultats.add(o);
        }
        return resultats;
    }

    /**
     * Récupère toutes les ordonnances qui sont encore valides à ce jour.
     *
     * @return La liste des ordonnances valides.
     */
    public List<Ordonnance> getOrdonnancesValides() {
        List<Ordonnance> resultats = new ArrayList<>();
        for (Ordonnance o : ordonnances) {
            if (o.estValide()) resultats.add(o);
        }
        return resultats;
    }

    /**
     * Retourne une copie de la liste de toutes les ordonnances enregistrées.
     *
     * @return La liste complète des ordonnances.
     */
    public List<Ordonnance> listerToutesLesOrdonnances() {
        return new ArrayList<>(ordonnances);
    }

    /**
     * Récupère le nombre total d'ordonnances enregistrées dans le système.
     *
     * @return Le nombre total d'ordonnances.
     */
    public int getNombreOrdonnances() {
        return ordonnances.size();
    }
}
