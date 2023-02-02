package com.example.bulkmessage.ui.mesajgonder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bulkmessage.Mesaj;
import com.example.bulkmessage.OnClickItemEventListener;
import com.example.bulkmessage.R;
import com.example.bulkmessage.ui.mesajolustur.MesajAdapter;

import java.util.ArrayList;
import java.util.List;

public class MesajGonderAdapter extends RecyclerView.Adapter<MesajGonderAdapter.MesajGonderViewHolder> {

    List<Mesaj> mesajList;
    OnClickItemEventListener onClickMesajGonderListener;

    public MesajGonderAdapter(List<Mesaj> mesajList, OnClickItemEventListener onClickMesajGonderListener) {
        this.mesajList = mesajList;
        this.onClickMesajGonderListener = onClickMesajGonderListener;
    }

    @NonNull
    @Override
    public MesajGonderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MesajGonderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mesajgonder_mesaj, parent, false), onClickMesajGonderListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MesajGonderViewHolder holder, int position) {
        Mesaj mesaj = mesajList.get(position);
        holder.setData(mesaj);
    }

    @Override
    public int getItemCount() {
        return mesajList.size();
    }

    public class MesajGonderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mesajAdiTextView, mesajTextView;
        OnClickItemEventListener onClickMesajGonderListener;

        public MesajGonderViewHolder(View itemView, OnClickItemEventListener onClickMesajGonderListener) {
            super(itemView);

            mesajAdiTextView = itemView.findViewById(R.id.item_mesajgonder_mesajadiTextView);
            mesajTextView = itemView.findViewById(R.id.item_mesajgonder_mesajTextView);
            this.onClickMesajGonderListener = onClickMesajGonderListener;

            itemView.setOnClickListener(this);

        }

        public void setData(Mesaj mesaj) {
            mesajAdiTextView.setText(mesaj.getIsim());
            mesajTextView.setText(mesaj.getMesajaciklama());

        }

        @Override
        public void onClick(View v) {
            onClickMesajGonderListener.onClickItemEvent(getAdapterPosition());
        }
    }
}
