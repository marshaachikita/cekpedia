package me.cekpedia.Fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.cekpedia.Adapter.ListCardAdapter;
import me.cekpedia.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NearMeFragment extends Fragment {

    View view;
    TextView st1, st2;
    Typeface tf1, tf2;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    public NearMeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_near_me, container, false);

        //Pengaturan Font
        st1 = (TextView) view.findViewById(R.id.toolbar_text);
        tf1 = Typeface.createFromAsset(getActivity().getAssets(), "scriptmtbold.ttf");
        st1.setTypeface(tf1);

//        st2 = (TextView) view.findViewById(R.id.id_nearme);
//        tf2 = Typeface.createFromAsset(getActivity().getAssets(), "Cambria_Math.ttf");
//        st2.setTypeface(tf2);

        //Pengaturan Recycler View
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ListCardAdapter();
        recyclerView.setAdapter(adapter);

        return view;
    }

}
