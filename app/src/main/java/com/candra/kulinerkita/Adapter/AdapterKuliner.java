package com.candra.kulinerkita.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.candra.kulinerkita.Model.ModelKuliner;
import com.candra.kulinerkita.R;

import java.util.List;

public class AdapterKuliner extends RecyclerView.Adapter<AdapterKuliner.HolderData>
{
    private Context context;
    private List<ModelKuliner> listKuliner;

    public AdapterKuliner(Context context, List<ModelKuliner> listKuliner) {
        this.context = context;
        this.listKuliner = listKuliner;
    }

    @NonNull
    @Override
    public AdapterKuliner.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_kuliner, parent, false);
        HolderData holderData = new HolderData(v);
        return  holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKuliner.HolderData holder, int position) {
        ModelKuliner mk = listKuliner.get(position);

        holder.tvId.setText(String.valueOf(mk.getId()));
        holder.tvNama.setText(mk.getNama());
        holder.tvAsal.setText(mk.getAsal());
        holder.tvDeskripsiSingkat.setText(mk.getDeskripsi_singkat());
    }

    @Override
    public int getItemCount() {
        return listKuliner.size();
    }

    public class HolderData extends RecyclerView.ViewHolder
    {
        TextView tvId, tvNama, tvAsal, tvDeskripsiSingkat;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvAsal = itemView.findViewById(R.id.tv_asal);
            tvDeskripsiSingkat = itemView.findViewById(R.id.tv_deskripsi_singkat);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
        }
    }
}
