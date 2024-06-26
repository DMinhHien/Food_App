package com.example.didong_foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.didong_foodapp.ui.Controller.LoginController;
import com.example.didong_foodapp.ui.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegistrationActivity extends AppCompatActivity {
    TextInputEditText InputMail,InputPassword,InputUsername;
    Button Reg;
    FirebaseAuth mAuth;
    ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InputMail=findViewById(R.id.Email);
        InputPassword=findViewById(R.id.Pass);
        InputUsername=findViewById(R.id.user);
        bar=findViewById(R.id.progressBar);
        mAuth=FirebaseAuth.getInstance();
        Reg=findViewById(R.id.button_register);
        bar.setVisibility(View.INVISIBLE);
        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email=InputMail.getText().toString();
                password=InputPassword.getText().toString();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(RegistrationActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(RegistrationActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    bar.setVisibility(View.VISIBLE);
                                    Toast.makeText(RegistrationActivity.this, "Account created",
                                            Toast.LENGTH_SHORT).show();
                                    UserModel uModel=new UserModel();
                                    uModel.setEmail(email);
                                    uModel.setUsername(InputUsername.getText().toString());
                                    String uid = task.getResult().getUser().getUid();
                                    LoginController LController= new LoginController();
                                    LController.AddInfoController(uModel,uid);
                                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                    
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegistrationActivity.this, "Password must contain 6 characters or more",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
                               }
        );

    }
    public void sign(View view) {
        startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
        finish();
    }
    public void login(View view) {
        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
        finish();
    }
}