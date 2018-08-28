package me.cekpedia.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import me.cekpedia.Activity.LoginActivity;
import me.cekpedia.R;
import me.cekpedia.models.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    FirebaseAuth mAuth;
    View view;
    TextView st;
    EditText nama, email, nohp;
    String Nama, Email, NoHp, Uid, photoUrl, favorit;
    Typeface tf;
    FirebaseDatabase mDb;
    DatabaseReference database;
    FirebaseAuth firebaseAuth;
    Button btnedit;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        //Pengaturan Font
        st = (TextView) view.findViewById(R.id.toolbar_text);
        nama = view.findViewById(R.id.et_nama);
        email = view.findViewById(R.id.et_email);
        nohp = view.findViewById(R.id.et_no_hp);
        btnedit = view.findViewById(R.id.btn_edit_profil);
        tf = Typeface.createFromAsset(getActivity().getAssets(), "FRSCRIPT.TTF");
        st.setTypeface(tf);
        database = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth == null) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        mDb = FirebaseDatabase.getInstance();
        final DatabaseReference dataprofile = database.child(firebaseUser.getEmail().replace(".", ","));
//        dataprofile.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Map<String, Object> detailprofil = (Map<String, Object>) dataSnapshot.getValue();
//                Nama = detailprofil.get("user").toString();
//                Email = detailprofil.get("email").toString();
//                NoHp = detailprofil.get("nohp").toString();
//                Uid = detailprofil.get("uid").toString();
//                photoUrl = detailprofil.get("photoUrl").toString();
//                favorit = detailprofil.get("favourite").toString();
//                nama.setText(Nama);
//                email.setText(Email);
//                nohp.setText(NoHp);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(nama.getText().toString(), email.getText().toString(), photoUrl, Uid, favorit, nohp.getText().toString());
                database.child(firebaseUser.getEmail().replace(".", ",")).setValue(user);
                Toast.makeText(getActivity(), "simpan berhasil", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.option_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_bantuan:
                Toast.makeText(getActivity(), "Action bantuan clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_keluar:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Yakin Ingin Keluar?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Ya",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mAuth.signOut();
                                AuthUI.getInstance().signOut(getActivity());
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().finish();
                                dialog.cancel();

                            }
                        });
                builder1.setNegativeButton(
                        "Tidak",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
                return true;
        }

        return false;
    }

}
