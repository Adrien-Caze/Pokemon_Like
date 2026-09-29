package item

import joueur
import monstre.IndividuMonstre

class MonsterKube(id: Int, nom: String, description: String, var chanceCapture: Double, var inventaire : MutableList<IndividuMonstre>) : Item(id, nom, description), Utilisable {

    override fun utiliser(cible: IndividuMonstre): Boolean {
        println("Vous lancez un MonsterKube !")

        if (cible.entraineur != null) {
            println("Vous ne pouvez pas capturer ce monstre, il appartient à ${cible.entraineur}")
            return false
        }

        val ratioPv = cible.pv.toDouble() / cible.pvMax
        val chanceEffective = maxOf(chanceCapture * (1.5 - ratioPv), 5.0)
        val tirage = (1..100).random()

        if (tirage > chanceEffective) {
            println("Dommage, le Kube n'a pas pu capturer le monstre !")
            return false
        }

        println("Le monstre est capturé !")
        cible.entraineur = joueur
        cible.postCapture()
        if (joueur.equipeMonstre.size >= 6) {
            inventaire.add(cible)
        } else {
            joueur.equipeMonstre.add(cible)
        }
        return true
    }

}
