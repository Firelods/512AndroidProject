package pro.seinksansdoozebank.app512.util;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pro.seinksansdoozebank.app512.R;
import pro.seinksansdoozebank.app512.model.ListCar;
import pro.seinksansdoozebank.app512.model.Purchase;

/**
 * Adapter pour la liste des achats sur l'activité PurchaseActivity
 */
public class PurchaseAdapter extends BaseAdapter {
    /**
     * Liste des achats
     */
    private final ArrayList<Purchase> purchaseList;
    /**
     * Inflater pour les vues
     */
    private final LayoutInflater inflater;
    /**
     * Le listener qui réceptionne les événements
     */
    private final AppCompatActivity listener;

    public PurchaseAdapter(AppCompatActivity activity, ArrayList<Purchase> purchases) {
        this.inflater = LayoutInflater.from(activity.getApplicationContext());
        this.listener = activity;
        this.purchaseList = purchases;
    }

    @Override
    public int getCount() {
        return purchaseList.size();
    }

    @Override
    public Object getItem(int i) {
        return purchaseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View layoutItem;
        // Si la vue n'existe pas, on la crée grâce à l'inflater
        layoutItem = view == null ? inflater.inflate(R.layout.purchase_item, viewGroup, false) : view;

        //Ajout d'une animation d'entrée
        Animation anim = AnimationUtils.loadAnimation(listener.getApplicationContext(), R.anim.right_to_left);
        anim.setDuration(anim.getDuration() + (i * 30L));
        layoutItem.startAnimation(anim);

        //Récupération des éléments de la vue et remplissage de ceux-ci
        TextView carName = layoutItem.findViewById(R.id.purchase_product_name);
        carName.setText(this.purchaseList.get(i).getMarque() + " " + this.purchaseList.get(i).getCarName());
        carName.setTypeface(Typeface.DEFAULT_BOLD);

        ImageView imageView = layoutItem.findViewById(R.id.product_image);
        Picasso.get().load(ListCar.getInstance().get(purchaseList.get(i).getId()).getImage()).into(imageView);

        TextView carPrice = layoutItem.findViewById(R.id.product_price1);
        carPrice.setText(String.format("%.2f€", this.purchaseList.get(i).getPrice()));

        TextView deliveryDate = layoutItem.findViewById(R.id.delivery_date);
        deliveryDate.setText(this.purchaseList.get(i).getDeliveryDate());

        TextView deliveryPlace = layoutItem.findViewById(R.id.delivery_place);
        deliveryPlace.setText(this.purchaseList.get(i).getDeliveryPlace());

        return layoutItem;
    }
}
