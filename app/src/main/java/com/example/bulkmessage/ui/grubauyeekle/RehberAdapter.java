package com.example.bulkmessage.ui.grubauyeekle;

import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bulkmessage.OnClickItemEventListener;
import com.example.bulkmessage.R;
import com.example.bulkmessage.Rehber;

import java.util.List;

public class RehberAdapter extends RecyclerView.Adapter<RehberAdapter.RehberViewHolder> {

    List<Rehber> rehberList;
    OnClickItemEventListener onClickRehberEventListener;

    public RehberAdapter(List<Rehber> rehberList, OnClickItemEventListener onClickRehberEventListener) {
        this.rehberList = rehberList;
        this.onClickRehberEventListener = onClickRehberEventListener;
    }

    @NonNull
    @Override
    public RehberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RehberViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grubauyeekle_rehber, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RehberViewHolder holder, int position) {
        Rehber rehber = rehberList.get(position);
        holder.setData(rehber);

    }

    @Override
    public int getItemCount() {
        return rehberList.size();
    }

    public class RehberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView rehberImageView;
        TextView nameTextView, numberTextView;

        public RehberViewHolder(View view) {
            super(view);
            rehberImageView = view.findViewById(R.id.item_grubauyeekle_imageImageView);
            nameTextView = view.findViewById(R.id.item_grubauyeekle_nameTextView);
            numberTextView = view.findViewById(R.id.item_grubauyeekle_numberTextView);


            view.setOnClickListener(this);
        }

        private void setData(Rehber rehber) {
            nameTextView.setText(rehber.getName());
            numberTextView.setText(rehber.getNumber());

            if (rehber.getImage() != null) {

                rehberImageView.setImageURI(Uri.parse(rehber.getImage()));
            }
        }

        @Override
        public void onClick(View v) {
            onClickRehberEventListener.onClickItemEvent(getAdapterPosition());

        }
    }

}
