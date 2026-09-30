package dresseur
import changeCouleur
import item.Item
import item.Utilisable
import monstre.IndividuMonstre

/**
 * Représente un entraîneur dans le contexte du jeu.
 *
 * Un entraîneur gère une équipe de monstres et un sac d'objets, et possède de l'argent.
 *
 * @property id L'identifiant unique de l'entraîneur.
 * @property nom Le nom de l'entraîneur.
 * @property argents La quantité d'argent en possession de l'entraîneur.
 * @property couleur Couleur d'affichage du nom (voir changeCouleur dans Main.kt).
 * @property sacAItem Objets possédés.
 * @property equipeDeMonstre Équipe ; le monstre à l'index 0 est le monstre "actif".
 */
class Entraineur(
    var id: Int,
    var nom: String,
    var argents:Int,
    var couleur: String,
    var sacAItem : MutableList<Item>,
    var equipeDeMonstre: MutableList<IndividuMonstre>
    //TODO equipeMonstre
    //TODO boiteMonstre  <- à ajouter ici (voir problème n°6)
    //TODO sacAKube
) {
    /**
     * Affiche le nom (coloré) et l'argent de l'entraîneur.
     */
    fun afficheDetail(){
        println("Dresseur : "+ changeCouleur(this.nom,couleur))
        println("Argents: ${this.argents} ")
    }

    /**
     * Affiche le contenu du sac et permet de choisir un objet à utiliser
     * sur le monstre actuellement équipé.
     *
     * ⚠ PROBLÈME n°1 : l'objet est toujours utilisé sur equipeDeMonstre[0] (son propre monstre).
     * Un Kube doit viser le monstre ADVERSE. La cible doit être passée en paramètre.
     * ⚠ PROBLÈME n°2 : equipeDeMonstre[0] plante (IndexOutOfBounds) si l'équipe est vide.
     */
    fun voirInventaire() {
        // Rien à afficher si le sac est vide
        if (sacAItem.isEmpty()) {
            println("Votre sac est vide.")
            return
        }

        // Affichage numéroté des objets (le tag signale ceux qui n'implémentent pas Utilisable)
        println("=== Inventaire de ${this.nom} ===")
        sacAItem.forEachIndexed { index, item ->
            val tag = if (item is Utilisable) "" else " (non utilisable)"
            println("${index + 1}. ${item.nom} - ${item.description}$tag")
        }
        println("0. Retour")

        // Lecture du choix ; toIntOrNull renvoie null si ce n'est pas un nombre
        print("Choisissez un objet : ")
        val choix = readLine()?.toIntOrNull()

        // Validation de la saisie
        if (choix == null || choix < 0 || choix > sacAItem.size) {
            println("Choix invalide.")
            return
        }
        if (choix == 0) return

        val item = sacAItem[choix - 1] // -1 : l'affichage commence à 1

        if (item is Utilisable) { // smart cast : item est utilisable dans ce bloc
            val effet = item.utiliser(equipeDeMonstre[0])
            if (effet) {
                println("${item.nom} a été utilisé sur ${equipeDeMonstre[0].nom} !")
                sacAItem.remove(item) // retirer si l'objet est consommable
            } else {
                // ⚠ PROBLÈME n°8 : un Kube raté n'est pas retiré du sac (Kubes infinis en cas d'échec)
                println("${item.nom} n'a eu aucun effet.")
            }
        } else {
            println("${item.nom} ne peut pas être utilisé.")
        }
    }

    /**
     * Affiche l'équipe et permet de choisir le monstre à mettre en première ligne.
     *
     * ⚠ PROBLÈME n°4 : Combat.monstreJoueur est un `val` : après ce changement, le combat
     * continue avec l'ancien monstre. De plus les monstres K.O. ne sont pas refusés (test commenté).
     * ⚠ Ici on remplace equipeDeMonstre[0] par `nouveau` sans le retirer de sa place d'origine :
     * le monstre apparaît en double et l'ancien actif disparaît. Il faut échanger (swap).
     */
    fun changerMonstre() {
        if (equipeDeMonstre.isEmpty()) {
            println("Votre équipe est vide.")
            return
        }

        // Affichage de l'équipe, le monstre actif est marqué (comparaison par référence ===)
        println("=== Équipe de ${this.nom} ===")
        equipeDeMonstre.forEachIndexed { index, monstre ->
            val marque = if (monstre === equipeDeMonstre[0]) " (équipé)" else ""
            println("${index + 1}. ${monstre.nom}$marque")
        }
        println("0. Retour")

        print("Choisissez un monstre : ")
        val choix = readLine()?.toIntOrNull()

        // Validation de la saisie
        if (choix == null || choix < 0 || choix > equipeDeMonstre.size) {
            println("Choix invalide.")
            return
        }
        if (choix == 0) return

        val nouveau = equipeDeMonstre[choix - 1]

        // Inutile de changer pour le monstre déjà actif
        if (nouveau === equipeDeMonstre[0]) {
            println("${nouveau.nom} est déjà équipé.")
            return
        }
        // Ajoutez ici un test de KO si votre IndividuMonstre a des PV, ex. :
        // if (nouveau.pv <= 0) { println("${nouveau.nom} est K.O. !"); return }

        equipeDeMonstre[0] = nouveau // voir avertissement ci-dessus
        println("${nouveau.nom} est maintenant votre monstre actif !")
    }
}
