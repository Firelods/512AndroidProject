package pro.seinksansdoozebank.app512.util;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import pro.seinksansdoozebank.app512.R;

/**
 * Fragment pour la toolbar présente dans toutes les activités sauf la MainActivity
 */
public class ToolBarFragment extends Fragment {

    /**
     * Le traitement de l'événement du click sur le bouton retour
     */
    private View.OnClickListener listener;
    /**
     * Le titre de la toolbar
     */
    private String title;
    /**
     * La taille de la police du titre de la toolbar
     */
    private int size;

    /**
     * Le constructeur vide requis par Android
     */
    public ToolBarFragment() {
    }

    /**
     * Le constructeur avec les paramètres utilisé dans les activités
     * @param listener le traitement de l'événement du click sur le bouton retour
     * @param title le titre de la toolbar
     * @param size la taille de la police du titre de la toolbar
     */
    public ToolBarFragment(View.OnClickListener listener, String title, int size) {
        this.listener = listener;
        this.title = title;
        this.size = size;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //configuration de la vue de la toolbar lorsqu'elle est créée
        requireView().findViewById(R.id.tool_bar_back_button).setOnClickListener(listener);
        TextView textView = requireView().findViewById(R.id.toolbar_title);
        textView.setText(title);
        textView.setTextSize(size);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tool_bar, container, false);
    }
}