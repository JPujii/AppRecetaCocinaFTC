package com.fct.apprecetascocinaftc;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fct.apprecetascocinaftc.databinding.ActivityLoginBinding;
import com.fct.apprecetascocinaftc.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private ActivitySignupBinding binding;

    private String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt.getBytes());
        byte[] hashedPassword = md.digest(password.getBytes());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(hashedPassword);
        } else return null;
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(salt);
        } else return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mFirestore = FirebaseFirestore.getInstance();

        binding.butRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = binding.nombreEditText.getText().toString().trim();
                String apellidos = binding.apellidosEditText.getText().toString().trim();
                String fechaNacimiento = binding.fechanacEditText.getText().toString().trim();
                String email = binding.emailEditText.getText().toString().trim();
                String password = binding.passwordEditText.getText().toString().trim();
                String id = email; // Utilizamos el email como identificador único
                String salt = generateSalt();
                String hashedPassword = "";
                try {
                    hashedPassword = hashPassword(password, salt);
                } catch (NoSuchAlgorithmException e) {
                    Log.e("SignupActivity", "Error al encriptar la contraseña", e);
                }

                // Validar entrada de usuario aquí según tus requerimientos

                Map<String, Object> user = new HashMap<>();
                user.put("nombre", nombre);
                user.put("apellidos", apellidos);
                user.put("fecha_nacimiento", fechaNacimiento);
                user.put("email", email);
                user.put("password", hashedPassword);
                user.put("id", id);
                user.put("salt", salt);


                //Metodo para verificarlo en el login:
                //String password = mPasswordEditText.getText().toString().trim();
                //boolean passwordMatch = PasswordUtils.verifyPassword(password, hashedPassword, salt);

                mFirestore.collection("Usuarios")
                        .document(id)
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SignupActivity.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignupActivity.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                                Log.e("SignupActivity", "Error al agregar usuario", e);
                            }
                        });
            }
        });
    }
}

