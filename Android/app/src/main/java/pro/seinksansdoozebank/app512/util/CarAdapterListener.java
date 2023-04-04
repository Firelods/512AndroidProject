package pro.seinksansdoozebank.app512.util;

import android.content.Context;

import pro.seinksansdoozebank.app512.model.Car;

public interface CarAdapterListener {
    void onClickProduct(Car item);
    void onClickImage(Car item);
    Context getContext();
}
