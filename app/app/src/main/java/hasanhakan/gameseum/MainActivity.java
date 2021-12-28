package hasanhakan.gameseum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("hasanhakan.gameseum", Context.MODE_PRIVATE);
        editor = preferences.edit();
        boolean isDarkModeOn = preferences.getBoolean("isDarkModeOn", false);

        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Instantiate all the UI elements
        editTextUsername = findViewById(R.id.editTextUsername_Login);
        editTextPassword = findViewById(R.id.editTextPassword_Login);
        btnLogin = findViewById(R.id.btnLogin_Login);
        btnRegister = findViewById(R.id.btnRegister_Login);

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
                Intent intent = new Intent(MainActivity.this, PageActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }
}