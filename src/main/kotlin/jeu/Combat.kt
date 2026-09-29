package jeu

import joueur
import monstre.IndividuMonstre

class Combat(monstreJoueur : IndividuMonstre, rival: IndividuMonstre) {

    fun gameOver(): Boolean{
        var result = false
        for(i in joueur.equipeMonstre){
            if(i.pv != 0){
                result = false
            }else{
                result =  true
            }
        }
        return result
    }

    fun joueurGagne(){

    }
}