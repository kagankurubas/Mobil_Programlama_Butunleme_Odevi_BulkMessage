package com.example.bulkmessage.ui.grubauyeekle;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bulkmessage.Grup;
import com.example.bulkmessage.R;
import com.example.bulkmessage.Rehber;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class GrubaUyeEkleFragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseFirestore firestore;

    RecyclerView gruplarRecyclerView, rehberRecyclerView;
    TextView seciliGrupTextView;

    Grup seciliGrup;
    ArrayList<Grup> grupArrayList;
    ArrayList<Rehber> rehberArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gruba_uye_ekle, container, false);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        gruplarRecyclerView = view.findViewById(R.id.grubauyeekle_gruplarRecylerView);
        rehberRecyclerView = view.findViewById(R.id.grubauyeekle_rehberRecyclerView);
        seciliGrupTextView = view.findViewById(R.id.grubauyeekle_seciligrupTextView);

        grupArrayList = new ArrayList<>();
        rehberArrayList = new ArrayList<>();

        ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGrant -> {
            if (isGrant) {
                FetchContacts();
            } else {
                Toast.makeText(getContext(), "Uygulamanın düzgün çalışabilmesi adına rehber iznini açmanız gerekmektedir. ", Toast.LENGTH_SHORT).show();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            launcher.launch(Manifest.permission.READ_CONTACTS);
        } else {
            FetchContacts();
        }
        GruplariCek();
        return view;
    }

    private void GruplariCek() {
        String uid = mAuth.getCurrentUser().getUid();

        firestore.collection("/userdata/" + uid + "/" + "groups").get().addOnSuccessListener(queryDocumentSnapshots -> {
            grupArrayList.clear();
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                Grup grup = new Grup(documentSnapshot.getString("name"), documentSnapshot.getString("description"), documentSnapshot.getString("image"), (List<String>) documentSnapshot.get("numbers"), documentSnapshot.getId());
                grupArrayList.add(grup);
            }
            gruplarRecyclerView.setAdapter(new GrupAdapter(grupArrayList, possition -> {
                seciliGrup = grupArrayList.get(possition);
                seciliGrupTextView.setText("Seçili Grup:" + " " + seciliGrup.getIsim());
            }));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            gruplarRecyclerView.setLayoutManager(linearLayoutManager);
        });

    }

    private void FetchContacts() {
        Cursor cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        rehberArrayList.clear();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

            Rehber rehber = new Rehber(name, number, image);
            rehberArrayList.add(rehber);
        }
        rehberRecyclerView.setAdapter(new RehberAdapter(rehberArrayList, position -> {
            Rehber rehber = rehberArrayList.get(position);

            Toast.makeText(getContext(), rehber.getName() + " " + rehber.getNumber(), Toast.LENGTH_SHORT).show();
        }));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rehberRecyclerView.setLayoutManager(linearLayoutManager);
    }
}