package me.cekpedia.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.cekpedia.Adapter.SliderGambarAdapter;
import me.cekpedia.Adapter.ImageListAdapter;
import me.cekpedia.Fragment.FavouriteFragment;
import me.cekpedia.R;
import me.cekpedia.models.ImageUpload;
import me.cekpedia.utils.Constants;

public class SubMenuActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int LOCATION_REQUEST = 500;
    private String namaMenu, address, title, nmphone, nomortelp, UserID, favorit, favorit1, favorit2, url, desk, item;
    private ImageButton info, MyLoc, RateUs, Telp, Sent, info1, detail, lokasi, telfon, favorite;
    private Button navigasi;
    Uri openNavigation;
    private ImageView imgMenu, fav, favfull;
    private double kordinatMaps1, kordinatMaps2;
    private FirebaseDatabase mDb;
    private TextView judul, alamat, detail_, deskripsi, telepon;
    private GoogleMap m_map;
    MapView mMapView;
    FirebaseStorage storage;
    StorageReference storageRef, imageRef;
    DatabaseReference database, mDatabase;
    ProgressDialog progressDialog;
    private String namaSub;
    public static final String FB_STORAGE_PATH = "images/";
    public static final String FB_DATABASE_PATH = "cekpedia/cekpediaItem";
    FirebaseAuth firebaseAuth;
    ListView listView;
    private RecyclerView mRecyclerView;
    private ImageListAdapter mAdapter;
    private List<ImageUpload> imgList;
    GoogleSignInAccount account;
    DatabaseReference mDatabaseRef;
    FirebaseUser FBUser;
    String[] FavoriteNameLike;
    int FavoriteNameSize;
    boolean FavoriteNameLikeIsEmpty;
    private int merchantNameLikeSize;
    private boolean isFavorite;
    private String favoritTemp;
    ConstraintLayout clInfo, clMap;
    LinearLayout info_, Telp_;
    ToggleButton toggle_info, toggle_map, toggle_telp, toggle_fav;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    TextView st;
    Typeface tf;

    int[] img = {
            R.drawable.hotelhorison,
            R.drawable.bandarahusein,
            R.drawable.bidandelima,
            R.drawable.apotekk24gerlong
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_menu);

        //Pengaturan ImageSlider Recycler View Baru
        recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        SliderGambarAdapter adapter = new SliderGambarAdapter(img, this);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        Telp_ = findViewById(R.id.Telp_);
        info_ = findViewById(R.id.info_);
        clMap = findViewById(R.id.const_maps);
        telepon = findViewById(R.id.tvTelp);
        toggle_info = findViewById(R.id.detail_info);
        toggle_map = findViewById(R.id.cari_lokasi);
        toggle_telp = findViewById(R.id.telepon);
        alamat = findViewById(R.id.tvalamat);
        judul = findViewById(R.id.tvjudul);
//        detail_ = findViewById(R.id.tvdetail);
        deskripsi = findViewById(R.id.tvdeskripsi);
        toggle_info.setChecked(true);
        toggle_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clMap.setVisibility(View.GONE);
                Telp_.setVisibility(View.GONE);
                toggle_map.setChecked(false);
                toggle_telp.setChecked(false);
                info_.setVisibility(View.VISIBLE);
                if (toggle_info.isChecked() == false){
                    info_.setVisibility(View.GONE);
                }
            }
        });

        toggle_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info_.setVisibility(View.GONE);
                Telp_.setVisibility(View.GONE);
                toggle_info.setChecked(false);
                toggle_telp.setChecked(false);
                clMap.setVisibility(View.VISIBLE);
                if (!toggle_map.isChecked()){
                    clMap.setVisibility(View.GONE);
                }
            }
        });
        toggle_telp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info_.setVisibility(View.GONE);
                clMap.setVisibility(View.GONE);
                toggle_info.setChecked(false);
                toggle_map.setChecked(false);
                Telp_.setVisibility(View.VISIBLE);
                if (!toggle_telp.isChecked()){
                    Telp_.setVisibility(View.GONE);
                }
            }
        });
        //Pengaturan Font
//        st = (TextView) findViewById(R.id.toolbar_text);
//        tf = Typeface.createFromAsset(this.getAssets(), "scriptmtbold.ttf");
//        st.setTypeface(tf);

