package alarmq.feri.com.perpustakaan.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import java.util.ArrayList;

import alarmq.feri.com.perpustakaan.RakBukuModel;

import static alarmq.feri.com.perpustakaan.DBHelper.DatabaseContract.TABLE_BUKU;
import static alarmq.feri.com.perpustakaan.DBHelper.DatabaseContract.TABLE_RAK_BUKU;
import static alarmq.feri.com.perpustakaan.DBHelper.DatabaseContract.RAK_BUKU_Columns.JUMLAH_BUKU;
import static alarmq.feri.com.perpustakaan.DBHelper.DatabaseContract.RAK_BUKU_Columns.NAMA_RAK;
import static android.provider.BaseColumns._ID;

public class RakBukuHelper {

    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public RakBukuHelper(Context context) {
        this.context = context;
    }

    public RakBukuHelper open() throws SQLException{
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<RakBukuModel> getAllData() {
        database.beginTransaction();
        ArrayList<RakBukuModel> arrayList = new ArrayList<>();
        try {
            Cursor cursor = database.query(TABLE_RAK_BUKU, null, null, null, null, null, _ID + " DESC", null);
            cursor.moveToFirst();
            RakBukuModel rakBukuModel;
            if (cursor.getCount() > 0) {
                do {
                    rakBukuModel = new RakBukuModel();
                    rakBukuModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                    rakBukuModel.setNama(cursor.getString(cursor.getColumnIndexOrThrow(NAMA_RAK)));
                    rakBukuModel.setJumlahbuku(cursor.getInt(cursor.getColumnIndexOrThrow(JUMLAH_BUKU)));
                    arrayList.add(rakBukuModel);
                    cursor.moveToNext();
                } while (!cursor.isAfterLast());
            }
            cursor.close();
            database.setTransactionSuccessful();
        }catch (SQLiteException e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }finally {
            database.endTransaction();
        }
        return arrayList;
    }

    public ArrayList<String> getAllNama() {
        Cursor cursor = database.query(TABLE_RAK_BUKU, null, null, null, null, null, _ID + " DESC", null);
        cursor.moveToFirst();
        ArrayList<String> arrayList = new ArrayList<>();
        if (cursor.getCount() > 0) {
            do {
                arrayList.add(cursor.getString(cursor.getColumnIndexOrThrow(NAMA_RAK)));
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(RakBukuModel RBM){
        ContentValues args = new ContentValues();
        args.put(JUMLAH_BUKU,RBM.getJumlahbuku());
        args.put(NAMA_RAK,RBM.getNama());
        return database.insert(TABLE_RAK_BUKU,null,args);
    }

    public long update(RakBukuModel RBM){
        ContentValues args = new ContentValues();
        args.put(JUMLAH_BUKU,RBM.getJumlahbuku());
        args.put(NAMA_RAK,RBM.getNama());
        return database.update(TABLE_RAK_BUKU,args,_ID+" = "+RBM.getId(),null);
    }

    public long updateJml(int jumlah,int id){
        ContentValues args = new ContentValues();
        args.put(JUMLAH_BUKU,jumlah);
        return database.update(TABLE_RAK_BUKU,args,_ID+"="+id,null);
    }

    public long updateJml(int jumlah,String nama){
        ContentValues args = new ContentValues();
        args.put(JUMLAH_BUKU,jumlah);
        return database.update(TABLE_RAK_BUKU,args,NAMA_RAK+"= '"+nama+"'",null);
    }

    public long delete(int id){
        return database.delete(TABLE_RAK_BUKU,_ID+"="+id,null);
    }
}
