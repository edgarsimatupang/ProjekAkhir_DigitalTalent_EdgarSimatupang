package com.example.myprojectt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DataMahasiswaActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ArrayAdapter<String> adapter;
    ArrayList<String> dataMahasiswa;

    private static final int EDIT_REQUEST_CODE = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_mahasiswa);

        // Inisialisasi DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Inisialisasi ListView
        ListView listView = findViewById(R.id.list_view);

        // Mengambil data mahasiswa dari tabel mahasiswa
        dataMahasiswa = databaseHelper.getAllMahasiswa();

        // Inisialisasi adapter untuk ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataMahasiswa);

        // Set adapter ke ListView
        listView.setAdapter(adapter);

        // Menambahkan listener untuk item di ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                // Mendapatkan nama mahasiswa yang dipilih
                final String selectedMahasiswa = dataMahasiswa.get(position);

                // Membuat AlertDialog untuk menampilkan pilihan
                AlertDialog.Builder builder = new AlertDialog.Builder(DataMahasiswaActivity.this);
                builder.setTitle(selectedMahasiswa);
                builder.setItems(new CharSequence[]{"Lihat Data", "Update Data", "Hapus Data"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // Tindakan untuk "Lihat Data"
                                // Mengarahkan ke Detail Activity
                                Intent detailIntent = new Intent(DataMahasiswaActivity.this, DetailActivity.class);
                                startActivity(detailIntent);
                                break;

                            case 1:
                                // Tindakan untuk "Edit Data"
                                // Mengarahkan ke TambahActivity untuk mengedit data
                                Intent editIntent = new Intent(DataMahasiswaActivity.this, UpdateActivity.class);
                                editIntent.putExtra("mode", "edit"); // Menambahkan mode edit
                                editIntent.putExtra("nama_mahasiswa", selectedMahasiswa); // Menambahkan nama mahasiswa yang dipilih
                                startActivityForResult(editIntent, EDIT_REQUEST_CODE);
                                break;

                            case 2:
                                // Tindakan untuk "Hapus Data"
                                // Hapus data dari database
                                boolean isDeleted = databaseHelper.deleteMahasiswa(selectedMahasiswa);
                                if (isDeleted) {
                                    // Hapus data dari ArrayList
                                    dataMahasiswa.remove(position);
                                    // Perbarui tampilan ListView
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(DataMahasiswaActivity.this, "Data mahasiswa berhasil dihapus", Toast.LENGTH_SHORT).show();
                                    recreate();
                                } else {
                                    Toast.makeText(DataMahasiswaActivity.this, "Gagal menghapus data mahasiswa", Toast.LENGTH_SHORT).show();
                                    recreate();
                                }
                                break;

                        }
                    }
                });

                // Menampilkan AlertDialog
                builder.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            // Update data setelah berhasil diupdate di UpdateActivity
            dataMahasiswa = databaseHelper.getAllMahasiswa();
            adapter.clear();
            adapter.addAll(dataMahasiswa);
            adapter.notifyDataSetChanged();
        }
    }


}
