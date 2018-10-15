package me.cekpedia.Fragment;


import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import me.cekpedia.Activity.InputLokasiActivity;
import me.cekpedia.Activity.LoginActivity;
import me.cekpedia.R;
import me.cekpedia.models.User;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    FirebaseAuth mAuth;
    View view;
    TextView st;
    EditText nama, email, nohp;
    String Nama, Email, NoHp, Uid, photoUrl, favorit, temp;
    Typeface tf;
    FirebaseDatabase mDb;
    DatabaseReference database;
    FirebaseAuth firebaseAuth;
    Button btnedit;
    FirebaseStorage storage;
    ImageView prof_pic;
    Uri selectedImage;
    StorageReference storageRef;
    GoogleSignInAccount account;
    private StorageTask mUploadTask;
    public static final String FB_STORAGE_PATH = "fotoprofil/";
    private static final int SELECT_PHOTO = 100;

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
        prof_pic = view.findViewById(R.id.circle_image);
        btnedit = view.findViewById(R.id.btn_edit_profil);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        final UploadTask.TaskSnapshot taskSnapshot = null;

        tf = Typeface.createFromAsset(getActivity().getAssets(), "scriptmtbold.ttf");
        st.setTypeface(tf);
        database = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();
//        if (firebaseAuth == null) {
//            startActivity(new Intent(getActivity(), LoginActivity.class));
//        }
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        mDb = FirebaseDatabase.getInstance();
        final DatabaseReference dataprofile = database.child(firebaseUser.getEmail().replace(".", ","));
        dataprofile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> detailprofil = (Map<String, Object>) dataSnapshot.getValue();
                if (getActivity() == null){
                    return;
                }
                Nama = detailprofil.get("user").toString();
                Email = detailprofil.get("email").toString();
                NoHp = detailprofil.get("nohp").toString();
                Uid = detailprofil.get("uid").toString();
                photoUrl = detailprofil.get("photoUrl").toString();
                Glide.with(getContext())
                        .load(photoUrl)
                        .into(prof_pic);
                favorit = detailprofil.get("favourite").toString();
                nama.setText(Nama);
                email.setText(Email);
                nohp.setText(NoHp);
                temp = detailprofil.get("temp").toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StorageReference ref = storageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(selectedImage));

//                if (photoUrl.equals(temp)){
//                    database.child(firebaseUser.getEmail().replace(".", ",")).setValue(user1);
//                    Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();
//                }else {

                taskSnapshot.getDownloadUrl().toString();
                final String img = taskSnapshot.getDownloadUrl().toString();
                if (!img.equals("")) {
                    mUploadTask = ref.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            final User user = new User(nama.getText().toString(), email.getText().toString(), img, Uid, favorit, nohp.getText().toString(), temp);
                            database.child(firebaseUser.getEmail().replace(".", ",")).setValue(user);
                            Toast.makeText(getActivity(), "simpan berhasil", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    final User user1 = new User(nama.getText().toString(), email.getText().toString(), photoUrl, Uid, favorit, nohp.getText().toString());
                    database.child(firebaseUser.getEmail().replace(".", ",")).setValue(user1);
                }
            }
//            }
        });

        prof_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getContext(), "Image selected, click on upload button", Toast.LENGTH_SHORT).show();
                selectedImage = imageReturnedIntent.getData();
                try {
                    Bitmap bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    bm.compress(Bitmap.CompressFormat.JPEG, 10, baos);
//                    bm.compress(Bitmap.CompressFormat.PNG,10, baos);
//                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(imageReturnedIntent);
//                    if(result.isSuccess()) {
//                        GoogleSignInAccount account = result.getSignInAccount();
//                    }
                    prof_pic.setImageBitmap(bm);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//
            }
            else {
                return;
            }
        }
    }

    public String getImageExt(Uri uri){
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
