package me.cekpedia.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import me.cekpedia.BuildConfig;
import me.cekpedia.R;
import me.cekpedia.models.User;
import me.cekpedia.utils.Constants;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
        ,GoogleApiClient.OnConnectionFailedListener
    {

    private LinearLayout Prof_Section;
    private Button SignOut;
    private SignInButton SignIn;
    private ImageView Prof_Pic;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;
    private TextView Name,Email;
    SignInButton googlelogin;
//    Button googlelogin;
    FirebaseAuth firebaseAuth;
    DatabaseReference database;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mFirebaseUser;
    private static final String TAG = LoginActivity.class.getSimpleName();
    String NoHp, favorit;
    Button sign_in;
    TextView st, favhidden, nohphidden;
    Typeface tf;
    EditText username, password;
    ProgressDialog progressDialog;
    String photoUrl = null;

    public ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth != null){
            mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("668210003774-dtag7e2q0sgbfld6rvk8upd6qcslqejl.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // Pengaturan Opacity Background Gambar
        View backgroundimage = findViewById(R.id.login_layout);
        Drawable background = backgroundimage.getBackground();
        background.setAlpha(128);

        // Pengaturan Ukuran Icon DrawableLeft
        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        final Button btn_google = (Button) findViewById(R.id.google_button);

        final float density = getResources().getDisplayMetrics().density;
        final Drawable drawable_username = getResources().getDrawable(R.drawable.ic_personwhite);
        final Drawable drawable_password = getResources().getDrawable(R.drawable.ic_keywhite);
        final Drawable drawable_google = getResources().getDrawable(R.mipmap.ic_google_logo);

        final int width = Math.round(24 * density);
        final int height = Math.round(24 * density);

        drawable_username.setBounds(0, 0, width, height);
        drawable_password.setBounds(0, 0, width, height);
        drawable_google.setBounds(0, 0, width, height);

        username.setCompoundDrawables(drawable_username, null, null, null);
        password.setCompoundDrawables(drawable_password, null, null, null);
//        btn_google.setCompoundDrawables(drawable_google, null, null, null);

        // Pengaturan Font
        st = (TextView) findViewById(R.id.cekpedia_login);
        tf = Typeface.createFromAsset(getAssets(), "scriptmtbold.ttf");
        st.setTypeface(tf);

        // Button Sign In & Sign Up
        sign_in = (Button) findViewById(R.id.btn_signin);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "fitur masih dalam pengembangan", Toast.LENGTH_SHORT).show();
//                progressDialog.setMessage("Please Wait...");
//                progressDialog.show();
//
//                if (username.getText().toString().equals("")) {
//                    progressDialog.cancel();
//                    Toast.makeText(LoginActivity.this, "Email Tidak boleh kosong", Toast.LENGTH_LONG).show();
//                } else if (password.getText().toString().equals("")) {
//                    Toast.makeText(LoginActivity.this, "Password Tidak boleh kosong", Toast.LENGTH_LONG).show();
//                    progressDialog.cancel();
//                } else {
//                    if (checkInternet()) {
//                        firebaseAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString()
//                        ).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                            @Override
//                            public void onSuccess(AuthResult authResult) {
//                                hideProgressDialog();
//                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            intent.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
//                                startActivity(intent);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                progressDialog.cancel();
//                                Toast.makeText(LoginActivity.this, "Login Gagal", Toast.LENGTH_LONG).show();
//                            }
//                        });
//                    }
//                    else {
//                        hideProgressDialog();
//                        Toast.makeText(LoginActivity.this, "No Connection", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                Intent intent = new Intent(view.getContext(), MainActivity.class);
//                startActivity(intent);
            }
        });

        Name = (TextView)findViewById(R.id.name);
        Email = (TextView)findViewById(R.id.email);
        favhidden = findViewById(R.id.favhidden);
        nohphidden = findViewById(R.id.nohphidden);

//        Prof_Section = (LinearLayout)findViewById(R.id.prof_section);
//        SignOut = (Button)findViewById(R.id.bn_logout);
//        SignIn = (SignInButton)findViewById(R.id.bn_login);
//        Prof_Pic = (ImageView)findViewById(R.id.prof_pic);
        googlelogin = findViewById(R.id.bn_login);
        googlelogin.setOnClickListener(this);
        googlelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

