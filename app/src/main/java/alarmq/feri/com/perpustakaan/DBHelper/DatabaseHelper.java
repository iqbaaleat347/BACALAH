package alarmq.feri.com.perpustakaan.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.provider.BaseColumns._ID;
import static alarmq.feri.com.perpustakaan.DBHelper.DatabaseContract.TABLE_RAK_BUKU;
import static alarmq.feri.com.perpustakaan.DBHelper.DatabaseContract.TABLE_BUKU;
import static alarmq.feri.com.perpustakaan.DBHelper.DatabaseContract.RAK_BUKU_Columns.JUMLAH_BUKU;
import static alarmq.feri.com.perpustakaan.DBHelper.DatabaseContract.RAK_BUKU_Columns.NAMA_RAK;
import static alarmq.feri.com.perpustakaan.DBHelper.DatabaseContract.BUKU_Columns.JUDUL_BUKU;
import static alarmq.feri.com.perpustakaan.DBHelper.DatabaseContract.BUKU_Columns.PENGARANG_BUKU;
import static alarmq.feri.com.perpustakaan.DBHelper.DatabaseContract.BUKU_Columns.TAHUN_TERBIT_BUKU;
import static alarmq.feri.com.perpustakaan.DBHelper.DatabaseContract.BUKU_Columns.RAK_ID;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static String DATABASE_NAME = "dbperpustakaan";
    public static String CREATE_TABLE_RAK_BUKU = "create table " + TABLE_RAK_BUKU +
            " (" + _ID + " integer primary key autoincrement, " +
            NAMA_RAK + " text unique not null, " +
            JUMLAH_BUKU + " integer);";
    public static String CREATE_TABLE_BUKU = "create table " + TABLE_BUKU +
            " (" + _ID + " integer primary key autoincrement, " +
            JUDUL_BUKU + " text not null, " +
            PENGARANG_BUKU + " text not null, " +
            TAHUN_TERBIT_BUKU + " text not null, " +
            RAK_ID + " integer not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RAK_BUKU);
        db.execSQL(CREATE_TABLE_BUKU);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RAK_BUKU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUKU);
        onCreate(db);
    }
}
