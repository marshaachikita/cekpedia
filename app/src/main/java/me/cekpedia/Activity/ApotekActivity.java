package me.cekpedia.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import me.cekpedia.Adapter.ImageListAdapter;
import me.cekpedia.Adapter.ListCardAdapter;
import me.cekpedia.R;
import me.cekpedia.models.ImageUpload;

public class ApotekActivity extends AppCompatActivity {
    ListView listView;
//    int gambar[] = {
//            R.drawable.apotekk24gerlong,
//            R.drawable.kimiafarmabubat
//    };
//    String judul[] = {
//            "Apotek K24 Geger Kalong",
//            "Kimia Farma Buah Batu"
//    };
//    String deskripsi[] = {
//            "Jalan Gegerkalong Hilir no.58, Rt 05 Rw 04 Kel.Sukasari, Kec.Gegerkalong, Gegerkalong, Sukasari, Kota Bandung, Jawa Barat 40153\n" +
//                    "Phone : (022) 2011806\n",
//            "Jl. Buah Batu No.259, Turangga, Lengkong, Kota Bandung, Jawa Barat 40264\n" +
//                    "Phone : (022) 7305019\n"
//    };

    private RecyclerView mRecyclerView;
    private ListCardAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;
    private List<ImageUpload> imgList;
    private ImageListAdapter adapter;
    private ProgressDialog mProgressDialog;
    public static final String FB_DATABASE_PATH = "cekpedia";
    ArrayList<String> namaList;
    ArrayList<String> detailList;
    ArrayList<String> gambarList;
    ArrayList<String> deskripsiList;
    ArrayList<String> jarakList;
    ArrayList<String> nameSubList;
    private RecyclerView mResult;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apotek);

        searchView = (SearchView) findViewById(R.id.cari);
        mResult = (RecyclerView) findViewById(R.id.result_list_apotek);
        namaList = new ArrayList<>();
        detailList = new ArrayList<>();
        gambarList = new ArrayList<>();
        deskripsiList = new ArrayList<>();
        jarakList = new ArrayList<>();
        nameSubList = new ArrayList<>();
//        final ArrayList<String> Kategori = new ArrayList<>();
//        imgList = new ArrayList<>();
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Kategori);
//        listView.setAdapter(arrayAdapter);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please Wait Loading List...");
        mProgressDialog.show();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String string) {
                if (!string.toString().isEmpty()){
                    setAdapter(string.toString());
//                    listView.setVisibility(View.GONE);
                    mResult.setVisibility(View.VISIBLE);
                }else {
                    namaList.clear();
                    detailList.clear();
                    gambarList.clear();
                    deskripsiList.clear();
                    nameSubList.clear();
                    mResult.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    mResult.removeAllViews();
                }
                return false;
            }
        });
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH).child("cekpediaItem").child("apotek");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProgressDialog.dismiss();
                    String judul = dataSnapshot.child("name").getValue(String.class);
                    String lokasi = dataSnapshot.child("lokasi").getValue(String.class);
                    String deskripsi = dataSnapshot.child("deskripsi").getValue(String.class);
                    String gambar = dataSnapshot.child("url").getValue(String.class);
                    String namaSub = dataSnapshot.child("nameSub").getValue(String.class);
                    namaList.add(judul);
                    detailList.add(lokasi);
                    gambarList.add(gambar);
                    deskripsiList.add(deskripsi);
                    nameSubList.add(namaSub);

//                    mAdapter = new ListCardAdapter(ApotekActivity.this, namaList, detailList, gambarList, deskripsiList, nameSubList);
                    mResult.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ApotekActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setAdapter(final String searchString) {
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int counter = 0;
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                    String judul = Snapshot.child("name").getValue(String.class);
                    String lokasi = Snapshot.child("lokasi").getValue(String.class);
                    String deskripsi = Snapshot.child("deskripsi").getValue(String.class);
                    String gambar = Snapshot.child("url").getValue(String.class);
                    String namaSub = Snapshot.child("nameSub").getValue(String.class);

                    if (!judul.contains(searchString)) {
                        listView.setVisibility(View.GONE);
                        namaList.add(judul);
                        detailList.add(lokasi);
                        gambarList.add(gambar);
                        deskripsiList.add(deskripsi);
                        nameSubList.add(namaSub);
                        mResult.setVisibility(View.VISIBLE);
                        counter++;
//                        break;
                    } else {
                        listView.setVisibility(View.VISIBLE);
                        mResult.setVisibility(View.GONE);
                        mResult.removeAllViews();
                        namaList.clear();
                        detailList.clear();
                        gambarList.clear();
                        deskripsiList.clear();
                    }
                    if (counter == 15) {
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void tomaps(View view){
        Intent intent = new Intent(ApotekActivity.this, SubMenuActivity.class);
        startActivity(intent);
    }

}