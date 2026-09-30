package monde

import monstre.EspeceMonstre

/**
 * Une zone du monde : liste chaînée de zones (précédente / suivante).
 *
 * @property expZone Niveau/expérience de référence de la zone.
 * @property monstres Espèces pouvant y apparaître.
 * @property zoneSuivante Zone suivante (null si dernière).
 * @property zonePrecedante Zone précédente (null si première) - faute : "précédente".
 */
class Zone(val id : Int, var nom : String, var expZone : Int, var monstres : MutableList<EspeceMonstre>, var zoneSuivante : Zone?, var zonePrecedante : Zone?){

    /** À implémenter : déclencher une rencontre (tirer un monstre et lancer un Combat). */
    fun rencontreMonstre(){

    }

    /** À implémenter : créer un IndividuMonstre à partir d'une espèce de [monstres]. */
    fun genererMonstre(){

    }

    /**
     * ⚠ PROBLÈME n°12 : récursion infinie. Dès que deux zones sont liées, A.toString() appelle
     * B.toString() qui rappelle A.toString()... => StackOverflowError.
     * Correction : n'afficher que les noms : `${zonePrecedante?.nom}` et `${zoneSuivante?.nom}`.
     */
    override fun toString(): String {
        return "${this.nom}\n${this.zonePrecedante}\n${this.zoneSuivante}\n"
    }
}
