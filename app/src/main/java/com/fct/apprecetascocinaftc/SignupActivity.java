package com.fct.apprecetascocinaftc;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    private EditText mNombreEditText;
    private EditText mApellidosEditText;
    private EditText mFechaNacEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mPassword2EditText;
    private Button mSignUpButton;
    private FirebaseFirestore mFirestore;

    private String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt.getBytes());
        byte[] hashedPassword = md.digest(password.getBytes());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(hashedPassword);
        }
        else return null;
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(salt);
        }
        else return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mNombreEditText = findViewById(R.id.nombre_edit_text);
        mApellidosEditText = findViewById(R.id.apellidos_edit_text);
        mFechaNacEditText = findViewById(R.id.fechanac_edit_text);
        mEmailEditText = findViewById(R.id.email_edit_text);
        mPasswordEditText = findViewById(R.id.password_edit_text);
        mPassword2EditText = findViewById(R.id.password2_edit_text);
        mSignUpButton = findViewById(R.id.butRegistro);

        mFirestore = FirebaseFirestore.getInstance();

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = mNombreEditText.getText().toString().trim();
                String apellidos = mApellidosEditText.getText().toString().trim();
                String fechaNacimiento = mFechaNacEditText.getText().toString().trim();
                String email = mEmailEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();
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

