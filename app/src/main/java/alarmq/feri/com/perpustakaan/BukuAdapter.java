package alarmq.feri.com.perpustakaan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import alarmq.feri.com.perpustakaan.DBHelper.BukuHelper;
import alarmq.feri.com.perpustakaan.DBHelper.RakBukuHelper;

public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.CustomViewHolder> {


    private BukuHelper bukuHelper;
    private Context context;
    private ArrayList<BukuModel> bukuModel;
    private LayoutInflater mInflater;
    private RakBukuHelper rakBukuHelper;

    public BukuAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        bukuHelper = new BukuHelper(context);
        rakBukuHelper = new RakBukuHelper(context);
    }

    @NonNull
    @Override
    public BukuAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.buku_item, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder,final int position) {
        final String judul = bukuModel.get(position).getJudul();
        final String pengarang = bukuModel.get(position).getPengarang();
        final String tahunterbit = bukuModel.get(position).getTahunterbit();
        final int idRak = bukuModel.get(position).getRak_id();
        customViewHolder.editJudul.setText(judul);
        customViewHolder.editPengarang.setText(pengarang);
        customViewHolder.editTahun.setText(tahunterbit);
        customViewHolder.EditRak.setText(getNamaRak(idRak));

        customViewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bukuModel.get(position).setJudul(customViewHolder.editJudul.getText().toString());
                bukuModel.get(position).setPengarang(customViewHolder.editPengarang.getText().toString());
                bukuModel.get(position).setTahunterbit(customViewHolder.editTahun.getText().toString());
                bukuModel.get(position).setRak_id(idRak);

                bukuHelper.open();
                bukuHelper.update(bukuModel.get(position));
                bukuHelper.close();
                Toast.makeText(context, "updated", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
        customViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int jumlahbuku=getJumlahBuku(idRak);
                deleteitem(bukuModel.get(position).getId());

                rakBukuHelper.open();
                rakBukuHelper.updateJml((jumlahbuku-1),idRak);
                rakBukuHelper.close();

                bukuModel.remove(position);
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return bukuModel.size();
    }

    private void deleteitem(int id) {

        bukuHelper.open();
        bukuHelper.delete(id);
        bukuHelper.close();

        Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();

    }

    private String getNamaRak(int idrak) {
        rakBukuHelper.open();
        ArrayList <RakBukuModel> rbmlist = rakBukuHelper.getAllData();
        rakBukuHelper.close();
        String nama = "";
        for(RakBukuModel rbm : rbmlist){
            if (rbm.getId()==(idrak)){
                nama = rbm.getNama();
            }
        }
        return nama;
    }

    public void addItem(ArrayList<BukuModel> mData) {
        this.bukuModel = mData;
        notifyDataSetChanged();
    }

    private int getJumlahBuku(int idRak){
        rakBukuHelper.open();
        ArrayList <RakBukuModel> rbmlist = rakBukuHelper.getAllData();
        rakBukuHelper.close();
        int jumlah = -99;
        for(RakBukuModel rbm : rbmlist){
            if (rbm.getId()==idRak){
                jumlah = rbm.getJumlahbuku();
            }
        }
        return jumlah;
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private EditText editJudul,editPengarang,editTahun,EditRak;
        private Button btnUpdate,btnDelete;
        private CardView cv;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            editJudul = (EditText) itemView.findViewById(R.id.edit_judul);
            editPengarang = (EditText) itemView.findViewById(R.id.edit_pengarang);
            editTahun = (EditText) itemView.findViewById(R.id.edit_tahun_terbit);
            EditRak = (EditText) itemView.findViewById(R.id.edit_rak);
            btnUpdate = (Button) itemView.findViewById(R.id.btn_update);
            btnDelete = (Button) itemView.findViewById(R.id.btn_delete);
            cv = (CardView) itemView.findViewById(R.id.cv);
        }
    }
}
