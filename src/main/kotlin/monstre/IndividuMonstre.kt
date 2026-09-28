package monstre

import dresseur.Entraineur
import kotlin.random.Random
import kotlin.math.pow


class IndividuMonstre(val id:Int = 1, var nom:String,var espece: EspeceMonstre,var entraineur: Entraineur,expInit:Double){

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
            while (field >= palierExp(niveau)) {
                val surPlus = field - palierExp(niveau).toInt()
                levelUp()
                field = surPlus
                println("Le monstre $nom est maintenant niveau $niveau !")
            }
        }

    var pv: Int = pvMax
        get() = field
        set(nouveauPv) {
            field = nouveauPv.coerceIn(0, pvMax)
        }

    fun palierExp(niveau:Int):Double{
        val result = 100 * (niveau-1).toDouble().pow(2)
        return result
    }

    fun levelUp(){
        this.niveau+=1
        exp = 0
        attaque = ((espece.modAttaque * potentiel) + calculStats(2)).toInt()
        defense = ((espece.modDefense * potentiel)+calculStats(2)).toInt()
        vitesse = ((espece.modVitesse * potentiel)+calculStats(2)).toInt()
        attaqueSpe = ((espece.modAttaqueSpe * potentiel)+calculStats(2)).toInt()
        defenseSpe = ((espece.modDefenseSpe * potentiel)+calculStats(2)).toInt()
        pvMax = ((espece.modPv * potentiel)+calculStats(5)).toInt()
    }


}