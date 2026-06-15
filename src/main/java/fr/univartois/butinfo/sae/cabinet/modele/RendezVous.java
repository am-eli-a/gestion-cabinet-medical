package fr.univartois.butinfo.sae.cabinet.modele;

import fr.univartois.butinfo.sae.cabinet.enumeration.StatutRendezVous;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Représente un rendez-vous médical entre un patient et un médecin.
 *
 * <p>Chaque rendez-vous est identifié par un ID unique généré automatiquement,
 * et possède un statut évoluant</p>
 * */
public class RendezVous {

    /**
     * Identifiant unique du rendez-vous, attribué automatiquement à la création.
     */
    private final int id;

    /**
     * Patient concerné par ce rendez-vous.
     */
    private final Patient patient;

    /**
     * Médecin en charge du rendez-vous.
     */
    private final Medecin medecin;

    /**
     * Date et heure du rendez-vous.
     */
    private LocalDateTime dateHeure;

    /**
     * Durée du rendez-vous en minutes.
     */
    private int dureeMinutes;

    /**
     * Statut courant du rendez-vous.
     */
    private StatutRendezVous statut;

    /**
     * Motif de la consultation.
     */
    private String motif;

    /**
     * Compteur statique servant à générer des identifiants uniques et croissants.
     */
    private static int compteurId = 1;

    /**
     * Formateur utilisé pour l'affichage de la date et de l'heure (ex : {@code 20/06/2025 à 09:30}).
     */
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm");

    // -------------------------
    // Constructeurs
    // -------------------------

    /**
     * Construit un rendez-vous avec tous les paramètres explicites.
     *
     * <p>Le statut est automatiquement initialisé à {@link StatutRendezVous#PLANIFIE}.</p>
     *
     * @param patient
     * @param medecin
     * @param dateHeure
     * @param dureeMinutes
     * @param motif
     * */
    public RendezVous(Patient patient, Medecin medecin, LocalDateTime dateHeure,
                      int dureeMinutes, String motif) {
        this.id = compteurId++;
        this.patient = patient;
        this.medecin = medecin;
        this.dateHeure = dateHeure;
        this.dureeMinutes = dureeMinutes;
        this.statut = StatutRendezVous.PLANIFIE;
        this.motif = motif;
    }

    /**
     * Construit un rendez-vous avec une durée par défaut de 30 minutes.
     *
     * <p>Équivalent à {@code new RendezVous(patient, medecin, dateHeure, 30, motif)}.</p>
     *
     * @param patient    le patient concerné
     * @param medecin    le médecin en charge
     * @param dateHeure  la date et l'heure du rendez-vous
     * @param motif      le motif de la consultation
     */
    public RendezVous(Patient patient, Medecin medecin, LocalDateTime dateHeure, String motif) {
        this(patient, medecin, dateHeure, 30, motif);
    }

    // -------------------------
    // Actions métier
    // -------------------------

    /**
     * Confirme le rendez-vous en passant son statut à {@link StatutRendezVous#CONFIRME}.
     *
     * <p>Cette action n'a d'effet que si le statut courant est {@link StatutRendezVous#PLANIFIE}.
     * Dans tous les autres cas, l'appel est ignoré silencieusement.</p>
     */
    public void confirmer() {
        if (this.statut == StatutRendezVous.PLANIFIE) {
            this.statut = StatutRendezVous.CONFIRME;
        }
    }

    /**
     * Annule le rendez-vous en passant son statut à {@link StatutRendezVous#ANNULE}.
     *
     * <p>Cette action est sans effet si le rendez-vous est déjà
     * {@link StatutRendezVous#TERMINE}.</p>
     */
    public void annuler() {
        if (this.statut != StatutRendezVous.TERMINE) {
            this.statut = StatutRendezVous.ANNULE;
        }
    }

    /**
     * Termine le rendez-vous en passant son statut à {@link StatutRendezVous#TERMINE}.
     *
     * <p>Cette transition est définitive : un rendez-vous terminé ne peut plus
     * être annulé ni confirmé.</p>
     */
    public void terminer() {
        this.statut = StatutRendezVous.TERMINE;
    }

    // -------------------------
    // Getters et Setters
    // -------------------------

    public int getId() { return id; }

    public Patient getPatient() { return patient; }

    public Medecin getMedecin() { return medecin; }

    public LocalDateTime getDateHeure() { return dateHeure; }

    public void setDateHeure(LocalDateTime dateHeure) { this.dateHeure = dateHeure; }

    public int getDureeMinutes() { return dureeMinutes; }

    public void setDureeMinutes(int dureeMinutes) { this.dureeMinutes = dureeMinutes; }

    public StatutRendezVous getStatut() { return statut; }

    public void setStatut(StatutRendezVous statut) { this.statut = statut; }

    public String getMotif() { return motif; }

    public void setMotif(String motif) { this.motif = motif; }

    // -------------------------
    // Calculs
    // -------------------------

    /**
     * Calcule le coût net de la consultation pour le patient.
     *
     * <p>Le calcul applique la formule suivante :</p>
     * <pre>
     * coût = tarifHoraire × (duréeMinutes / 60) × (1 - tauxRemboursement)
     * </pre>
     * <p>où {@code tauxRemboursement} est exprimé entre 0 et 1.</p>
     *
     * @return le prix de la consultation en euros, après déduction du remboursement
     */
    public double prixConsultation() {
        // TODO
        return medecin.getTarifHoraire() * (dureeMinutes / 60.0)
                - (medecin.getTarifHoraire() * (dureeMinutes / 60.0) * patient.getTauxRemboursement());
    }

    // -------------------------
    // Object
    // -------------------------

    /**
     * Calcule le code de hachage du rendez-vous.
     *
     * <p>Basé sur le patient, le médecin et la date/heure, de façon cohérente
     * avec {@link #equals(Object)}.</p>
     *
     * @return le code de hachage
     */
    @Override
    public int hashCode() {
        return patient.hashCode() * 31 + medecin.hashCode() * 17 + dateHeure.hashCode();
    }

    /**
     * Vérifie l'égalité entre deux rendez-vous.
     *
     * <p>Deux rendez-vous sont considérés égaux s'ils concernent le même patient,
     * le même médecin, et ont la même date/heure.</p>
     *
     * @param obj l'objet à comparer
     * @return {@code true} si les deux rendez-vous sont équivalents, {@code false} sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RendezVous autre = (RendezVous) obj;
        return this.patient.equals(autre.patient) &&
                this.medecin.equals(autre.medecin) &&
                this.dateHeure.equals(autre.dateHeure);
    }

    /**
     * Retourne une représentation textuelle du rendez-vous.
     *
     * <p>Format : {@code RDV { [ID=X] Prénom Nom avec Dr Prénom Nom | jj/MM/aaaa à HH:mm
     * (N min) | Motif: ... | Statut: ... | Coût : X.XX }}</p>
     *
     * @return la représentation du rendez-vous sous forme de chaîne
     */
    @Override
    public String toString() {
        return "RDV      { [ID=" + id + "] " +
                patient.getNomComplet() + " avec Dr " + medecin.getNomComplet() +
                " | " + dateHeure.format(FORMATTER) +
                " (" + dureeMinutes + " min)" +
                " | Motif: " + motif +
                " | Statut: " + statut.getLibelle() +
                " | Coût : " + prixConsultation() + " }";
    }
}