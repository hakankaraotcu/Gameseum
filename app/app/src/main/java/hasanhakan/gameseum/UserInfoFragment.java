package hasanhakan.gameseum;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInfoFragment extends Fragment {
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private TextView name;
    private TextView surname;
    private TextView username;
    private TextView email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        name = view.findViewById(R.id.editTextName_profile);
        surname= view.findViewById(R.id.editTextSurname_profile);
        username = view.findViewById(R.id.editTextUsername_profile);
        email = view.findViewById(R.id.editTextEmail_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null) {
                    name.setText("Name: " + userProfile.name);
                    surname.setText("Surname: " + userProfile.surname);
                    username.setText("Username: " + userProfile.username);
                    email.setText("E-mail: " + userProfile.email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}