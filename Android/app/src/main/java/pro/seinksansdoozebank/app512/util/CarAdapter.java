package pro.seinksansdoozebank.app512.util;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import pro.seinksansdoozebank.app512.R;
import pro.seinksansdoozebank.app512.model.ListCar;
import pro.seinksansdoozebank.app512.views.MainActivity;

public class CarAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private CarAdapterListener listener;

    private final Object synchro = new Object();

    public CarAdapter(CarAdapterListener listener) {
        this.inflater = LayoutInflater.from(listener.getContext());
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return ListCar.getInstance().size();
    }

    @Override
    public Object getItem(int i) {
        return ListCar.getInstance().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View layoutItem;

        layoutItem = view == null ? inflater.inflate(R.layout.car_item, viewGroup, false) : view;
        Animation anim = AnimationUtils.loadAnimation(listener.getContext(), R.anim.right_to_left);
        anim.setDuration(anim.getDuration()+(i* 30L));
        layoutItem.startAnimation(anim);

        TextView carBrand = layoutItem.findViewById(R.id.product_brand);
        ListCar.getInstance();
        carBrand.setText(ListCar.getInstance().get(i).getMarque());
        carBrand.setTypeface(Typeface.DEFAULT_BOLD);
        ImageView imageView = layoutItem.findViewById(R.id.product_image);
        Picasso.get().load(ListCar.getInstance().get(i).getImage()).into(imageView);

        imageView.setOnClickListener(c ->
        {
            listener.onClickImage(ListCar.getInstance().get(i));
        });

        TextView carName = layoutItem.findViewById(R.id.product_name);
        carName.setText(ListCar.getInstance().get(i).getName());

        TextView carPrice = layoutItem.findViewById(R.id.product_price);
        carPrice.setText(String.format("%.2f€",ListCar.getInstance().get(i).getPrice()));

        layoutItem.setOnClickListener(c -> listener.onClickProduct(ListCar.getInstance().get(i)));

        return layoutItem;
    }
}
