package item

import monstre.IndividuMonstre

/**
 * Interface définissant le comportement d'un objet ou d'une action
 * pouvant être utilisé(e) sur un [IndividuMonstre].
 *
 * Les classes qui implémentent cette interface doivent fournir
 * une implémentation de la méthode [utiliser].
 */
interface Utilisable {

    /**
     * Applique l'effet de l'objet ou de l'action sur le monstre cible.
     *
     * @param cible Le [IndividuMonstre] sur lequel l'objet est utilisé.
     * @return `true` si l'action a eu un effet (ex. : capture réussie, soin appliqué),
     *         `false` sinon.
     *
     * ⚠ PROBLÈME n°8 : `false` mélange "aucun effet" et "échec mais objet consommé"
     * (un Kube raté est quand même perdu). Prévoir un résultat plus riche (enum/data class).
     */
    fun utiliser(cible: IndividuMonstre): Boolean
}
