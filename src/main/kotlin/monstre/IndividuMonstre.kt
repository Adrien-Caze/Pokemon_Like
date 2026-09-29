package monstre

import changeCouleur
import dresseur.Entraineur
import kotlin.io.println
import kotlin.random.Random
import kotlin.math.pow


class IndividuMonstre(val id:Int, var nom:String,var espece: EspeceMonstre,var entraineur: Entraineur?,expInit:Double){

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
    var attaque = espece.baseAttaque+calculStats(2)
    var defense = espece.baseDefense+calculStats(2)
    var vitesse = espece.baseVitesse+calculStats(2)
    var attaqueSpe = espece.baseAttaqueSpe+calculStats(2)
    var defenseSpe = espece.baseDefenseSpe+calculStats(2)
    var pvMax = espece.basePv+calculStats(5)
    val potentiel = Random.nextDouble(0.5,2.0)
    var exp: Int = 0
        get() = field
        set(newExp) {
            field = newExp
            while (field >= palierExp()) {
                val surPlus = field - palierExp().toInt()
                levelUp()
                field = surPlus
                println("Le monstre $nom est maintenant niveau $niveau !")
            }
        }

    init {
        this.exp = expInit.toInt() // applique le setter et déclenche un éventuel level-up
    }


    var pv: Int = pvMax
        get() = field
        set(nouveauPv) {
            field = nouveauPv.coerceIn(0, pvMax)
        }

    fun palierExp():Double{
        val result = 100 * (niveau).toDouble().pow(2)
        return result
    }

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

    fun attaquer(rival: IndividuMonstre){

        var degatsTotal = this.attaque - (rival.defense/2)

        if(degatsTotal < 1){
            degatsTotal = 1
        }
        rival.pv -= degatsTotal
        println("${this.nom} inflige $degatsTotal dégats à ${rival.nom}")
    }

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

    fun postCapture() {

        print("Nommer ${this.nom} : ")

        while (true) {

            val nouveauNom = readln()

            if (nouveauNom.isBlank()) {
                print("Êtes-vous sûr de vouloir garder le nom ${this.nom} ? O/n : ")

                when (readln().lowercase()) {
                    "o" -> break
                    "n" -> continue
                    else -> println(changeCouleur("Option invalide !", "rouge"))
                }

            } else {
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