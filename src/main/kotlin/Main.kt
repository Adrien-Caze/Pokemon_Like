import dresseur.Entraineur
import item.MonsterKube
import jeu.Combat
import monde.Zone
import monstre.EspeceMonstre
import monstre.IndividuMonstre

/*
#=============== Entraineurs ===============#
*/
// Le joueur : sac et équipe vides au départ.
// ⚠ PROBLÈME n°7 : variable globale utilisée par Combat et MonsterKube. À passer en paramètre.
val joueur = Entraineur(1,"Adrien",0,"cyan", mutableListOf(),mutableListOf())

/*
#=============== Monstres ===============#
*/
// Espèces. ⚠ PROBLÈME n°17 : `chimpanzini` et `sixSeven` ont le même id (2), et les noms de variables
// ne correspondent pas aux noms d'espèce ("springleaf", "aquamy", "flamkip"). Les stats sont identiques (copier-coller).
val tuntung = EspeceMonstre(1,"laoumi", "brainrot",15,13,14,15,27,54,2.0,2.0,2.0,2.0,2.0,2.0,"vivant","fière")
val chimpanzini = EspeceMonstre(2,"galum", "brainrot",19,10,10,15,27,54,2.0,2.0,2.0,2.0,2.0,2.0,"vivant","fière")
val sixSeven = EspeceMonstre(3,"bugsyface", "brainrot",15,13,14,15,27,54,2.0,2.0,2.0,2.0,2.0,2.0,"vivant","fière")

var monstre1 = IndividuMonstre(1, "springleaf", tuntung,null,1.25)
val monstre2 = IndividuMonstre(2, "aquamy", chimpanzini,null,2.0)
val monstre3 = IndividuMonstre(3, "sixseven", sixSeven,null,0.0)

// Liste des espèces d'une zone. ⚠ PROBLÈME n°12 : la MÊME liste est partagée par les deux zones
// (modifier l'une modifie l'autre) et elle ne contient qu'une espèce.
var monstres = mutableListOf(tuntung)
/*
#=============== Zones ===============#
*/
// ⚠ Les zones ne sont pas reliées (zoneSuivante/zonePrecedante sont null).
var zone1 = Zone(1, "Spawn", 25, monstres, null, null)
var zone2 = Zone(2, "Tuto", 30, monstres, null, null)


fun main() {

    // ⚠ PROBLÈME n°2 : on définit le propriétaire, mais monstre1 n'est PAS ajouté à
    // joueur.equipeDeMonstre (vide). Le menu "Utiliser un objet"/"Changer" plantera ou n'aura aucun effet.
    /*monstre1.entraineur = joueur
    println(monstre1.afficheDetail())
    println(monstre2.afficheDetail())
    println(monstre3.afficheDetail())*/
    val combat = Combat(monstre1,monstre2)
    val cube = MonsterKube(1,"SixSeven Kube","Kube de capture",1.25, mutableListOf<IndividuMonstre>())
    joueur.sacAItem.add(cube)
    print(combat.combat())


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
    val reset = "\u001B[0m" // code ANSI de remise à zéro (fin de la couleur)
    // Associe le nom français de la couleur à son code ANSI
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