//        for (int i = 0; i < googlelogin.getChildCount(); i++) {
//            View v = googlelogin.getChildAt(i);
//
//            if (v instanceof TextView)
//            {
//                TextView tv = (TextView) v;
//                tv.setTextSize(14);
//                tv.setTypeface(null, Typeface.NORMAL);
//                tv.setText("My Text");
//                tv.setTextColor(Color.parseColor("#FFFFFF"));
//                tv.setBackgroundResource(R.drawable.ellips_button);
//                tv.setSingleLine(true);
//                tv.setPadding(15, 15, 15, 15);
//
//                ViewGroup.LayoutParams params = tv.getLayoutParams();
//                params.width = 100;
//                params.height = 70;
//                tv.setLayoutParams(params);
//
//                return;
//            }
//        }


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mFirebaseUser = firebaseAuth.getCurrentUser();
                if (mFirebaseUser != null){
                    if (BuildConfig.DEBUG) Log.d(TAG, "onAuthStateChanged:signed_in " + mFirebaseUser.getDisplayName());
                }else {
                    if (BuildConfig.DEBUG) Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mFirebaseUser = firebaseAuth.getCurrentUser();
                if (mFirebaseUser != null){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    Toast.makeText(LoginActivity.this, "Sign in berhasil", Toast.LENGTH_SHORT).show();
                    finish();
                    if (mFirebaseUser != null) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onAuthStateChanged:signed_in " + mFirebaseUser.getDisplayName());
                    } else {
                        if (BuildConfig.DEBUG) Log.d(TAG, "onAuthStateChanged:signed_out");
                    }
                }
            }
        };
    }
    private void signIn(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) firebaseAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
        if (ActivityCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
    }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            finish();
        }

        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQ_CODE){
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if(result.isSuccess()){
                    GoogleSignInAccount account = result.getSignInAccount();
                    firebaseAuthWithGoogle(account);
                }else{
                    hideProgressDialog();
                }
            }else {
                hideProgressDialog();
            }
        }else {
            hideProgressDialog();
        }
    }
//    private void signOut() {
//        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
//            @Override
//            public void onResult(@NonNull Status status) {
////                updateUI(false);
//            }
//        });
//
//    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account){
//        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:complete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:" + task.getException());
//                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                            updateUI(user);
                        }
                        else {

                            final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            database = FirebaseDatabase.getInstance().getReference("users");
                            final DatabaseReference dataprofile = database.child(firebaseUser.getEmail().replace(".", ","));
                            dataprofile.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Map<String, Object> detailprofil = (Map<String, Object>) dataSnapshot.getValue();
                                    if(dataSnapshot.getChildrenCount()>=5) {
                                        if (!detailprofil.get("nohp").toString().equals("")){
//                                                || !detailprofil.get("photourl").toString().equals("")
                                            NoHp = detailprofil.get("nohp").toString();
                                            favorit = detailprofil.get("favourite").toString();
                                            photoUrl = detailprofil.get("photoUrl").toString();
//                                            nohphidden.setText(NoHp);
//                                            favhidden.setText(favorit);
                                        } else {
                                            NoHp = "";
                                            favorit = "";
                                        }
                                    }else {
                                        NoHp = "";
                                        favorit = "";
                                    }

                                    if (account.getPhotoUrl() != null){
                                        photoUrl = account.getPhotoUrl().toString();
                                    }
                                    User user = new User(
                                            account.getDisplayName(),
//                                            " " + account.getFamilyName(),
                                            account.getEmail(),
                                            photoUrl,
                                            FirebaseAuth.getInstance().getCurrentUser().getUid(),favorit,NoHp
                                    );
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference userRef = database.getReference(Constants.USER_KEY);
                                    userRef.child(account.getEmail().replace(".", ","))
                                            .setValue(user, new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                }
                                            });
                                    if (BuildConfig.DEBUG)Log.v(TAG, "Auth Successful");
//                                    Toast.makeText(LoginActivity.this, "Sign In Success", Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        }
// else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }

                        // ...
                    }
                });
    }
        protected void hideProgressDialog(){
            if(mProgressDialog != null && mProgressDialog.isShowing()){
                mProgressDialog.cancel();
            }
        }

        protected void showProgressDialog(){
            if(mProgressDialog == null){
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage(getString(R.string.loading));
                mProgressDialog.setIndeterminate(true);
            }
            mProgressDialog.show();
        }
        protected void signOut(){
            firebaseAuth.signOut();

            Auth.GoogleSignInApi.signOut(googleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {

                        }
                    });
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
//            firebaseAuth.removeAuthStateListener(mAuthListener);
        }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.bn_login:
//                signIn();
//                break;
//            case R.id.bn_logout:
//                signOut();
//                break;
//        }
    }

    public void daftarAkun(View view) {
//        Intent intent = new Intent(this, LoginActivity2.class);
//        startActivity(intent);
        Toast.makeText(this, "fitur masih dalam pengembangan", Toast.LENGTH_SHORT).show();
        }
    public boolean checkInternet(){
        boolean connectStatus = true;
        ConnectivityManager ConnectionManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()==true ) {
            connectStatus = true;
        }
        else {
           connectStatus = false;
        }
        return connectStatus;
    }
}
