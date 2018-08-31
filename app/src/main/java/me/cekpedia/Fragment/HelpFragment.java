package me.cekpedia.Fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.cekpedia.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {

    View view;
    TextView st;
    Typeface tf;

    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_help, container, false);

        //Pengaturan Font
        st = (TextView) view.findViewById(R.id.toolbar_text);
        tf = Typeface.createFromAsset(getActivity().getAssets(), "scriptmtbold.ttf");
        st.setTypeface(tf);

        return view;
    }

}
