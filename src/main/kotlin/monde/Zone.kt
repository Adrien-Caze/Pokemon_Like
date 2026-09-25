package monde

import monstre.EspeceMonstre

class Zone(val id : Int, var nom : String, var expZone : Int, var monstres : MutableList<EspeceMonstre>, var zoneSuivante : Zone?, var zonePrecedante : Zone?){

    fun rencontreMonstre(){

    }

    fun genererMonstre(){

    }
}