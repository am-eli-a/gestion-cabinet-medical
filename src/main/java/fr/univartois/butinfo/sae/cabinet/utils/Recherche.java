package fr.univartois.butinfo.sae.cabinet.utils;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;
import fr.univartois.butinfo.sae.cabinet.modele.Medecin;
import fr.univartois.butinfo.sae.cabinet.modele.Patient;
import fr.univartois.butinfo.sae.cabinet.service.GestionMedecins;
import fr.univartois.butinfo.sae.cabinet.service.GestionPatients;
import java.util.List;

/**
 * Module de recherche transversale pour patients et médecins.
 * Regroupe les fonctionnalités de recherche avancée.
 */
public class Recherche {

    /**
     * Le service de gestion des patients utilisé pour les recherches.
     */
    private final GestionPatients gestionPatients;

    /**
     * Le service de gestion des médecins utilisé pour les recherches.
     */
    private final GestionMedecins gestionMedecins;

    /**
     * Constructeur de Recherche.
     * Injecte les services de gestion de patients et de médecins.
     *
     * @param gestionPatients Le service de gestion des patients.
     * @param gestionMedecins Le service de gestion des médecins.
     */
    public Recherche(GestionPatients gestionPatients, GestionMedecins gestionMedecins) {
        this.gestionPatients = gestionPatients;
        this.gestionMedecins = gestionMedecins;
    }

    // -------------------------
    // Recherche patients
    // -------------------------

    /**
     * Recherche des patients par leur nom ou leur prénom et affiche les résultats.
     *
     * @param terme Le terme de recherche (nom ou prénom).
     * @return La liste des patients correspondants.
     */
    public List<Patient> rechercherPatientParNom(String terme) {
        List<Patient> resultats = gestionPatients.rechercherParNom(terme);
        afficherResultatsPatients(resultats, "Patients trouvés pour \"" + terme + "\"");
        return resultats;
    }

    /**
     * Recherche un patient par son numéro de sécurité sociale et affiche le résultat.
     *
     * @param numeroSecu Le numéro de sécurité sociale recherché.
     * @return Le patient correspondant, ou null si aucun patient n'est trouvé.
     */
    public Patient rechercherPatientParSecu(String numeroSecu) {
        Patient p = gestionPatients.rechercherParNumeroSecu(numeroSecu);
        if (p != null) {
            System.out.println("Patient trouvé : " + p);
        } else {
            System.out.println("Aucun patient trouvé pour le numéro de sécu : " + numeroSecu);
        }
        return p;
    }

    // -------------------------
    // Recherche médecins
    // -------------------------

    /**
     * Recherche des médecins par leur nom ou leur prénom et affiche les résultats.
     *
     * @param terme Le terme de recherche (nom ou prénom).
     * @return La liste des médecins correspondants.
     */
    public List<Medecin> rechercherMedecinParNom(String terme) {
        List<Medecin> resultats = gestionMedecins.rechercherParNom(terme);
        afficherResultatsMedecins(resultats, "Médecins trouvés pour \"" + terme + "\"");
        return resultats;
    }

    /**
     * Recherche des médecins par leur spécialité médicale et affiche les résultats.
     *
     * @param specialite La spécialité recherchée.
     * @return La liste des médecins ayant cette spécialité.
     */
    public List<Medecin> rechercherMedecinParSpecialite(Specialite specialite) {
        List<Medecin> resultats = gestionMedecins.rechercherParSpecialite(specialite);
        afficherResultatsMedecins(resultats,
            "Médecins de spécialité \"" + specialite.getLibelle() + "\"");
        return resultats;
    }

    /**
     * Recherche un médecin par son numéro d'ordre professionnel et affiche le résultat.
     *
     * @param numeroOrdre Le numéro d'ordre recherché.
     * @return Le médecin correspondant, ou null si aucun médecin n'est trouvé.
     */
    public Medecin rechercherMedecinParOrdre(String numeroOrdre) {
        Medecin m = gestionMedecins.rechercherParNumeroOrdre(numeroOrdre);
        if (m != null) {
            System.out.println("Médecin trouvé : " + m);
        } else {
            System.out.println("Aucun médecin trouvé pour le n° d'ordre : " + numeroOrdre);
        }
        return m;
    }

    // -------------------------
    // Affichage des résultats
    // -------------------------

    /**
     * Affiche dans la console la liste des patients trouvés avec un titre personnalisé.
     *
     * @param liste La liste des patients à afficher.
     * @param titre Le titre de la section d'affichage.
     */
    private String afficherResultatsPatients(List<Patient> liste, String titre) {
        StringBuilder texte = new StringBuilder();

        texte.append("--- " + titre + " (" + liste.size() + " résultat(s)) ---");
        if (liste.isEmpty()) {
            texte.append("  Aucun résultat.");
        } else {
            for (Patient p : liste) texte.append("  " + p);
        }

        return texte.toString();
    }

    /**
     * Affiche dans la console la liste des médecins trouvés avec un titre personnalisé.
     *
     * @param liste La liste des médecins à afficher.
     * @param titre Le titre de la section d'affichage.
     */
    private String afficherResultatsMedecins(List<Medecin> liste, String titre) {
        StringBuilder texte = new StringBuilder();
        texte.append("--- " + titre + " (" + liste.size() + " résultat(s)) ---");
        if (liste.isEmpty()) {
            texte.append("  Aucun résultat.");
        } else {
            for (Medecin m : liste) texte.append("  " + m);
        }

        return texte.toString();
    }

    /**
     * Retourne une représentation textuelle de l'état du module de recherche.
     *
     * @return Une chaîne de caractères décrivant le nombre de patients et de médecins gérés par les services associés.
     */
    @Override
    public String toString() {
        return "Module Recherche { patients=" + gestionPatients.getNombrePatients() +
               ", médecins=" + gestionMedecins.getNombreMedecins() + " }";
    }
}