//        info = findViewById(R.id.imageButtonInfo);
//        info1 = findViewById(R.id.imageButtonInfo1);
//        RateUs = findViewById(R.id.imageButtonRateUs);
//        Telp = findViewById(R.id.imageButtonTelp);
//        Sent = findViewById(R.id.imageButtonSent);
//        imgMenu = findViewById(R.id.gambarmenu);
//        mMapView = findViewById(R.id.map);
//        fav = findViewById(R.id.imgfav);
//        favfull = findViewById(R.id.imgfavmark);


//        navigasi = findViewById(R.id.buttonNavigation);
//        MyLoc = findViewById(R.id.imageButtonPos);
//        storage = FirebaseStorage.getInstance();
//        database = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);

        //creates a storage reference
//        storageRef = storage.getReference();
//        firebaseAuth = FirebaseAuth.getInstance();
//        FBUser = firebaseAuth.getCurrentUser();
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Constants.USER_KEY);
//        UserID = firebaseAuth.getCurrentUser().getUid();
//        fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);

//        final FirebaseDatabase FBfav = FirebaseDatabase.getInstance();
//        FavoriteNameLike = new String[50];
//        FavoriteNameSize = 0;
//        FavoriteNameLikeIsEmpty = false;
//

//        mMapView.onCreate(savedInstanceState);
//        mMapView.onResume(); // needed to get the map to display immediately
//        MapsInitializer.initialize(this.getApplicationContext());
//        RateUs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(SubMenuActivity.this, "Fitur ini masih dalam pengembangan", Toast.LENGTH_SHORT).show();
//            }
//        });
//        Sent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(SubMenuActivity.this, "Fitur ini masih dalam pengembangan", Toast.LENGTH_SHORT).show();
//            }
//        });
//        m_map.getUiSettings().setZoomControlsEnabled(true);
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
//            return;
//        }
//        m_map.setMyLocationEnabled(true);
        mDb = FirebaseDatabase.getInstance();
        Intent i = getIntent();
        namaMenu = i.getStringExtra("JUDUL").toLowerCase();
        namaSub = i.getStringExtra("SUB");
        item = namaMenu;

        final DatabaseReference submenu = mDb.getReference(FB_DATABASE_PATH).child(namaSub).child(namaMenu);
//        final DatabaseReference submenusub = mDb.getReference(Constants.USER_KEY).child(FBUser.getEmail().replace(".", ","));
//        final DatabaseReference submenufav = mDb.getReference(Constants.USER_KEY).child(FBUser.getEmail().replace(".", ","));
//            submenu.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    final Map<String, Object> detail = (Map<String, Object>) dataSnapshot.getValue();
//                    title = dataSnapshot.child("name").getValue(String.class).toUpperCase();
//                    address = dataSnapshot.child("lokasi").getValue(String.class).toString();
//                    ImageUpload imag = dataSnapshot.getValue(ImageUpload.class);
//                    judul.setText(title);
//                    alamat.setText(address);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
        submenu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final HashMap<String, Object> detailMenu = (HashMap<String, Object>) dataSnapshot.getValue();
                //String s = detailMenu.get("lokasi").toString();
                kordinatMaps1 = Double.parseDouble(detailMenu.get("lng").toString());
                kordinatMaps2 = Double.parseDouble(detailMenu.get("lat").toString());

//                Glide.with(getBaseContext())
//                        .load(detailMenu.get("url").toString())
//                        .into(imgMenu);

                title = detailMenu.get("name").toString().toUpperCase();
                address = detailMenu.get("lokasi").toString();
                nomortelp = detailMenu.get("number").toString();
                desk = detailMenu.get("deskripsi").toString();
                url = detailMenu.get("url").toString();
//                LOADFavorite();
                judul.setText(title);
                alamat.setText(address);
                telepon.setText(nomortelp);
                deskripsi.setText(desk);

                telepon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + nomortelp));
                        startActivity(intent);
                    }
                });
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    //                    @SuppressLint("MissingPermission")

                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        m_map = googleMap;
                        final LatLng klinik = new LatLng(kordinatMaps1, kordinatMaps2);
                        m_map.addMarker(new MarkerOptions().position(klinik).title(namaMenu));
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(klinik).zoom(17).build();
                        m_map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        m_map.setMyLocationEnabled(true);
