package alarmq.feri.com.perpustakaan.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import java.util.ArrayList;

import alarmq.feri.com.perpustakaan.BukuModel;

import static alarmq.feri.com.perpustakaan.DBHelper.DatabaseContract.RAK_BUKU_Columns.NAMA_RAK;
import static alarmq.feri.com.perpustakaan.DBHelper.DatabaseContract.TABLE_BUKU;
import static alarmq.feri.com.perpustakaan.DBHelper.DatabaseContract.BUKU_Columns.JUDUL_BUKU;
import static alarmq.feri.com.perpustakaan.DBHelper.DatabaseContract.BUKU_Columns.PENGARANG_BUKU;
import static alarmq.feri.com.perpustakaan.DBHelper.DatabaseContract.BUKU_Columns.TAHUN_TERBIT_BUKU;
import static alarmq.feri.com.perpustakaan.DBHelper.DatabaseContract.BUKU_Columns.RAK_ID;
import static alarmq.feri.com.perpustakaan.DBHelper.DatabaseContract.TABLE_RAK_BUKU;
import static android.provider.BaseColumns._ID;

public class BukuHelper {

    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public BukuHelper(Context context) {
        this.context = context;
    }

    public BukuHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<BukuModel> getAllData() {
        database.beginTransaction();
        ArrayList<BukuModel> arrayList = new ArrayList<>();
        try {
            Cursor cursor = database.query(TABLE_BUKU,null,null,null,null,null,_ID+" DESC",null);
            cursor.moveToFirst();
            BukuModel bukuModel;
            if (cursor.getCount() > 0) {
                do {
                    bukuModel = new BukuModel();
                    bukuModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                    bukuModel.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL_BUKU)));
                    bukuModel.setPengarang(cursor.getString(cursor.getColumnIndexOrThrow(PENGARANG_BUKU)));
                    bukuModel.setTahunterbit(cursor.getString(cursor.getColumnIndexOrThrow(TAHUN_TERBIT_BUKU)));
                    bukuModel.setRak_id(cursor.getInt(cursor.getColumnIndexOrThrow(RAK_ID)));
                    arrayList.add(bukuModel);
                    cursor.moveToNext();
                } while (!cursor.isAfterLast());
            }
            cursor.close();

            database.setTransactionSuccessful();;
        }catch (SQLiteException e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }finally {
            database.endTransaction();
        }

        return arrayList;
    }

    public long insert(BukuModel BM){
        ContentValues args = new ContentValues();
        args.put(JUDUL_BUKU,BM.getJudul());
        args.put(PENGARANG_BUKU,BM.getPengarang());
        args.put(TAHUN_TERBIT_BUKU,BM.getTahunterbit());
        args.put(RAK_ID,BM.getRak_id());
        return database.insert(TABLE_BUKU,null,args);
    }

    public long update(BukuModel BM){
        ContentValues args = new ContentValues();
        args.put(JUDUL_BUKU,BM.getJudul());
        args.put(PENGARANG_BUKU,BM.getPengarang());
        args.put(TAHUN_TERBIT_BUKU,BM.getTahunterbit());
        args.put(RAK_ID,BM.getRak_id());
        return database.update(TABLE_BUKU,args,_ID+" = "+BM.getId(),null);
    }

    public long delete(int id){
        return database.delete(TABLE_BUKU,_ID+"="+id,null);
    }
}
