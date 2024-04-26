package com.example.myprojectt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "SignLog.db";
    private static DatabaseHelper instance;

    public DatabaseHelper(Context context) {
        super(context, "SignLog.db", null, 1);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        // Buat tabel pengguna (users)
        MyDatabase.execSQL("create Table users(email TEXT primary key, password TEXT)");

        // Buat tabel mahasiswa
        MyDatabase.execSQL("create Table mahasiswa(id INTEGER primary key AUTOINCREMENT, nama_mahasiswa TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        // Hapus tabel pengguna jika ada perubahan skema
        MyDB.execSQL("drop Table if exists users");

        // Hapus tabel mahasiswa jika ada perubahan skema
        MyDB.execSQL("drop Table if exists mahasiswa");

        // Buat kembali tabel baru
        onCreate(MyDB);
    }

    public Boolean insertData(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("users", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ?", new String[]{email});

        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ? and password = ?", new String[]{email, password});

        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
    public boolean updateMahasiswa(String namaLama, String namaBaru) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama_mahasiswa", namaBaru);
        int rowsAffected = db.update("mahasiswa", contentValues, "nama_mahasiswa=?", new String[]{namaLama});
        return rowsAffected > 0;
    }

    public boolean insertMahasiswa(String nama) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama_mahasiswa", nama);
        long result = MyDatabase.insert("mahasiswa", null, contentValues);
        return result != -1; // Jika hasil penyisipan tidak -1, maka data berhasil disisipkan
    }

    public ArrayList<String> getAllMahasiswa() {
        ArrayList<String> mahasiswaList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nama_mahasiswa FROM mahasiswa", null);

        if (cursor.moveToFirst()) {
            do {
                mahasiswaList.add(cursor.getString(0)); // Ambil nama mahasiswa dari indeks kolom pertama
            } while (cursor.moveToNext());
        }

        cursor.close();
        return mahasiswaList;
    }
    public boolean deleteMahasiswa(String namaMahasiswa) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("mahasiswa", "nama_mahasiswa=?", new String[]{namaMahasiswa}) > 0;
    }





}
