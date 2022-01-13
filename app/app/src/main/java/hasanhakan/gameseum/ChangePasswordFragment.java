package hasanhakan.gameseum;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ChangePasswordFragment extends Fragment {

    private EditText currentPassword;
    private EditText newPassword;
    private String email;
    private Button btnChange;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        currentPassword = view.findViewById(R.id.editTextCurrentPassword);
        newPassword = view.findViewById(R.id.editTextNewPassword);
        btnChange = view.findViewById(R.id.btnChangePassword);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                email = user.getEmail();
                AuthCredential credential = EmailAuthProvider.getCredential(email, currentPassword.getText().toString());
                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            user.updatePassword(newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(newPassword.getText().toString().length() < 6){
                                        Snackbar snackbar_fail = Snackbar
                                                .make(view, "The password is too short", Snackbar.LENGTH_LONG);
                                        snackbar_fail.show();
                                    }
                                    else if(!task.isSuccessful()){
                                        Snackbar snackbar_fail = Snackbar
                                                .make(view, "Something went wrong. Please try again later", Snackbar.LENGTH_LONG);
                                        snackbar_fail.show();
                                    }
                                    else{
                                        Snackbar snackbar_su = Snackbar
                                                .make(view, "Password Successfully Modified", Snackbar.LENGTH_LONG);
                                        snackbar_su.show();
                                        ProfileFragment profileFragment = new ProfileFragment();
                                        getParentFragmentManager().beginTransaction().replace(R.id.page_activity_frameLayout, profileFragment).commit();
                                    }
                                }
                            });
                        }else{
                            Snackbar snackbar_su = Snackbar
                                    .make(view, "Authentication Failed", Snackbar.LENGTH_LONG);
                            snackbar_su.show();
                        }
                    }
                });
            }
        });

        return view;
    }

}