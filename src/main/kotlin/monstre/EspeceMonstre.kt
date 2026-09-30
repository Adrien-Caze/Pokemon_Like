package monstre
import java.io.File

/**
 * Une espèce de monstre (le "modèle", comme une fiche de Pokédex).
 *
 * //@property base Statistiques de départ d'un monstre de cette espèce.
 * //@property mod.. Gain de statistique par niveau (multiplié par le potentiel de l'individu).
 * @property description Texte descriptif.
 * @property particularites Particularités de l'espèce.
 * @property caracteres Caractère(s) de l'espèce.
 */
class EspeceMonstre(
    var id : Int,
    var nom: String,
    var type: String,
    val baseAttaque: Int,
    val baseDefense: Int,
    val baseVitesse: Int,
    val baseAttaqueSpe: Int,
    val baseDefenseSpe: Int,
    val basePv: Int,
    val modAttaque: Double,
    val modDefense: Double,
    val modVitesse: Double,
    val modAttaqueSpe: Double,
    val modDefenseSpe: Double,
    val modPv: Double,
    val description: String = "",
    val particularites: String = "",
    val caracteres: String = "",
) {
    /**
     * Charge l'art ASCII du monstre depuis un fichier texte.
     * @param deFace true = "front.txt", false = "back.txt".
     *
     * ⚠ PROBLÈME n°13 : chemin relatif au dossier de lancement ("src/main/resources/...").
     * Fonctionne dans l'IDE mais plante (FileNotFoundException) depuis un .jar, un autre dossier,
     * ou si le fichier manque (le nom doit correspondre au dossier en minuscules).
     * Correction : lire via le classpath, ex.
     * `EspeceMonstre::class.java.getResource("/art/${nom.lowercase()}/$nomFichier.txt")?.readText() ?: ""`
     */
    fun afficheArt(deFace: Boolean=true): String{
        val nomFichier = if(deFace) "front" else "back";
        val art= File("src/main/resources/art/${this.nom.lowercase()}/$nomFichier.txt").readText()
        // Remplace "/" par un caractère visuellement proche pour ne pas casser l'affichage
        val safeArt = art.replace("/", "∕")
        // Le fichier contient le texte littéral "\u001B" : on le transforme en vrai caractère d'échappement ANSI (couleurs)
        return safeArt.replace("\\u001B", "\u001B")

    }
}
