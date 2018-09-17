package me.cekpedia.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.cekpedia.Adapter.ImageListAdapter;
import me.cekpedia.Adapter.ListCardAdapter;
import me.cekpedia.R;
import me.cekpedia.models.ImageUpload;
import me.cekpedia.utils.Constants;


public class FavouriteFragment extends Fragment {

    View view;
    TextView st;
    Typeface tf;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ListView listView;
//    private ImageListAdapter mAdapter;
    private DatabaseReference mDatabaseRef, mDatabaseRefAll, Reference, Reference2;
    private StorageReference mStorageRef;
    private List<ImageUpload> imgList;
    private ListCardAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    public static final String FB_DATABASE_PATH = "cekpedia";
    FirebaseAuth firebaseAuth;
    private String UserID, namaMenu, namaSub, favorit;
    String[] FavoriteNameLike;
    int FavoriteNameSize;
    boolean FavoriteNameLikeIsEmpty;
    private int merchantNameLikeSize;
    private String favoritTemp;
    ArrayList<String> namaList;
    ArrayList<String> detailList;
    ArrayList<String> gambarList;
    ArrayList<String> deskripsiList;
    ArrayList<String> jarakList;
    ArrayList<String> nameSubList;
    ArrayList<String> noTelpList;
    List<ImageUpload> listData;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_favourite, container, false);

        //Pengaturan Font
        st = (TextView) view.findViewById(R.id.toolbar_text);
        tf = Typeface.createFromAsset(getActivity().getAssets(), "scriptmtbold.ttf");
        st.setTypeface(tf);

        //Pengaturan Recycler View
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

//        adapter = new ListCardAdapter();
//        recyclerView.setAdapter(adapter);
        namaList = new ArrayList<>();
        detailList = new ArrayList<>();
        gambarList = new ArrayList<>();
        deskripsiList = new ArrayList<>();
        jarakList = new ArrayList<>();
        nameSubList = new ArrayList<>();
        noTelpList = new ArrayList<>();
        listData = new ArrayList<>();
//        listView = view.findViewById(R.id.listviewfav1);
//        final ArrayList<String> Kategori = new ArrayList<>();
//        imgList = new ArrayList<>();
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Kategori);
//        listView.setAdapter(arrayAdapter);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Please Wait Loading List...");
        mProgressDialog.show();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser UserID = firebaseAuth.getCurrentUser();
        FavoriteNameLike = new String[10];
        FavoriteNameSize = 0;
        FavoriteNameLikeIsEmpty = false;
//        FavouriteFragment i = new FavouriteFragment();
        Intent i = getActivity().getIntent();
//        namaSub = getArguments().getString("SUB");
        namaMenu = i.getStringExtra("JUDUL");
        namaSub = i.getStringExtra("SUB");
        mDatabaseRefAll = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH).child("cekpediaItem");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Constants.USER_KEY).child(UserID.getEmail().replace(".",","));
//
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProgressDialog.dismiss();
                final Map<String, Object> detailMenu = (Map<String, Object>) dataSnapshot.getValue();
                favorit = detailMenu.get("favourite").toString();
                if (!favorit.equals("")){
                    merchantNameLikeSize = 0;
                    while (favorit.length() > 1) {
                        favorit = favorit.substring(1);
                        FavoriteNameLike[merchantNameLikeSize] = favorit.substring(0, favorit.indexOf("/"));
                        favorit = favorit.substring(favorit.indexOf("/"));
                        merchantNameLikeSize++;
                    }
                    for (int i=0; i<merchantNameLikeSize; i++){
                        Reference = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH).child("keterangan");
                        final int finalI = i;
                        Reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final Map<String, Object> detail = (Map<String, Object>) dataSnapshot.getValue();
                                final String sub = detail.get(FavoriteNameLike[finalI]).toString();
                                Reference2 = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH).child("cekpediaItem").child(sub).child(FavoriteNameLike[finalI]);
                                Reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        ImageUpload img = dataSnapshot.getValue(ImageUpload.class);
//                                        imgList.add(img);
//                                        listData.add(img);
//                                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                            String judul = dataSnapshot.child("name").getValue(String.class);
                                            String lokasi = dataSnapshot.child("lokasi").getValue(String.class);
                                            String deskripsi = dataSnapshot.child("deskripsi").getValue(String.class);
                                            String gambar = dataSnapshot.child("url").getValue(String.class);
                                            String namaSub = dataSnapshot.child("nameSub").getValue(String.class);
                                            String noTelp = dataSnapshot.child("number").getValue(String.class);
                                            String jarak = "";
                                            namaList.add(judul);
                                            detailList.add(lokasi);
                                            gambarList.add(gambar);
                                            deskripsiList.add(deskripsi);
                                            jarakList.add(jarak);
                                            nameSubList.add(namaSub);
                                            noTelpList.add(noTelp);
                                            mAdapter = new ListCardAdapter(getActivity(), namaList, detailList, gambarList, noTelpList, deskripsiList, nameSubList);
                                            recyclerView.setAdapter(mAdapter);
                                            mAdapter.notifyDataSetChanged();
//                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Inflate the layout for this fragment

        return view;
    }
}
