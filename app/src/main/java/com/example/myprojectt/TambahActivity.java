package com.example.myprojectt;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TambahActivity extends AppCompatActivity {

    EditText editTextNama;
    Button buttonSimpan;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        // Inisialisasi komponen tampilan
        editTextNama = findViewById(R.id.editTextNama);
        buttonSimpan = findViewById(R.id.buttonSimpan);
        databaseHelper = new DatabaseHelper(this); // Inisialisasi objek DatabaseHelper

        // Aksi ketika tombol "Simpan" diklik
        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mengambil nilai dari input nama
                String nama = editTextNama.getText().toString();

                // Menyimpan data ke dalam tabel mahasiswa
                if (!nama.isEmpty()) {
                    boolean isInserted = databaseHelper.insertMahasiswa(nama); // Panggil metode insertMahasiswa dari DatabaseHelper

                    if (isInserted) {
                        Toast.makeText(TambahActivity.this, "Data mahasiswa berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        recreate();
                    } else {
                        Toast.makeText(TambahActivity.this, "Gagal menambahkan data mahasiswa", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Menampilkan pesan kesalahan jika input nama kosong
                    Toast.makeText(TambahActivity.this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
