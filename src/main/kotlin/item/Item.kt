package item

/**
 * Classe de base de tous les objets du jeu (Kubes, badges, ...).
 * Elle est `open` pour pouvoir être héritée.
 *
 * @property id Identifiant unique (immuable, d'où le `val`).
 * @property nom Nom affiché (modifiable).
 * @property description Texte descriptif affiché dans l'inventaire.
 */
open class Item (val id : Int, var nom : String, var description : String){

}