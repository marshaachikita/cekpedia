package me.cekpedia;

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

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    private SliderLayout sliderLayout;
    private EditText searchView;
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;

    private BottomNavigationView bottomNavigationView;
    private FrameLayout mMainFrame;

    private ProfileFragment accountFragment;
    private HomeFragment homeFragment;
    private NearMeFragment nearMeFragment;
    private HelpFragment helpFragment;

    int[] gambar = {
            R.drawable.ic_masjid,
            R.drawable.ic_wisata,
            R.drawable.ic_penginapan,
            R.drawable.ic_rumah_sakit,
            R.drawable.ic_restoran,
            R.drawable.ic_supermarket,
            R.drawable.ic_sekolah,
            R.drawable.ic_transportasi,
            R.drawable.ic_input_lokasi,
            R.drawable.ic_spbu,
            R.drawable.ic_apotek,
            R.drawable.ic_bidan
        };
    String [] namaMenu = {
        "Masjid",
        "Wisata",
        "Penginapan",
        "Rumah Sakit",
        "Restoran",
        "Supermarket",
        "Sekolah",
        "Transportasi",
        "Input Lokasi",
        "SPBU",
        "Apotek",
        "Bidan"
    };
    public MainActivity(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        helpFragment = new HelpFragment();
        nearMeFragment = new NearMeFragment();

        setFragment(nearMeFragment);

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
                        setFragment(helpFragment);
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
}