//                        MyLoc.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                m_map.addMarker(new MarkerOptions().position(klinik).title(namaMenu));
//                                CameraPosition cameraPosition = new CameraPosition.Builder().target(klinik).zoom(17).build();
//                                m_map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//                            }
//                        });
//                        navigasi.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                String googleMaps = "com.google.android.apps.maps";
//                                openNavigation = Uri.parse("google.navigation:q=" + address);
//                                Intent intent = new Intent(Intent.ACTION_VIEW, openNavigation);
//                                intent.setPackage(googleMaps);
//                                if (intent.resolveActivity(getPackageManager()) != null) {
//                                    startActivity(intent);
//                                }
//                            }
//                        });

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        fav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!isFavorite){
//                    if (favorit.equals("")){
//                        submenufav.child("favourite").setValue("/" + namaMenu + "/");
//                        favorit = "/" + namaMenu + "/";
////                        Toast.makeText(SubMenuActivity.this, "1", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        submenufav.child("favourite").setValue(favorit + namaMenu + "/");
//                        favorit = favorit + namaMenu + "/";
////                        Toast.makeText(SubMenuActivity.this, "2", Toast.LENGTH_SHORT).show();
//                    }
//                    fav.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
//                    isFavorite=true;
//                    Toast.makeText(SubMenuActivity.this, "Add To Favorite", Toast.LENGTH_SHORT).show();
//                    merchantNameLikeSize=0;
//                    while (favorit.length()>1){
//                        favorit = favorit.substring(1);
//                        FavoriteNameLike[merchantNameLikeSize] = favorit.substring(0, favorit.indexOf("/"));
//                        favorit = favorit.substring(favorit.indexOf("/"));
//                        merchantNameLikeSize++;
//                    }
//                }
//                else {
//                    if (merchantNameLikeSize == 1){
//                        favorit = "";
//                    }else {
////                        Toast.makeText(SubMenuActivity.this, "size= "+merchantNameLikeSize, Toast.LENGTH_SHORT).show();
////                        Toast.makeText(SubMenuActivity.this, "namamenu= "+namaMenu, Toast.LENGTH_SHORT).show();
//                        favorit = "/";
////                        Toast.makeText(SubMenuActivity.this, "fav= "+favorit, Toast.LENGTH_SHORT).show();
//                        for (int i = 0; i < merchantNameLikeSize; i++){
//                            if (!FavoriteNameLike[i].equals(namaMenu)){
//                                favorit = favorit + FavoriteNameLike[i] + "/";
////                                Toast.makeText(SubMenuActivity.this, "fav= "+favorit, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                    submenufav.child("favourite").setValue(favorit);
//                    fav.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
//                    Toast.makeText(SubMenuActivity.this, "Remo  ve From Favorite", Toast.LENGTH_SHORT).show();
//                    isFavorite=false;
//                }
//            }
//        });
    }

        public void LOADFavorite(){
        FirebaseDatabase.getInstance().getReference(Constants.USER_KEY)
                .child(FBUser.getEmail().replace(".", ","))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final Map<String, Object> detailMenu = (Map<String, Object>) dataSnapshot.getValue();
                        favorit = detailMenu.get("favourite").toString();
                        favoritTemp = favorit;
                        if (!favoritTemp.equals("")){
                            merchantNameLikeSize=0;
                            while (favoritTemp.length()>1){
//                                Toast.makeText(SubMenuActivity.this, "size "+merchantNameLikeSize+"", Toast.LENGTH_SHORT).show();
                                favoritTemp = favoritTemp.substring(1);
                                FavoriteNameLike[merchantNameLikeSize] = favoritTemp.substring(0, favoritTemp.indexOf("/"));

//                                Toast.makeText(SubMenuActivity.this, "fav= "+FavoriteNameLike[merchantNameLikeSize], Toast.LENGTH_SHORT).show();
                                favoritTemp = favoritTemp.substring(favoritTemp.indexOf("/"));
                                merchantNameLikeSize++;
                            }
                            for (int i = 0; i < merchantNameLikeSize; i++){
                                if (FavoriteNameLike[i].equals(namaMenu)){
                                    fav.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                                    isFavorite = true;
//                                    Toast.makeText(SubMenuActivity.this, "is "+isFavorite+"", Toast.LENGTH_SHORT).show();
                                    return;
                                }else {
                                    fav.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                                    isFavorite = false;
                                }

                            }
                        }
                        else {
                            fav.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                            isFavorite = false;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    //    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//            case LOCATION_REQUEST:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    m_map.setMyLocationEnabled(true);
//                }
//                break;
//        }
//    }
//
    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

}