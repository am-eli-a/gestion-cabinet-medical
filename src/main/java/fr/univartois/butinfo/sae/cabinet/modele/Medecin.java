package fr.univartois.butinfo.sae.cabinet.modele;

import fr.univartois.butinfo.sae.cabinet.enumeration.Specialite;

/**
 * Classe représentant un médecin du cabinet. Hérite de la classe abstraite Personne.
 */
public class Medecin extends Personne {

  // Numéro d'identification au Conseil de l'Ordre des Médecins
  private final String numeroOrdre;
  private Specialite specialite;
  private double tarifHoraire; // Tarif de base pour une consultation

  /**
   * Constructeur complet d'un Médecin.
   *  @param nom, prenom, telephone, email, numeroOrdre, specialite, tarifHorairegit
   */
  public Medecin(String nom, String prenom, String telephone, String email,
      String numeroOrdre, Specialite specialite, double tarifHoraire) {
    super(nom, prenom, telephone, email);
    this.numeroOrdre = numeroOrdre;
    this.specialite = specialite;
    this.tarifHoraire = tarifHoraire;
  }

  /**
   * Constructeur simplifié (sans précision du cabinet).
   * @param nom, prenom, telephone, email, numeroOrdre, specialite
   */
  public Medecin(String nom, String prenom, String telephone, String email,
      String numeroOrdre, Specialite specialite) {
    this(nom, prenom, telephone, email, numeroOrdre, specialite, 30.00);
  }

  // -------------------------
  // Getters et Setters
  // -------------------------

  /**
   * Retourne le numéro d'identification du médecin.
   * @return String numeroOrdre
   */
  public String getNumeroOrdre() {
    return numeroOrdre;
  }

  /**
   * Retourne la spécialité du médecin concerné
   * @return L'énumération {@link Specialite}
   */
  public Specialite getSpecialite() {
    return specialite;
  }

  /**
   * Modifie ou met à jour la spécialité médicale du médecin.
   * @param specialite, la spécialité a attribuer au médecin dans l'énumération Spécialité
   */
  public void setSpecialite(Specialite specialite) {
    this.specialite = specialite;
  }

  /**
   * Retourne le tarif horaire utiliser par le médecin pour ses consultations.
   * @return Le montant du tarif horaire (en euros).
   */
  public double getTarifHoraire() {
    return tarifHoraire;
  }

  /**
   * Modifie le tarif horaire utiliser par le médecin.
   * Permet d'ajuster les tarifs horaires en fonction des mises à jour
   * @param tarifHoraire Le nouveau montant du tarif horaire (en euros).
   */
  public void setTarifHoraire(double tarifHoraire) {
    this.tarifHoraire = tarifHoraire;
  }

  /**
   * Deux médecins sont égaux s'ils ont le même numéro d'ordre.
   * @param obj
   * @return true or false
   */
  @Override
  public boolean equals(Object obj) {
    if (!super.equals(obj)) {
      return false;
    }
    Medecin autre = (Medecin) obj;
    return this.numeroOrdre.equals(autre.numeroOrdre);
  }


  /**
   * Génère le code hash code du médecin basé sur son numéro d'ordre
   *@return Le code hash code du médecin
   */
  @Override
  public int hashCode() {
    return numeroOrdre.hashCode();
  }

  /**
   * Méthode toString permettant d'avoir les informations concernant le médecin
   * @return nom médecin, specialite.getLibelle(), numberOrdre, tarifHoraire
   */
  @Override
  public String toString() {
    return "Médecin  { " + super.toString() +
        " | Spécialité: " + specialite.getLibelle() +
        " | N° Ordre: " + numeroOrdre +
        " | tarif: " + tarifHoraire + " }";
  }
}
