package hasanhakan.gameseum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private Button btnRegister;
    private FirebaseAuth mAuth;
    private EditText name;
    private EditText surname;
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText confirm;
    private CheckBox terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        btnRegister = findViewById(R.id.btnRegister_Register);
        name = findViewById(R.id.editTextName_Register);
        surname = findViewById(R.id.editTextSurname_Register);
        username = findViewById(R.id.editTexUserName_Register);
        email = findViewById(R.id.editTextEmail_Register);
        password = findViewById(R.id.editTextPassword_Register);
        confirm = findViewById(R.id.editTextConfirmPassword_Register);
        terms = findViewById(R.id.checkBoxTerms_Register);

        // What will happen after we click the Register Button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser(){
        String sName = name.getText().toString();
        String sSurname = surname.getText().toString();
        String sUserName = username.getText().toString();
        String sEmail = email.getText().toString();
        String sPassword = password.getText().toString();
        String sConfirm = confirm.getText().toString();
        if(sName.isEmpty()){
            name.setError("Name is required");
            name.requestFocus();
            return;
        }
        if(sSurname.isEmpty()){
            surname.setError("Surname is required");
            surname.requestFocus();
            return;
        }
        if(sUserName.isEmpty()){
            username.setError("Username is required");
            username.requestFocus();
            return;
        }
        if(sEmail.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(sUserName).matches()){
            email.setError("Please provide a valid username");
            email.requestFocus();
            return;
        }
        if(sPassword.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if(sPassword.length() < 6){
            password.setError("Please enter at least 6 characters");
            password.requestFocus();
            return;
        }
        if(!sConfirm.equals(sPassword)){
            confirm.setError("Passwords do not match");
            password.setError("Passwords do not match");
            password.requestFocus();
            confirm.requestFocus();
            return;
        }
        if(!terms.isChecked()){
            terms.setError("You must accepts the terms");
            terms.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(sEmail, sPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(sName, sSurname, sUserName, sEmail);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "User has been registered", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, PageActivity.class);
                                        finish();
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this, "User failed to register", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    }
                });
    }
}