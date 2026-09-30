package item

import joueur
import monstre.IndividuMonstre

/**
 * Objet de capture : lancé sur un monstre sauvage, il tente de le capturer.
 *
 * @property chanceCapture Chance de base de capture.
 * @property inventaire Liste où va le monstre capturé quand l'équipe est pleine (la "boîte").
 *
 * ⚠ PROBLÈME n°6 : la boîte de monstres n'a rien à faire dans le Kube, elle doit
 * appartenir à l'[dresseur.Entraineur] (le TODO `boiteMonstre` de sa classe).
 * ⚠ PROBLÈME n°7 : dépendance à la variable globale `joueur` (import de Main.kt).
 */
class MonsterKube(id: Int, nom: String, description: String, var chanceCapture: Double, var inventaire : MutableList<IndividuMonstre>) : Item(id, nom, description), Utilisable {

    /**
     * Tente de capturer [cible].
     * @return true si capturé, false sinon.
     */
    override fun utiliser(cible: IndividuMonstre): Boolean {
        println("Vous lancez un MonsterKube !")

        // Un monstre déjà possédé par un dresseur ne peut pas être capturé
        if (cible.entraineur != null) {
            // ⚠ PROBLÈME n°3 : affiche l'objet Entraineur (Entraineur@1a2b3c), il faut `.nom`
            println("Vous ne pouvez pas capturer ce monstre, il appartient à ${cible.entraineur}")
            return false
        }

        // Plus le monstre est blessé (ratio bas), plus la capture est facile (coeff. de 0.5 à 1.5)
        val ratioPv = cible.pv.toDouble() / cible.pvMax
        // ⚠ PROBLÈME n°5 : échelle incohérente. Si chanceCapture est une proportion (0.4),
        // le résultat est toujours < 5 et le max() renvoie toujours 5 %. Il faut choisir : pourcentage ou 0..1.
        val chanceEffective = maxOf(chanceCapture * (1.5 - ratioPv), 5.0)
        val tirage = (1..100).random() // tirage entre 1 et 100

        // Tirage trop haut = échec
        if (tirage > chanceEffective) {
            println("Dommage, le Kube n'a pas pu capturer le monstre !")
            return false
        }

        println("Le monstre est capturé !")
        cible.entraineur = joueur   // le monstre appartient désormais au joueur
        cible.postCapture()         // demande un surnom (bloquant : attend la saisie)
        // Équipe limitée à 6 : au-delà, le monstre part dans la boîte
        if (joueur.equipeDeMonstre.size >= 6) {
            inventaire.add(cible)
        } else {
            joueur.equipeDeMonstre.add(cible)
        }
        return true
    }

}
