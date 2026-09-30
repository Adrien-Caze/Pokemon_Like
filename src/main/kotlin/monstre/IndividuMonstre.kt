package monstre

import changeCouleur
import dresseur.Entraineur
import kotlin.io.println
import kotlin.random.Random
import kotlin.math.pow


/**
 * Un monstre concret (une instance d'une [EspeceMonstre]), avec ses stats propres.
 *
 * @property espece L'espèce (statistiques de base).
 * @property entraineur Son propriétaire, null s'il est sauvage.
 * @param expInit Expérience de départ (peut déclencher des montées de niveau).
 */
class IndividuMonstre(val id:Int, var nom:String,var espece: EspeceMonstre,var entraineur: Entraineur?, expInit:Double){

    /** Renvoie `nombre` ou `-nombre` au hasard (variation aléatoire des stats). */
    private fun calculStats(nombre:Int):Int{
        val signes = listOf("-","+")
        val result = signes.random()
        if (result == "-"){
            return -nombre
        }
        else{
            return nombre
        }

    }
    var niveau:Int = 1
    // Stats individuelles = base de l'espèce ± 2 (± 5 pour les PV)
    var attaque = espece.baseAttaque+calculStats(2)
    var defense = espece.baseDefense+calculStats(2)
    var vitesse = espece.baseVitesse+calculStats(2)
    var attaqueSpe = espece.baseAttaqueSpe+calculStats(2)
    var defenseSpe = espece.baseDefenseSpe+calculStats(2)
    var pvMax = espece.basePv+calculStats(5)
    // Multiplicateur de croissance propre à l'individu (entre 0.5 et 2.0)
    val potentiel = Random.nextDouble(0.5,2.0)

    /**
     * Expérience courante. Le setter fait monter de niveau tant que le palier est dépassé,
     * en reportant le surplus.
     * ⚠ PROBLÈME n°14 : fragile, car levelUp() remet aussi `exp = 0` (rappel récursif du setter)
     * avant que `field = surPlus` ne rétablisse la valeur. Supprimer `exp = 0` dans levelUp()
     * simplifierait tout. De plus, le `get() = field` est inutile (comportement par défaut).
     */
    var exp: Int = 0
        get() = field
        set(newExp) {
            field = newExp
            while (field >= palierExp()) {
                val surPlus = field - palierExp().toInt() // exp restante après le palier
                levelUp()
                field = surPlus
                println("Le monstre $nom est maintenant niveau $niveau !")
            }
        }

    init {
        this.exp = expInit.toInt() // applique le setter et déclenche un éventuel level-up
    }

    /**
     * Points de vie courants, toujours bornés entre 0 et pvMax.
     * ⚠ PROBLÈME n°15 : déclaré APRÈS le bloc init : si un level-up a lieu dans init,
     * pv prend la nouvelle pvMax, ce qui marche par chance. Ne pas modifier cet ordre.
     * Aussi : à la montée de niveau, pvMax augmente mais pas pv (pas de soin).
     */
    var pv: Int = pvMax
        get() = field
        set(nouveauPv) {
            field = nouveauPv.coerceIn(0, pvMax)
        }

    /** Exp nécessaire pour passer au niveau suivant : 100 × niveau². (Double, d'où "100.0" à l'affichage.) */
    fun palierExp():Double{
        val result = 100 * (niveau).toDouble().pow(2)
        return result
    }

    /** Monte d'un niveau et augmente chaque stat de (mod × potentiel ± aléatoire). */
    fun levelUp(){
        this.niveau+=1
        exp = 0
        attaque += ((espece.modAttaque * potentiel) + calculStats(2)).toInt()
        defense += ((espece.modDefense * potentiel)+calculStats(2)).toInt()
        vitesse += ((espece.modVitesse * potentiel)+calculStats(2)).toInt()
        attaqueSpe += ((espece.modAttaqueSpe * potentiel)+calculStats(2)).toInt()
        defenseSpe += ((espece.modDefenseSpe * potentiel)+calculStats(2)).toInt()
        pvMax += ((espece.modPv * potentiel)+calculStats(5)).toInt()
    }

    /**
     * Attaque physique sur [rival] : dégâts = attaque - défense/2, minimum 1.
     * (Attaque spéciale, types et vitesse pas encore utilisés.)
     */
    fun attaquer(rival: IndividuMonstre){

        var degatsTotal = this.attaque - (rival.defense/2)

        // Toujours au moins 1 dégât
        if(degatsTotal < 1){
            degatsTotal = 1
        }
        rival.pv -= degatsTotal
        println("${this.nom} inflige $degatsTotal dégats à ${rival.nom}")
    }

    /** Propose de renommer le monstre ; boucle jusqu'à obtenir une réponse valide. */
    fun renommer(){

        while(true) {
            println("Renommer ${this.nom} ? O/n")
            val choix = readln()
            if(choix == "o" || choix == "O"){
                print("Renommer en : ")
                val nouveauNom = readln()
                this.nom = nouveauNom
                break

            }
            else if(choix == "n" || choix == "N"){
                break
            }
            else{
                println(changeCouleur("Option invalide !","rouge"))
            }
        }
    }

    /**
     * Demande un surnom après une capture (nom vide = garder le nom par défaut),
     * avec confirmation. Boucle jusqu'à validation.
     * ⚠ PROBLÈME n°16 : le prompt "Nommer ..." n'est affiché qu'une fois : après un "n" ou une
     * option invalide, le programme attend une saisie sans rien afficher. Et la logique
     * est en doublon avec renommer().
     */
    fun postCapture() {

        print("Nommer ${this.nom} : ")

        while (true) {

            val nouveauNom = readln()

            if (nouveauNom.isBlank()) {
                // Aucun nom saisi : confirmer de garder le nom actuel
                print("Êtes-vous sûr de vouloir garder le nom ${this.nom} ? O/n : ")

                when (readln().lowercase()) {
                    "o" -> break
                    "n" -> continue
                    else -> println(changeCouleur("Option invalide !", "rouge"))
                }

            } else {
                // Nom saisi : confirmer le nouveau nom
                print("Êtes-vous sûr de vouloir le nommer $nouveauNom ? O/n : ")

                when (readln().lowercase()) {
                    "o" -> {
                        this.nom = nouveauNom
                        break
                    }
                    "n" -> continue
                    else -> println(changeCouleur("Option invalide !", "rouge"))
                }
            }
        }
    }

    /**
     * Construit la fiche du monstre (art ASCII + nom, niveau, exp, PV, stats colorées).
     * ⚠ PROBLÈME n°13 : plante si le fichier d'art de l'espèce est introuvable (voir afficheArt).
     * Aussi : "Exp : x/100.0" est affiché en Double, convertir palierExp() en Int.
     */
    fun afficheDetail():String{
        val msg = changeCouleur("=============================\n","vert") +
                "Nom : ${this.nom}   Niveau : ${this.niveau}\n" +
                "Exp : ${this.exp}/${palierExp()}\n" +
                "PV : ${this.pv}/${this.pvMax}\n" +
                changeCouleur("=============================\n","vert") +
                changeCouleur("Atq : ","rouge")+"${this.attaque}   "+changeCouleur("Def : ","vert")+"${this.defense}   "+changeCouleur("Vitesse : ","cyan")+"${this.vitesse}\n" +
                changeCouleur("AtqSpe : ","rouge")+"${this.attaqueSpe}   "+changeCouleur("DefSpe : ","vert")+"${this.defenseSpe}\n" +
                changeCouleur("=============================\n","vert")
        return espece.afficheArt()+msg
    }


}
