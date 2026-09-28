package monstre

import dresseur.Entraineur
import kotlin.random.Random
import kotlin.math.pow


class IndividuMonstre(val id:Int, var nom:String,var espece: EspeceMonstre,var entraineur: Entraineur,expInit:Double){

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

    fun montrerStats():String{
        val msg = "PV : ${this.pv}\n" +
                "Niveau : ${this.niveau}\n" +
                "XP : ${this.exp}/${palierExp()}\n" +
                "Attaque : ${this.attaque}\n" +
                "Défense : ${this.defense}\n" +
                "Vitesse : ${this.vitesse}\n" +
                "Attaque Spe : ${this.attaqueSpe}\n" +
                "Defense Spe : ${this.defenseSpe}\n" +
                "PV max : ${this.pvMax}\n" +
                "Potentel : ${this.potentiel}\n"
        return msg
    }

    fun attaquer(rival: IndividuMonstre){

        var degatsTotal = this.attaque - (rival.defense/2)

        if(degatsTotal < 1){
            degatsTotal = 1
        }
        rival.pv -= degatsTotal
        println("${this.nom} inflige $degatsTotal dégats à ${rival.nom}")
    }


}