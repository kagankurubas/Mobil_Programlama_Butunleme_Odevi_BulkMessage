package com.example.bulkmessage.ui.mesajolustur;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bulkmessage.Mesaj;
import com.example.bulkmessage.R;

import java.util.List;

public class MesajAdapter extends RecyclerView.Adapter<MesajAdapter.MessageViewHolder> {

    List<Mesaj> mesajList;

    public MesajAdapter(List<Mesaj> mesajList) {
        this.mesajList = mesajList;
    }

    @NonNull
    @Override
    public MesajAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mesajolustur_mesaj, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MesajAdapter.MessageViewHolder holder, int position) {
        Mesaj mesaj = mesajList.get(position);
        holder.setData(mesaj);

    }

    @Override
    public int getItemCount() {
        return mesajList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView mesajadiTextView, mesajTextView;

        public MessageViewHolder(View itemView) {
            super(itemView);
            mesajadiTextView = itemView.findViewById(R.id.item_mesajolustur_mesajadiTextView);
            mesajTextView = itemView.findViewById(R.id.item_mesajolustur_mesajTextView);

        }

        public void setData(Mesaj mesaj) {
            mesajadiTextView.setText(mesaj.getIsim());
            mesajTextView.setText(mesaj.getMesajaciklama());
        }
    }
}
