package alarmq.feri.com.perpustakaan;

public class BukuModel {

    private int id;
    private String judul;
    private String pengarang;
    private String tahunterbit;
    private int rak_id;
    private String nama_rak;

    public BukuModel(){

    }

    public BukuModel(int id, String judul, String pengarang, String tahunterbit, String nama_rak) {
        this.id = id;
        this.judul = judul;
        this.pengarang = pengarang;
        this.tahunterbit = tahunterbit;
        this.nama_rak = nama_rak;
    }

    public BukuModel(String judul, String pengarang, String tahunterbit, int rak_id) {
        this.judul = judul;
        this.pengarang = pengarang;
        this.tahunterbit = tahunterbit;
        this.rak_id = rak_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public String getTahunterbit() {
        return tahunterbit;
    }

    public void setTahunterbit(String tahunterbit) {
        this.tahunterbit = tahunterbit;
    }

    public int getRak_id() {
        return rak_id;
    }

    public void setRak_id(int rak_id) {
        this.rak_id = rak_id;
    }

    public String getNama_rak() {
        return nama_rak;
    }

    public void setNama_rak(String nama_rak) {
        this.nama_rak = nama_rak;
    }
}
