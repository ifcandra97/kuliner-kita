package com.candra.kulinerkita.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.candra.kulinerkita.API.APIRequestData;
import com.candra.kulinerkita.API.RetroServer;
import com.candra.kulinerkita.Activity.MainActivity;
import com.candra.kulinerkita.Activity.UbahActivity;
import com.candra.kulinerkita.Model.ModelKuliner;
import com.candra.kulinerkita.Model.ModelResponse;
import com.candra.kulinerkita.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterKuliner extends RecyclerView.Adapter<AdapterKuliner.VHKuliner>
{
    private Context context;
    private List<ModelKuliner> listKuliner;

    public AdapterKuliner(Context context, List<ModelKuliner> listKuliner) {
        this.context = context;
        this.listKuliner = listKuliner;
    }

    @NonNull
    @Override
    public VHKuliner onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_kuliner, parent, false);
        return new VHKuliner(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VHKuliner holder, int position) {
        ModelKuliner mk = listKuliner.get(position);

        holder.tvId.setText(mk.getId());
        holder.tvNama.setText(mk.getNama());
        holder.tvAsal.setText(mk.getAsal());
        holder.tvDeskripsiSingkat.setText(mk.getDeskripsi_singkat());
    }

    @Override
    public int getItemCount() {
        return listKuliner.size();
    }

    public class VHKuliner extends RecyclerView.ViewHolder
    {
        TextView tvId, tvNama, tvAsal, tvDeskripsiSingkat;

        public VHKuliner(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvAsal = itemView.findViewById(R.id.tv_asal);
            tvDeskripsiSingkat = itemView.findViewById(R.id.tv_deskripsi_singkat);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String idKuliner = tvId.getText().toString();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Perhatian !");
                    dialog.setMessage("Silahkan pilih perintah yang anda inginkan !");
                    dialog.setCancelable(true);
                    dialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteKuliner(idKuliner);
                            dialogInterface.dismiss();
                        }
                    });

                    dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(context, UbahActivity.class);
                            intent.putExtra("varId", tvId.getText().toString());
                            intent.putExtra("varNama", tvNama.getText().toString());
                            intent.putExtra("varAsal", tvAsal.getText().toString());
                            intent.putExtra("varDeskripsiSingkat", tvDeskripsiSingkat.getText().toString());
                            context.startActivity(intent);

                        }
                    });
                    dialog.show();
                    return false;
                }
            });
        }
    }

    private void deleteKuliner(String id)
    {
        APIRequestData ard = RetroServer.connectionRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ard.ardDelete(id);
        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                Toast.makeText(context, "kode : " + kode + " | Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                ((MainActivity) context).retrieveKuliner();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(context, "gagal menghubungi server, Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
