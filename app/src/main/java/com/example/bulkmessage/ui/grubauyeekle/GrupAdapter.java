package com.example.bulkmessage.ui.grubauyeekle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bulkmessage.Grup;
import com.example.bulkmessage.OnClickItemEventListener;
import com.example.bulkmessage.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GrupAdapter extends RecyclerView.Adapter<GrupAdapter.GroupViewHolder> {

    List<Grup> grupList;
    OnClickItemEventListener onClickItemEventListener;

    public GrupAdapter(List<Grup> grupList, OnClickItemEventListener onClickItemEventListener) {
        this.grupList = grupList;
        this.onClickItemEventListener = onClickItemEventListener;

    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GroupViewHolder groupViewHolder = new GroupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grubauyeekle_gruplar, parent, false), onClickItemEventListener);
        return groupViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Grup grup = grupList.get(position);
        holder.setData(grup);

    }

    @Override
    public int getItemCount() {
        return grupList.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView grupImageView;
        TextView grupadiTextView, grupaciklamasiTextView;

        OnClickItemEventListener onClickItemEventListener;

        public GroupViewHolder(View itemView, OnClickItemEventListener onClickItemEventListener) {
            super(itemView);
            grupImageView = itemView.findViewById(R.id.item_grubauyeekle_gruplar_grupImageView);
            grupadiTextView = itemView.findViewById(R.id.item_grubauyeekle_gruplar_grupadiTextView);
            grupaciklamasiTextView = itemView.findViewById(R.id.item_grubauyeekle_gruplar_aciklamaTextView);

            this.onClickItemEventListener = onClickItemEventListener;
            itemView.setOnClickListener(this);

        }

        public void setData(Grup grup) {
            grupadiTextView.setText(grup.getIsim());
            grupaciklamasiTextView.setText(grup.getAciklama());
            if (grup.getImage() != null) {
                Picasso.get().load(grup.getImage()).into(grupImageView);
            }
        }

        @Override
        public void onClick(View v) {
            onClickItemEventListener.onClickItemEvent(getAdapterPosition());
        }
    }
}
