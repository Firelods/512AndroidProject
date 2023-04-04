package pro.seinksansdoozebank.app512.util;

import android.content.Context;

import pro.seinksansdoozebank.app512.model.Car;

/**
 * Interface pour les actions de l'adapter
 */
public interface CarAdapterListener {
    /**
     * Action sur le click d'un produit
     * @param item voiture cliquée
     */
    void onClickProduct(Car item);

    /**
     * Action sur le click d'une image
     * @param item voiture cliquée
     */
    void onClickImage(Car item);

    /**
     * Réccupère le contexte
     * @return le contexte de l'activité
     */
    Context getContext();
}
