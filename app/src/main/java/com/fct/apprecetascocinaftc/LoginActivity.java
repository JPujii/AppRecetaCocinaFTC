package com.fct.apprecetascocinaftc;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fct.apprecetascocinaftc.MainActivity;
import com.fct.apprecetascocinaftc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private FirebaseFirestore mFirestore;

    private String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt.getBytes());
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.encodeToString(hashedPassword, Base64.NO_WRAP);
    }

    private boolean comparePasswords(String inputPassword, String storedPassword, String storedSalt) throws NoSuchAlgorithmException {
        String hashedInputPassword = hashPassword(inputPassword, storedSalt);
        byte[] decodedStoredPassword = Base64.decode(storedPassword, Base64.NO_WRAP);
        byte[] decodedHashedInputPassword = Base64.decode(hashedInputPassword, Base64.NO_WRAP);
        return MessageDigest.isEqual(decodedStoredPassword, decodedHashedInputPassword);
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.encodeToString(salt, Base64.NO_WRAP);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailEditText = findViewById(R.id.email_edit_text);
        mPasswordEditText = findViewById(R.id.password_edit_text);
        mLoginButton = findViewById(R.id.butLogin);

        mFirestore = FirebaseFirestore.getInstance();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    mFirestore.collection("Usuarios")
                            .document(email)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            String storedPassword = document.getString("password");
                                            String storedSalt = document.getString("salt");
                                            try {
                                                if (comparePasswords(password, storedPassword, storedSalt)) {
                                                    Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Toast.makeText(LoginActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (NoSuchAlgorithmException e) {
                                                Log.e("LoginActivity", "Error al encriptar la contraseña", e);
                                            }
                                        } else {
                                            Toast.makeText(LoginActivity.this, "El correo no existe", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText
                                                (LoginActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT
                                        ).show();
                                        Log.e("LoginActivity", "Error al obtener documento", task.getException());
                                    }
                                }
                            });
                }
            }
        });
    }
}
