package pro.seinksansdoozebank.app512.util;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import pro.seinksansdoozebank.app512.R;
import pro.seinksansdoozebank.app512.model.ListCar;
import pro.seinksansdoozebank.app512.model.Purchase;

public class PurchaseAdapter extends BaseAdapter {
    private final ArrayList<Purchase> purchaseList;
    private LayoutInflater inflater;
    private AppCompatActivity listener;
    private Bitmap carBitmap;

    private final Object synchro = new Object();

    public PurchaseAdapter(AppCompatActivity activity, ArrayList<Purchase> purchases) {
        this.inflater = LayoutInflater.from(activity.getApplicationContext());
        this.listener = activity;
        this.purchaseList = purchases;
        Log.e(TAG, "PurchaseAdapter: "+purchases  );
    }

    @Override
    public int getCount() {
        Log.e(TAG, "getCount: "+purchaseList.size() );
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View layoutItem;

        layoutItem = view == null ? inflater.inflate(R.layout.purchase_item, viewGroup, false) : view;
//        Animation anim = AnimationUtils.loadAnimation(listener.getApplicationContext(), R.anim.right_to_left);
//        anim.setDuration(anim.getDuration()+(i* 30L));
//        layoutItem.startAnimation(anim);

        TextView carName = layoutItem.findViewById(R.id.purchase_product_name);
        carName.setText(this.purchaseList.get(i).getMarque()+" "+this.purchaseList.get(i).getCarName());
        carName.setTypeface(Typeface.DEFAULT_BOLD);
        ImageView imageView = layoutItem.findViewById(R.id.product_image);
        new Thread(()->{
            try {
                synchronized (synchro){
                    System.out.println("Chargement de l'image...");
                    this.carBitmap = BitmapFactory.decodeStream((InputStream)new URL(ListCar.getInstance().get(purchaseList.get(i).getId()).getImage()).getContent());
                    System.out.println("Image chargée");
                    synchro.notify();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        synchronized (synchro){
            try {
                System.out.println("Attente de l'image...");
                synchro.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            imageView.setImageBitmap(this.carBitmap);
        }

        TextView carPrice = layoutItem.findViewById(R.id.product_price1);
        carPrice.setText(String.format("%.2f€",this.purchaseList.get(i).getPrice()));

        TextView deliveryDate = layoutItem.findViewById(R.id.delivery_date);
        deliveryDate.setText(this.purchaseList.get(i).getDeliveryDate());

        TextView deliveryPlace = layoutItem.findViewById(R.id.delivery_place);
        deliveryPlace.setText(this.purchaseList.get(i).getDeliveryPlace());
        return layoutItem;
    }
}
