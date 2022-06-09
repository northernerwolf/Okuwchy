package com.example.okuwchy.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

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
import com.example.okuwchy.StartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements  View.OnClickListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditText edtPasswordreg, usernamereg,emailreg,locationreg, schooln, studentid;
    private Button btngirin;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ProgressBar progressBar;
    private TextView girin;
    String saylawS, ulanyjy;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        studentid = findViewById(R.id.student_idre);
        edtPasswordreg = findViewById(R.id.parol_reg);
        usernamereg = findViewById(R.id.ulanyjy_reg);
        emailreg = findViewById(R.id.email_reg);
        btngirin = findViewById(R.id.btn_login_reg);
        locationreg = findViewById(R.id.location);
        schooln = findViewById(R.id.schoolN);
        btngirin.setOnClickListener(this);
        girin = findViewById(R.id.girinokuwcy);
//        progressBar = findViewById(R.id.progressbar_reg);
        mAuth = FirebaseAuth.getInstance();


        girin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(2);
                finish();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_reg:
                registerUser();
        }
    }

    private void registerUser() {



        String email = emailreg.getText().toString().trim();
        String password = edtPasswordreg.getText().toString().trim();
        String username = usernamereg.getText().toString().trim();
        String location = locationreg.getText().toString().trim();
        String school = schooln.getText().toString().trim();
        String studentID = studentid.getText().toString().trim();


        if (username.isEmpty()){
            usernamereg.setError("Ady famil giriziň");
            usernamereg.requestFocus();
            return;
        }
        if (school.isEmpty()){
            schooln.setError("Mekdep  № giriziň");
            schooln.requestFocus();
            return;
        }
        if (location.isEmpty()){
            usernamereg.setError("Ýerleşýän ýerini giriziň");
            usernamereg.requestFocus();
            return;
        }
        if (studentID.isEmpty()){
            studentid.setError("Okuwçy DI giriziň");
            studentid.requestFocus();
            return;
        }
        if (email.length()<5){
            usernamereg.setError("Ulanyjy ady gysga");
            usernamereg.requestFocus();
            return;
        }
        if (password.isEmpty()){
            edtPasswordreg.setError("Gizlin belgini giriziň");
            edtPasswordreg.requestFocus();
            return;
        }
        if (password.length()<7){
            edtPasswordreg.setError("Gizlin belgiňiz gysga");
            edtPasswordreg.requestFocus();
            return;
        }

        if (email.isEmpty()){
            emailreg.setError("Ulanyjy ady giriziň");
            emailreg.requestFocus();
            return;
        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            emailreg.setError("Email salgyňyz nädogry");
//            emailreg.requestFocus();
//            return;
//        }

//        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(email,username,password,location,studentID,school);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull  Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Üstünlikli girildi! Giriň bolümine dolanyň", Toast.LENGTH_SHORT).show();
//                                        progressBar.setVisibility(View.GONE);
                                    }else {
                                        Toast.makeText(RegisterActivity.this, "Üstünliksiz 1. Täzeden barlaň!", Toast.LENGTH_SHORT).show();
//                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this, "Üstünliksiz 2. Täzeden barlaň!", Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

    }
//    @Override
//    public void onStart(){
//        super.onStart();
//        if (user!=null){
//            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finishAffinity();
//
//        }
//    }
}