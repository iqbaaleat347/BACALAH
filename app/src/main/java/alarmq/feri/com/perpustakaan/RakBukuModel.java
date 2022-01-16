package alarmq.feri.com.perpustakaan;

public class RakBukuModel {

    private int id;
    private String nama;
    private int jumlahbuku;

    public RakBukuModel() {
    }

    public RakBukuModel(String nama, int jumlahbuku) {
        this.nama = nama;
        this.jumlahbuku = jumlahbuku;
    }

    public RakBukuModel(int id, String nama, int jumlahbuku) {
        this.id = id;
        this.nama = nama;
        this.jumlahbuku = jumlahbuku;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getJumlahbuku() {
        return jumlahbuku;
    }

    public void setJumlahbuku(int jumlahbuku) {
        this.jumlahbuku = jumlahbuku;
    }
}
