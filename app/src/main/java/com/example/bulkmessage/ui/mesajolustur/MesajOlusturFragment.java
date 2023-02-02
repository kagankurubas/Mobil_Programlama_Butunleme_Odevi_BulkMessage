package com.example.bulkmessage.ui.mesajolustur;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bulkmessage.Mesaj;
import com.example.bulkmessage.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;


public class MesajOlusturFragment extends Fragment {

    FirebaseFirestore firestore;
    FirebaseAuth mAuth;

    EditText mesajadiEditText, mesajEditText;
    Button mesajolusturButton;
    RecyclerView mesajlarRecyclerView;

    ArrayList<Mesaj> mesajArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mesaj_olustur, container, false);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        mesajArrayList = new ArrayList<>();

        mesajadiEditText = view.findViewById(R.id.nav_mesajolustur_mesajadiEditText);
        mesajEditText = view.findViewById(R.id.nav_mesajolustur_mesajEditText);
        mesajlarRecyclerView = view.findViewById(R.id.nav_mesajolustur_mesajRecylerView);
        mesajolusturButton = view.findViewById(R.id.nav_mesajolustur_mesajolusturButton);

        mesajolusturButton.setOnClickListener(v -> {
            String mesajAdi = mesajadiEditText.getText().toString();
            String mesajAciklamasi = mesajEditText.getText().toString();

            if (mesajAdi.isEmpty() || mesajAciklamasi.isEmpty()) {
                Toast.makeText(getContext(), "Lütfen Tüm Alanları Doldurunuz", Toast.LENGTH_SHORT).show();
                return;
            }

            MesajOlustur(mesajAdi, mesajAciklamasi);
        });

        MesajCek();
        return view;
    }

    private void MesajOlustur(String mesajAdi, String mesajAciklama) {
        String userId = mAuth.getCurrentUser().getUid();
        firestore.collection("/userdata/" + userId + "/" + "messages").add(new HashMap<String, String>() {{
            put("name", mesajAdi);
            put("message", mesajAciklama);
        }}).addOnSuccessListener(documentReference -> {
            Toast.makeText(getContext(), "Mesaj Oluşturuldu", Toast.LENGTH_SHORT).show();

            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                Mesaj mesaj = new Mesaj(mesajAdi, mesajAciklama, documentSnapshot.getId());
                mesajArrayList.add(mesaj);
                mesajlarRecyclerView.getAdapter().notifyItemInserted(mesajArrayList.size() - 1);
            });

        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Mesaj Oluşturulamadı", Toast.LENGTH_SHORT).show();
        });
    }

    public void MesajCek() {
        String userId = mAuth.getCurrentUser().getUid();
        firestore.collection("/userdata/" + userId + "/messages").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        Mesaj mesaj = new Mesaj(documentSnapshot.getString("name"), documentSnapshot.getString("messagge"), documentSnapshot.getId());
                        mesajArrayList.add(mesaj);
                    }
                    mesajlarRecyclerView.setAdapter(new MesajAdapter(mesajArrayList));
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    mesajlarRecyclerView.setLayoutManager(linearLayoutManager);
                }).addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Mesaj Alınamadı", Toast.LENGTH_SHORT).show();
                });
    }
}