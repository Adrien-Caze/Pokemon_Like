package jeu

import changeCouleur
import joueur
import monstre.IndividuMonstre
import java.io.File

/**
 * Représente un combat entre le monstre du joueur et un monstre rival.
 *
 * ⚠ PROBLÈME n°4 : `monstreJoueur` est un `val` : si le joueur change de monstre
 * (option 3), le combat garde l'ancien. Le monstre actif doit être `var` ou lu depuis
 * joueur.equipeDeMonstre[0].
 * ⚠ Il n'y a aucune boucle de combat (tours, ordre selon la vitesse, fin de combat).
 */
class Combat(val monstreJoueur : IndividuMonstre, val monstreRival: IndividuMonstre) {

    fun affichageGameOver():String{
        val art= File("src/main/resources/art/gameover/GameOver.txt").readText()
        // Remplace "/" par un caractère visuellement proche pour ne pas casser l'affichage
        val safeArt = art.replace("/", "∕")
        // Le fichier contient le texte littéral "\u001B" : on le transforme en vrai caractère d'échappement ANSI (couleurs)
        return safeArt.replace("\\u001B", "\u001B")
    }
    /**
     * Le joueur a perdu si tous ses monstres sont K.O.
     *
     * ⚠ PROBLÈME n°10 : `result` est écrasé à chaque tour de boucle, seul le DERNIER monstre
     * compte. Si le dernier est K.O. mais pas les autres, renvoie true à tort.
     * Correction : `joueur.equipeDeMonstre.all { it.pv <= 0 }`
     */
    fun gameOver(): Boolean {
        var result = false
        for (i in joueur.equipeDeMonstre) {
            if (i.pv != 0) {
                result = false
            } else {
                result = true
                println(affichageGameOver())
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
    fun joueurGagne(): Boolean {
        var result = false
        if (monstreRival.pv <= 0) {
            // Rival K.O. : victoire, 20 % de l'exp du rival
            println("${joueur.nom} a gagné !")
            val CalcExp = monstreRival.exp * 0.20
            monstreJoueur.exp + CalcExp
            println("${monstreJoueur.nom} gagne $CalcExp exp")
            result = true
        } else {
            // Rival vivant mais appartenant au joueur = il a été capturé
            if (monstreRival.entraineur == joueur) {
                println("${monstreRival.nom} a été capturé !")
                result = true
            } else {
                result = false
            }
        }
        return result
    }

    /**
     * Tour de l'adversaire : il attaque tant qu'il est en vie.
     */
    fun actionAdverse() {
        if (monstreRival.pv > 0) {
            monstreRival.attaquer(monstreJoueur)
        }
    }

    /**
     * Tour du joueur : menu d'actions.
     */
    fun actionJoueur(){
        if (gameOver()) {
            return
        }
        else {
            println(
                "Choisisez votre action :\n" +
                        "1 : Attaquer\n" +
                        "2 : Utiliser un Objet\n" +
                        "3 : Changer de Brainrot\n"
            )
            when (readln()) {
                "1" -> monstreJoueur.attaquer(monstreRival)
                "2" -> joueur.voirInventaire(monstreJoueur,monstreRival)
                "3" -> joueur.changerMonstre()
            }

        }
    }

    fun lancer(): Boolean{
        var commence = false
        gameOver()
        if(monstreJoueur.vitesse > monstreRival.vitesse){
            commence = true
            println("Votre Monstre : "+changeCouleur(monstreJoueur.nom,"cyan")+" a été plus rapide, vous commencez !")
            actionJoueur()
            actionAdverse()
        }else {
            println("Votre Rival : "+ changeCouleur(monstreRival.nom,"rouge")+ "a été plus rapide, Il commence !")
            actionAdverse()
            actionJoueur()
        }
        return commence
    }
    fun tour(quiCommence: Boolean){
        if(quiCommence){

        }
    }

    fun combat(){
        lancer()
        while(true){
            if(gameOver()){
                break
            }else if(joueurGagne()){
                break
            }else{

            }
        }

    }
}
