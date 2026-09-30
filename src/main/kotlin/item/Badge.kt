package item

/**
 * Un badge est un objet de collection (Item) sans effet sur un monstre.
 *
 * ⚠ PROBLÈME n°9 : le paramètre `utilisable` n'est ni `val`/`var` ni utilisé :
 * il est ignoré. Un badge n'implémente pas [Utilisable], donc `utilisable` ne sert à rien.
 * Supprimez-le, ou faites implémenter l'interface si un badge doit avoir un effet.
 */
class Badge(id: Int, nom: String, description: String, utilisable:Boolean): Item(id, nom, description){

}
