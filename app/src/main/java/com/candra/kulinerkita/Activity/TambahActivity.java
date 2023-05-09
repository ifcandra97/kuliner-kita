package com.candra.kulinerkita.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.candra.kulinerkita.API.APIRequestData;
import com.candra.kulinerkita.API.RetroServer;
import com.candra.kulinerkita.Model.ModelResponse;
import com.candra.kulinerkita.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText etNama, etAsal, etDeskripsiSingkat;
    private Button btnTambah;
    private String nama, asal, deskripsiSingkat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama = findViewById(R.id.et_nama);
        etAsal = findViewById(R.id.et_asal);
        etDeskripsiSingkat = findViewById(R.id.et_deskripsi_singkat);
        btnTambah = findViewById(R.id.btn_tambah);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama = etNama.getText().toString();
                asal = etAsal.getText().toString();
                deskripsiSingkat =  etDeskripsiSingkat.getText().toString();

                if(nama.trim().equals(""))
                {
                    etNama.setError("Nama Kuliner tidak boleh kosong !");
                    etNama.requestFocus();
                }
                else if(asal.trim().equals(""))
                {
                    etAsal.setError("Asal Kuliner tidak boleh kosong !");
                    etAsal.requestFocus();
                }
                else if(deskripsiSingkat.trim().equals(""))
                {
                    etDeskripsiSingkat.setError("Deskripsi kuliner tidak boleh kosong !");
                    etDeskripsiSingkat.requestFocus();
                }
                else
                {
                    tambahKuliner();
                }
            }
        });
    }

    private void tambahKuliner()
    {
        APIRequestData ard = RetroServer.connectionRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ard.ardCreate(nama, asal, deskripsiSingkat);
        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "Kode : " + kode + " | Pesan : " + pesan , Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal menghubungi server, Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}