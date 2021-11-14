package hasanhakan.gameseum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }
}