package me.cekpedia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    private SliderLayout sliderLayout;
    private EditText searchView;
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    FirebaseAuth mAuth;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout mMainFrame;
    private FirebaseUser mFirebaseUser;
    private ProfileFragment accountFragment;
    private HomeFragment homeFragment;
    private NearMeFragment nearMeFragment;
    private FavouriteFragment favFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null){
            mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            startActivity(new Intent(this, LoginActivity.class));
        }
//        final ArrayList<Image> cekpedia = new ArrayList<>();
//
//        cekpedia.add(new Image(R.drawable.ic_masjid, "MASJID"));
//        cekpedia.add(new Image(R.drawable.ic_wisata, "Wisata"));


//
//        searchView = (EditText) findViewById(R.id.cari);
//        searchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, ListCariActivity.class));
//                setTitle("Trafinder");
//                HomeFragment fragment = new HomeFragment();
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.frameLayout, fragment);
//                fragmentTransaction.commit();
//            }
//        });

        // CLickable Bottom Navbar
        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.btm_nav);

        accountFragment = new ProfileFragment();
        homeFragment = new HomeFragment();
        favFragment = new FavouriteFragment();
        nearMeFragment = new NearMeFragment();

        setFragment(homeFragment);

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.action_home :
                        setFragment(homeFragment);
                        return true;
                    case R.id.action_loc :
                        setFragment(nearMeFragment);
                        return true;
                    case R.id.action_help :
                        setFragment(favFragment);
                        return true;
                    case R.id.action_account :
                        setFragment(accountFragment);
                        return true;

                        default:
                            return false;
                }
            }
        });


    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
    public void logoutout(View view){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setMessage("Yakin Ingin Keluar?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ya",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mAuth.signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
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
    }
}

