package alarmq.feri.com.perpustakaan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import alarmq.feri.com.perpustakaan.DBHelper.BukuHelper;
import alarmq.feri.com.perpustakaan.DBHelper.RakBukuHelper;

public class fragment_insert extends Fragment {

    View v;
    private EditText inNama,inJudul,inPengarang,inTahun;
    private Button btn_rak,btn_buku;
    private Spinner rak_spin;
    private RakBukuHelper rakBukuHelper;
    private BukuHelper bukuHelper;
    private int rak_id;

    public fragment_insert() {
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.insert_fragment,container,false);

        inNama = (EditText) v.findViewById(R.id.in_nama_rak);
        inJudul = (EditText) v.findViewById(R.id.in_judul);
        inPengarang = (EditText) v.findViewById(R.id.in_pengarang);
        inTahun = (EditText) v.findViewById(R.id.in_tahun_terbit);

        btn_rak = (Button) v.findViewById(R.id.input_rak);
        btn_buku = (Button) v.findViewById(R.id.input_buku);

        rak_spin = (Spinner) v.findViewById(R.id.rak_spinner);

        updatespinner();

        btn_buku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _inJudul = inJudul.getText().toString();
                String _inPengarang = inPengarang.getText().toString();
                String _inTahun = inTahun.getText().toString();

                if (_inJudul.equals("")){
                    inJudul.setError("isi kolom judul");
                }else if(_inPengarang.equals("")){
                    inPengarang.setError("isi kolom name");
                }else if(_inTahun.equals("")){
                    inTahun.setError("isi kolom name");
                }else{
                    insertBuku();
                    updatespinner();
                }
            }
        });

        btn_rak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _inNama = inNama.getText().toString();
                if (_inNama.equals("")){
                    inNama.setError("Isi kolom name");
                }else{
                    insertRak();
                    updatespinner();
                }
            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rakBukuHelper = new RakBukuHelper(getActivity());
        bukuHelper = new BukuHelper(getActivity());

    }

    public void insertRak(){
        RakBukuModel RBM = new RakBukuModel(inNama.getText().toString(),0);
        rakBukuHelper.open();
        rakBukuHelper.insert(RBM);
        rakBukuHelper.close();

        Toast.makeText(getActivity(), "Berhasil input", Toast.LENGTH_SHORT).show();
    }

    public void insertBuku(){
        rak_id = getIdRak(rak_spin.getSelectedItem().toString());
        int Jumlah_buku=getJumlahBuku(rak_spin.getSelectedItem().toString());
        BukuModel BM = new BukuModel(inJudul.getText().toString(),inPengarang.getText().toString(),inTahun.getText().toString(),rak_id);
        bukuHelper.open();
        bukuHelper.insert(BM);
        bukuHelper.close();

        rakBukuHelper.open();
        rakBukuHelper.updateJml((Jumlah_buku+1),rak_id);
        rakBukuHelper.close();

        Toast.makeText(getActivity(), "Berhasil input", Toast.LENGTH_SHORT).show();
    }

    private int getIdRak(String nama_rak) {
        rakBukuHelper.open();
        ArrayList <RakBukuModel> rbmlist = rakBukuHelper.getAllData();
        rakBukuHelper.close();
        int id = -99;
        for(RakBukuModel rbm : rbmlist){
            if (rbm.getNama().equalsIgnoreCase(nama_rak)){
                id = rbm.getId();
            }
        }
        return id;
    }

    private int getJumlahBuku(String nama_rak){
        rakBukuHelper.open();
        ArrayList <RakBukuModel> rbmlist = rakBukuHelper.getAllData();
        rakBukuHelper.close();
        int jumlah = -99;
        for(RakBukuModel rbm : rbmlist){
            if (rbm.getNama().equalsIgnoreCase(nama_rak)){
                jumlah = rbm.getJumlahbuku();
            }
        }
        return jumlah;
    }

    public void updatespinner(){
        rakBukuHelper.open();
        List<String> list = rakBukuHelper.getAllNama();
        rakBukuHelper.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        rak_spin.setAdapter(adapter);
    }
}
