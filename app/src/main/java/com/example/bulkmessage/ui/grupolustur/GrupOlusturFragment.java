package com.example.bulkmessage.ui.grupolustur;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bulkmessage.Grup;
import com.example.bulkmessage.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GrupOlusturFragment extends Fragment {
    EditText grupAdiEditText, grupAciklamasiEditText;
    ImageView grupSimgesiImageView;
    Button grupolusturButton;
    RecyclerView grupOlusturRecyclerView;

    Uri filePath;

    FirebaseAuth mAuth;
    FirebaseStorage mStorage;
    FirebaseFirestore mFirestore;

    ArrayList<Grup> grupArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grup_olustur, container, false);

        grupAdiEditText = view.findViewById(R.id.grupolustur_grupadiEditText);
        grupAciklamasiEditText = view.findViewById(R.id.grupolustur_grupaciklamasiEditText);
        grupolusturButton = view.findViewById(R.id.grupolustur_grupolusturButton);
        grupSimgesiImageView = view.findViewById(R.id.grupolustur_grupasimgesiImageView);
        grupOlusturRecyclerView = view.findViewById(R.id.grupolustur_gruplarRecylerView);

        grupArrayList = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mStorage = FirebaseStorage.getInstance();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK) {
                        Intent data = result.getData();
                        filePath = data.getData();
                        grupSimgesiImageView.setImageURI(filePath);
                    }
                }
        );
        grupSimgesiImageView.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activityResultLauncher.launch(intent);

        });
        grupolusturButton.setOnClickListener(v -> {
            String grupadi = grupAdiEditText.getText().toString();
            String grupaciklamasi = grupAciklamasiEditText.getText().toString();
            if (grupadi.isEmpty()) {
                grupAdiEditText.setError("Grup Adı Boş Bırakılamaz");
            }
            if (grupaciklamasi.isEmpty()) {
                grupAciklamasiEditText.setError("Grup Açıklaması Boş Bırakılamaz");
            }
            if (filePath != null) {
                StorageReference storageReference = mStorage.getReference().child("resimler" + UUID.randomUUID().toString());

                storageReference.putFile(filePath).addOnSuccessListener(taskSnapshot -> {
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        String downloadUrl = uri.toString();
                        Toast.makeText(getContext(), "Resim Yüklendi", Toast.LENGTH_SHORT).show();

                        GrupOlustur(grupadi, grupaciklamasi, downloadUrl);

                    });
                });
                return;
            } else {
                GrupOlustur(grupadi, grupaciklamasi, null);
            }

        });

        GrupCek();

        return view;
    }

    private void GrupOlustur(String name, String description, String image) {
        String userId = mAuth.getCurrentUser().getUid();

        mFirestore.collection("/userdata/" + userId + "/" + "groups").add(new HashMap<String, Object>() {
            {
                put("name", name);
                put("description", description);
                put("image", image);
                put("Numbers", new ArrayList<String>());
            }
        }).addOnSuccessListener(documentReference -> {
            Toast.makeText(getContext(), "Grup Oluşturuldu", Toast.LENGTH_SHORT).show();

            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                Grup grup = new Grup(name, description, image, (List<String>) documentSnapshot.get("numbers"), documentSnapshot.getId());
                grupArrayList.add(grup);
                grupOlusturRecyclerView.getAdapter().notifyItemInserted(grupArrayList.size() - 1);
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Grup Oluşturulamadı", Toast.LENGTH_SHORT).show();
        });


    }

    private void GrupCek() {
        String userId = mAuth.getCurrentUser().getUid();

        mFirestore.collection("/userdata/" + userId + "/" + "groups").get().addOnSuccessListener(queryDocumentSnapshots -> {
            grupArrayList.clear();
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                Grup grup = new Grup(documentSnapshot.getString("name"), documentSnapshot.getString("description"), documentSnapshot.getString("image"), (List<String>) documentSnapshot.get("numbers"), documentSnapshot.getId());
                grupArrayList.add(grup);
            }
            grupOlusturRecyclerView.setAdapter(new GrupAdapter(grupArrayList));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            grupOlusturRecyclerView.setLayoutManager(linearLayoutManager);
        });
    }
}