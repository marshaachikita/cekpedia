package me.cekpedia.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import me.cekpedia.R;
import me.cekpedia.models.User;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity2 extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    DatabaseReference database;
    private EditText Nama, Email, NomorTelp, Password;
    private Button register;
    private static final String FB_DATABASE_PATH = "users";
    ProgressDialog progressDialog;
    TextView st;
    Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        // Pengaturan Font
        st = (TextView) findViewById(R.id.cekpedia_signup);
        tf = Typeface.createFromAsset(getAssets(), "FRSCRIPT.TTF");
        st.setTypeface(tf);
        database = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);
        Nama = findViewById(R.id.et_nama);
        Email = findViewById(R.id.et_email);
        NomorTelp = findViewById(R.id.et_hp);
        Password = findViewById(R.id.et_password);
        register = findViewById(R.id.signup);
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Nama.getText().toString().equals("")) {
                    progressDialog.cancel();
                    Toast.makeText(LoginActivity2.this, "Tidak Valid", Toast.LENGTH_SHORT).show();
                } else if (Email.getText().toString().equals("")) {
                    progressDialog.cancel();
                    Toast.makeText(LoginActivity2.this, "Tidak Valid", Toast.LENGTH_SHORT).show();
                } else if (NomorTelp.getText().toString().equals("")) {
                    progressDialog.cancel();
                    Toast.makeText(LoginActivity2.this, "Tidak Valid", Toast.LENGTH_SHORT).show();
                } else if (Password.getText().toString().equals("")) {
                    progressDialog.cancel();
                    Toast.makeText(LoginActivity2.this, "Tidak Valid", Toast.LENGTH_SHORT).show();
                }else {
                    User user = new User(Nama.getText().toString(), Email.getText().toString(), Password.getText().toString(), NomorTelp.getText().toString());
                    database.child("userbyemail").child(Email.getText().toString().replace(".", ",")).setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();

                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginActivity2.this, "Registration Successfuly", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity2.this, LoginActivity.class);

                                        startActivity(intent);
                                        finish();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginActivity2.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            });
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString()
                    ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();

                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity2.this, "Registration Successfuly", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity2.this, LoginActivity.class);

                                startActivity(intent);
                            }
//                            else {
//                                progressDialog.dismiss();
//                                Toast.makeText(LoginActivity2.this, "Registration Failed", Toast.LENGTH_SHORT).show();
//                            }
                        }
                    });
                }
            }
        });

    }
}

