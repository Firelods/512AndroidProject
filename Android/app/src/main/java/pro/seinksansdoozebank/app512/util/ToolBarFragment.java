package pro.seinksansdoozebank.app512.util;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pro.seinksansdoozebank.app512.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ToolBarFragment extends Fragment {

    private View.OnClickListener listener;
    private String title;
    private int size;


    public ToolBarFragment() {
        // Required empty public constructor
    }

    public ToolBarFragment(View.OnClickListener listener, String title, int size) {
        this.listener = listener;
        this.title = title;
        this.size = size;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getView().findViewById(R.id.tool_bar_back_button).setOnClickListener(listener);
        TextView textView = getView().findViewById(R.id.toolbar_title);
        textView.setText(title);
        textView.setTextSize(size);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tool_bar, container, false);
    }
}