package com.nazaruk.myappv2.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nazaruk.myappv2.LoginActivity;
import com.nazaruk.myappv2.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class MyProfileFragment extends Fragment {

    private FirebaseAuth auth;
    private TextInputLayout newUsernameLayout;
    private TextInputLayout newEmailLayout;
    private TextInputEditText newUsernameField;
    private TextInputEditText newEmailField;
    private TextView username;
    private TextView email;
    private ImageView UserPhoto;
    private FirebaseUser currentUser;
    private StorageReference userPhotosFolder;
    private StorageReference photoName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        newUsernameLayout = getView().findViewById(R.id.new_username_layout);
        newUsernameField = getView().findViewById(R.id.new_username_input);
        newEmailLayout = getView().findViewById(R.id.new_email_layout);
        newEmailField = getView().findViewById(R.id.new_email_input);
        username = getView().findViewById(R.id.user_name);
        email = getView().findViewById(R.id.user_email);
        Button signOutBtn = getView().findViewById(R.id.signout_btn);
        Button updateUsernameBtn = getView().findViewById(R.id.update_username_btn);
        Button updateEmailBtn = getView().findViewById(R.id.update_email_btn);
        Button uploadUserPhotoBtn = getView().findViewById(R.id.upload_user_photo);
        UserPhoto = getView().findViewById(R.id.user_photo_image_view);

        userPhotosFolder = FirebaseStorage.getInstance().getReference().child("User photos");

        getUserInfo();

        uploadUserPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadUserPhoto();
            }
        });

        updateUsernameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUsername();
            }
        });

        updateEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEmail();
            }
        });

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    private void signOut() {
        auth.signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        Objects.requireNonNull(getActivity()).overridePendingTransition(0, 0);
    }

    private void updateEmail() {
        final String newEmail = Objects.requireNonNull(newEmailField.getText()).toString().trim();
        if (isEmailValid(newEmail)) {
            currentUser.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getActivity(), getString(R.string.email_updated_message), Toast.LENGTH_SHORT).show();
                    email.setText(newEmail);
                }
            });
        }
    }

    private void updateUsername() {
        String newUsername = Objects.requireNonNull(newUsernameField.getText()).toString().trim();
        if (isUsernameValid(newUsername)) {
            username.setText(newUsername);
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(newUsername)
                    .build();

            currentUser.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), getString(R.string.username_updated_message), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void getUserInfo() {
        currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            username.setText(currentUser.getDisplayName());
            email.setText(currentUser.getEmail());
            photoName = userPhotosFolder.child(currentUser.getUid() + ".jpg");
            placeImage();
        } else {
            Toast.makeText(getActivity(), getString(R.string.error_message), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri ImageData = Objects.requireNonNull(data).getData();
                photoName.putFile(Objects.requireNonNull(ImageData)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), getString(R.string.image_uploaded_message), Toast.LENGTH_SHORT).show();
                    }
                });

                placeImage();
            }
        }
    }

    public void uploadUserPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    public void placeImage() {
        photoName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).into(UserPhoto);
            }
        });
    }

    public boolean isUsernameValid(String username) {
        if (username.isEmpty()) {
            newUsernameLayout.setError(getString(R.string.enter_username_error));
            newUsernameLayout.requestFocus();
            return false;
        } else {
            newUsernameLayout.setError(null);
            return true;
        }
    }

    public boolean isEmailValid(String email) {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            newEmailLayout.setError(getString(R.string.enter_valid_email_error));
            newEmailLayout.requestFocus();
            return false;
        } else {
            newEmailLayout.setError(null);
            return true;
        }
    }
}