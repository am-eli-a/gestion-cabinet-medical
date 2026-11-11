package fr.univartois.butinfo.sae.cabinet.modele;

/**
 * Classe représentant une ligne de prescription dans une ordonnance. Associe un médicament à sa
 * posologie et durée de traitement.
 */
public class LignePrescription {

    /**
   * Le médicament prescrit dans cette ligne de prescription.
   */
  private final Medicament medicament;

  /**
   * La quantité de médicament prescrite.
   */
  private int quantite;

  /**
   * La posologie du médicament
   */
  private String posologie;

  /**
   * La durée du traitement, exprimée en jours.
   */
  private int dureeTraitement;

  /**
   * Les consignes particulières associées à la prise du médicament.
   */
  private String instructions;

  /**
   * Constructeur complet d'une ligne de prescription.
   *
   * @param medicament      Le médicament prescrit.
   * @param quantite        La quantité de médicament prescrite.
   * @param posologie       La posologie du médicament.
   * @param dureeTraitement La durée du traitement, exprimée en jours.
   * @param instructions    Les consignes particulières associées à la prise du médicament.
   */
  public LignePrescription(Medicament medicament, int quantite, String posologie,
      int dureeTraitement, String instructions) {
    this.medicament = medicament;
    this.quantite = quantite;
    this.posologie = posologie;
    this.dureeTraitement = dureeTraitement;
    this.instructions = instructions;
  }

  /**
   * Constructeur simplifié d'une ligne de prescription avec une quantité par défaut (1)
   * et sans instructions particulières.
   *
   * @param medicament      Le médicament prescrit.
   * @param posologie       La posologie du médicament.
   * @param dureeTraitement La durée du traitement, exprimée en jours.
   */
  public LignePrescription(Medicament medicament, String posologie, int dureeTraitement) {
    this(medicament, 1, posologie, dureeTraitement, "Aucune instruction particulière");
  }

  // -------------------------
  // Getters et Setters
  // -------------------------

  /**
   * Récupère le médicament prescrit.
   *
   * @return Le médicament prescrit.
   */
  public Medicament getMedicament() {
    return medicament;
  }

  /**
   * Récupère la posologie du médicament.
   *
   * @return La posologie.
   */
  public String getPosologie() {
    return posologie;
  }

  /**
   * Modifie la posologie du médicament.
   *
   * @param posologie La nouvelle posologie.
   */
  public void setPosologie(String posologie) {
    this.posologie = posologie;
  }

  /**
   * Récupère la durée du traitement en jours.
   *
   * @return La durée du traitement.
   */
  public int getDureeTraitement() {
    return dureeTraitement;
  }

  /**
   * Modifie la durée du traitement en jours.
   *
   * @param dureeTraitement La nouvelle durée du traitement.
   */
  public void setDureeTraitement(int dureeTraitement) {
    this.dureeTraitement = dureeTraitement;
  }

  /**
   * Récupère les consignes particulières du traitement.
   *
   * @return Les instructions associées.
   */
  public String getInstructions() {
    return instructions;
  }

  /**
   * Modifie les consignes particulières du traitement.
   *
   * @param instructions Les nouvelles instructions.
   */
  public void setInstructions(String instructions) {
    this.instructions = instructions;
  }

  /**
   * Récupère la quantité prescrite du médicament.
   *
   * @return La quantité prescrite.
   */
  public int getQuantite() {
    return quantite;
  }

  /**
   * Modifie la quantité prescrite du médicament.
   *
   * @param quantite La nouvelle quantité.
   */
  public void setQuantite(int quantite) {
    this.quantite = quantite;
  }

  /**
   * Retourne une représentation textuelle de la ligne de prescription.
   *
   * @return Une chaîne de caractères décrivant le médicament, la quantité, la posologie,
   *         la durée de traitement et les instructions.
   */
  @Override
  public String toString() {
    return "  → " + medicament.getNom() +
        " | Quantité: " + quantite +
        " | Posologie: " + posologie +
        " | Durée: " + dureeTraitement + " jours" +
        " | Instructions: " + instructions;
  }
}
