package com.example.myprojectt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button buttonTambahData = findViewById(R.id.btnTambahData);
        Button buttonLihatDataMahasiswa = findViewById(R.id.btnLihatData);
        buttonLihatDataMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menginisialisasi Intent untuk Activity Data Mahasiswa
                Intent intent = new Intent(MainActivity.this, DataMahasiswaActivity.class);
                // Memulai Activity Data Mahasiswa
                startActivity(intent);
            }
        });

        buttonTambahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menginisialisasi Intent untuk Activity Data Mahasiswa
                Intent intent = new Intent(MainActivity.this, TambahActivity.class);
                // Memulai Activity Data Mahasiswa
                startActivity(intent);
            }
        });
    }
}
