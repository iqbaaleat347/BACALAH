package alarmq.feri.com.perpustakaan.DBHelper;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_RAK_BUKU = "table_rak_buku";

    static final class RAK_BUKU_Columns implements BaseColumns{
        static String NAMA_RAK = "nama";
        static String JUMLAH_BUKU = "jumlahbuku";
    }

    static String TABLE_BUKU = "table_buku";

    static final class BUKU_Columns implements BaseColumns{
        static String JUDUL_BUKU = "judul";
        static String PENGARANG_BUKU = "pengarang";
        static String TAHUN_TERBIT_BUKU = "tahun_terbit";
        static String RAK_ID = "rak_id";
    }
}
