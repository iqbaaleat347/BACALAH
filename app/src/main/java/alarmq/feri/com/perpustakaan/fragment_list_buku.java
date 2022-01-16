package alarmq.feri.com.perpustakaan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import alarmq.feri.com.perpustakaan.DBHelper.BukuHelper;

public class fragment_list_buku extends Fragment {

    View v;
    private RecyclerView recyclerView;
    private BukuAdapter bukuAdapter;
    private BukuHelper bukuHelper;
    private EditText search;

    public fragment_list_buku() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.list_buku_fragment,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_buku);
        bukuAdapter = new BukuAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getAllData();
        search = (EditText) v.findViewById(R.id.edit_search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()!=0){
                    getSearchData(search.getText().toString());
                }else{
                    getAllData();
                }
            }
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bukuHelper = new BukuHelper(getActivity());

    }

    public void getSearchData(String s){
        bukuHelper.open();
        // Ambil semua data mahasiswa di database
        ArrayList<BukuModel> mahasiswaModels = bukuHelper.getAllData();
        bukuHelper.close();
        ArrayList<BukuModel> _mahasiswaModels= new ArrayList<BukuModel>();
        for(BukuModel data:mahasiswaModels){
            if (data.getJudul().contains(s)){
                _mahasiswaModels.add(data);
            }
        }
        bukuAdapter.addItem(_mahasiswaModels);
        recyclerView.setAdapter(bukuAdapter);
    }

    public void getAllData() {
        bukuHelper.open();
        // Ambil semua data mahasiswa di database
        ArrayList<BukuModel> bukuModels = bukuHelper.getAllData();
        bukuHelper.close();
        bukuAdapter.addItem(bukuModels);
        recyclerView.setAdapter(bukuAdapter);
    }


}
