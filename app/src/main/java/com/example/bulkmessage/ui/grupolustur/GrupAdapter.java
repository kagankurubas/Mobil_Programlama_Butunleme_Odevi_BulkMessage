package com.example.bulkmessage.ui.grupolustur;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bulkmessage.Grup;
import com.example.bulkmessage.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class GrupAdapter extends RecyclerView.Adapter<GrupAdapter.GroupViewHolder> {

    List<Grup> grupList;

    public GrupAdapter(List<Grup> grupList) {
        this.grupList = grupList;

    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GroupViewHolder groupViewHolder = new GroupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grupolustur_grup, parent, false));
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

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        ImageView grupImageView;
        TextView grupadiTextView, grupaciklamasiTextView;

        public GroupViewHolder(View itemView) {
            super(itemView);
            grupImageView = itemView.findViewById(R.id.item_grupolustur_grup_imageView);
            grupadiTextView = itemView.findViewById(R.id.item_grupolustur_grup_grupaditextView);
            grupaciklamasiTextView = itemView.findViewById(R.id.item_grupolustur_grup_grupaciklamasitextView);

        }

        public void setData(Grup grup) {
            grupadiTextView.setText(grup.getIsim());
            grupaciklamasiTextView.setText(grup.getAciklama());
            if (grup.getImage() != null) {
                Picasso.get().load(grup.getImage()).into(grupImageView);
            }
        }
    }
}
