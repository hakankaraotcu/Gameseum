package hasanhakan.gameseum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        preferences = getSharedPreferences("hasanhakan.gameseum", Context.MODE_PRIVATE);
        editor = preferences.edit();
        boolean isDarkModeOn = preferences.getBoolean("isDarkModeOn", false);

        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Instantiate all the UI elements
        editTextEmail = findViewById(R.id.editTextEmail_Login);
        editTextPassword = findViewById(R.id.editTextPassword_Login);
        btnLogin = findViewById(R.id.btnLogin_Login);
        btnRegister = findViewById(R.id.btnRegister_Login);
        editTextEmail.setText("hasan@gmail.com");
        editTextPassword.setText("123456");

        // What will happen after we click the Register Button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                finish();
                startActivity(intent);
            }
        });

        // What will happen after we click the Login Button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

    }

    public void userLogin(){
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        if(email.isEmpty()){
            editTextEmail.setError("Please fill the email section");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextEmail.setError("Please fill the password section");
            editTextEmail.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(MainActivity.this, PageActivity.class);
                    finish();
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "No such user, please try again", Toast.LENGTH_SHORT).show();
                editTextEmail.setText("");
                editTextPassword.setText("");
            }
        });
    }
}