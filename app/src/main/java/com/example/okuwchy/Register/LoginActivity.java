package com.example.okuwchy.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.okuwchy.Fragment.HomeFragment;
import com.example.okuwchy.MainActivity;
import com.example.okuwchy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtPassword, edtEmail, studentid;
    private Button btnlogin;
    private TextView yazyl, forgetpassword;
    private static final String SERE_PREF_NAME = "mypref";
    private static final String TITLE = "title";
    private static final String SID ="studentid";
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ProgressBar progressBar;
    private  SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtPassword = findViewById(R.id.password_log);
        edtEmail = findViewById(R.id.email_log);
        btnlogin = findViewById(R.id.btn_login);
        yazyl= findViewById(R.id.yazyl);
        studentid = findViewById(R.id.studentid);
        sharedPreferences = getSharedPreferences(SERE_PREF_NAME, MODE_PRIVATE);
        progressBar = findViewById(R.id.progresbarlogin);
        btnlogin.setOnClickListener(this);

        String stid = sharedPreferences.getString(SID, null);



        yazyl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_login:
                userLogin();

        }
    }

    private void userLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SID, studentid.getText().toString());
        editor.apply();


        if (email.isEmpty()){
            edtEmail.setError("Ulanyjy ady boş");
            edtEmail.requestFocus();
        }
        if (email.length()<5){
            edtEmail.setError("Ulanyjy ady gysga");
            edtEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            edtPassword.setError("Gizlin belgini giriziň");
            edtPassword.requestFocus();
            return;
        }
        if (password.length()<5){
            edtPassword.setError("Gizlin belgiňiz gysga");
            edtPassword.requestFocus();
            return;
        }
        mAuth = FirebaseAuth.getInstance();

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                    Toast.makeText(LoginActivity.this, "Giriş şowly !", Toast.LENGTH_SHORT).show();


                    progressBar.setVisibility(View.GONE);
                }else {
                    Toast.makeText(LoginActivity.this, "Giriş şowsuz! Täzeden synanşyň!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user!=null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}