import dresseur.Entraineur
import monstre.EspeceMonstre

/*
#=============== Entraineurs ===============#
*/

val joueur = Entraineur(1,"Adrien",0,"cyan")
val rival = Entraineur(2,"Peter",1000,"rouge")

/*
#=============== Monstres ===============#
*/

val tuntung = EspeceMonstre(1,"aquamy", "brainrot",10,8,4,5,7,4,2.0,2.0,2.0,2.0,2.0,2.0,"vivant","fière")
fun main() {

    joueur.afficheDetail()
    rival.afficheDetail()
    joueur.argents+=1500
    joueur.afficheDetail()
    print(tuntung.afficheArt())
}

/**
 * Change la couleur du message donné selon le nom de la couleur spécifié.
 * Cette fonction utilise les codes d'échappement ANSI pour appliquer une couleur à la sortie console. Si un nom de couleur
 * non reconnu ou une chaîne vide est fourni, aucune couleur n'est appliquée.
 *
 * @param message Le message auquel la couleur sera appliquée.
 * @param couleur Le nom de la couleur à appliquer (ex: "rouge", "vert", "bleu"). Par défaut c'est une chaîne vide, ce qui n'applique aucune couleur.
 * @return Le message coloré sous forme de chaîne, ou le même message si aucune couleur n'est appliquée.
 */
fun changeCouleur(message: String, couleur:String=""): String {
    val reset = "\u001B[0m"
    val codeCouleur = when (couleur.lowercase()) {
        "rouge" -> "\u001B[31m"
        "vert" -> "\u001B[32m"
        "jaune" -> "\u001B[33m"
        "bleu" -> "\u001B[34m"
        "magenta" -> "\u001B[35m"
        "cyan" -> "\u001B[36m"
        "blanc" -> "\u001B[37m"
        else -> "" // pas de couleur si non reconnu
    }
    return "$codeCouleur$message$reset"
}