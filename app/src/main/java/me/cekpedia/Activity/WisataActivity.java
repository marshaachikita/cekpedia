package me.cekpedia.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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

public class WisataActivity extends AppCompatActivity {
    ListView listView;
    private RecyclerView mRecyclerView;
    private ListCardAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;
    private List<ImageUpload> imgList;
    private ImageListAdapter adapter;
    ArrayList<String> namaList;
    ArrayList<String> detailList;
    ArrayList<String> gambarList;
    ArrayList<String> deskripsiList;
    ArrayList<String> jarakList;
    ArrayList<String> nameSubList;
    private RecyclerView mResult;
    private ProgressDialog mProgressDialog;
    public static final String FB_DATABASE_PATH = "cekpedia";
    SearchView searchView;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
//    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wisata);

        //Pengaturan Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.list_wisata);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//
//        adapter = new ListCardAdapter();
//        recyclerView.setAdapter(adapter);

//        listView = (ListView) findViewById(R.id.list_wisata);
//        searchView = (SearchView) findViewById(R.id.cari);
//        mResult = (RecyclerView) findViewById(R.id.reult_list_wisata);
        namaList = new ArrayList<>();
        detailList = new ArrayList<>();
        gambarList = new ArrayList<>();
        deskripsiList = new ArrayList<>();
        jarakList = new ArrayList<>();
        nameSubList = new ArrayList<>();
        final ArrayList<String> Kategori = new ArrayList<>();
        imgList = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Kategori);
//        listView.setAdapter(arrayAdapter);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please Wait Loading List...");
        mProgressDialog.show();


        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH).child("cekpediaItem").child("hotel");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProgressDialog.dismiss();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String judul = postSnapshot.child("name").getValue(String.class);
                    String lokasi = postSnapshot.child("lokasi").getValue(String.class);
                    String deskripsi = postSnapshot.child("deskripsi").getValue(String.class);
                    String gambar = postSnapshot.child("url").getValue(String.class);
                    String namaSub = postSnapshot.child("nameSub").getValue(String.class);
                    namaList.add(judul);
                    detailList.add(lokasi);
                    gambarList.add(gambar);
                    deskripsiList.add(deskripsi);
                    nameSubList.add(namaSub);

                    mAdapter = new ListCardAdapter(WisataActivity.this, namaList, detailList, gambarList, deskripsiList, nameSubList);
                    recyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(WisataActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String string) {
//                if (!string.toString().isEmpty()){
//                    setAdapter(string.toString());
////                    listView.setVisibility(View.GONE);
//                    mResult.setVisibility(View.VISIBLE);
//                }else {
//                    JudulList.clear();
//                    NomorList.clear();
//                    GambarList.clear();
//                    LokasiList.clear();
//                    nameSub.clear();
//                    mResult.setVisibility(View.GONE);
//                    listView.setVisibility(View.VISIBLE);
//                    mResult.removeAllViews();
//                }
//                return false;
//            }
//        });
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

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
