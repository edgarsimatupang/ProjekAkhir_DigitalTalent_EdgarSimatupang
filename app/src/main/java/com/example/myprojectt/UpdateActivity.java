package com.example.myprojectt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    private EditText editTextNama;
    private Button buttonUpdate;
    private String namaMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Inisialisasi komponen tampilan
        editTextNama = findViewById(R.id.editTextNama);
        buttonUpdate = findViewById(R.id.buttonSimpan);

        // Mendapatkan data mahasiswa yang akan diupdate dari intent
        namaMahasiswa = getIntent().getStringExtra("nama_mahasiswa");
        editTextNama.setText(namaMahasiswa);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        // Aksi ketika tombol "Update" diklik
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mendapatkan nilai nama yang diubah oleh pengguna
                String namaBaru = editTextNama.getText().toString();

                // Periksa apakah nama baru tidak kosong
                if (!namaBaru.isEmpty()) {
                    // Lakukan update data di database
                    boolean isUpdated = databaseHelper.updateMahasiswa(namaMahasiswa, namaBaru);
                    if (isUpdated) {
                        Toast.makeText(UpdateActivity.this, "Data mahasiswa berhasil diperbarui", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(UpdateActivity.this, "Gagal memperbarui data mahasiswa", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Menampilkan pesan kesalahan jika input nama kosong
                    Toast.makeText(UpdateActivity.this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
