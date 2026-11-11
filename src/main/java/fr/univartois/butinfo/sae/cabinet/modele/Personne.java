package fr.univartois.butinfo.sae.cabinet.modele;

/**
 * Classe abstraite représentant une personne (base commune pour Patient et Medecin).
 * Applique le principe d'encapsulation et sert de superclasse pour l'héritage.
 */
public abstract class Personne {

    /**
     * Identifiant unique de la personne.
     */
    private final int id;

    /**
     * Le nom de la personne.
     */
    protected String nom;

    /**
     * Le prénom de la personne.
     */
    protected String prenom;

    /**
     * Le numéro de téléphone de la personne.
     */
    protected String telephone;

    /**
     * L'adresse e-mail de la personne.
     */
    protected String email;

    /**
     * Compteur statique permettant de générer automatiquement des identifiants uniques.
     */
    private static int compteurId = 1;

    /**
     * Constructeur complet de Personne.
     *
     * @param nom       Le nom de la personne.
     * @param prenom    Le prénom de la personne.
     * @param telephone Le numéro de téléphone de la personne.
     * @param email     L'adresse e-mail de la personne.
     */
    public Personne(String nom, String prenom, String telephone, String email) {
        this.id = compteurId++;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
    }

    // -------------------------
    // Getters et Setters
    // -------------------------

    /**
     * Récupère l'identifiant unique de la personne.
     *
     * @return L'identifiant.
     */
    public int getId() {
        return id;
    }

    /**
     * Récupère le nom de la personne.
     *
     * @return Le nom.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom de la personne.
     *
     * @param nom Le nouveau nom.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Récupère le prénom de la personne.
     *
     * @return Le prénom.
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Modifie le prénom de la personne.
     *
     * @param prenom Le nouveau prénom.
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Récupère le numéro de téléphone de la personne.
     *
     * @return Le numéro de téléphone.
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Modifie le numéro de téléphone de la personne.
     *
     * @param telephone Le nouveau numéro de téléphone.
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Récupère l'adresse e-mail de la personne.
     *
     * @return L'adresse e-mail.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Modifie l'adresse e-mail de la personne.
     *
     * @param email La nouvelle adresse e-mail.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retourne le nom complet de la personne (prénom suivi du nom).
     *
     * @return Le nom complet.
     */
    public String getNomComplet() {
        return prenom + " " + nom;
    }

    /**
     * Indique si une autre personne est égale à celle-ci.
     * Deux personnes sont considérées comme égales si elles ont le même identifiant.
     *
     * @param obj L'objet à comparer.
     * @return true si l'objet est une Personne avec le même identifiant, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Personne autre = (Personne) obj;
        return this.id == autre.id;
    }

    /**
     * Calcule le code de hachage de la personne basé sur son identifiant.
     *
     * @return Le code de hachage.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /**
     * Retourne une représentation textuelle de la personne.
     *
     * @return Une chaîne de caractères contenant l'ID, le nom complet, le téléphone et l'e-mail.
     */
    @Override
    public String toString() {
        return "[ID=" + id + "] " + prenom + " " + nom +
               " | Tél: " + telephone +
               " | Email: " + email;
    }
}
