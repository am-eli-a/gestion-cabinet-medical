package fr.univartois.butinfo.sae.cabinet.service;

import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Service de gestion des patients.
 * Fournit les opérations CRUD (Créer, Lire, Mettre à jour, Supprimer).
 */
public class GestionPatients {

    /**
     * Liste interne des patients enregistrés dans le système.
     */
    private final ObservableList<Patient> patients;

    /**
     * Constructeur : initialise une liste de patients vide.
     */
    public GestionPatients() {
        this.patients = FXCollections.observableArrayList();
    }

    public ObservableList<Patient> getPatients() {
        return patients;
    }

    // -------------------------
    // CRUD
    // -------------------------

    /**
     * Ajoute un nouveau patient si son numéro de sécurité sociale n'existe pas déjà.
     * Si le patient existe déjà (même numéro de sécu), affiche un message d'erreur et n'ajoute pas le patient.
     *
     * @param patient Le patient à ajouter.
     */
    public void ajouterPatient(Patient patient) {
        if (rechercherParNumeroSecu(patient.getNumeroSecu()) != null) {
            System.out.println("ERREUR : Un patient avec ce numéro de sécu existe déjà.");
            return;
        }
        patients.add(patient);
        System.out.println("Patient ajouté : " + patient.getNomComplet());
    }

    /**
     * Recherche et retourne un patient par son identifiant unique.
     *
     * @param id L'identifiant unique du patient recherché.
     * @return Le patient correspondant, ou null si aucun patient n'est trouvé.
     */
    public Patient rechercherParId(int id) {
        for (Patient p : patients) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    /**
     * Recherche et retourne un patient par son numéro de sécurité sociale.
     *
     * @param numeroSecu Le numéro de sécurité sociale recherché.
     * @return Le patient correspondant, ou null si aucun patient n'est trouvé.
     */
    public Patient rechercherParNumeroSecu(String numeroSecu) {
        for (Patient p : patients) {
            if (p.getNumeroSecu().equalsIgnoreCase(numeroSecu)) return p;
        }
        return null;
    }

    /**
     * Recherche des patients dont le nom ou le prénom contient le terme donné (recherche insensible à la casse).
     *
     * @param terme Le terme à rechercher dans le nom ou le prénom.
     * @return La liste des patients correspondants.
     */
    public List<Patient> rechercherParNom(String terme) {
        List<Patient> resultats = new ArrayList<>();
        String termeLower = terme.toLowerCase();
        for (Patient p : patients) {
            if (p.getNom().toLowerCase().contains(termeLower) ||
                p.getPrenom().toLowerCase().contains(termeLower)) {
                resultats.add(p);
            }
        }
        return resultats;
    }

    /**
     * Met à jour les informations d'un patient existant (sauf son identifiant unique et son numéro de sécurité sociale).
     * Si le patient n'est pas trouvé, affiche un message d'erreur.
     *
     * @param id                       L'identifiant unique du patient à mettre à jour.
     * @param nouveauNom               Le nouveau nom du patient.
     * @param nouveauPrenom            Le nouveau prénom du patient.
     * @param nouveauTel               Le nouveau numéro de téléphone du patient.
     * @param nouveauEmail             La nouvelle adresse e-mail du patient.
     * @param nouvelleAdresse          La nouvelle adresse postale du patient.
     * @param nouveauTauxRemboursement Le nouveau taux de remboursement (assurance maladie) du patient.
     */
    public void mettreAJourPatient(int id, String nouveauNom, String nouveauPrenom,
                                       String nouveauTel, String nouveauEmail,
                                       String nouvelleAdresse, double nouveauTauxRemboursement) {
        Patient p = rechercherParId(id);
        if (p == null) {
            System.out.println("ERREUR : Patient ID=" + id + " introuvable.");
            return;
        }
        p.setNom(nouveauNom);
        p.setPrenom(nouveauPrenom);
        p.setTelephone(nouveauTel);
        p.setEmail(nouveauEmail);
        p.setAdresse(nouvelleAdresse);
        p.setTauxRemboursement(nouveauTauxRemboursement);
        System.out.println("Patient mis à jour : " + p.getNomComplet());
    }

    /**
     * Supprime un patient de la liste à partir de son identifiant unique.
     * Si le patient n'est pas trouvé, affiche un message d'erreur.
     *
     * @param id L'identifiant unique du patient à supprimer.
     */
    public void supprimerPatient(int id) {
        Patient p = rechercherParId(id);
        if (p == null) {
            System.out.println("ERREUR : Patient ID=" + id + " introuvable.");
            return;
        }
        patients.remove(p);
        System.out.println("Patient supprimé : " + p.getNomComplet());
    }

    /**
     * Retourne la liste complète de tous les patients enregistrés (copie défensive).
     *
     * @return La liste complète des patients.
     */
    public List<Patient> listerTousLesPatients() {
        return new ArrayList<>(patients);
    }

    /**
     * Récupère le nombre total de patients enregistrés dans le système.
     *
     * @return Le nombre de patients.
     */
    public int getNombrePatients() {
        return patients.size();
    }
}
