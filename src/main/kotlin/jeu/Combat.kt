package jeu

import joueur
import monstre.IndividuMonstre

/**
 * Représente un combat entre le monstre du joueur et un monstre rival.
 *
 * ⚠ PROBLÈME n°4 : `monstreJoueur` est un `val` : si le joueur change de monstre
 * (option 3), le combat garde l'ancien. Le monstre actif doit être `var` ou lu depuis
 * joueur.equipeDeMonstre[0].
 * ⚠ Il n'y a aucune boucle de combat (tours, ordre selon la vitesse, fin de combat).
 */
class Combat(val monstreJoueur : IndividuMonstre, val monstreRival: IndividuMonstre) {

    /**
     * Le joueur a perdu si tous ses monstres sont K.O.
     *
     * ⚠ PROBLÈME n°10 : `result` est écrasé à chaque tour de boucle, seul le DERNIER monstre
     * compte. Si le dernier est K.O. mais pas les autres, renvoie true à tort.
     * Correction : `joueur.equipeDeMonstre.all { it.pv <= 0 }`
     */
    fun gameOver(): Boolean{
        var result = false
        for(i in joueur.equipeDeMonstre){
            if(i.pv != 0){
                result = false
            }else{
                result =  true
            }
        }
        return result
    }

    /**
     * Vérifie si le joueur a gagné (rival K.O.) ou capturé le rival, et donne l'expérience.
     *
     * ⚠ PROBLÈME n°11 : `monstreJoueur.exp + CalcExp` calcule une valeur puis la jette :
     * l'exp n'est jamais ajoutée. Il faut `monstreJoueur.exp += CalcExp.toInt()` (exp est un Int,
     * CalcExp un Double). L'exp du rival est de plus sa propre exp courante, pas une récompense.
     * ⚠ Un monstre capturé ne devrait pas donner d'exp de victoire : à vérifier selon vos règles.
     */
    fun joueurGagne(): Boolean{
        var result = false
        if(monstreRival.pv <= 0){
            // Rival K.O. : victoire, 20 % de l'exp du rival
            println("${joueur.nom} a gagné !")
            val CalcExp = monstreRival.exp*0.20
            monstreJoueur.exp + CalcExp
            println("${monstreJoueur.nom} gagne $CalcExp exp")
            result = true
        }
        else{
            // Rival vivant mais appartenant au joueur = il a été capturé
            if(monstreRival.entraineur == joueur){
                println("${monstreRival.nom} a été capturé !")
                result = true
            }
            else{
                result = false
            }
        }
        return result
    }

    /**
     * Tour de l'adversaire : il attaque tant qu'il est en vie.
     */
    fun actionAdverse(){
        if(monstreRival.pv > 0){
            monstreRival.attaquer(monstreJoueur)
        }
    }

    /**
     * Tour du joueur : menu d'actions.
     * ⚠ PROBLÈME n°1 : "Utiliser un Objet" ne transmet pas le rival, donc un Kube ne peut
     * pas le cibler. Aucune réponse à une saisie invalide (le `when` n'a pas de `else`).
     */
    fun actionJoueur(){
        println("Choisisez votre action :\n" +
                "1 : Attaquer\n" +
                "2 : Utiliser un Objet\n" +
                "3 : Changer de Brainrot\n")
        when (readln()){
            "1" -> monstreJoueur.attaquer(monstreRival)
            "2" -> joueur.voirInventaire()
            "3" -> joueur.changerMonstre()
        }
    }
}
