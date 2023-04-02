package pro.seinksansdoozebank.app512.util;


import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import pro.seinksansdoozebank.app512.R;
import pro.seinksansdoozebank.app512.model.ListCar;
import pro.seinksansdoozebank.app512.views.MainActivity;

public class CarAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private CarAdapterListener listener;


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

        TextView carBrand = layoutItem.findViewById(R.id.product_brand);
        ListCar.getInstance();
//        synchronized (MainActivity.sync) {
//            try {
//                System.out.println("Waiting for sync");
//                MainActivity.sync.wait();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
        carBrand.setText(ListCar.getInstance().get(i).getMarque());


        carBrand.setTypeface(Typeface.DEFAULT_BOLD);


        TextView carName = layoutItem.findViewById(R.id.product_name);
        carName.setText(ListCar.getInstance().get(i).getName());

        TextView carPrice = layoutItem.findViewById(R.id.product_price);
        carPrice.setText(String.format("%.2f€",ListCar.getInstance().get(i).getPrice()));

        layoutItem.setOnClickListener(c -> listener.onClickProduct(ListCar.getInstance().get(i)));

        return layoutItem;
    }
}
