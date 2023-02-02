package com.example.bulkmessage.ui.mesajgonder;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bulkmessage.Grup;
import com.example.bulkmessage.Mesaj;
import com.example.bulkmessage.R;
import com.example.bulkmessage.ui.grubauyeekle.GrupAdapter;
import com.example.bulkmessage.ui.mesajolustur.MesajAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MesajGonderFragment extends Fragment {

    RecyclerView gruplarRecyclerView, mesajlarRecyclerView;
    TextView seciliGrupTextView, seciliMesajTextView;
    Button gonderButton;

    FirebaseAuth mAuth;
    FirebaseFirestore firestore;

    ArrayList<Grup> grupList;
    ArrayList<Mesaj> mesajList;

    Grup seciliGrup;
    Mesaj seciliMesaj;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mesaj_gonder, container, false);
        gruplarRecyclerView = view.findViewById(R.id.mesajgonder_gruplarRecylerView);
        mesajlarRecyclerView = view.findViewById(R.id.mesajgonder_mesajlarRecyclerView);

        seciliGrupTextView = view.findViewById(R.id.mesajgonder_seciligrupTextView);
        seciliMesajTextView = view.findViewById(R.id.mesajgonder_secilimesajTextView2);

        gonderButton = view.findViewById(R.id.mesajgonder_mesajGonderButton);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        grupList = new ArrayList<>();
        mesajList = new ArrayList<>();

        ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGrant -> {
            if (isGrant) {
                MesajGonder();
            } else {
                Toast.makeText(getContext(), "Sms göndermek için izin gereklidir.", Toast.LENGTH_SHORT).show();
            }
        });

        gonderButton.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                launcher.launch(android.Manifest.permission.SEND_SMS);
            } else {
                MesajGonder();
            }
        });

        GruplariCek();
        MesajCek();
        return view;
    }

    private void GruplariCek() {
        String uid = mAuth.getCurrentUser().getUid();

        firestore.collection("/userdata/" + uid + "/groups").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                grupList.clear();
                for (DocumentSnapshot document : task.getResult()) {
                    Grup grup = new Grup(document.getString("name"), document.getString("description"), document.getString("image"), (List<String>) document.get("numbers"), document.getId());
                    grupList.add(grup);
                }

                gruplarRecyclerView.setAdapter(new GrupAdapter(grupList, possition -> {
                    seciliGrup = grupList.get(possition);
                    seciliGrupTextView.setText("Seçili Grup: " + seciliGrup.getIsim());
                }));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                gruplarRecyclerView.setLayoutManager(linearLayoutManager);
            }
        });
    }

    private void MesajCek() {
        String uid = mAuth.getCurrentUser().getUid();

        firestore.collection("/userdata/" + uid + "/messages").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mesajList.clear();
                for (DocumentSnapshot document : task.getResult()) {
                    Mesaj mesaj = new Mesaj(document.getString("name"), document.getString("message"), document.getId());
                    mesajList.add(mesaj);
                }
                mesajlarRecyclerView.setAdapter(new MesajGonderAdapter(mesajList, possition -> {
                    seciliMesaj = mesajList.get(possition);
                    seciliMesajTextView.setText("Seçili Mesaj: " + seciliMesaj.getIsim());
                }));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                mesajlarRecyclerView.setLayoutManager(linearLayoutManager);
            }
        });
    }

    private void MesajGonder() {
        if (seciliMesaj == null || seciliGrup == null) {
            Toast.makeText(getContext(), "Lütfen grup ve mesaj seçiniz", Toast.LENGTH_SHORT).show();
            return;
        }
        if (seciliGrup.getNum() != null && seciliGrup.getNum().size() > 0) {
            SmsManager smsManager = SmsManager.getDefault();
            for (String num : seciliGrup.getNum()) {
                smsManager.sendTextMessage(num, null, seciliMesaj.getMesajaciklama(), null, null);
            }

            Toast.makeText(getContext(), "Mesaj Gönderildi", Toast.LENGTH_SHORT).show();
        }
    }
}