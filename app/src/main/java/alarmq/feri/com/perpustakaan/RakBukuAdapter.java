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

import alarmq.feri.com.perpustakaan.DBHelper.RakBukuHelper;

public class RakBukuAdapter extends RecyclerView.Adapter<RakBukuAdapter.CustomViewHolder>{

    private LayoutInflater mInflater;
    private ArrayList<RakBukuModel> rakbuku;
    private Context context;
    private RakBukuHelper rakBukuHelper;

    public RakBukuAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rakBukuHelper = new RakBukuHelper(context);
    }

    @NonNull
    @Override
    public RakBukuAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.rak_buku_item, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int position) {
        final String nama = rakbuku.get(position).getNama();
        final int jumlah = rakbuku.get(position).getJumlahbuku();
        customViewHolder.editNama.setText(nama);
        customViewHolder.editJumlah.setText(String.valueOf(jumlah));

        customViewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rakbuku.get(position).setNama(customViewHolder.editNama.getText().toString());
                rakbuku.get(position).setJumlahbuku(Integer.parseInt(customViewHolder.editJumlah.getText().toString()));

                rakBukuHelper.open();
                rakBukuHelper.update(rakbuku.get(position));
                rakBukuHelper.close();
                Toast.makeText(context, "updated", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
        customViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteitem(rakbuku.get(position).getId());
                rakbuku.remove(position);
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return rakbuku.size();
    }

    public void addItem(ArrayList<RakBukuModel> mData) {
        this.rakbuku = mData;
        notifyDataSetChanged();
    }

    private void deleteitem(int id) {

        rakBukuHelper.open();
        rakBukuHelper.delete(id);
        rakBukuHelper.close();

        Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private EditText editNama,editJumlah;
        private Button btnUpdate,btnDelete;
        private CardView cv;

        public CustomViewHolder(View itemView) {
            super(itemView);

            editNama = (EditText) itemView.findViewById(R.id.edit_nama_rak);
            editJumlah = (EditText) itemView.findViewById(R.id.edit_jumlah_buku);
            btnUpdate = (Button) itemView.findViewById(R.id.btn_update);
            btnDelete = (Button) itemView.findViewById(R.id.btn_delete);
            cv = (CardView) itemView.findViewById(R.id.cv);
        }

    }
}
